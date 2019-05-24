name:= "Hello-Akka"

version:= "1.0"

scalaVersion:= "2.12.7"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.22"

libraryDependencies += "com.typesafe.akka" %% "akka-agent" % "2.5.22"

libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.5.22" % Test

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.0-SNAP10" % Test

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.0" % Test

libraryDependencies += "com.typesafe.akka" %% "akka-persistence" % "2.5.22"

libraryDependencies += "org.iq80.leveldb" % "leveldb" % "0.10"


