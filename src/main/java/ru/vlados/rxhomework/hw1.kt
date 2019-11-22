@file:JvmName("Main")

package ru.vlados.rxhomework

import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import ru.vlados.rxhomework.internal.Permission
import ru.vlados.rxhomework.internal.getUsersFromSource1
import ru.vlados.rxhomework.internal.getUsersFromSource2
import ru.vlados.rxhomework.internal.withError
import java.util.concurrent.TimeUnit
import kotlin.random.Random

// flatMap users to permissions,
// distinct their
fun task1(): Flowable<Permission> {
    return getUsersFromSource1().flatMap {
        Flowable.fromIterable(it.permissions)
    }.distinct()
}

// flatMap users to permissions with random delay,
// distinct their
fun task2(): Flowable<Permission> {
    return getUsersFromSource2().flatMap {
        Flowable.fromIterable(it.permissions)
    }.distinct().delay(Random.nextInt(1, 5).toLong(), TimeUnit.SECONDS)
}

// concatMap users to permissions with random delay,
// distinct their
fun task3(): Flowable<Permission> {
    return getUsersFromSource2().concatMap {
        Flowable.fromIterable(it.permissions)
    }.distinct().delay(Random.nextInt(1, 5).toLong(), TimeUnit.SECONDS)
}

// map user from different sources to usernames,
// merge their
fun task4(): Flowable<String> {
    return Flowable.merge(
        getUsersFromSource1(),
        getUsersFromSource2()
    ).map { it.username }
}

// implement 2 times retrying
// with linear-grown delay,
// step equals 2 sec;
// on 3rd time throw error
fun task5(): Flowable<Int> {
    return withError().retryWhen {
        Flowable.zip<Throwable, Int, Int>(it, Flowable.range(1, 3),
            BiFunction { error, count ->
                if (count == 3) throw error else count
            }).delay { count -> Flowable.timer(count * 2L, TimeUnit.SECONDS) }
    }
}

fun main() {
    task1().blockingForEach { println(it) }
    println()

    task2().blockingForEach { println(it) }
    println()

    task3().blockingForEach { println(it) }
    println()

    task4().blockingForEach { println(it) }
    println()

    task5().blockingSubscribe({
        println(it)
    }, {
        println(it)
    })
    println()
}