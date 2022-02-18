package com.balc2013.albanote.domain.dto

interface BaseDTO<T> {
    fun convertToModel(): T
}

fun <T> List<BaseDTO<T>>.convertToModels(): List<T> {
    return this.map { it.convertToModel() }
}