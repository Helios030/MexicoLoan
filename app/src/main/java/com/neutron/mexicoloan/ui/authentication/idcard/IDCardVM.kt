package com.neutron.mexicoloan.ui.authentication.idcard

import androidx.lifecycle.MutableLiveData

import com.neutron.baselib.base.BaseApplication.Companion.sContext
import com.neutron.baselib.base.BaseViewModel
import com.neutron.baselib.bean.IDCardInfoResult
import com.neutron.baselib.bean.SUserInfoResult
import com.neutron.baselib.utils.*
import okhttp3.MediaType

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.File

class IDCardVM :BaseViewModel() {


    val sUserInfoResult: MutableLiveData<SUserInfoResult> = MutableLiveData()

    fun getServiceUserInfo() {
        request({
            val map = HashMap<String, Any>()
            map["user_id"] = PreferencesHelper.getUserID()
            mLiveApiRepository.getServiceUserInfo(map.createBody())
        }, {
            sUserInfoResult.postValue(this)
        }, {}, isShowLoading = true)
    }

    val idCardInfoResult: MutableLiveData<IDCardInfoResult> = MutableLiveData()

    fun uploadIDCard(file: File) {


        val version = sContext.getVersionName()
        val IMEI = PreferencesHelper.getIMEI()
        val mediaType = MediaType.parse("application/octet-stream") //设置类型，类型为八位字节流
        val requestBody = RequestBody.create(mediaType, file) //把文件与类型放入请求体
        val map = HashMap<String, Any>()
        map["app_version"] = version
        map["version"] = "1.0"
        map["channel"] = "1"
        map["imei"] = IMEI
        map["user_id"] = PreferencesHelper.getUserID()
        map["timestamp"] = System.currentTimeMillis().toString()
        map["pkg_name"] = sContext.packageName
        map["file"] = file.name
        val multipartBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("app_version", map["app_version"].toString())
            .addFormDataPart("version", map["version"].toString())
            .addFormDataPart("channel", map["channel"].toString())
            .addFormDataPart("imei", map["imei"].toString())
            .addFormDataPart("user_id", map["user_id"].toString())
            .addFormDataPart("timestamp", map["timestamp"].toString())
            .addFormDataPart("pkg_name", map["pkg_name"].toString())
            .addFormDataPart("file", map["file"].toString(), requestBody) //文件名,请求体里的文件
            .addFormDataPart("sign", map.signParameter())
            .build()



        request({
            mLiveApiRepository.uploadIDCard(multipartBody)
        }, {
            idCardInfoResult.postValue(this)
        }, {
            it.printStackTrace()
        }, isShowLoading = true)
    }


}