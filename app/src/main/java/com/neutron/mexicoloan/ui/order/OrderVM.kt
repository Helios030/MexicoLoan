package com.neutron.mexicoloan.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.neutron.baselib.base.BaseViewModel
import com.neutron.baselib.bean.OrderBeanResult
import com.neutron.baselib.utils.PreferencesHelper
import com.neutron.baselib.utils.createBody
import com.neutron.baselib.utils.createCommonParams

class OrderVM : BaseViewModel() {

    val OrderBeanResults: MutableLiveData<List<OrderBeanResult>> = MutableLiveData()

    fun getOrderList() {
        val map= HashMap<String, Any>()
        map["user_id"] = PreferencesHelper.getUserID()
        request({
            mLiveApiRepository.getOrderList(map.createBody())
        }, {
            OrderBeanResults.postValue(this)
        }, {}, isShowLoading = true)
    }
}