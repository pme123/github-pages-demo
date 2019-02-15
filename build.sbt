import java.nio.file.StandardCopyOption

import org.scalajs.sbtplugin.ScalaJSPlugin.AutoImport.fastOptJS

enablePlugins(ScalaJSPlugin)

name := "Scala.js Tutorial"
scalaVersion := "2.12.6" // or any other Scala version >= 2.10.2

// This is an application with a main method
//scalaJSUseMainModuleInitializer := true

libraryDependencies += "com.thoughtworks.binding" %%% "dom" % "latest.release"

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

lazy val copyTask = taskKey[Unit]("copyJS")

copyTask := {
  val r = (Compile / fastOptJS).value
  val destinationPath = file("docs/_includes/scala-js-tutorial-fastopt.js").toPath
  java.nio.file.Files.copy(r.data.toPath, destinationPath, StandardCopyOption.REPLACE_EXISTING)
}

