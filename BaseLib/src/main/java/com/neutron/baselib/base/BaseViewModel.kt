package com.neutron.baselib.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neutron.baselib.net.LiveRepository
import com.neutron.baselib.utils.Slog
import com.neutron.baselib.utils.toast
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


abstract class BaseViewModel : ViewModel() {

    private var mOnLoadingListener: OnLoadingListener? = null
    protected val mLiveApiRepository = LiveRepository

    protected fun <T> request(
        block: suspend () -> T,
        result: T.() -> Unit,
        error: (suspend (Throwable) -> Unit) = {},
        isShowLoading: Boolean = false,
        loadingMsg: String = ""
    ): Job {
        return viewModelScope.launch {
            runCatching {
                if (isShowLoading)
                    mOnLoadingListener?.onShowLoading?.invoke(loadingMsg)
                block()
            }.onSuccess {
                mOnLoadingListener?.onCloseLoading?.invoke()
                it?.let { result(it) }
                Slog.d("API访问正常  $it")

            }.onFailure {
                mOnLoadingListener?.onCloseLoading?.invoke()
                it.printStackTrace()
                error(it)
                it.message?.let {
                    BaseApplication.sContext.toast(it)
                }
                Slog.e("API访问错误  $it")
            }
        }
    }


    fun registerListener(onLoadingListener: OnLoadingListener.() -> Unit) {
        mOnLoadingListener = OnLoadingListener().also(onLoadingListener)
    }


    class OnLoadingListener {
        var onShowLoading: (String) -> Unit = {}

        var onCloseLoading: (() -> Unit) = {}

        var onShowErrorTip: (String) -> Unit = {}

        fun showLoading(block: (String) -> Unit) {
            onShowLoading = block
        }

        fun closeLoading(block: () -> Unit) {
            onCloseLoading = block
        }

        fun showErrorTip(block: (String) -> Unit) {
            onShowErrorTip = block
        }

    }

}