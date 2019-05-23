package com.janikibichi.five

import akka.actor.{Props,ActorSystem,ActorLogging,Actor}

class LoggingActor extends Actor with ActorLogging{
    def receive ={
        case (a: Int, b: Int) =>{
            log.info(s"Sum of $a and $b is ${a+b}")
        }

        case msg => log.warning(s"I don't know what are you talking about: msg")
    }
}

object Logging extends App {
    val system = ActorSystem("HelloAkka")
    val actor = system.actorOf(Props[LoggingActor],"SumActor")

    actor ! (10,12)
    actor ! "Hello !!"
    system.terminate()
}