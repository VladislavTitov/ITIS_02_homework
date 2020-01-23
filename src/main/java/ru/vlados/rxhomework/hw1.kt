@file:JvmName("Main")
package ru.vlados.rxhomework

import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import ru.vlados.rxhomework.internal.Permission
import ru.vlados.rxhomework.internal.getUsersFromSource1
import ru.vlados.rxhomework.internal.getUsersFromSource2
import ru.vlados.rxhomework.internal.withError
import java.util.*
import java.util.concurrent.TimeUnit

// flatMap users to permissions,
// distinct their
fun task1(): Flowable<Permission> {
    return getUsersFromSource1()
            .flatMapIterable { it.permissions }
            .distinct()
}

// flatMap users to permissions with random delay,
// distinct their
fun task2(): Flowable<Permission> {
    val random = Random()
    val bound = 9
    return getUsersFromSource2()
            .flatMap {
                Flowable.fromIterable(it.permissions)
                        .delay(random.nextInt(bound) + 1L, TimeUnit.SECONDS)
            }.distinct()
}

// concatMap users to permissions with random delay,
// distinct their
fun task3(): Flowable<Permission> {
    val random = Random()
    val bound = 9
    return getUsersFromSource2()
            .concatMap {
                Flowable.fromIterable(it.permissions)
                        .delay(random.nextInt(bound) + 1L, TimeUnit.SECONDS)
            }
            .distinct()
}

// map user from different sources to usernames,
// merge their
fun task4(): Flowable<String> {
    return Flowable.merge(getUsersFromSource1().map { it.username }, getUsersFromSource2().map { it.username })
            .distinct()
}

// implement 2 times retrying
// with linear-grown delay,
// step equals 2 sec;
// on 3rd time throw error
fun task5(): Flowable<Int> {
    val startRange = 1
    val endRange = 3
    val count = 2
    val step = 2
    return withError()
            .retryWhen {
                it.zipWith(Flowable.range(startRange, endRange),
                        BiFunction<Throwable, Int, Int?> {
                            error: Throwable, retryCount: Int ->
                            if (retryCount > count) {
                               throw error
                            } else {
                              retryCount
                            }
                        }
                )
                .flatMap { retryCount ->
                    println("delay retry by " + retryCount * 2 + " second(s)");
                    Flowable.timer(retryCount.toLong() * step, TimeUnit.SECONDS)
                }
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