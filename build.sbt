name:= "Hello-Akka"

version:= "1.0"

scalaVersion:= "2.12.7"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.19"

libraryDependencies += "com.typesafe.akka" %% "akka-agent" % "2.5.19"

libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.5.19" % Test

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.0-SNAP10" % Test

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.0" % Test

libraryDependencies += "com.typesafe.akka" %% "akka-persistence" % "2.5.19"

libraryDependencies += "org.iq80.leveldb" % "leveldb" % "0.10"

libraryDependencies += "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8"

libraryDependencies += "com.typesafe.akka" %% "akka-persistence-cassandra" % "0.97"

libraryDependencies += "com.hootsuite" %% "akka-persistence-redis" % "0.8.0" % "runtime"

libraryDependencies += "com.github.romix.akka" %% "akka-kryo-serialization" % "0.5.2"

libraryDependencies += "com.typesafe.akka" %% "akka-cluster" % "2.5.19"

libraryDependencies += "com.typesafe.akka" %% "akka-cluster-sharding" % "2.5.19"

libraryDependencies += "com.typesafe.akka" %% "akka-distributed-data" % "2.5.19"




