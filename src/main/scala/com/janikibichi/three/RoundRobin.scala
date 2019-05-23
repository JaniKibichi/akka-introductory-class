package com.janikibichi.three

import akka.actor.{Props, ActorSystem, Actor}
import akka.routing.RoundRobinPool

class RoundRobinPoolActor extends Actor{
    override def receive: Actor.Receive = {
        case msg: String => println(s"I am ${self.path.name}")
        case _ => println(s"I don't understand the message.")
    }
}

object RoundRobinPoolApp extends App{
    val actorSystem = ActorSystem("HelloAkka")
    val router = actorSystem.actorOf(RoundRobinPool(5).props(Props[RoundRobinPoolActor]))
    for(i <- 1 to 5){
        router ! s"Hello $i"
    }
}