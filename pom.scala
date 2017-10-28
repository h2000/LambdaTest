import org.sonatype.maven.polyglot.scala.model._
import scala.collection.immutable.Seq

val namespace = "de.tobiasroeser.lambdatest"
val lambdatest = "de.tototec" % namespace % "0.3.0-SNAPSHOT"
val testng = "org.testng" % "testng" % "6.9.10"
val junit = "junit" % "junit" % "4.11"

object Plugins {
  val retrolambda = "net.orfjackal.retrolambda" % "retrolambda-maven-plugin" % "1.8.0"
  val jar = "org.apache.maven.plugins" % "maven-jar-plugin" % "2.5"
  val bundle = "org.apache.felix" % "maven-bundle-plugin" % "3.3.0"
}

Model(
  lambdatest,
  name = "Poor Mans Lambda Test",
  description = "Minimal Java8 Lambda enabled testing for TestNG",
  url = "https://github.com/lefou/LambdaTest",
  prerequisites = Prerequisites(
    maven = "3.1"
  ),
  developers = Seq(
    Developer(
      email = "le.petit.fou@web.de",
      name = "Tobias Roeser"
    )
  ),
  licenses = Seq(
    License(
      name = "Apache License, Version 2.0",
      url = "http://www.apache.org/licenses/LICENSE-2.0.txt",
      distribution = "repo"
    )
  ),
  scm = Scm(
    url = "https://github.com/lefou/LambdaTest"
  ),
  dependencies = Seq(
    testng % "provided",
    junit % "provided"
  ),
  properties = Map(
    "project.build.sourceEncoding" -> "UTF-8",
    "maven.compiler.source" -> "1.8",
    "maven.compiler.target" -> "1.8"
  ),
  build = Build(
    plugins = Seq(
      Plugin(
        Plugins.retrolambda,
        executions = Seq(
          Execution(
            id = "process-java7-classes",
            goals = Seq("process-main"),
            configuration = Config(
              target = "1.7",
              mainOutputDir = "${project.build.directory}/java7-classes"
            )
          ),
          Execution(
            id = "process-java6-classes",
            goals = Seq("process-main"),
            configuration = Config(
              target = "1.6",
              mainOutputDir = "${project.build.directory}/java6-classes"
            )
          )
        )
      ),
      Plugin(
        Plugins.jar,
        executions = Seq(
          Execution(
            id = "jar-java7",
            phase = "package",
            goals = Seq("jar"),
            configuration = Config(
              classifier = "java7",
              classesDirectory = "${project.build.directory}/java7-classes"
            )
          ),
          Execution(
            id = "jar-java6",
            phase = "package",
            goals = Seq("jar"),
            configuration = Config(
              classifier = "java6",
              classesDirectory = "${project.build.directory}/java6-classes"
            )
          )
        )
      )
    )
  ),
  modelVersion = "4.0.0"
)
