package coroutine

import kotlinx.coroutines.*

fun main(){
    runBlocking {
        val job = launch {
            repeat(1000){
                print(".")
                yield()
//                delay(100)
            }
        }

        delay(1000)
        job.cancelAndJoin()
        println("Exiting")
    }
}