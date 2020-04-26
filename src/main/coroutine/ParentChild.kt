package coroutine

import kotlinx.coroutines.*

fun main() {
    runBlocking {
        println(coroutineContext)
        try {
            launch {
                try {
                    println(coroutineContext)
                    launch {
                        try {
                            println(coroutineContext)
                            repeat(100) {
                                print(".")
                                delay(1)
                            }
                        } catch (e: Exception) {
                            println("Cancelled inner most")
                            e.printStackTrace()
                        }
                    }
                } catch (e: Exception) {
                    println("Cancelled inner")
                    e.printStackTrace()
                }
            }.cancelChildren(CancellationException("cancelled children"))
        } catch (e: CancellationException) {
            e.printStackTrace()
        }
    }
}