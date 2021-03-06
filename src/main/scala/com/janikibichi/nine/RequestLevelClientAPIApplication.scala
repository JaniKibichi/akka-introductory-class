package com.janikibichi.nine

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import akka.stream.ActorMaterializer

import scala.concurrent.duration._
import scala.util.{Failure,Success}

object RequestLevelClientAPIApplication extends App{
    implicit val actorSystem = ActorSystem()
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = actorSystem.dispatcher

    val akkaToolkitRequest = HttpRequest(uri = "https://api.github.com/repos/akka/akka-http")
    val responseFuture = Http().singleRequest(akkaToolkitRequest)

    responseFuture.andThen{
        case Success(response) => 
            response.entity.toStrict(5 seconds).map(_.data.decodeString("UTF-8")).andThen{
                case Success(json) =>
                    val pattern = """.*"open_issues":(.*?),.*""".r                 
                    pattern.findAllIn(json).matchData foreach{ m =>
                        println(s"There are ${m.group(1)} open issues in Akka Http.")
                        materializer.shutdown()
                        actorSystem.terminate()
                    }
                case _ =>
            }
        case _ => println(s"Request Failed")
    }
}