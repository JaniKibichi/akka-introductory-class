package com.janikibichi.five

import akka.actor.{Actor,ActorSystem,Props}
import akka.pattern.{CircuitBreaker,ask}
import akka.util.Timeout
import scala.concurrent.duration._

case class FetchRecord(recordID: Int)
case class Person(name: String, age: Int)

object DB{
    val data = Map(
        1 -> Person("John",21), 
        2 -> Person("Peter",30),
        3 -> Person("James",40),
        4 -> Person("Alice",25),
        5 -> Person("Henry",26),
        6 -> Person("Jackson",48)
    )
}

class DBActor extends Actor{
    def receive: Actor.Receive = {
        case FetchRecord(recordID) =>
            if(recordID >= 3 && recordID <= 5)
                Thread.sleep(3000)
            else sender ! DB.data.get(recordID)
    }
}

object CircuitBreakerApp extends App{
    val system = ActorSystem("HelloAkka")
    implicit val ec = system.dispatcher
    implicit val timeout = Timeout(3 seconds)

    val breaker = new CircuitBreaker(system.scheduler, maxFailures=3, callTimeout=1 seconds,resetTimeout=2 seconds)
    .onOpen(println("State is Open"))
    .onClose(println("State is closed"))

    val db = system.actorOf(Props[DBActor],"DBActor")

    (1 to 10).map(recordID =>{
        Thread.sleep(3000)

        val askFuture = breaker.withCircuitBreaker(db ? FetchRecord(recordID))
        askFuture.map(record => s"Record is: $record and RecordID $recordID").recover({
            case fail => "Failed With: "+fail.toString
        }).foreach(x => println(x))

    })
}

