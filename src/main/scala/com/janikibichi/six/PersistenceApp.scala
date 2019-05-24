package com.janikibichi.six

import akka.actor.{ActorSystem, Props}

object PersistenceApp extends App{
    val actorSystem = ActorSystem("persistence")
    val persistentActorOne = actorSystem.actorOf(Props[PersistenceActor])

    persistentActorOne ! UserUpdate("Hone", Add)
    persistentActorOne ! UserUpdate("Twoo", Add)
    persistentActorOne ! "snap"
    persistentActorOne ! "print"
    persistentActorOne ! UserUpdate("Threee", Remove)
    persistentActorOne ! "print"

    Thread.sleep(2000)

    actorSystem.stop(persistentActorOne)

    val persistentActorTwo = actorSystem.actorOf(Props[PersistenceActor])
    persistentActorTwo ! "print"

    Thread.sleep(2000)
    actorSystem.terminate
}