assembly / assemblyJarName := "euler-problems.jar"

name := "euler-problems"

assembly / target  := baseDirectory.value

scalaVersion := "2.13.5"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.5" % Test,
  "org.scalacheck" %% "scalacheck" % "1.14.1" % Test
)
