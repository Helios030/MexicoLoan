package com.neutron.mexicoloan.ui.authentication.work

import androidx.lifecycle.MutableLiveData
import com.neutron.baselib.base.BaseViewModel
import com.neutron.baselib.bean.CityBeanResult
import com.neutron.baselib.bean.SWorkInfoResult
import com.neutron.baselib.utils.PreferencesHelper
import com.neutron.baselib.utils.createBody

class WorkVM : BaseViewModel() {
    val sWorkInfoResult: MutableLiveData<SWorkInfoResult> = MutableLiveData()
    val CityBeanResults: MutableLiveData<List<CityBeanResult>> = MutableLiveData()
    val isUploadSuccess: MutableLiveData<Boolean> = MutableLiveData()


    fun getWorkInfo() {
        request({
            val map = HashMap<String, Any>()
            map["user_id"] = PreferencesHelper.getUserID()
            mLiveApiRepository.getServiceWorkInfo(map.createBody())
        }, {
            sWorkInfoResult.postValue(this)
        }, {}, isShowLoading = true)
    }

    fun getCityList(cityId: String) {
        request({
            val map = HashMap<String, Any>()
            map["address_no"] = cityId
            mLiveApiRepository.getCityById(map.createBody())
        }, {
            CityBeanResults.postValue(this)
        }, {}, isShowLoading = true)
    }


    fun uploadUserWorkInfo(map: HashMap<String, Any>) {
        request({
            mLiveApiRepository.uploadUserWorkInfo(map.createBody())
        }, {
            isUploadSuccess.postValue(true)
        }, {}, isShowLoading = true)
    }


}