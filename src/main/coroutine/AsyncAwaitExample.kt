package coroutine

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() = runBlocking{

//    val job = launch {
//        val timeMillis = measureTimeMillis {
//            doWork1()
//            doWork2()
//            println("result = ${doWork1() + doWork2()}")
//        }
//        println("Done in $timeMillis millis")
//    }
//    job.join()

    val job = launch {
        val timeMillis = measureTimeMillis {
            val deferred = async {
                doWork1()
            }
            val deferred2 = async {
                doWork2()
            }
            println("result = ${deferred.await() + deferred2.await()}")
        }
        println("Done in $timeMillis millis")
    }
    job.join()
}

suspend fun doWork2() :Int{
    delay(100)
    return 1
}

suspend fun doWork1() :Int{
    delay(200)
    return 12
}
