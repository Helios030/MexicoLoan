package com.neutron.mexicoloan.ui.welcome

import android.os.Handler
import android.os.Looper
import com.neutron.baselib.base.IBaseActivity
import com.neutron.baselib.net.LiveRepository
import com.neutron.baselib.utils.*
import com.neutron.baselib.utils.Utils.Companion.getkey
import com.neutron.mexicoloan.R
import com.neutron.mexicoloan.ui.login.LoginMethodActivity
import com.neutron.mexicoloan.ui.main.MainActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class WelcomeActivity : IBaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_welcome
    }

    override fun initBaseView() {
        Slog.d("getkey ${getkey(this)}")

       DeviceInfoFactory.getInstance().getIMEI()
        Handler(Looper.getMainLooper()).postDelayed({
            if (PreferencesHelper.getUserID().isNullOrEmpty()) {
                startTo(LoginMethodActivity::class.java, true)
            } else {
                startTo(MainActivity::class.java, true)
            }
        },2000)


        if (PreferencesHelper.isFirstStart()) {
            trackInstallEvent()
            uploadAppDownload()
            PreferencesHelper.setFirstStart(false)
        }


    }



    private fun uploadAppDownload() {
        GlobalScope.launch(Dispatchers.Main) {
            val map = HashMap<String, Any>();
            map["uuid"] = DeviceInfoFactory.getInstance().getUUID() ?: ""
            map["imei"] = PreferencesHelper.getIMEI()
            map["referrer"] = PreferencesHelper.getReferrer()
            map["download_time"] = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
            try {
                val result = LiveRepository.mApiService.uploadAppFirst(Utils.createBody(Utils.createCommonParams(map)))
                Slog.d("第一次上传结果  $result")
            } catch (e: Exception) {
                e.printStackTrace()
                Slog.e("第一次上传结果  $e")
            }
        }
    }


}