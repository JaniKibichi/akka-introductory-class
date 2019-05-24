package com.janikibichi.five

import akka.actor.{Props,Actor,ActorSystem}
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory

object ActorWithConfig extends App{
    val config: Config = ConfigFactory.load("akka.conf")

    val system = ActorSystem(config.getString("myactor.actorsystem"))
    val actorName = config.getString("myactor.actorname")

    val actor = system.actorOf(Props[ConfigdActor],actorName)
    
    println(actor.path)
}

class ConfigdActor extends Actor{
    def receive ={
        case msg: String => println(msg)
    }
}
