package com.janikibichi.one

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.PoisonPill

case object Stop

object ShutdownApp extends App{
    val actorSystem = ActorSystem("HelloAkka")
    val shutDownActor = actorSystem.actorOf(Props[ShutdownActor],"firstShutDownActor")

    shutDownActor ! "hello"
    shutDownActor ! PoisonPill
    shutDownActor ! "Are you there?"

    val shutDownActorTwo = actorSystem.actorOf(Props[ShutdownActor],"secondShutDownActor")

    shutDownActorTwo ! "hello"
    shutDownActorTwo ! Stop
    shutDownActorTwo ! "Are you there?"
}

class ShutdownActor extends Actor{
    override def receive: Receive ={
        case msg: String => println(s"$msg")
        case Stop => context.stop(self)
    }
}