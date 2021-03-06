package com.example.core.rx

import io.reactivex.FlowableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.SingleTransformer

abstract class SymmetricObservableTransformer<T> : ObservableTransformer<T, T>

abstract class SymmetricFlowableTransformer<T> : FlowableTransformer<T, T>

abstract class SymmetricSingleTransformer<T> : SingleTransformer<T, T>
