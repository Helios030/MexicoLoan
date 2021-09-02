package com.neutron.mexicoloan.ui.main.fragment.product

import androidx.lifecycle.MutableLiveData
import com.neutron.baselib.base.BaseViewModel
import com.neutron.baselib.bean.ProductsResult
import com.neutron.baselib.bean.UserStatusResult
import com.neutron.baselib.utils.PreferencesHelper
import com.neutron.baselib.utils.Slog
import com.neutron.baselib.utils.createBody

class ProductVM :BaseViewModel() {

    val products: MutableLiveData<List<ProductsResult>> = MutableLiveData()
    val userStatus: MutableLiveData<UserStatusResult> = MutableLiveData()
    fun getProducts() {
        request({
            val map = HashMap<String, Any>();
            map["user_id"] = PreferencesHelper.getUserID()
            mLiveApiRepository.getProducts(map.createBody())
        }, {
            Slog.d("products  ==  $this")
            products.postValue(this)
        }, {

        }, isShowLoading = true)
    }


    fun getUserStatus() {
        request({
            val map = HashMap<String, Any>();
            map["user_id"] = PreferencesHelper.getUserID()
            mLiveApiRepository.getUserStatus(map.createBody())

        }, {
            Slog.d("userStatus  ==  $this")
            userStatus.postValue(this)
        }, {

        }, isShowLoading = true)
    }


}