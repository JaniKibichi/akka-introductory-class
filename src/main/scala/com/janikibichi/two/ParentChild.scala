package com.janikibichi.two

import akka.actor.{ActorSystem, Props, Actor}

case object CreateChild
case class Greet(msg: String)

object ParentChild extends App{
    val actorSystem = ActorSystem("Supervision")
    val parent = actorSystem.actorOf(Props[ParentActor],"parent")

    parent ! CreateChild
}

class ChildActor extends Actor{
    def receive = {
        case Greet(msg) => println(s"My parent [${self.path.parent}] greeted me [${self.path}]")
    }
}

class ParentActor extends Actor{
    def receive: Actor.Receive = {
        case CreateChild =>
            val child = context.actorOf(Props[ChildActor],"Child")
            child ! Greet("Hello Child")
    }
}