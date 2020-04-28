package coroutine.channels

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking

fun CoroutineScope.produceNumber() : ReceiveChannel<Int> = produce {
    var x=1
    while (true)
        send(x++)
}

fun CoroutineScope.squareNumber(channel:ReceiveChannel<Int>) = produce<Int> {
    for (x in channel) send(x * x)
}

fun main() = runBlocking {
    val producer = produceNumber()
    val square = squareNumber(producer)

    for (i in 1..5) println(square.receive())

    producer.cancel()
    square.cancel()
}