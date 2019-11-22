package ru.vlados.rxhomework

import org.junit.Test
import ru.vlados.rxhomework.internal.forTest
import ru.vlados.rxhomework.internal.forTestWithError
import java.lang.IllegalArgumentException

class HwTest {

    // make sure that
    //     value at index 0 equals 5;
    //     value at index 2 equals 1;
    //     errors are not present;
    //     chain is completed
    @Test
    fun task6() {
        forTest().test().await()
            .assertValueAt(0, 5)
            .assertValueAt(2, 1)
            .assertNoErrors()
            .assertComplete()
    }

    // make sure that
    //     values are the same with [1, 2, 3, 4];
    //     type of error is IllegalArgumentException
    @Test
    fun task7() {
        forTestWithError().test().await()
            .assertValueSequence(listOf(1, 2, 3, 4))
            .assertError(IllegalArgumentException::class.java)
    }

}