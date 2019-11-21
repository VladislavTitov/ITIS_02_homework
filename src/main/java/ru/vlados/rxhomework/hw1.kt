@file:JvmName("Main")

package ru.vlados.rxhomework

import io.reactivex.Flowable
import ru.vlados.rxhomework.internal.Permission
import ru.vlados.rxhomework.internal.getUsersFromSource1
import ru.vlados.rxhomework.internal.getUsersFromSource2
import java.util.concurrent.TimeUnit

// flatMap users to permissions,
// distinct their
fun task1(): Flowable<Permission> {
    // return getUsersFromSource1()...
    return getUsersFromSource1()
            .flatMapIterable {
                it.permissions
            }
            .distinct()
}

// flatMap users to permissions with random delay,
// distinct their
fun task2(): Flowable<Permission> {
    // return getUsersFromSource2()...
    return getUsersFromSource2()
            .flatMap {
                Flowable.fromIterable(it.permissions)
                        .delay((1000..4000).random().toLong(), TimeUnit.MILLISECONDS)
            }
            .distinct()
}

// concatMap users to permissions with random delay,
// distinct their
fun task3(): Flowable<Permission> {
    // return getUsersFromSource2()...
    return getUsersFromSource2()
            .delay((1000..4000).random().toLong(), TimeUnit.MILLISECONDS)
            .concatMapIterable {
                it.permissions
            }
            .distinct()
}

// map user from different sources to usernames,
// merge their
fun task4(): Flowable<String> {
    return Flowable.merge(getUsersFromSource1(), getUsersFromSource2())
            .map {
                it.username
            }
}

// implement 2 times retrying
// with linear-grown delay,
// step equals 2 sec;
// on 3rd time throw error
fun task5(): Flowable<Int> {
    // return withError()...
    TODO()
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
