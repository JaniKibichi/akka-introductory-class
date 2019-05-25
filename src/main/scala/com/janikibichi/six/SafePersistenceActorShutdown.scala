package com.janikibichi.six

import akka.actor.{ActorSystem,PoisonPill,Props}

object SafePersistenceActorShutdown extends App{
    val actorSystem = ActorSystem("safe-shutdown")
    val persistentActorOne = actorSystem.actorOf(Props[PersistenceActor])
    val persistentActorTwo = actorSystem.actorOf(Props[PersistenceActor])

    persistentActorOne ! UserUpdate("Hwan",Add)
    persistentActorOne ! UserUpdate("Twoo",Add)
    persistentActorOne ! PoisonPill

    persistentActorTwo ! UserUpdate("Hwan",Add)
    persistentActorTwo ! UserUpdate("Twoo",Add)
    persistentActorTwo ! ShutdownPersistentActor
}