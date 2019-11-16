@file:JvmName("Main")
package ru.vlados.rxhomework

import io.reactivex.Flowable
import ru.vlados.rxhomework.internal.Permission

// flatMap users to permissions,
// distinct their
fun task1(): Flowable<Permission> {
    // return getUsersFromSource1()...
    TODO()
}

// flatMap users to permissions with random delay,
// distinct their
fun task2(): Flowable<Permission> {
    // return getUsersFromSource2()...
    TODO()
}

// concatMap users to permissions with random delay,
// distinct their
fun task3(): Flowable<Permission> {
    // return getUsersFromSource2()...
    TODO()
}

// map user from different sources to usernames,
// merge their
fun task4(): Flowable<String> {
    TODO()
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