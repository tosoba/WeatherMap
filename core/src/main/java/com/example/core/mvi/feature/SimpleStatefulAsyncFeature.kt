package com.example.core.mvi.feature

import com.badoo.mvicore.element.Actor
import com.badoo.mvicore.element.Bootstrapper
import com.badoo.mvicore.element.NewsPublisher
import com.badoo.mvicore.element.Reducer
import com.badoo.mvicore.feature.ActorReducerFeature
import com.example.core.mvi.news.ErrorOnlyNews
import com.example.core.mvi.news.ResultNews
import com.example.core.mvi.state.AsyncState
import com.example.core.mvi.wish.LoadAsyncWish
import io.reactivex.Observable
import io.reactivex.ObservableTransformer


open class SimpleStatefulAsyncFeature<Input : Any, Value : Any, Result : Any, News : Any>(
        initialState: AsyncState<Value>,
        actor: ActorImpl<Input, Value>,
        reducer: ReducerImpl<Value, Result>,
        newsPublisher: NewsPublisher<LoadAsyncWish<Input>, Effect, AsyncState<Value>, News>,
        bootstrapper: Bootstrapper<LoadAsyncWish<Input>>? = null
) : ActorReducerFeature<LoadAsyncWish<Input>, SimpleStatefulAsyncFeature.Effect, AsyncState<Value>, News>(
        initialState = initialState,
        actor = actor,
        reducer = reducer,
        newsPublisher = newsPublisher,
        bootstrapper = bootstrapper
) {

    sealed class Effect {
        object StartedLoading : Effect()
        data class Loaded<Result : Any>(val result: Result) : Effect()
        data class ErrorLoading(val throwable: Throwable) : Effect()
    }

    open class ReducerImpl<Value : Any, Result : Any>(
            private val mapToStateValue: Result.(AsyncState<Value>) -> Value
    ) : Reducer<AsyncState<Value>, Effect> {

        @Suppress("UNCHECKED_CAST")
        override fun invoke(
                previousState: AsyncState<Value>,
                effect: Effect
        ): AsyncState<Value> = when (effect) {
            is Effect.StartedLoading -> previousState.copy(isLoading = true)
            is Effect.Loaded<*> -> previousState.copy(
                    isLoading = false,
                    value = (effect.result as Result).mapToStateValue(previousState)
            )
            is Effect.ErrorLoading -> previousState.copy(isLoading = false)
        }
    }

    open class ActorImpl<Input : Any, Value : Any>(
            private val transformer: ObservableTransformer<Effect, Effect>,
            private val mapInputToEffect: Input.(AsyncState<Value>) -> Observable<out Effect>
    ) : Actor<AsyncState<Value>, LoadAsyncWish<Input>, Effect> {

        override fun invoke(
                previousState: AsyncState<Value>,
                wish: LoadAsyncWish<Input>
        ): Observable<out Effect> = wish.input.mapInputToEffect(previousState)
                .compose(transformer)
                .startWith(Effect.StartedLoading)
                .onErrorReturn { Effect.ErrorLoading(it) }
    }

    open class ErrorNewsPublisher<Input : Any, Value : Any> : NewsPublisher<LoadAsyncWish<Input>, Effect, AsyncState<Value>, ErrorOnlyNews> {

        override fun invoke(
                wish: LoadAsyncWish<Input>,
                effect: Effect,
                state: AsyncState<Value>
        ): ErrorOnlyNews? = when (effect) {
            is Effect.ErrorLoading -> ErrorOnlyNews(effect.throwable)
            else -> null
        }
    }

    open class AllResultsNewsPublisher<Input : Any, Value : Any> : NewsPublisher<LoadAsyncWish<Input>, Effect, AsyncState<Value>, ResultNews> {

        override fun invoke(
                wish: LoadAsyncWish<Input>,
                effect: Effect,
                state: AsyncState<Value>
        ): ResultNews? = when (effect) {
            is Effect.StartedLoading -> ResultNews.StartedLoading
            is Effect.Loaded<*> -> ResultNews.Loaded(effect.result)
            is Effect.ErrorLoading -> ResultNews.ErrorLoading(effect.throwable)
        }
    }
}