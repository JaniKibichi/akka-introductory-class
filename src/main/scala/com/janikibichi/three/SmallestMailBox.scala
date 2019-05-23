package com.janikibichi.three

import akka.actor.{Props,ActorSystem,Actor}
import akka.routing.SmallestMailboxPool

object SmallestMailBox extends App{
    val actorSystem = ActorSystem("HelloAkka")
    val router = actorSystem.actorOf(SmallestMailboxPool(5).props(Props[SmallestMailBoxActor]))

    for (i <- 1 to 5){
        router ! s"Hello $i"
    }
}
class SmallestMailBoxActor extends Actor{
    override def receive ={
        case msg: String => println(s"I am ${self.path.name}")
        case _ => println(s"I don't understand the message")
    }
}