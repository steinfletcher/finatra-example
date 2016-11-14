name := "dy"

version := "1.0"

scalaVersion := "2.11.8"

fork in run := true

javaOptions ++= Seq(
  "-Dlog.service.output=/dev/stderr",
  "-Dlog.access.output=/dev/stderr")

(testOptions in Test) += Tests.Argument("-h", "target/html-test-report", "-o")

lazy val versions = new {
  val finatra = "2.5.0"
  val guice = "4.0"
  val logback = "1.1.7"
  val mockito = "1.9.5"
  val scalatest = "2.2.6"
  val specs2 = "2.3.12"
  val jackson = "2.6.5"
  val slick = "3.1.1"
  val postgres = "9.4-1200-jdbc41"
  val typesafeConfig = "1.3.0"
  val flyway = "4.0.3"
  val scalikejdbc = "2.5.0"
}

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  "Twitter Maven" at "https://maven.twttr.com"
)

libraryDependencies ++= Seq(
  "com.twitter" %% "finatra-http" % versions.finatra,
  "com.twitter" %% "finatra-httpclient" % versions.finatra,
  "ch.qos.logback" % "logback-classic" % versions.logback,
  "org.scalikejdbc" %% "scalikejdbc" % versions.scalikejdbc,
  "org.scalikejdbc" %% "scalikejdbc-config" % versions.scalikejdbc,
  "org.scalikejdbc" %% "scalikejdbc-test" % versions.scalikejdbc,
  "org.scalikejdbc" %% "scalikejdbc-jsr310" % versions.scalikejdbc,
  "org.postgresql" % "postgresql" % versions.postgres,
  "com.typesafe" % "config" % versions.typesafeConfig,
  "org.flywaydb" % "flyway-core" % versions.flyway,
  "com.fasterxml.jackson.datatype" % "jackson-datatype-jsr310" % versions.jackson,

  "com.twitter" %% "finatra-http" % versions.finatra % "test",
  "com.twitter" %% "finatra-jackson" % versions.finatra % "test",
  "com.twitter" %% "inject-server" % versions.finatra % "test",
  "com.twitter" %% "inject-app" % versions.finatra % "test",
  "com.twitter" %% "inject-core" % versions.finatra % "test",
  "com.twitter" %% "inject-modules" % versions.finatra % "test",
  "com.google.inject.extensions" % "guice-testlib" % versions.guice % "test",
  "com.h2database" % "h2" % "1.4.193" % "test",
  "io.gatling" %% "jsonpath" % "0.6.8" % "test",

  "com.twitter" %% "finatra-http" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "finatra-jackson" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-server" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-app" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-core" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-modules" % versions.finatra % "test" classifier "tests",

  "org.mockito" % "mockito-core" % versions.mockito % "test",
  "org.scalatest" %% "scalatest" % versions.scalatest % "test",
  "org.specs2" %% "specs2" % versions.specs2 % "test"
)

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}