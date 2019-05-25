package com.janikibichi.six

import akka.actor.ActorSystem

object PersistentFSMApp extends App{
    val actorSystem = ActorSystem("akkasystem")

    val actorOne = createActor("uid1")

    actorOne ! Initialize(4)
    actorOne ! Mark
    actorOne ! Mark
    Thread.sleep(2000)
    actorSystem.stop(actorOne)

    val actorTwo = createActor("uid1")
    actorTwo ! Mark
    actorTwo ! Mark

    def createActor(id: String) = actorSystem.actorOf(PersistentFSMActor.props(id))
}