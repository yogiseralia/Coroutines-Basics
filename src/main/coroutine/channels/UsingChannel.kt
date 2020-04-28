package coroutine.channels

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val channel = Channel<Int>()

    val job = launch {
        for (x in 1..5){
            println("send $x")
            channel.send(x)
        }
    }

    repeat(5){
        println("receive ${channel.receive()}")
    }

    job.join()
}