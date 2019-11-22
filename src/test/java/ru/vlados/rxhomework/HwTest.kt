package ru.vlados.rxhomework

import io.reactivex.Flowable
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
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
        RxJavaPlugins.setIoSchedulerHandler {
            Schedulers.trampoline()
        }

        val testSubscriber = forTest().test()
        testSubscriber.assertValueAt(0, 5)
        testSubscriber.assertValueAt(2, 1)
        testSubscriber.assertNoErrors()
        testSubscriber.assertComplete()

    }

    // make sure that
    //     values are the same with [1, 2, 3, 4];
    //     type of error is IllegalArgumentException
    @Test
    fun task7() {
        RxJavaPlugins.setIoSchedulerHandler {
            Schedulers.trampoline()
        }
        val testSubscriber = forTestWithError().test()
        testSubscriber.assertValues(1, 2, 3, 4)
        testSubscriber.assertError(IllegalArgumentException::class.java)
    }

}