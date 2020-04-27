Composing Functions & Returning Data from Coroutines
-----------------------------------------------------

Until now, coroutines have been fire and forget.
 - What if we want to get data from a coroutine?
 - Or discover why the coroutine have finished?
 
To do this - 

**Using async Coroutine Builder-**

 - **launch** coroutine builder returns a job object. What **launch** doesn't do is return a data?
 - **async** coroutine builder returns a [``Deffered``](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-deferred/).
   - Can use this later in your code (Like [``Future``](https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/Future.html) in java)
   - Derives from Job
   - ``Deffered``'s ``await()`` method block the execution of current block till the async block executes.   
   
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

Now let's see what's the real power of async await is? 

For e.g. -
`````kotlin
val job = launch {
    val async = async(coroutineContext) {
        println(doWork1())
    }
    async.await()
    println(doWork2())
}
`````

OUTPUT - 

````text
12
1
````
Here ``doWork2()`` will always execute after ``doWork1()``. Since ``async`` blocks the execution till ``await()`` called up. (this effect is just like ``join``).


Making Async Functions
---

````kotlin
private fun CoroutineScope.doWorkAsync(msg: String): Deferred<Int> = async {
    log("$msg - working...")
    delay(1000)
    log("$msg - work done")
    return@async 23
}
````

Here above code shows the return type is ``Deferred<Int>``, which enables this function to be called asynchronously.
Now this function is extension function of CoroutineScope. Since ``async`` can't be called outside a Coroutine Scope.

Calling of this method would be - 

````kotlin
fun main() = runBlocking {
    println(doWorkAsync("working").await())
}
````


Lazy Evaluation using ``async``
------------------------------

Normally async methods executes as normal methods i.e.- executing on calling.
But what if we want execution at certain time? In this case we want execution 
of function on calling the ``await()`` method on this function.  

To test that the function calls eagerly - 

````kotlin
fun main() = runBlocking {
    val deferred = async { doWork1() }
}
````

OUTPUT - 

````text
12
````

Here we didn't called ``await()`` and still async block got executed.

**Solution** - 

````kotlin
fun main() = runBlocking {
    val deferred = async(start = CoroutineStart.LAZY) { doWork1() }
}
````
OUTPUT - 
````text

````
Now we will call ``await()`` on deffered object and ``doWork1()`` should get execute.

````kotlin
fun main(): Unit = runBlocking {
    val deferred = async(start = CoroutineStart.LAZY) { doWork1() }
    print(deferred.await().toString())
}
````

OUTPUT - 

````text
12
````