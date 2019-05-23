package com.janikibichi.two

import akka.actor.{Props, ActorSystem, Actor, ActorRef}

case class DoubleValue(x: Int)
case object CreateOtherChild
case object Send
case class Response(x: Int)

object SendMessagesToChilds extends App{
    val actorSystem = ActorSystem("Hello-Akka")
    val parent = actorSystem.actorOf(Props[ParentSecondActor],"parent")
    parent ! CreateOtherChild
    parent ! CreateOtherChild
    parent ! CreateOtherChild
    parent ! Send
}

class DoubleActor extends Actor{
    def receive: Actor.Receive = {
        case DoubleValue(number) => println(s"${self.path.name} Got the number $number")
        sender ! Response(number * 2)
    }
}

class ParentSecondActor extends Actor{
    val random = new scala.util.Random
    var childcollection = scala.collection.mutable.ListBuffer[ActorRef]()

    def receive: Actor.Receive = {
        case CreateOtherChild => childcollection ++= List(context.actorOf(Props[DoubleActor]))
        case Send => println(s"Sending messages to child")
            childcollection.zipWithIndex map {
                case (child, value) => child ! DoubleValue(random.nextInt(10))
            }
        case Response(x) => println(s"Parent: Response from child ${sender.path.name} is $x")
    }
}