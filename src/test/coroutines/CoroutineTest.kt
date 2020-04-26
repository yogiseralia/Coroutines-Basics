package coroutines

import coroutine.doWork
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class CoroutineTest {

    @Test
    fun firstTest() = runBlocking{
        Assert.assertEquals(2,2)
        doWork()
    }
}