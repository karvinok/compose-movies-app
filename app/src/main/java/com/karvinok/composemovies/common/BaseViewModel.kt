package com.karvinok.composemovies.common

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.karvinok.composemovies.BuildConfig
import com.karvinok.composemovies.util.ext.postSingleValue
import com.karvinok.composemovies.util.ext.timber
import com.karvinok.data.common.BaseError
import com.karvinok.data.common.CacheManager
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineRunner, KoinComponent {

    protected val cache: CacheManager by inject()

    override val parentJob = SupervisorJob()

    private val _viewAction: MutableStateFlow<UIAction?> = MutableStateFlow(null)
    val viewAction: StateFlow<UIAction?> = _viewAction

    protected val _loadingState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState

    final override fun <P> doOnBackground(
        showPreloader: Boolean,
        doOnAsyncBlock: suspend CoroutineScope.() -> P
    ): Job {
        var job: Job = Job()
        try {
            if (showPreloader) {
                setLoading(true)
            }
            job = doCoroutineWork(doOnAsyncBlock, Dispatchers.IO) {
                if (showPreloader) stopLoading()
            }
        } catch (e: Exception) {
            job.cancel(e.localizedMessage)
            if (showPreloader) stopLoading()
            Timber.e(e)
        }
        return job
    }

    override fun <P> doOnUI(doOnAsyncBlock: suspend CoroutineScope.() -> P): Job {
        return doCoroutineWork(doOnAsyncBlock, Dispatchers.Main) {}
    }

    private fun stopLoading() {
        setLoading(false)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancelChildren()
        parentJob.cancel()
    }

    private inline fun <P> doCoroutineWork(
        crossinline doOnAsyncBlock: suspend CoroutineScope.() -> P,
        context: CoroutineContext,
        crossinline doOnCompleteblock: suspend () -> Unit
    ): Job {
        return scope.launch(context) {
            doOnAsyncBlock.invoke(this)
            doOnCompleteblock.invoke()
        }
    }

    fun postAction(action: UIAction) {
        doOnUI {
            _viewAction.postSingleValue(action)
        }
    }

    /**
     * can be used as a block to handle exceptions
     * repository.requestData().either(::handleError, data)
     */
    protected open fun handleError(error: BaseError) {
        doOnUI {
            when (error) {
                is BaseError.ServerError -> {
                    showMessage(error.message.apply {
                        if (BuildConfig.DEBUG) plus(" ${error.code}")
                    })
                    //err code 401 -> logout etc
                }
                else -> {
                    showMessage(error.localizedMessage)
                }
            }
        }
    }

    fun closeScreen() {
        postAction(CloseScreen())
    }

    fun setLoading(loading: Boolean) {
        doOnUI {
            _loadingState.value = loading
        }
    }

    protected fun showMessage(msg: String?) {
        timber("showMessage: $msg")
        postAction(ShowMessage(msg ?: "message_null"))
    }

    protected fun showMessage(@StringRes res: Int) {
        timber("showMessage: $res")
        postAction(ShowMessageRes(res))
    }

    interface UIAction

    class CloseScreen : UIAction
    class OpenAuthScreen : UIAction
    class ShowMessage(val msg: String) : UIAction
    class ShowMessageRes(val res: Int) : UIAction
}