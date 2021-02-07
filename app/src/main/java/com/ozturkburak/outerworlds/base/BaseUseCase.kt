package com.ozturkburak.outerworlds.base

interface BaseUseCase<T> {
    fun execute() : T
}