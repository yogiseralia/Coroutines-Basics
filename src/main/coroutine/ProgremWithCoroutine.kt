package coroutine

import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

val SEQUENTIAL_THRESHOLD = 5000


suspend fun compute(high: Int, low: Int, array: IntArray): Long {
    return if (high - low <= SEQUENTIAL_THRESHOLD) {
        (low until high).map { array[it].toLong() }
            .sum()
    } else {
        val mid = low + (high - low) / 2
        val left =  compute(array = array, low = low, high = mid)
        val right = compute(array = array, low = mid, high = high)
        return left + right
    }
}


fun main() = runBlocking {
    val list = mutableListOf<Int>()
    var limit = 20_000_000
    while (limit > 0) {
        list.add(limit--)
    }

    var result = 0L
    var time = measureTimeMillis {
        result = compute(array = list.toIntArray(), low = 0, high = list.toIntArray().size)
    }

    print("$result in $time ms")
}