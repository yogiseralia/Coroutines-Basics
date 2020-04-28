package coroutine.channels

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking


fun main() = runBlocking{
    val channel = produceNumbers()

//  consumeEach{} returns data in channel iteratively
    channel.consumeEach {
        println("receive - $it")
    }
}

fun CoroutineScope.produceNumbers(): ReceiveChannel<Int> = produce {

//    no need use channel object explicitly
//    no need to launch a job to use channel
    for (i in 1..5){
        send(i)
        println("send - $i")
    }
    // no need to close channel explicitly.
}
