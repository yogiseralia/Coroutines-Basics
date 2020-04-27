package coroutine

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.system.measureTimeMillis

fun main(): Unit = runBlocking {

//    withoutAsyncAwait()
//    doSimultaneously()
//    doOneByOne();
//    println(doWorkAsync("working"))
//    val deferred = async { doWork1() }
    val deferred = async(start = CoroutineStart.LAZY) { doWork1() }
    log(deferred.await().toString())
}

private fun CoroutineScope.doWorkAsync(msg: String): Deferred<Int> = async {
    log("$msg - working...")
    delay(1000)
    log("$msg - work done")
    return@async 23
}

private suspend fun doOneByOne() {
    coroutineScope {
        val job = launch {
            val async = async(coroutineContext) {
                println(doWork1())
            }
            async.await()
            println(doWork2())
        }.join()
    }
}

private suspend fun withoutAsyncAwait() {
    coroutineScope {
        val job = launch {
            val timeMillis = measureTimeMillis {
                doWork1()
                doWork2()
                println("result = ${doWork1() + doWork2()}")
            }
            println("Done in $timeMillis millis")
        }.join()
    }
}

private suspend fun doSimultaneously() {
    coroutineScope {
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
        }.join()
    }
}

suspend fun doWork2(): Int {
    delay(100)
    return 1
}

suspend fun doWork1(): Int {
    delay(200)
    return 12
}

fun log(msg: String) {
    println(msg)
}
