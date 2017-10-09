name := "akka-http"

version := "1.0"

scalaVersion := "2.12.3"

libraryDependencies ++= {
  val akkaVersion = "2.5.6"
  val akkaHttpVersion = "10.0.10"
  val scalaTestVersion = "3.0.1"
  val scalaMockV = "3.5.0"
  val slickVersion = "3.2.1"
  Seq(
    "com.typesafe.akka"  %% "akka-actor"                  % akkaVersion,
    "com.typesafe.akka"  %% "akka-stream"                 % akkaVersion,

    "com.typesafe.akka"  %% "akka-http"                   % akkaHttpVersion,
    "com.typesafe.akka"  %% "akka-http-core"              % akkaHttpVersion,
    "com.typesafe.akka"  %% "akka-http-spray-json"        % akkaHttpVersion,
    "com.typesafe.akka"  %% "akka-http-testkit"           % akkaHttpVersion % Test,

    "com.typesafe.slick" %% "slick"                       % slickVersion,
    "com.typesafe.slick" %% "slick-hikaricp"              % slickVersion,
    "org.slf4j"           % "slf4j-nop"                   % "1.7.25",
    "org.postgresql"      % "postgresql"                  % "42.1.4",
    "org.flywaydb"        % "flyway-core"                 % "3.2.1",

    "org.scalatest"      %% "scalatest"                   % scalaTestVersion % Test,
    "org.scalamock"      %% "scalamock-scalatest-support" % scalaMockV % Test
  )
}

resolvers += "Typesafe" at "https://repo.typesafe.com/typesafe/releases/"

Revolver.settings
enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)

dockerExposedPorts := Seq(9000)
dockerEntrypoint := Seq("bin/%s" format executableScriptName.value, "-Dconfig.resource=docker.conf")
