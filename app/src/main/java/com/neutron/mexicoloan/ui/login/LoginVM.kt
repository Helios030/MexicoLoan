package com.neutron.mexicoloan.ui.login


import androidx.lifecycle.MutableLiveData
import com.neutron.baselib.base.BaseViewModel
import com.neutron.baselib.bean.ApiException
import com.neutron.baselib.bean.SmsLoginResult
import com.neutron.baselib.bean.SocialLoginBeanResult
import com.neutron.baselib.utils.LoginType
import com.neutron.baselib.utils.Slog
import com.neutron.baselib.utils.createBody

class LoginVM :BaseViewModel() {

    val isSend: MutableLiveData<Boolean> = MutableLiveData()
    val loginResult: MutableLiveData<SmsLoginResult> = MutableLiveData()



    fun sendSmsCode(phone: String) {
        request({
            val map = HashMap<String, Any>();
            map["phone"] = phone
            map["type"] = LoginType.type_login
            mLiveApiRepository.getSmsCode(map.createBody())

        }, {
            Slog.d("request  ==  $this")
            isSend.postValue(true)
        }, {

        }, isShowLoading = true)
    }


    fun checkSmsCode(phone: String, code: String, type: String="", id: String="") {
        request({
            val map = java.util.HashMap<String, Any>();
            map["phone"] = phone
            map["vcode"] = code
            map["socialType"] = type
            map["socialId"] = id
            mLiveApiRepository.smsLogin(map.createBody())
        }, {
            Slog.d("request  ==  $this")
            loginResult.postValue(this)
        }, {

        }, isShowLoading = true)
    }


    val sLoginResult: MutableLiveData<SocialLoginBeanResult> = MutableLiveData()


    val needBind: MutableLiveData<Boolean> = MutableLiveData()

    fun socialLogin(map:HashMap<String, Any>){



        request({
            mLiveApiRepository.socialLogin(map.createBody())
        }, {
            Slog.d("socialLogin  ==  $this")
            sLoginResult.postValue(this)
        }, {

            Slog.e("第三方登录错误 it $it")

           if(it is ApiException){

               if(it.code=="600312"){
                   needBind.postValue(true)
               }


           }else{
               needBind.postValue(false)
           }

        }, isShowLoading = true)

    }

}