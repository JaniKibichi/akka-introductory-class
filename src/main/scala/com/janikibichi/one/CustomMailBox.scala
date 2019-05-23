package com.janikibichi.one

import java.util.concurrent.ConcurrentLinkedQueue
import akka.actor.{Props,Actor,ActorSystem}
import akka.dispatch.{MailboxType,ProducesMessageQueue,Envelope,MessageQueue}
import com.typesafe.config.Config
import akka.actor.ActorRef

object CustomMailBox extends App{
    val actorSystem = ActorSystem("HelloAkka")

    val actor = actorSystem.actorOf(Props[MySpecialActor].withDispatcher("custom-dispatcher"))
    val actor1 = actorSystem.actorOf(Props[MyActor],"xyz")
    val actor2 = actorSystem.actorOf(Props[MyActor],"MyActor")

    actor1 ! ("hello",actor)
    actor2 ! ("hello",actor)
}
class MyMessageQueue extends MessageQueue{
    private final val queue = new ConcurrentLinkedQueue[Envelope]

    def enqueue(receiver: ActorRef, handle: Envelope): Unit ={

        if(handle.sender.path.name.equals("MyActor")){
            handle.sender ! "Hey Dude, How are you? I know your name, processing your request"
            queue.offer(handle)
        }
        else handle.sender ! "I don't talk to strangers, I can't process your request"
    }

    def dequeue(): Envelope = queue.poll

    def numberOfMessages: Int = queue.size

    def hasMessages: Boolean = !queue.isEmpty

    def cleanUp(owner: ActorRef, deadLetters: MessageQueue): Unit = {
        while (hasMessages){
            deadLetters.enqueue(owner,dequeue())
        }
    }
}

class MyUnboundedMailbox extends MailboxType with ProducesMessageQueue[MyMessageQueue]{
    def this(settings: ActorSystem.Settings, config: Config){
        this()
    }

    final override def create(owner: Option[ActorRef], system: Option[ActorSystem]):MessageQueue =new MyMessageQueue()
}

class MySpecialActor extends Actor{
    override def receive: Receive ={
        case msg: String =>println(s"message is $msg")
    }
}

class MyActor extends Actor{

    override def receive: Receive ={
        case (msg: String, actorRef: ActorRef) => 
            actorRef ! msg

        case msg => 
            println(msg)
    }
}