package com.janikibichi.nine

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Source,Sink}

import scala.concurrent.duration._
import scala.util.{Failure,Success}

object HostLevelClientAPIApplication extends App{
    implicit val actorSystem = ActorSystem()
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = actorSystem.dispatcher

    val poolClientFlow = Http().cachedHostConnectionPoolHttps[String]("api.github.com")
    val akkaToolKitRequest = HttpRequest(uri="/repos/akka/akka-http")-> """.*"open_issues":(.*?),.*"""
    val responseFuture = Source.single(akkaToolKitRequest).via(poolClientFlow).runWith(Sink.head)

    responseFuture.andThen{
        case Success(result) => 
            val(tryResponse,regex) = result
            tryResponse match {
                case Success(response) =>
                    response.entity.toStrict(5 seconds).map(_.data.decodeString("UTF-8")).andThen{
                        case Success(json) => 
                            val pattern = regex.r
                            pattern.findAllIn(json).matchData foreach{
                                m => println(s"There are ${m.group(1)} open issues in Akka Http")
                                materializer.shutdown()
                                actorSystem.terminate()
                            }
                        case _ =>
                    }
                case _ => println("Request Failed")
            }
    }
}