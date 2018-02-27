assemblyJarName in assembly := "euler-problems.jar"

name := "euler-problems"

target in assembly := baseDirectory.value

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.3" % "test"
)
