package com.janikibichi.three

import akka.actor.{Actor,ActorSystem,Props}
import akka.routing.{RoundRobinPool,DefaultResizer}

case object Load

object ResizablePool extends App{
    val system = ActorSystem("HelloAkka")
    val resizer = DefaultResizer(lowerBound = 2, upperBound = 15)
    val router = system.actorOf(RoundRobinPool(5,Some(resizer)).props(Props[LoadActor]))

    router ! Load
}

class LoadActor extends Actor{
    def receive ={
        case Load => println("Handing loads of requests")
    }
}