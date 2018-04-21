package jp._5000164.backlog_exporter_for_jira.interfaces

import com.nulabinc.backlog4j.conf.{BacklogConfigure, BacklogJpConfigure}
import com.nulabinc.backlog4j.{BacklogClient, BacklogClientFactory, Issue, IssueComment}

import scala.collection.JavaConverters._

class Backlog(val spaceId: String, val apiKey: String) {
  val configure: BacklogConfigure = new BacklogJpConfigure(spaceId).apiKey(apiKey)
  val client: BacklogClient = new BacklogClientFactory(configure).newClient()

  def fetchIssues(projectKey: String): Seq[(Issue, Seq[IssueComment])] = {
    val issueKey = s"$projectKey-1"
    val issue = client.getIssue(issueKey)
    val comments = client.getIssueComments(issueKey).asScala
    Seq((issue, comments))
  }
}
