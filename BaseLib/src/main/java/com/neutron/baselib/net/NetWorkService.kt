package com.neutron.baselib.net


import com.neutron.baselib.bean.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface NetWorkService {

    

    //查询申请状态
    @POST("loan/queryloanstatus")
    suspend fun getRequestState(@Body body: RequestBody): BaseResponse<LoanStatusResult>

    //发送验证码
    @POST("register/app/sendSms")
    suspend fun sendSms(@Body body: RequestBody): BaseResponse<LoginResuleResult>

    //短信登录
    @POST("login/v2/smsLogin")
    suspend fun smsLogin(@Body body: RequestBody): BaseResponse<SmsLoginResult>

    //    获取
    @POST("user/userStatus")
    suspend fun getUserStatus(@Body body: RequestBody): BaseResponse<UserStatusResult>

    //    //获取用户配置
    @POST("user/config")
    suspend fun getUserConfig(@Body body: RequestBody): BaseResponse<UserConfigResult>

    @POST("query/queryRegion")
    suspend fun getCityById(@Body body: RequestBody): BaseResponse<List<CityBeanResult>>

    @POST("user/userWork")
    suspend fun uploadUserWorkInfo(@Body body: RequestBody): BaseResponse<*>


    @POST("user/userContact")
    suspend fun uploadContactPerson(@Body body: RequestBody): BaseResponse<*>

    @POST("query/bankcode")
    suspend fun getBankInfo(@Body body: RequestBody): BaseResponse<List<BankInfoResult>>

    @POST("user/userCard")
    suspend fun uploadMoneyCardInfo(@Body body: RequestBody): BaseResponse<*>

    @POST("user/idCardOcr")
    suspend fun uploadIDCard(@Body body: MultipartBody): BaseResponse<IDCardInfoResult>


    @POST("user/userBase")
    suspend fun uploadUserInfo(@Body body: RequestBody): BaseResponse<*>


    //Advance活体获取license
    @POST("user/license")
    suspend fun getAdvancelicense(@Body body: RequestBody): BaseResponse<AdvanceLicenseResult>

    //获取产品列表
    @POST("loan/queryproducts2")
    suspend fun getProducts(@Body body: RequestBody): BaseResponse<List<ProductsResult>>

    @POST("user/queryUserwork")
    suspend fun getServiceWorkInfo(@Body body: RequestBody): BaseResponse<SWorkInfoResult>

    //    //   确认信息
    @POST("loan/confirm")
    suspend fun confirmInfo(@Body body: RequestBody): BaseResponse<ConfirmInfoResult>

    //   提交订单
    @POST("loan/v2/loanapp")
    suspend fun uploadRequest(@Body body: RequestBody): BaseResponse<RequestOrderResult>

    // 查询联系人
    @POST("user/queryUserContact")
    suspend fun getServiceContactInfo(@Body body: RequestBody): BaseResponse<SContactInfoResult>


    @POST("loan/loanappquery")
    suspend fun getOrderList(@Body body: RequestBody): BaseResponse<List<OrderBeanResult>>

    @POST("comm/downoknotify")
    suspend fun uploadAppFirst(@Body body: RequestBody): BaseResponse<*>

    @POST("user/queryUsercard")
    suspend fun getServiceMoneyCard(@Body body: RequestBody): BaseResponse<SMoneyCardInfoResult>


    @POST("user/queryUserBase")
    suspend fun getServiceUserInfo(@Body body: RequestBody): BaseResponse<SUserInfoResult>

    @POST("loan/getva")
    suspend fun getVa(@Body body: RequestBody): BaseResponse<VABeanResultX>


    //    @Multipart
    @POST("loan/repay/vouch")
    suspend fun uploadRepayment(@Body body: MultipartBody): BaseResponse<*>

    @Multipart
    @POST("loan/repay/vouch")
    suspend fun uploadRepayment(@Part body: List<MultipartBody.Part>): BaseResponse<*>


    //
    @POST("fetch/user/addressbook")
    suspend fun uploadPhone(@Body body: RequestBody): BaseResponse<*>

    //
    @POST("fetch/user/device")
    suspend fun uploadApp(@Body body: RequestBody): BaseResponse<*>

    @POST("fetch/user/position")
    suspend fun uploadLocation(@Body body: RequestBody): BaseResponse<*>


    //http://mockjs.docway.net/mock/1awz6TK9DUm/login/socialLogin

    //第三方登录
    @POST("login/socialLogin")
    suspend fun socialLogin(@Body body: RequestBody): BaseResponse<SocialLoginBeanResult>

    @POST("user/queryuserMedia")
    suspend fun getUploadImg(@Body body: RequestBody): BaseResponse<SImgInfoResult>


}