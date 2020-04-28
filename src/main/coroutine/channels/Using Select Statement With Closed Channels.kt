package coroutine.channels

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.onReceiveOrNull
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select

fun CoroutineScope.producer3() = produce {
    send("from producer 1")
}

fun CoroutineScope.producer4() = produce {
    send("from producer 2")
}

suspend fun selector_(message1: ReceiveChannel<String>, message2: ReceiveChannel<String>) {
    select<Unit> {
        message1.onReceiveOrNull { value ->
            println(value)
        }
        message2.onReceiveOrNull { value ->
            println(value)
        }
    }
}

fun main() = runBlocking {
    val producer3 = producer3()
    val producer4 = producer4()

    repeat(15) {
        selector_(producer3, producer4)
    }
}