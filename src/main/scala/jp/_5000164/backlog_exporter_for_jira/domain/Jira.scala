package jp._5000164.backlog_exporter_for_jira.domain

import java.text.SimpleDateFormat
import java.util.Date

import io.circe.generic.auto._
import io.circe.syntax._
import io.circe.{Encoder, Json}

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
                  description: String,
                  status: String,
                  summary: String,
                  comments: Seq[Comment]
                )

case class Comment(
                    body: String,
                    created: Date
                  )

object Jira {
  val statusMapping = Map(
    "未対応" -> "Open",
    "処理中" -> "In Progress",
    "処理済み" -> "Resolved",
    "完了" -> "Closed"
  )

  def toJson(jira: Jira): String = {
    implicit val dateEncoder: Encoder[Date] = (a: Date) => Json.fromString(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZ").format(a))
    jira.asJson.noSpaces
  }
}
