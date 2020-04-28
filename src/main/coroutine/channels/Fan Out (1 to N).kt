package coroutine.channels

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun CoroutineScope.produceNo() : ReceiveChannel<Int> = produce {
    var x = 1 // start from 1
    while (true) {
        send(x++) // produce next
        delay(100)
    }
}

fun CoroutineScope.consumer(id:Int,channel: ReceiveChannel<Int>) = produce<Int> {
    channel.consumeEach {
        println("Consumer #$id received $it from producer in thread ${Thread.currentThread().name}")
    }
}

fun main() = runBlocking<Unit> {
    val produceNo = produceNo()
    repeat(5) {
        consumer(it,produceNo)
    }
    println("launcher")
    delay(2650)
    produceNo.cancel() // cancel producer coroutine and thus kill them all.
}