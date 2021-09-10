package com.neutron.mexicoloan.ui.face

import androidx.lifecycle.MutableLiveData
import com.neutron.baselib.base.BaseViewModel
import com.neutron.baselib.bean.AdvanceLicenseResult
import com.neutron.baselib.utils.PreferencesHelper
import com.neutron.baselib.utils.createBody
import java.util.HashMap

class FaceDetectionVM:BaseViewModel() {
    val advanceLicenseResult: MutableLiveData<AdvanceLicenseResult> = MutableLiveData()
    fun getAdvancelicense(){


        val map = HashMap<String, Any>()
        map["user_id"] = PreferencesHelper.getUserID()

        request({
            mLiveApiRepository.getAdvancelicense(map.createBody())
        }, {
            advanceLicenseResult.postValue(this)
        }, {}, isShowLoading = true)
    }
}