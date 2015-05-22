name := """banking-ua-full"""

version := "1.0"

scalaVersion := "2.11.6"

val akkaVersion = "2.3.11"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor"   % akkaVersion,
  "com.typesafe.akka" %% "akka-remote"  % akkaVersion,
  "com.typesafe.akka" %% "akka-slf4j"   % akkaVersion,
  "ch.qos.logback"    %  "logback-classic" % "1.1.3",
  "joda-time"         % "joda-time"     % "2.7"
)

// Test dependencies
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
  "org.scalatest"     %% "scalatest"    % "2.2.4"     % "test",
  "org.mockito"       %  "mockito-all"  % "1.10.19"   % "test")


fork in run := true

connectInput in run := true