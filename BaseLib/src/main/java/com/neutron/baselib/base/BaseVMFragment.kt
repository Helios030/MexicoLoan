package com.neutron.baselib.base

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.neutron.baselib.utils.toast


abstract class BaseVMFragment<VM : BaseViewModel>(
    private val viewModelClass: Class<VM>,
) : BaseFragment() {

    protected lateinit var mViewModel: VM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        createViewModel()
        observer()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun createViewModel() {
        mViewModel = ViewModelProvider(this).get(viewModelClass)
    }

    private fun observer() {
        mViewModel.registerListener {
            showLoading {
                showLoading()
            }
            closeLoading {
                hideLoading()
            }
            showErrorTip {
                mActivity.toast(it)
            }
        }
    }


}