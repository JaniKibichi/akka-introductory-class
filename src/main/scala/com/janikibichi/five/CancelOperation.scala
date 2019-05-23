package com.janikibichi.five

import akka.actor.{Cancellable,Props,ActorSystem,Actor}
import scala.concurrent.duration._

object Scheduler extends App{
    val system = ActorSystem("HelloAkka")

    import system.dispatcher
    val actor = system.actorOf(Props[CancelOperation])

    val cancellable: Cancellable = system.scheduler.schedule(0 seconds, 2 seconds, actor, "tick")
}

class CancelOperation extends Actor{
    var i = 10

    def receive ={
        case "tick" => {
            println(s"Hi, do you know I do the same task again and again")
            i = i -1
            if(i == 0) Scheduler.cancellable.cancel()
        }
    }
}