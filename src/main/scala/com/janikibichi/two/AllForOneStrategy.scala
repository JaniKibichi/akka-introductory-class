package com.janikibichi.two

import akka.actor._
import scala.concurrent.duration._

case class Add(a: Int, b: Int)
case class Sub(a: Int, b: Int)
case class Div(a: Int, b: Int)

object AllForOneStrategyApp extends App{
    val system = ActorSystem("Hello-Akka")
    val supervisor = system.actorOf(Props[AllForOneStrategySupervisor],"supervisor")

    supervisor ! "Start"
}

class Calculator(printer: ActorRef) extends Actor{
    override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
        println("Calculator : I am restarting because of ArithmeticException")
    }

    def receive: Actor.Receive = {
        case Add(a,b) => printer ! s"sum is ${a+b}"
        case Sub(a,b) => printer ! s"diff is ${a-b}"
        case Div(a,b) => printer ! s"div is ${a/b}"
    }
}

class ResultPrinter extends Actor {
    override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
        println("Printer : I am restarting as well")
    }
    def receive: Actor.Receive = {
        case msg => println(msg)
    }
}

class AllForOneStrategySupervisor extends Actor{

    override val supervisorStrategy = AllForOneStrategy(maxNrOfRetries = 10,withinTimeRange = 1 seconds){
        case _: ArithmeticException => SupervisorStrategy.Restart
        case _: NullPointerException => SupervisorStrategy.Resume
        case _: IllegalArgumentException => SupervisorStrategy.Stop
        case _: Exception => SupervisorStrategy.Escalate
    }

    val printer = context.actorOf(Props[ResultPrinter])
    val calculator = context.actorOf(Props(classOf[Calculator],printer))

    def receive: Actor.Receive = {
        case "Start" => 
            calculator ! Add(10,12)
            calculator ! Sub(12,10)
            calculator ! Div(5,2)
            calculator ! Div(5,0)
    }
}