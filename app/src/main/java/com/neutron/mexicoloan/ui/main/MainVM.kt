package com.neutron.mexicoloan.ui.main

import androidx.lifecycle.MutableLiveData
import com.neutron.baselib.base.BaseViewModel
import com.neutron.baselib.bean.LoanStatusResult
import com.neutron.baselib.bean.UserConfigResult
import com.neutron.baselib.utils.PreferencesHelper
import com.neutron.baselib.utils.Slog
import com.neutron.baselib.utils.createBody

class MainVM :BaseViewModel() {

    val statusResult: MutableLiveData<LoanStatusResult> = MutableLiveData()
    val configResult: MutableLiveData<UserConfigResult> = MutableLiveData()

    fun getRequestState() {
        request({
            val map = HashMap<String, Any>();
            map["user_id"] = PreferencesHelper.getUserID()
            mLiveApiRepository.getRequestState(map.createBody())

        }, {
            Slog.d("statusResult  ==  $this")
            statusResult.postValue(this)
        }, {

        }, isShowLoading = true)
    }



    fun getUserConfig() {
        request({
            val map = HashMap<String, Any>();
             map["user_id"] = PreferencesHelper.getUserID()
            mLiveApiRepository.getUserConfig(map.createBody())
        }, {
            Slog.d("configResult  ==  $this")
            configResult.postValue(this)
        }, {

        }, isShowLoading = false)
    }


}