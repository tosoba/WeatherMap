package com.example.there.weathermap

import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.util.concurrent.Callable
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class RxImmediateSchedulerRule : TestRule {
    private val immediate = object : Scheduler() {
        override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
            // this prevents StackOverflowErrors when scheduling with a delay
            return super.scheduleDirect(run, 0, unit)
        }

        override fun createWorker(): Scheduler.Worker {
            return ExecutorScheduler.ExecutorWorker(Executor { runnable -> runnable.run() })
        }
    }

    override fun apply(base: Statement, description: Description): Statement = object : Statement() {
        override fun evaluate() {
            val handler: (Callable<Scheduler>) -> Scheduler = { _ -> immediate }

            RxJavaPlugins.setInitIoSchedulerHandler(handler)
            RxJavaPlugins.setInitComputationSchedulerHandler(handler)
            RxJavaPlugins.setInitNewThreadSchedulerHandler(handler)
            RxJavaPlugins.setInitSingleSchedulerHandler(handler)
            RxAndroidPlugins.setInitMainThreadSchedulerHandler(handler)

            try {
                base.evaluate()
            } finally {
                RxJavaPlugins.reset()
                RxAndroidPlugins.reset()
            }
        }
    }
}
