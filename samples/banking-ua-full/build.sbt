import com.typesafe.sbt.SbtAspectj._

name := """banking-ua-full"""

version := "1.0"

scalaVersion := "2.11.6"

val akkaVersion = "2.3.11"
val kamonVersion = "0.4.0"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor"   % akkaVersion,
  "com.typesafe.akka" %% "akka-remote"  % akkaVersion,
  "com.typesafe.akka" %% "akka-slf4j"   % akkaVersion,
  "ch.qos.logback"    %  "logback-classic" % "1.1.3",
  "joda-time"         %  "joda-time"    % "2.7",
  "net.databinder.dispatch" %% "dispatch-core"  % "0.11.2",
  "io.kamon"          %% "kamon-core"           % kamonVersion,
  "io.kamon"          %% "kamon-datadog"        % kamonVersion,
  "io.kamon"          %% "kamon-akka"           % kamonVersion,
  "io.kamon"          %% "kamon-akka-remote"    % kamonVersion,
  "io.kamon"          %% "kamon-log-reporter"   % kamonVersion,
  "org.aspectj" % "aspectjweaver" % "1.8.1"
)

// Test dependencies
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
  "org.scalatest"     %% "scalatest"    % "2.2.4"     % "test",
  "org.mockito"       %  "mockito-all"  % "1.10.19"   % "test")

aspectjSettings

javaOptions <++= AspectjKeys.weaverOptions in Aspectj

fork in run := true

connectInput in run := true