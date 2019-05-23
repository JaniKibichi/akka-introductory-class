package com.janikibichi.one

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

object BecomeUnbecome extends App{
    val actorSystem = ActorSystem("HelloAkka")
    val becomeUnbecome = actorSystem.actorOf(Props[BecomeUnbecome])

    becomeUnbecome ! true 
    becomeUnbecome ! "Hello How are you?"
    becomeUnbecome ! false
    becomeUnbecome ! 1100
    becomeUnbecome ! true 
    becomeUnbecome ! "What do u do?"
}

class BecomeUnbecome extends Actor{
    def receive: Receive ={
        case true => context.become(isStateTrue)
        case false => context.become(isStateFalse)
        case _ => println("I don't know waht you want to say")
    }

    def isStateTrue: Receive ={
        case msg: String => println(s"$msg at isStateTrue")
        case false => context.become(isStateFalse)
    }

    def isStateFalse: Receive ={
        case msg: Int => println(s"$msg at isStateFalse")
        case true => context.become(isStateTrue)
    }
}