package com.neutron.mexicoloan.ui.login


import android.view.View
import com.neutron.baselib.base.BaseVMActivity
import com.neutron.baselib.bean.UserInfo
import com.neutron.baselib.utils.*
import com.neutron.mexicoloan.R
import com.neutron.mexicoloan.ui.main.MainActivity
import com.neutron.mexicoloan.ui.view.LoginVIew
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseVMActivity<LoginVM>(LoginVM::class.java) {
    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    var phone = ""
    override fun initView() {
        lv_login.setloginViewListener({
            val text = lv_login.getEditText()
            if (text.isNullOrEmpty()) {
                toast(getString(R.string.pls_input_phone))
            } else {
                phone = text
                mViewModel.sendSmsCode(phone)
            }
        }, {
            tv_count_down.text=(it)
        }, {
            Slog.e("输入异常  $it")
            toast(getString(R.string.input_code_error))
        }, { comp ->
            comp?.let {
                    mViewModel.checkSmsCode(phone, it,socialType,socialId)
            }
        }, {
            tv_count_down.visibility = View.INVISIBLE
        })

    }

    override fun observeValue() {
        mViewModel.isSend.observe(this, {
            if (it) {
                tv_count_down.visibility = View.VISIBLE
                lv_login.startTime()
            }
        })

        mViewModel.loginResult.observe(this, {
            toast(getString(R.string.login_success))

            Slog.d("it  $it")
            val vCode = it.vcode ?: ""
            val register = it.register ?: ""
            PreferencesHelper.setUserID(it.user_id)
            PreferencesHelper.setUserInfo(
                UserInfo(
                    it.user_id,
                    it.userName,
                    it.signKeyToken,
                    vCode.toString(),
                    it.phone,
                    it.phonepre,
                    register.toString()
                )
            )

            if (it.register == false) {
                trackRegisterEvent(it.phone)
            }
            trackLoginEvent(it.phone)

            startTo(MainActivity::class.java, true)
        })
    }

    var loginType = LoginVIew.VIEW_STYLE_LOGIN

  var  socialType=""
  var  socialId=""

    override fun initData() {
      val ext=  intent.extras
        loginType = ext?.getInt("loginType") ?: LoginVIew.VIEW_STYLE_LOGIN
        socialType= ext?.getString("socialType")?:""
        socialId= ext?.getString("socialId")?:""
        lv_login.setViewStyle(loginType)
    }
}