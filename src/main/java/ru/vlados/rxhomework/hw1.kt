@file:JvmName("Main")

package ru.vlados.rxhomework

import io.reactivex.Flowable
import io.reactivex.Flowable.just
import ru.vlados.rxhomework.internal.*
import java.util.concurrent.TimeUnit

object Random {
    val random = java.util.Random()
    fun next(): Long {
        return random.nextLong() % 1000
    }
}

// flatMap users to permissions,
// distinct them
fun task1(): Flowable<Permission> {
    return getUsersFromSource1().flatMap {
        Flowable.fromIterable(it.permissions)
    }.distinct()
}

// flatMap users to permissions with random delay,
// distinct them
fun task2(): Flowable<Permission> {
    return getUsersFromSource2().flatMap {
        Flowable.fromIterable(it.permissions)
    }.distinct().delay(Random.next(), TimeUnit.MILLISECONDS)
}

// concatMap users to permissions with random delay,
// distinct them
fun task3(): Flowable<Permission> {
    return getUsersFromSource2().concatMap {
        Flowable.fromIterable(it.permissions)
    }.distinct().delay(Random.next(), TimeUnit.MILLISECONDS)
}

// map user from different sources to usernames,
// merge them
fun task4(): Flowable<String> {
    return Flowable.merge(
        getUsersFromSource1(),
        getUsersFromSource2()
    ).map(User::username)
}

// implement 2 times retrying
// with linear-grown delay,
// step equals 2 sec;
// on 3rd time throw error
fun task5(): Flowable<Int> {
    return withError().retryWhen { errors ->
        errors.zipWith(1..3) { e, i -> e to i }
            .flatMap { just(it).delay(2 * it.second.toLong(), TimeUnit.SECONDS) }
            .map {
                when (it.second) {
                    3 -> throw it.first
                    else -> it
                }
            }
    }
}

fun main() {
//    task1().blockingForEach { println(it) }
//    println()
//
//    task2().blockingForEach { println(it) }
//    println(
//
//    task3().blockingForEach { println(it) }
//    println()
//
//    task4().blockingForEach { println(it) }
//    println()
//
//    task3().blockingForEach { println(it) }
//    println()
//
//    task4().blockingForEach { println(it) }
//    println()

    task5().blockingSubscribe(::println, ::println)
}
