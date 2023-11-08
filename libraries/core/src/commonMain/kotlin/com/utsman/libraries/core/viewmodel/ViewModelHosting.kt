package com.utsman.libraries.core.viewmodel

import kotlinx.atomicfu.locks.SynchronizedObject
import kotlinx.atomicfu.locks.synchronized
import kotlin.native.concurrent.ThreadLocal

@OptIn(ExperimentalStdlibApi::class)
class ViewModelHost : AutoCloseable {

    private val viewModels = mutableMapOf<String, ViewModel>()

    fun <T: ViewModel> getViewModel(viewModel: T): T {
        val key = viewModel::class.simpleName.orEmpty()
        return viewModels.getOrPut(key) {
            viewModel
        } as T
    }

    override fun close() {
        viewModels.clear()
    }

    @ThreadLocal
    companion object : SynchronizedObject() {

        private var _host: ViewModelHost? = null

        fun getInstance(): ViewModelHost {
            return synchronized(this) {
                (_host ?: ViewModelHost()).also {
                    _host = it
                }
            }
        }
    }
}