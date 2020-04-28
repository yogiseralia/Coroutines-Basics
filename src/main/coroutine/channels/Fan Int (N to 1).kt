package coroutine.channels

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

suspend fun sendString(channel: Channel<String>, s: String, interval: Long){
    while (true){
        delay(interval)
        channel.send(s)
    }
}

fun main() = runBlocking<Unit>{
    val channel = Channel<String>()

//  two producer
    launch(coroutineContext) { sendString(channel,"yogesh",100L)  }
    launch(coroutineContext) { sendString(channel,"seralia",300L)  }

//  single consumer
    repeat(10) {
        println(channel.receive())
    }

    coroutineContext.cancelChildren()
}