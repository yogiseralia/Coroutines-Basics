package coroutine.channels

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val channel = Channel<Int>(4) // create buffered channel
    val sender = launch(coroutineContext) {
        repeat(10){
            println("Sending $it")
            channel.send(it)
        }
        channel.close()
    }

    for (x in channel){
        println(" -Receiving $x")
    }

    sender.join()
}