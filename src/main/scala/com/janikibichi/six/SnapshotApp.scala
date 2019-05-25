package com.janikibichi.six

import akka.actor.{ActorSystem,Props}

object SnapshotApp extends App{
    val actorSystem = ActorSystem("snapshot")

    val persistentActorOne = actorSystem.actorOf(Props[SnapshotActor])

    persistentActorOne ! UserUpdate("UserOne",Add)
    persistentActorOne ! UserUpdate("UserTwo",Add)
    persistentActorOne ! "snap"

    Thread.sleep(2000)

    actorSystem.stop(persistentActorOne)

    val persistentActorTwo = actorSystem.actorOf(Props[SnapshotActor])

    Thread.sleep(2000)

    actorSystem.terminate()

}