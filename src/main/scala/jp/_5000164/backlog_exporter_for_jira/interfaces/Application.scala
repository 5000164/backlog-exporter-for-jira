package jp._5000164.backlog_exporter_for_jira.interfaces

import jp._5000164.backlog_exporter_for_jira.infractructure.Settings

object Application extends App {
  val backlog = new Backlog(sys.env("BACKLOG_SPACE_ID"), sys.env("BACKLOG_API_KEY"))
  val exportContent = Settings.settings.targets.map(backlog.fetchIssues)
  println(exportContent)
}
