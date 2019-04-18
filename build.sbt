import java.nio.file.StandardCopyOption

import org.scalajs.sbtplugin.ScalaJSPlugin.AutoImport.fastOptJS

resolvers += Resolver.bintrayRepo("oyvindberg", "ScalablyTyped")


enablePlugins(ScalaJSPlugin)
enablePlugins(ScalaJSBundlerPlugin)

name := "Scala.js Tutorial"
scalaVersion := "2.12.6" // or any other Scala version >= 2.10.2

// This is an application with a main method
//scalaJSUseMainModuleInitializer := true

webpackBundlingMode := BundlingMode.LibraryAndApplication()

libraryDependencies ++= Seq(ScalablyTyped.P.`plotly_dot_js`)

npmDependencies in Compile ++= Seq("plotly.js" -> "1.47.2")

libraryDependencies += "com.thoughtworks.binding" %%% "dom" % "latest.release"

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

lazy val copyTask = taskKey[Unit]("copyJS")

copyTask := {
  val bundle = (Compile / fastOptJS / webpack).value.head
  
  val destinationPath = file(s"docs/_includes/${bundle.data.name}").toPath
  
  java.nio.file.Files.copy(bundle.data.toPath, destinationPath, StandardCopyOption.REPLACE_EXISTING)
}
