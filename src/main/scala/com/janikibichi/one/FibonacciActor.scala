package com.janikibichi.one

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import akka.actor.ActorSystem
import scala.concurrent.Await

object FibonacciActorApp extends App{
    implicit val timeout = Timeout(10 seconds)

    val actorSystem = ActorSystem("HelloAkka")
    val actor = actorSystem.actorOf(Props[FibonacciActor])

    val future = (actor ? 10).mapTo[Int]
    val fibonacciNumber = Await.result(future, 10 seconds)

    println(fibonacciNumber)
}

class FibonacciActor extends Actor{
    override def receive: Receive ={
        case num: Int =>
            val fibonacciNumber = fib(num)
            sender ! fibonacciNumber
    }

    def fib(n: Int): Int = n match{
        case 0 | 1 => n
        case _ => fib(n-1) + fib(n-2)
    }
}