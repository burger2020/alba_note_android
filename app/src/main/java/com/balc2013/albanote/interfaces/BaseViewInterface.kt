package com.balc2013.albanote.interfaces

interface BaseViewInterface {
    val initUnit: () -> Unit

    fun onInitUnit() {
        initUnit.invoke()
    }
}