package jp._5000164.backlog_exporter_for_jira.interfaces

import com.nulabinc.backlog4j.conf.{BacklogConfigure, BacklogJpConfigure}
import com.nulabinc.backlog4j.{BacklogClient, BacklogClientFactory, Issue, IssueComment}
import jp._5000164.backlog_exporter_for_jira.domain.{Jira, Project, Comment => JiraComment, Issue => JiraIssue}

import scala.collection.JavaConverters._

class Backlog(val spaceId: String, val apiKey: String) {
  val configure: BacklogConfigure = new BacklogJpConfigure(spaceId).apiKey(apiKey)
  val client: BacklogClient = new BacklogClientFactory(configure).newClient()

  def fetchIssues(projectKey: String): Project = {
    val issueKey = s"$projectKey-1"
    val issue = client.getIssue(issueKey)
    val comments = client.getIssueComments(issueKey).asScala
    transform(issue, comments)
  }

  def transform(backlogIssue: Issue, backlogComments: Seq[IssueComment]): Project = {
    val comments = backlogComments.map(backlogComment => {
      val changes = backlogComment.getChangeLog.asScala
      val formattedChanges = changes.flatMap(change => Some(s"${change.getField}: ${change.getOriginalValue} -> ${change.getNewValue}"))
      val changeMessage = if (formattedChanges.nonEmpty) formattedChanges.mkString("\n") + "\n" else ""
      val content = Option(backlogComment.getContent).getOrElse("")
      val body = changeMessage + content
      JiraComment(body, backlogComment.getCreated)
    })
    val issue = JiraIssue(
      backlogIssue.getDescription,
      Jira.statusMapping(backlogIssue.getStatus.getName),
      backlogIssue.getSummary,
      comments
    )
    Project("Sample data", "SAM", "software", Seq(issue))
  }
}
