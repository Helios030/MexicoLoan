package com.neutron.mexicoloan.ui.authentication.contact

import androidx.lifecycle.MutableLiveData
import com.neutron.baselib.base.BaseVMActivity
import com.neutron.baselib.base.BaseViewModel
import com.neutron.baselib.bean.CityBeanResult
import com.neutron.baselib.bean.SContactInfoResult
import com.neutron.baselib.bean.SWorkInfoResult
import com.neutron.baselib.utils.PreferencesHelper
import com.neutron.baselib.utils.createBody

class ContactVM : BaseViewModel() {
    val sWorkInfoResult: MutableLiveData<SContactInfoResult> = MutableLiveData()
    val isUploadSuccess: MutableLiveData<Boolean> = MutableLiveData()


    fun getServiceContactInfo() {
        request({
            val map = HashMap<String, Any>()
            map["user_id"] = PreferencesHelper.getUserID()
            mLiveApiRepository.getServiceContactInfo(map.createBody())
        }, {
            sWorkInfoResult.postValue(this)
        }, {}, isShowLoading = true)
    }


    fun uploadContactPerson(map: HashMap<String, Any>) {
        request({
            mLiveApiRepository.uploadContactPerson(map.createBody())
        }, {
            isUploadSuccess.postValue(true)
        }, {}, isShowLoading = true)
    }

}