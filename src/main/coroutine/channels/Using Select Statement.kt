package coroutine.channels

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select

fun CoroutineScope.producer1() = produce {
    while (true) {
        send("from producer 1")
    }
}

fun CoroutineScope.producer2() = produce {
    while (true) {
        send("from producer 2")
    }
}

suspend fun selector(message1: ReceiveChannel<String>, message2: ReceiveChannel<String>){
    select<Unit> {
        message1.onReceive { value->
            println(value)
        }
        message2.onReceive { value->
            println(value)
        }
    }
}

fun main() = runBlocking {
    val producer1 = producer1()
    val producer2 = producer2()

    repeat(15){
        selector(producer1,producer2)
    }
}