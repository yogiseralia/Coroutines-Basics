package coroutine.channels

import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

data class Comment(var count: Int)

fun main() = runBlocking<Unit> {
    val discussion = Channel<Comment>()

    launch(coroutineContext) { child("he did it",discussion)  }
    launch(coroutineContext) { child("she did it",discussion)  }

    discussion.send(Comment(0))
    delay(1000)
    coroutineContext.cancel()
}

suspend fun child(text:String,discussion:Channel<Comment>){
    for (comment in discussion) {
        comment.count++
        println("$text $comment")
        delay(100)
        discussion.send(comment)
    }
}