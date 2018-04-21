package jp._5000164.backlog_exporter_for_jira.domain

import java.util.Date

case class Jira(
                 projects: Seq[Project]
               )

case class Project(
                    name: String,
                    key: String,
                    `type`: String,
                    issues: Seq[Issue]
                  )

case class Issue(
                  status: String,
                  summary: String,
                  comments: Seq[Comment]
                )

case class Comment(
                    body: String,
                    created: Date
                  )
