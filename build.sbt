assembly / assemblyJarName := "euler-problems.jar"
assembly / mainClass := Some("com.keshav.euler.Euler")
assembly / test := {}

name := "euler-problems"

scalaVersion := "2.13.6"

scalacOptions ++= Seq("-feature", "-language:postfixOps,higherKinds,implicitConversions", "-unchecked", "-deprecation")

assembly / assemblyMergeStrategy := {
  case PathList("javax", "servlet", xs @ _*)         => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".html" => MergeStrategy.first
  case "application.conf"                            => MergeStrategy.concat
  case "unwanted.txt"                                => MergeStrategy.discard
  case x =>
    val oldStrategy = (assembly / assemblyMergeStrategy).value
    oldStrategy(x)
}

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.2",
  "org.scalatest" %% "scalatest" % "3.2.5" % Test,
  "org.scalacheck" %% "scalacheck" % "1.14.1" % Test
)
