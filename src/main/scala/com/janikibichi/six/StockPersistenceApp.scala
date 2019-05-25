package com.janikibichi.six

trait AkkaHelper{
    lazy val actorSystem = akka.actor.ActorSystem("SetUp")
    lazy val teslaStockActor = actorSystem.actorOf(StockPersistenceActor.props("TLSA"))
}

object StockApp extends App with AkkaHelper{
    teslaStockActor ! ValueUpdate(305.12)
    teslaStockActor ! ValueUpdate(305.15)
    teslaStockActor ! "print"

    Thread.sleep(2000)
    actorSystem.terminate()
}

object StockRecoveryApp extends App with AkkaHelper{
    teslaStockActor ! ValueUpdate(305.20)
    teslaStockActor ! "print"
    
    Thread.sleep(2000)
    actorSystem.terminate()
}