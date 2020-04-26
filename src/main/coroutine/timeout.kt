package coroutine

import kotlinx.coroutines.*

fun main() {
    runBlocking {
//        throws TimeOutException
//        val job = withTimeout(100) {
//            repeat(1000) {
//                yield()
//                print(".")
//                Thread.sleep(1)
//            }
//        }
        launch(Dispatchers.Unconfined) {
            println("coroutineContext with thread - ${Thread.currentThread().name}")
        }.join()

        val job = withTimeoutOrNull(100) {
            println("${this.coroutineContext} : in thread ${Thread.currentThread().name}")
            repeat(1000) {
                yield()
                print(".")
                Thread.sleep(1)
            }
        }
        if (job == null) {
            print("timed out")
        }
        delay(100)
    }
}