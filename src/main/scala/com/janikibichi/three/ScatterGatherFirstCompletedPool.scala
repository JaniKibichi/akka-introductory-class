package com.janikibichi.three

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import akka.pattern.ask
import akka.routing.ScatterGatherFirstCompletedPool
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.Await

object ScatterGatherFirstCompletedPoolActor extends App{
    implicit val timeout = Timeout(10 seconds)
    val actorSystem = ActorSystem("helloAkka")
    val router = actorSystem.actorOf(ScatterGatherFirstCompletedPool(5,within=10.seconds).props(Props[ScatterGatherFirstCompletedPoolActor]))
    println(Await.result((router ? "hello").mapTo[String],10 seconds))
}

class ScatterGatherFirstCompletedPoolActor extends Actor{
    override def receive = {
        case msg: String => sender ! "I say hello back to you!"
        case _ => println(s"I don't understand the message")
    }
}

