package com.neutron.mexicoloan.ui.login


import android.view.View
import androidx.lifecycle.Observer
import com.neutron.baselib.base.BaseVMActivity
import com.neutron.baselib.utils.startTo
import com.neutron.baselib.utils.toast
import com.neutron.mexicoloan.R
import com.neutron.mexicoloan.ui.MainActivity
import com.neutron.mexicoloan.ui.view.LoginVIew
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseVMActivity<LoginVM>(LoginVM::class.java) {


    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }


    var phone = ""

    override fun initView() {

        lv_login.setloginViewListener(object : LoginVIew.loginViewListener {
            override fun onClickStart() {
                val text = lv_login.getEditText()
                if (text.isNullOrEmpty()) {
                    toast(getString(R.string.pls_input_phone))
                    return
                }
                phone = text
                mViewModel.sendSmsCode(phone)
            }

            override fun onChage(count: String) {
                tv_count_down.animateText(count)
            }

            override fun onInputError(count: String?) {
                toast(getString(R.string.input_code_error))
            }

            override fun onInputComplete(count: String) {
                mViewModel.checkSmsCode(phone, count)

            }

            override fun onFinish() {
                tv_count_down.visibility = View.INVISIBLE
            }
        })

        observeValue()

    }

    private fun observeValue() {
        mViewModel.isSend.observe(this, {
            if (it) {
                tv_count_down.visibility = View.VISIBLE
                lv_login.startTime()
            }
        })

        mViewModel.isSuccess.observe(this, {
            if (it) {
                toast(getString(R.string.login_success))
                startTo(MainActivity::class.java, true)
            }


        })

    }
}