package com.neutron.mexicoloan.ui.repay

import androidx.lifecycle.MutableLiveData
import com.neutron.baselib.base.BaseViewModel
import com.neutron.baselib.bean.OrderBeanResult
import com.neutron.baselib.bean.VABeanResultX
import com.neutron.baselib.utils.PreferencesHelper
import com.neutron.baselib.utils.createBody
import com.neutron.baselib.utils.createCommonParams
import com.neutron.baselib.utils.signParameter
import okhttp3.MediaType

import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class RePayVM :BaseViewModel() {


    val VABeanResult: MutableLiveData<VABeanResultX> = MutableLiveData()

    fun getVa(map: HashMap<String, Any>) {
        request({
            mLiveApiRepository.getVa(map.createBody())
        }, {
            VABeanResult.postValue(this)
        }, {}, isShowLoading = true)
    }

val isUploadSuccess:MutableLiveData<Boolean> = MutableLiveData()


    fun uploadRepayment(rawMap: HashMap<String, Any>, file: File){

        val map=rawMap.createCommonParams()

        val requestBody =
            RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val parts: MutableList<MultipartBody.Part> = ArrayList()
        parts.add(MultipartBody.Part.createFormData("user_id", map["user_id"].toString()))
        parts.add(MultipartBody.Part.createFormData("sign", map.signParameter()))
        parts.add(MultipartBody.Part.createFormData("app_version", map["app_version"].toString()))
        parts.add(MultipartBody.Part.createFormData("version", map["version"].toString()))
        parts.add(MultipartBody.Part.createFormData("channel", map["channel"].toString()))
        parts.add(MultipartBody.Part.createFormData("timestamp", map["timestamp"].toString()))
        parts.add(MultipartBody.Part.createFormData("pkg_name", map["pkg_name"].toString()))
        parts.add(MultipartBody.Part.createFormData("application_id", map["application_id"].toString()))
        parts.add(MultipartBody.Part.createFormData("amount", map["amount"].toString()))
        parts.add(MultipartBody.Part.createFormData("card_no", map["cardNo"].toString()))
        parts.add(MultipartBody.Part.createFormData("bankCardNo", map["bankCardNo"].toString()))
        parts.add(MultipartBody.Part.createFormData("vaType", map["vaType"].toString()))
        parts.add(MultipartBody.Part.createFormData("file", file.name, requestBody))
        request({
            mLiveApiRepository.uploadRepayment(parts)
        }, {
            isUploadSuccess.postValue(true)
        }, {}, isShowLoading = true)
    }









}