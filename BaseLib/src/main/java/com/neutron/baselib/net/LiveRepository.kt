package com.neutron.baselib.net


import com.neutron.baselib.utils.BaseConstant
import okhttp3.MultipartBody
import okhttp3.RequestBody

object LiveRepository : BaseRetrofitClient(){

    val mApiService by lazy { getService(NetWorkService::class.java, BaseConstant.BaseUri) }
    suspend fun getSmsCode(body: RequestBody) = mApiService.sendSms(body).getResponse()
    suspend fun getRequestState(body: RequestBody) = mApiService.getRequestState(body).getResponse()
    suspend fun smsLogin(body: RequestBody) = mApiService.smsLogin(body).getResponse()
    suspend fun socialLogin(body: RequestBody) = mApiService.socialLogin(body).getResponse()
    suspend fun getUploadImg(body: RequestBody) = mApiService.getUploadImg(body).getResponse()
    suspend fun getUserStatus(body: RequestBody) = mApiService.getUserStatus(body).getResponse()
    suspend fun getUserConfig(body: RequestBody) = mApiService.getUserConfig(body).getResponse()
    suspend fun getCityById(body: RequestBody) = mApiService.getCityById(body).getResponse()
    suspend fun uploadUserWorkInfo(body: RequestBody) = mApiService.uploadUserWorkInfo(body).getResponse()
    suspend fun uploadContactPerson(body: RequestBody) = mApiService.uploadContactPerson(body).getResponse()
    suspend fun getBankInfo(body: RequestBody) = mApiService.getBankInfo(body).getResponse()
    suspend fun uploadMoneyCardInfo(body: RequestBody) = mApiService.uploadMoneyCardInfo(body).getResponse()
    suspend fun uploadIDCard(body: MultipartBody) = mApiService.uploadIDCard(body).getResponse()
    suspend fun uploadUserInfo(body: RequestBody) = mApiService.uploadUserInfo(body).getResponse()
    suspend fun getAdvancelicense(body: RequestBody) = mApiService.getAdvancelicense(body).getResponse()
    suspend fun getProducts(body: RequestBody) = mApiService.getProducts(body).getResponse()
    suspend fun getServiceWorkInfo(body: RequestBody) = mApiService.getServiceWorkInfo(body).getResponse()
    suspend fun confirmInfo(body: RequestBody) = mApiService.confirmInfo(body).getResponse()
    suspend fun uploadRequest(body: RequestBody) = mApiService.uploadRequest(body).getResponse()
    suspend fun getServiceContactInfo(body: RequestBody) = mApiService.getServiceContactInfo(body).getResponse()
    suspend fun getOrderList(body: RequestBody) = mApiService.getOrderList(body).getResponse()
    suspend fun uploadAppFirst(body: RequestBody) = mApiService.uploadAppFirst(body).getResponse()
    suspend fun getServiceMoneyCard(body: RequestBody) = mApiService.getServiceMoneyCard(body).getResponse()
    suspend fun getServiceUserInfo(body: RequestBody) = mApiService.getServiceUserInfo(body).getResponse()
    suspend fun getVa(body: RequestBody) = mApiService.getVa(body).getResponse()
    suspend fun uploadRepayment(body: List<MultipartBody.Part>) = mApiService.uploadRepayment(body).getResponse()
    suspend fun uploadPhone(body: RequestBody) = mApiService.uploadPhone(body).getResponse()
    suspend fun uploadApp(body: RequestBody) = mApiService.uploadApp(body).getResponse()
    suspend fun uploadLocation(body: RequestBody) = mApiService.uploadLocation(body).getResponse()




}