package com.janikibichi.one

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

object BehaviorAndState extends App{
    val actorSystem = ActorSystem("HelloAkka")
    val actor = actorSystem.actorOf(Props[SummingActor],"summingactor")

    while(true){
        Thread.sleep(3000)
        actor ! 1
    }

    println(actor.path)
}

class SummingActor extends Actor{
    //state
    var sum = 0
    //behavior
    override def receive: Receive ={
        case x: Int => sum = sum+x
            println(s"my state as SUM is $sum")

        case _ => println("I don't know what you are talking about")
    }

}