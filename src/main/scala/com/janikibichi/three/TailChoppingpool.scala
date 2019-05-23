package com.janikibichi.three

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import akka.pattern.ask
import scala.concurrent.duration._
import akka.util.Timeout
import akka.routing.TailChoppingPool
import scala.concurrent.Await

object TailChoppingpoolApp extends App{
    implicit val timeout = Timeout(10 seconds)
    val actorSystem = ActorSystem("HelloAkka")
    val router = actorSystem.actorOf(TailChoppingPool(5,within=10.seconds,interval=20.millis).props(Props[TailChoppingActor]))
    println(Await.result((router ? "hello").mapTo[String],10 seconds))
}

class TailChoppingActor extends Actor{
    override def receive ={
        case msg: String => sender ! "I say hello back to you"
        case _ => println(s"I don't understand the message.")
    }
}