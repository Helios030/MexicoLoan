package com.neutron.mexicoloan.ui.welcome

import android.os.Handler
import android.os.Looper
import com.neutron.baselib.base.IBaseActivity
import com.neutron.baselib.utils.*
import com.neutron.mexicoloan.R
import com.neutron.mexicoloan.ui.login.LoginActivity


class WelcomeActivity : IBaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_welcome
    }

    override fun initBaseView() {
       DeviceInfoFactory.getInstance().getIMEI()

        Handler(Looper.getMainLooper()).postDelayed({startTo(LoginActivity::class.java)},2000)

    }


}