package com.example.core.di

import javax.inject.Scope

@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope

@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class FragmentScope

@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class ChildFragmentScope