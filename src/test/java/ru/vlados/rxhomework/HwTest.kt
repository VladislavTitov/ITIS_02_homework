package ru.vlados.rxhomework

import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import ru.vlados.rxhomework.internal.forTest
import ru.vlados.rxhomework.internal.forTestWithError

class HwTest {

    // make sure that
    //     value at index 0 equals 5;
    //     value at index 2 equals 1;
    //     errors are not present;
    //     chain is completed
    @Test
    fun task6() {
        forTest().test()
            .assertValueAt(0, 5)
            .assertValueAt(2, 1)
            .assertComplete()
            .assertNoErrors()
    }

    // make sure that
    //     values are the same with [1, 2, 3, 4];
    //     type of error is IllegalArgumentException
    @Test
    fun task7() {

        RxJavaPlugins.setIoSchedulerHandler {
            Schedulers.trampoline()
        }

        forTestWithError().subscribeOn(Schedulers.io())
            .test()
            .assertValues(1, 2, 3, 4)
            .assertError(IllegalArgumentException::class.java)
    }

}