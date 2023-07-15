package com.arifwidayana.musiclens.arch.base

import java.lang.Exception

/**
 * BaseContract created to make abstract function inheritance especially for BaseActivity
 * and immutable change in child class to ensure the code can't be changed arbitrarily
*/
interface BaseContract {
    fun showMessageToast(message: String? = null, exception: Exception? = null)
    fun showMessageSnackbar(message: String? = null, exception: Exception? = null)
}