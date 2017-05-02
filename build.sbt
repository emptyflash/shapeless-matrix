import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "io.github.emptyflash",
      scalaVersion := "2.11.10",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "shapeless-matrix",
    libraryDependencies ++= Seq(
      "com.chuusai" %% "shapeless" % "2.3.2",
      "com.github.alexarchambault" %% "shapeless-refined-std" % "0.1.0",
      scalaTest % Test
    ),
    resolvers ++= Seq(
      Resolver.sonatypeRepo("releases"),
      Resolver.sonatypeRepo("snapshots")
    )
  )
