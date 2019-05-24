package com.janikibichi.six

case class AddFriend(friend: Friend)
case class RemoveFriend(friend: Friend)
case class Friend(id: String)

sealed trait FriendEvent
case class FriendAdded(friend: Friend) extends FriendEvent
case class FriendRemoved(friend: Friend) extends FriendEvent

case class FriendState(friends: Vector[Friend] = Vector.empty[Friend]){
    def update(event: FriendEvent) = event match{
        case FriendAdded(friend) => copy(friends :+ friend)
        case FriendRemoved(friend) => copy(friends.filterNot( _ == friend))
    }
    override def toString = friends.mkString(",")
}
