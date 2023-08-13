package com.arifwidayana.musiclens.arch.base

import com.arifwidayana.musiclens.arch.wrapper.ViewResource
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * BaseUseCase created for business flow to ensure avoid duplication code
 * and make it easy for testing
 */
abstract class BaseUseCase<P, R : Any?> (private val scheduler: Scheduler) {
    operator fun invoke(param: P? = null): Observable<ViewResource<R>> {
        return execute(param)
            .onErrorReturn { ViewResource.Error(Exception(it)) }
            .startWithItem(ViewResource.Loading())
            .subscribeOn(scheduler)
            .observeOn(Schedulers.computation())
    }

    @Throws(RuntimeException::class)
    abstract fun execute(param: P? = null): Observable<ViewResource<R>>
}
