import AssemblyKeys._

name := "predictionio-process-commons-evaluations-hadoop-scalding"

packageOptions += Package.ManifestAttributes(java.util.jar.Attributes.Name.MAIN_CLASS -> "com.twitter.scalding.Tool")

parallelExecution in Test := false

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2" % "1.14" % "test",
  "org.slf4j" % "slf4j-log4j12" % "1.6.6")

resolvers ++= Seq("Concurrent Maven Repo" at "http://conjars.org/repo")

assemblySettings

test in assembly := {}

assembleArtifact in packageScala := true

excludedJars in assembly <<= (fullClasspath in assembly) map { cp =>
  val excludes = Set(
    "jsp-api-2.1-6.1.14.jar",
    "jsp-2.1-6.1.14.jar",
    "jasper-compiler-5.5.12.jar",
    "janino-2.5.16.jar",
    "minlog-1.2.jar",
    "hadoop-core-1.0.4.jar")
  cp filter { jar => excludes(jar.data.getName)}
}

// Some of these files have duplicates, let's ignore:
mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
  {
    case s if s.endsWith(".class") => MergeStrategy.last
    case s if s.endsWith("project.clj") => MergeStrategy.concat
    case s if s.endsWith(".html") => MergeStrategy.last
    case s if s.endsWith(".properties") => MergeStrategy.last
    case s if s.endsWith(".xml") => MergeStrategy.last
    case x => old(x)
  }
}
