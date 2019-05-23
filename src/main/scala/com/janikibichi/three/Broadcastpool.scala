package com.janikibichi.three

import akka.actor.{Props, ActorSystem,Actor}
import akka.routing.BroadcastPool

class BroadcastpoolActor extends Actor{
    override def receive ={
        case msg: String => println(s"$msg, I am ${self.path.name}")
        case _ => println(s"I don't understand the message")
    }
}

object Broadcastpool extends App{
    val actorSystem = ActorSystem("HelloAkka")
    val router = actorSystem.actorOf(BroadcastPool(5).props(Props[BroadcastpoolActor]))
    router ! "hello"
}