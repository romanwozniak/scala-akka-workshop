name := """exchange-rates"""

version := "1.0"

scalaVersion := "2.11.6"

val akkaVersion = "2.3.11"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor"   % akkaVersion,
  "com.typesafe.akka" %% "akka-remote"  % akkaVersion,
  "com.typesafe.akka" %% "akka-slf4j"   % akkaVersion,
  "com.typesafe.play" %% "play-json"    % "2.3.9",
  "ch.qos.logback"    %  "logback-classic" % "1.1.3",
  "net.databinder.dispatch" %% "dispatch-core" % "0.11.2")


fork in run := true