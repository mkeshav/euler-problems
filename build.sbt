assemblyJarName in assembly := "euler-problems.jar"

name := "euler-problems"

target in assembly := baseDirectory.value

scalaVersion := "2.13.5"
scalacOptions += "-language:postfixOps"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.5" % Test,
  "org.scalacheck" %% "scalacheck" % "1.14.0" % Test
)
