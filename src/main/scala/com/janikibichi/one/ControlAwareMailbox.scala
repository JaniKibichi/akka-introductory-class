package com.janikibichi.one

import akka.actor.Actor
import akka.dispatch.ControlMessage
import akka.actor.ActorSystem
import akka.actor.Props

case object MyControlMessage extends ControlMessage

object ControlAwareMailbox extends App{
    val actorSystem = ActorSystem("HelloAkka")
    val actor = actorSystem.actorOf(Props[Logger].withDispatcher("control-aware-dispatcher"))

    actor ! "hello"
    actor ! "how are you"
    actor ! "you?"
    actor ! MyControlMessage
}
class Logger extends Actor{
    def receive: Receive ={
        case MyControlMessage => println("Oh, I have to process control message first")
        case x => println(x.toString)
    }
}