package coroutine

import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {

        val job = launch {

            println("${coroutineContext[Job]}")
        }
        job.join()
    }
}