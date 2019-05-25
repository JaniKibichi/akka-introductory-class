package com.janikibichi.seven

import akka.actor.{Actor, ActorRef}

case class Work(workId: String)
case class WorkDone(workId: String)

class WorkerActor extends Actor{
    def receive: Actor.Receive = {
        case Work(workId) =>    
            Thread.sleep(3000)
            sender ! WorkDone(workId)
            println(s"Work $workId was done by worker actor")
    }
}