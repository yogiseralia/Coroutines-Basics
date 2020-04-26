Composing Functions & Returning Data from Coroutines
-----------------------------------------------------

Until now, coroutines have been fire and forget.
 - What if we want to get data from a coroutine?
 - Or discover why the coroutine have finished?
 
To do this - 

**Using async Coroutine Builder-**

 - **launch** coroutine builder returns a job object. What **launch** doesn't do is return a data?
 - **async** coroutine builder returns a Deffered.
   - Can use this later in your code (Like ``Future`` in java)
   - Derives from Job
   
   
Demo 
--

Suspend function is a block which is async with respect to other blocks of code, but itself is an synchronous block. Now to reduce the computation time, we can use ``async`` block inside this function to do async operations.

for e.g. - 

`````kotlin - 
fun main() = runBlocking{

    val job = launch {
        val timeMillis = measureTimeMillis {
            doWork1()
            doWork2()
            println("result = ${doWork1() + doWork2()}")
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
`````

OUTPUT-

````text
result = 13
Done in 610 millis
````
 
Now using ``async`` coroutine builder, we can reduce time of whole launch{} block.

````kotlin
fun main() = runBlocking{
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
````
OUTPUT - 

````text
result = 13
Done in 208 millis
````

We can see significant reduction in time, since two async blocks can run simultaneously.
