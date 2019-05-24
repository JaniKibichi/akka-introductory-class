package com.janikibichi.six


import akka.actor.ActorSystem
import akka.persistence.{Recovery, SnapshotSelectionCriteria}

object FriendApp extends App{
    val actorSystem = ActorSystem("friend")
    val hector = actorSystem.actorOf(FriendActor.props("Hector",Recovery()))

    hector ! AddFriend(Friend("Laura"))
    hector ! AddFriend(Friend("Nancy"))
    hector ! AddFriend(Friend("Oliver"))
    hector ! AddFriend(Friend("Steve"))
    hector ! "snap"
    hector ! RemoveFriend(Friend("Oliver"))
    hector ! "print"

    Thread.sleep(2000)
    actorSystem.terminate()
}
//default behavior of recovery
object FriendRecoveryDefault extends App{
    val actorSystem = ActorSystem("friend")
    val hector = actorSystem.actorOf(FriendActor.props("Hector",Recovery()))

    hector ! "print"

    Thread.sleep(2000)
    actorSystem.terminate()
}
//recover only from events and not snapshots
object FriendRecoveryOnlyEvents extends App{
    val actorSystem = ActorSystem("friend")

    val recovery = Recovery(fromSnapshot = SnapshotSelectionCriteria.None)
    val hector = actorSystem.actorOf(FriendActor.props("Hector",recovery))

    hector ! "print"

    Thread.sleep(2000)
    actorSystem.terminate()
}
//place a limit on events number to be recovered
object FriendRecoveryEventsSequence extends App{
    val actorSystem = ActorSystem("friend")

    val recovery = Recovery(fromSnapshot = SnapshotSelectionCriteria.None, toSequenceNr = 2)
    val hector = actorSystem.actorOf(FriendActor.props("Hector",recovery))

    Thread.sleep(2000)
    actorSystem.terminate()
}
//place a limit on the events to replay
object FriendRecoveryEventsReplay extends App{
    val actorSystem = ActorSystem("friend")

    val recovery = Recovery(fromSnapshot = SnapshotSelectionCriteria.None, replayMax = 3)
    val hector = actorSystem.actorOf(FriendActor.props("Hector",recovery))

    Thread.sleep(2000)
    actorSystem.terminate()
}