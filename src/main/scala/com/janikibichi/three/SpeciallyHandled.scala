package com.janikibichi.three

import akka.actor.{Actor,ActorSystem,Props,PoisonPill}
import akka.routing.{Broadcast,RandomPool}

case object Handle

object SpeciallyHandled extends App{
    val actorSystem = ActorSystem("HelloAkka")

    val router = actorSystem.actorOf(RandomPool(5).props(Props[SpeciallyHandledActor]))
    router ! Broadcast(Handle)
    router ! Broadcast(PoisonPill)
    router ! Handle
}

class SpeciallyHandledActor extends Actor{
    override def receive ={
        case Handle => println(s"${self.path.name} says hello")
    }
}