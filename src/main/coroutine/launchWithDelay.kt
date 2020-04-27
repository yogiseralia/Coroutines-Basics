package coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    val job = launch {
        delay(1000)
        println("World")
    }

    print("Hello, ")
    job.join()
//    doWork()
}

suspend fun doWork(){
    delay(1500)
}