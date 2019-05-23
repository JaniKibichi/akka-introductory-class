package com.janikibichi.four

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure,Success}

object Callback extends App{
    val future = Future(1+2).mapTo[Int]
    future onComplete{
        case Success(value) => println(s"result is $value")
        case Failure(fail) => fail.printStackTrace()
    }
    println("Executed before callback")
}