package com.neutron.mexicoloan.ui.welcome

import android.os.Handler
import android.os.Looper
import com.neutron.baselib.base.IBaseActivity
import com.neutron.baselib.utils.DeviceInfoFactory
import com.neutron.baselib.utils.PreferencesHelper
import com.neutron.baselib.utils.startTo
import com.neutron.mexicoloan.R
import com.neutron.mexicoloan.ui.login.LoginActivity
import com.neutron.mexicoloan.ui.main.MainActivity


class WelcomeActivity : IBaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_welcome
    }

    override fun initBaseView() {
       DeviceInfoFactory.getInstance().getIMEI()



        Handler(Looper.getMainLooper()).postDelayed({
            if (PreferencesHelper.getUserID().isNullOrEmpty()) {
                startTo(LoginActivity::class.java, true)
            } else {
                startTo(MainActivity::class.java, true)
            }
        },2000)

    }


}