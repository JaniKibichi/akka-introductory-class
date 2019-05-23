package com.janikibichi.three

import akka.actor.{Props, ActorSystem,Actor}
import akka.routing.BalancingPool

object BalancingPoolApp extends App{
    val actorSystem = ActorSystem("Hello-Akka")
    val router = actorSystem.actorOf(BalancingPool(5).props(Props[BalancingPoolActor]))
    for (i <- 1 to 5){
        router ! s"Hello $i"
    }
}

class BalancingPoolActor extends Actor{
    override def receive = {
        case msg: String => println(s"I am ${self.path.name}")
        case _ => println(s"I don't understand the message")
    }
}

