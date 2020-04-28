package coroutine.channels

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.runBlocking

fun main() = runBlocking{
    val channel = Channel<Int>()
    channel.send(1)
    var value = channel.receive()
}