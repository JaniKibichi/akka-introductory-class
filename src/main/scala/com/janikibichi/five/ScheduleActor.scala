package com.janikibichi.five

import akka.actor.Actor
import akka.actor.Props
import scala.concurrent.duration._
import akka.actor.ActorSystem

object ScheduleActor extends App{
    val system = ActorSystem("HelloAkka")

    import system.dispatcher
    val actor = system.actorOf(Props[RandomIntegerAddition])
    
    system.scheduler.scheduleOnce(10 seconds,actor,"tick")
    system.scheduler.schedule(11 seconds, 2 seconds, actor, "tick")
}

class RandomIntegerAddition extends Actor{
    val r = scala.util.Random

    def receive: Actor.Receive = {
        case "tick" =>
            val randomIntegerA = r.nextInt(10)
            val randomIntegerB = r.nextInt(10)
        
        println(s"sum of $randomIntegerA and $randomIntegerB is ${randomIntegerA+randomIntegerB}")
    }
}