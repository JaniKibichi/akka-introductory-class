package com.janikibichi.seven

import akka.actor.Actor
import akka.cluster.Cluster
import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.{Subscribe,SubscribeAck}

case class Notification(title: String, body: String)

class NotificationSubscriber extends Actor{
    val mediator = DistributedPubSub(context.system).mediator

    mediator ! Subscribe("notification",self)

    val cluster = Cluster(context.system)
    val clusterAddress = cluster.selfUniqueAddress

    def receive: Actor.Receive = {
        case notification: Notification =>
            println(s"Got notification in node $clusterAddress => $notification")

        case SubscribeAck(Subscribe("notification",None,`self`)) => println("subscribing")
    }
}