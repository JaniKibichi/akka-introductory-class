package com.janikibichi.one

import akka.actor.ActorSystem

object HelloAkka extends App{
    val system = ActorSystem("HelloAkka")
    println(system)
}