package com.neutron.mexicoloan.ui.authentication.card

import androidx.lifecycle.MutableLiveData
import com.neutron.baselib.base.BaseViewModel
import com.neutron.baselib.bean.BankInfoResult
import com.neutron.baselib.bean.SContactInfoResult
import com.neutron.baselib.bean.SMoneyCardInfoResult
import com.neutron.baselib.utils.PreferencesHelper
import com.neutron.baselib.utils.createBody

class CardVM:BaseViewModel() {
    val isUploadSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val sMoneyCardInfoResult: MutableLiveData<SMoneyCardInfoResult> = MutableLiveData()
    val BankInfoResults: MutableLiveData<List<BankInfoResult>> = MutableLiveData()

    fun getServiceMoneyCard() {
        request({
            val map = HashMap<String, Any>()
            map["user_id"] = PreferencesHelper.getUserID()
            mLiveApiRepository.getServiceMoneyCard(map.createBody())
        }, {
            sMoneyCardInfoResult.postValue(this)
        }, {}, isShowLoading = true)
    }

    fun getBankInfo() {
        request({
            val map = java.util.HashMap<String, Any>();
            map["pay_type"] = "1"
            mLiveApiRepository.getBankInfo(map.createBody())
        }, {
            BankInfoResults.postValue(this)
        }, {}, isShowLoading = true)
    }

    fun uploadMoneyCardInfo(map: HashMap<String, Any>) {
        request({
            mLiveApiRepository.uploadMoneyCardInfo(map.createBody())
        }, {
            isUploadSuccess.postValue(true)
        }, {}, isShowLoading = true)
    }
}