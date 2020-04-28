package coroutine.channels

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*

val totalWork: Int = 20
val numberOfWorkers: Int = 10
val input = Channel<Work>()
val output = Channel<Work>()

data class Work(var x: Long = 0, var y: Long = 0, var z: Long = 0)

suspend fun worker(input: Channel<Work>, output: Channel<Work>) {
    for (w in input) {
//        multiply x and y and delay for z
        w.z = w.x * w.y
        delay(w.z)
        output.send(w)
    }
}

//setup the workers
fun run(block: CoroutineScope) {

    repeat(numberOfWorkers) {
        block.launch { worker(input, output) }
    }

    block.launch { sendLotsOfWork(input) }
    block.launch { receiveLotsOfResults(output) }
}

suspend fun sendLotsOfWork(input: Channel<Work>) {
    repeat(totalWork) {
        input.send(Work((0L..100).random(), (0L..10).random()))
    }
}

suspend fun receiveLotsOfResults(output: Channel<Work>) {
    for (work in output) {
        println("${work.x} * ${work.y} = ${work.z}")
    }
}

fun main() = runBlocking<Unit> {
    run(this)
    delay(5000)
    input.close()
    output.close()
}

private object RandomRangeSingleton : Random()

fun ClosedRange<Long>.random() = (RandomRangeSingleton.nextInt((endInclusive.toInt() + 1) - start.toInt()) + start)
