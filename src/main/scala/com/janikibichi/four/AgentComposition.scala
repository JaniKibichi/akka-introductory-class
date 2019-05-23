package com.janikibichi.four

import akka.agent.Agent
import scala.concurrent.ExecutionContext.Implicits.global

object AgentComposition extends App{
    val agentOne = Agent("hello")
    val agentTwo = Agent("world")

    val finalAgent = for{
        valueone <- agentOne
        valuetwo <- agentTwo
    } yield valueone + valuetwo

    println(finalAgent.get)
}