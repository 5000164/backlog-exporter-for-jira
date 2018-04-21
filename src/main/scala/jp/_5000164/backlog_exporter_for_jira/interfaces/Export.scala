package jp._5000164.backlog_exporter_for_jira.interfaces

import java.text.SimpleDateFormat
import java.util.Date

import io.circe.generic.auto._
import io.circe.syntax._
import io.circe.{Encoder, Json}
import jp._5000164.backlog_exporter_for_jira.domain.Jira

object Export {
  def json(jira: Jira): String = {
    implicit val dateEncoder: Encoder[Date] = (a: Date) => Json.fromString(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZ").format(a))
    jira.asJson.noSpaces
  }
}
