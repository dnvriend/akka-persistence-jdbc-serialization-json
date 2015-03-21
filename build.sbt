import bintray.Plugin._

seq(bintraySettings:_*)

organization := "com.github.dnvriend"

name := "akka-persistence-jdbc-serialization-json"

version := "1.0.0"

scalaVersion := "2.11.6"

crossScalaVersions := Seq("2.10.5", "2.11.6")

resolvers += "dnvriend at bintray" at "http://dl.bintray.com/dnvriend/maven"

libraryDependencies ++= {
    val akkaVersion = "2.3.9"
    Seq(
    "com.typesafe.akka"   %% "akka-actor"                    % akkaVersion,
    "com.typesafe.akka"   %% "akka-persistence-experimental" % akkaVersion,
    "com.github.dnvriend" %% "akka-persistence-jdbc"         % "1.1.2",
    "io.spray"            %% "spray-json"                    % "1.3.1",
    "com.typesafe.akka"   %% "akka-testkit"                  % akkaVersion      % Test,
    "org.scalatest"       %% "scalatest"                     % "2.2.4"          % Test
  )
}

autoCompilerPlugins := true

parallelExecution in Test := false

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

publishMavenStyle := true

net.virtualvoid.sbt.graph.Plugin.graphSettings

licenses += ("Apache-2.0", url("http://opensource.org/licenses/apache2.0.php"))

bintray.Keys.packageLabels in bintray.Keys.bintray := Seq("akka", "jdbc", "persistence")

bintray.Keys.packageAttributes in bintray.Keys.bintray ~=
  ((_: bintray.AttrMap) ++ Map("website_url" -> Seq(bintry.StringAttr("https://github.com/dnvriend/akka-persistence-jdbc")), "github_repo" -> Seq(bintry.StringAttr("https://github.com/dnvriend/akka-persistence-jdbc.git")), "issue_tracker_url" -> Seq(bintry.StringAttr("https://github.com/dnvriend/akka-persistence-jdbc/issues/"))))
