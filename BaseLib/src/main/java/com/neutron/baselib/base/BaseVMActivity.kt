package com.neutron.baselib.base

import androidx.lifecycle.ViewModelProvider
import com.neutron.baselib.utils.toast

abstract class BaseVMActivity<VM : BaseViewModel>(private val viewModelClass: Class<VM>) :
    IBaseActivity() {

    lateinit var mViewModel: VM

    override fun initBaseView() {
        createViewModel()
        observer()
        initView()
        initData()
        observeValue()
    }

    abstract fun initView()
    abstract fun initData()
    abstract fun observeValue()

    private fun observer() {
        mViewModel.registerListener {
            showLoading {
               showLoading()
            }
            closeLoading {
                hideLoading()
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