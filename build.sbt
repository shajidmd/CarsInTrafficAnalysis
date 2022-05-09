import sbt._

name := "aips"
version := "0.1"
scalaVersion := "2.12.8"

lazy val root = (project in file("."))
  .settings(name := "aips")
  .settings(
    inThisBuild(
      Seq(
        organization := "com.aips.traffic.signal",
        scalaVersion := "2.12.8",
        scalacOptions ++= Seq(
          "-feature",
          "-deprecation",
          "-unchecked",
          "-Xcheckinit",
          "-Xlint",
          "-Xverify",
          "-Yno-adapted-args",
          "-encoding", "utf8"
        ),
        Test / coverageEnabled := true,
        Compile / coverageEnabled := false
      )
    )
  )
  .settings(Test / parallelExecution := false)
  .settings(libraryDependencies ++= (Dependencies.compile ++ Dependencies.test))
  .settings(
    // Assembly settings
    buildInfoPackage := "com.aips.traffic.signal",
    assembly / test  := {},
    assembly / assemblyJarName  := name.value + "-assembly.jar"
  )
