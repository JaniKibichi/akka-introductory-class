package com.janikibichi.four

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import akka.util.Timeout
import scala.concurrent.ExecutionContext.Implicits.global

object ReducingFutures extends App{
    val timeout = Timeout(10 seconds)

    val listOfFutures = (1 to 10).map(Future(_))
    println(listOfFutures)

    val finalFuture = Future.reduce(listOfFutures)(_+_)
    println(s"sum of numbers from 1 to 10 is ${Await.result(finalFuture, 10 seconds)}")
}