package com.neutron.mexicoloan.ui.authentication.userinfo

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.neutron.baselib.base.BaseViewModel
import com.neutron.baselib.bean.CityBeanResult
import com.neutron.baselib.bean.SContactInfoResult
import com.neutron.baselib.bean.SUserInfoResult
import com.neutron.baselib.utils.PreferencesHelper
import com.neutron.baselib.utils.createBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PersonalInfoVM :BaseViewModel() {
    val isUploadSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val CityBeanResults: MutableLiveData<List<CityBeanResult>> = MutableLiveData()
    val sUserInfoResult: MutableLiveData<SUserInfoResult> = MutableLiveData()

    fun getCityById(cityId: String) {
        request({
            val map =HashMap<String, Any>();
            map["address_no"] = cityId
            mLiveApiRepository.getCityById(map.createBody())
        }, {
            CityBeanResults.postValue(this)
        }, {}, isShowLoading = true)
    }

    fun uploadUserInfo(map: HashMap<String, Any>) {
        request({
            mLiveApiRepository.uploadUserInfo(map.createBody())
        }, {
            isUploadSuccess.postValue(true)
        }, {}, isShowLoading = true)
    }


    fun getServiceUserInfo() {
        request({
            val map = HashMap<String, Any>()
            map["user_id"] = PreferencesHelper.getUserID()
            mLiveApiRepository.getServiceUserInfo(map.createBody())
        }, {
            sUserInfoResult.postValue(this)
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


}