package eu.mobilebear.cv.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RxFactory {

    val ioScheduler: Scheduler
        get() = Schedulers.io()

    val computationScheduler: Scheduler
        get() = Schedulers.computation()

    val singleThreadScheduler: Scheduler
        get() = Schedulers.single()

    val mainThreadScheduler: Scheduler
        get() = AndroidSchedulers.mainThread()
}
