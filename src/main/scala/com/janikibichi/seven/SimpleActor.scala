package com.janikibichi.seven

import akka.actor.Actor

class SimpleActor extends Actor{
    def receive: Actor.Receive = {
        case _ =>
            println(s"I have been created at ${self.path.address.hostPort}")
    }
}