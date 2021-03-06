package com.neutron.mexicoloan.ui.confirm

import androidx.lifecycle.MutableLiveData
import com.neutron.baselib.base.BaseViewModel
import com.neutron.baselib.bean.ConfirmInfoResult
import com.neutron.baselib.bean.RequestOrderResult
import com.neutron.baselib.utils.PreferencesHelper
import com.neutron.baselib.utils.createBody
import com.neutron.baselib.utils.trackClickVaEvent
import com.neutron.baselib.utils.trackCommitLoanSuccessEvent

class ConfirmVM :BaseViewModel() {


    val confirmationRequest:MutableLiveData<ConfirmInfoResult> = MutableLiveData()
    val requestOrderResult:MutableLiveData<RequestOrderResult> = MutableLiveData()



    fun confirmInfo(id:String) {

        val map = java.util.HashMap<String, Any>();
        map["user_id"] = PreferencesHelper.getUserID()
        map["product_id"] = id

        request({
            mLiveApiRepository.confirmInfo(map.createBody())
        }, {
            confirmationRequest.postValue(this)
        }, {}, isShowLoading = true)
    }


    fun uploadRequest(map: HashMap<String, Any>) {
        request({
            mLiveApiRepository.uploadRequest(map.createBody())
        }, {


            requestOrderResult.postValue(this)


             PreferencesHelper.getUserInfo()?.let {

                 trackCommitLoanSuccessEvent(it.phone,map["product_id"].toString())

             }


        }, {}, isShowLoading = true)
    }

}