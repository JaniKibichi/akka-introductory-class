package com.janikibichi.one

import akka.actor.ActorRef
import akka.actor.Actor
import akka.actor.Props
import scala.util.Random._
import akka.actor.ActorSystem

object Communication extends App{
    import Messages._

    val actorSystem = ActorSystem("HelloAkka")
    val randomNumberGenerator = actorSystem.actorOf(Props[RandomNumberGeneratorActor],"randomNumberGeneratorActor")
    val queryActor = actorSystem.actorOf(Props[QueryActor],"queryActor")

    queryActor ! Start(randomNumberGenerator)
}

object Messages{
    case class Done(randomNumber: Int)
    case object GiveMeRandomNumber
    case class Start(actorRef: ActorRef)
}

class RandomNumberGeneratorActor extends Actor{
    import Messages._

    override def receive: Receive ={
        case GiveMeRandomNumber =>
            println("received a message to generate a random integer")
            val randomNumber = nextInt

            sender ! Done(randomNumber)
    }
}

class QueryActor extends Actor{
    import Messages._

    override def receive: Receive = {
        case Start(actorRef) => 
            println(s"send me the next random number")
            actorRef ! GiveMeRandomNumber
            
        case Done(randomNumber) =>
            println(s"received a random number $randomNumber")
    }
}