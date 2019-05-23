package com.janikibichi.one

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import akka.dispatch.PriorityGenerator
import com.typesafe.config.Config
import akka.dispatch.UnboundedPriorityMailbox

object PriorityMailBoxApp extends App{
    val actorSystem = ActorSystem("HelloAkka")
    val myPriorityActor = actorSystem.actorOf(Props[MyPriorityActor].withDispatcher("prio-dispatcher"))

    myPriorityActor ! 6.0
    myPriorityActor ! 1
    myPriorityActor ! 5.0
    myPriorityActor ! 3
    myPriorityActor ! "Hello"
    myPriorityActor ! 5
    myPriorityActor ! "I am a priority Actor"
    myPriorityActor ! "I process string messages first, them integer, long, others"
}

class MyPriorityActor extends Actor{
    def receive: PartialFunction[Any,Unit] ={
        case x: Int => println(x)
        case x: String => println(x)
        case x: Long => println(x)
        case x => println(x)
    }
}

class MyPriorityActorMailbox(settings: ActorSystem.Settings, config: Config) 
    extends UnboundedPriorityMailbox(
        PriorityGenerator{
        case x: Int => 1
        //highest priority
        case x: String => 0
        case x: Long => 2
        case _ => 3
})