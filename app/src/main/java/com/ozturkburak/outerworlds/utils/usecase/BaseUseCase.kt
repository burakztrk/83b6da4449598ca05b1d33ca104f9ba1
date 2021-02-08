package com.ozturkburak.outerworlds.utils.usecase

interface BaseUseCase<T> {
    fun execute() : T
}