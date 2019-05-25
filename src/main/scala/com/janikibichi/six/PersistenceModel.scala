package com.janikibichi.six

import akka.persistence.{PersistentActor, SnapshotOffer}

sealed trait UserAction
case object Add extends UserAction
case object Remove extends UserAction
case class UserUpdate(userId: String, action: UserAction)

sealed trait Event
case class AddUserEvent(userId: String) extends Event
case class RemoveUserEvent(userId: String) extends Event

case class ActiveUsers(users: Set[String] = Set.empty[String]){
    def update(event: Event) = event match{
        case AddUserEvent(userId) => copy(users + userId)
        case RemoveUserEvent(userId) => copy(users.filterNot(_ == userId))
    }
    override def toString = s"$users"
}

case object ShutdownPersistentActor

class PersistenceActor extends PersistentActor{
    override val persistenceId = "unique-id-1"
    var state = ActiveUsers()

    def updateState(event: Event) = state = state.update(event)

    val receiveRecover: Receive = {
        case event: Event => 
            updateState(event)

        case SnapshotOffer(_, snapshot: ActiveUsers) => 
            state = snapshot
    }

    val receiveCommand: Receive = {
        case UserUpdate(userId,Add) =>
            persist(AddUserEvent(userId))(updateState)

        case UserUpdate( userId, Remove) =>
            persist(RemoveUserEvent(userId))(updateState)

        case "snap" => saveSnapshot(state)

        case "print" => println(state)

        case ShutdownPersistentActor =>
            context.stop(self)
    }
    override def postStop() = println(s"Stopping [${self.path}]")
}

