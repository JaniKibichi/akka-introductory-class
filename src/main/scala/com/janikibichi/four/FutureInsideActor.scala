package com.janikibichi.four

import akka.actor.{Props,ActorSystem,Actor}
import scala.concurrent.{Await,Future}
import scala.concurrent.duration._

object FutureInsideActor extends App{
    val actorSystem = ActorSystem("HelloAkka")
    val futureActor = actorSystem.actorOf(Props[FutureActor])
    
    futureActor ! (10,20)
}

class FutureActor extends Actor{
    import context.dispatcher

    def receive: Actor.Receive = {
        case (a: Int, b: Int) => 
            val f = Future(a+b)
            val onsuccess = Await.result(f,10 seconds)
            println(s"Future Result is $onsuccess")
    }
}