package com.neutron.baselib.base

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.neutron.baselib.utils.Slog
import com.neutron.baselib.utils.toast

abstract class BaseVMActivity<VM : BaseViewModel>(private val viewModelClass: Class<VM>) :
    IBaseActivity() {

    lateinit var mViewModel: VM

    override fun initBaseView() {
        createViewModel()
        observer()
        initView()
    }

    abstract fun initView()

    private fun observer() {
        mViewModel.registerListener {
            showLoading {
                Slog.d("===showLoading===")
            }
            closeLoading {
                Slog.d("===closeLoading===")
            }

            showErrorTip {
                toast(it)
            }
        }
    }

    private fun createViewModel() {
        mViewModel = ViewModelProvider(this).get(viewModelClass)
    }


}