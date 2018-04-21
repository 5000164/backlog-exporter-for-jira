package jp._5000164.backlog_exporter_for_jira.infractructure

import scala.io.Source
import scala.reflect.runtime.{currentMirror, universe}
import scala.tools.reflect.ToolBox

object Settings {
  val toolbox: ToolBox[universe.type] = currentMirror.mkToolBox()
  val settings: SettingsType = toolbox.eval(toolbox.parse(Source.fromResource("Settings.scala").mkString)).asInstanceOf[SettingsType]
}

trait SettingsType {
  type ProjectKey = String
  val targets: Seq[ProjectKey]
}
