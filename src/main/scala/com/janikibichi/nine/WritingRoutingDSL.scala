package com.janikibichi.nine

import akka.http.scaladsl.server.HttpApp
import akka.http.scaladsl.settings.ServerSettings
import com.typesafe.config.ConfigFactory

import akka.http.scaladsl.server.Route
import scala.collection.mutable.Map

class TemperatureInMemoryStorageRestApi(cache: Map[String,TemperatureMeasurement]) extends HttpApp
    with InMemoryStorageRestAPI[String,TemperatureMeasurement]
    with GetRequestsHandler
    with PostRequestsHandler
    with PutRequestsHandler
    with DeleteRequestsHandler{
        implicit val fixedPath="temperature"
        val routes: Route = composedRoute(cache)
    }

object TemperatureInMemoryStorageRestApiApplication extends App{
    val cache = Map.empty[String, TemperatureMeasurement]
    new TemperatureInMemoryStorageRestApi(cache).startServer("0.0.0.0",8088,ServerSettings(ConfigFactory.load))
}