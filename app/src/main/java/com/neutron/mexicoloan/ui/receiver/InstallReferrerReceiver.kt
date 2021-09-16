package com.neutron.mexicoloan.ui.receiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.neutron.baselib.net.LiveRepository
import com.neutron.baselib.utils.*

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URLDecoder
import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * @Description:
 * @Author:         cxj
 * @CreateDate:     2021/3/30
 */

class InstallReferrerReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        getReferrerValue(intent.extras, context)
    }

    private fun getReferrerValue(bundle: Bundle?, context: Context) {
        Slog.d("安装事件")
        try {
            if (PreferencesHelper.isFirstStart()) {
                val mGooglePlayReferrer = PreferencesHelper.getReferrer()
                var referrerValue: String? = bundle?.getString("referrer")
                referrerValue = URLDecoder.decode(referrerValue, "utf-8")

                if (mGooglePlayReferrer.isEmpty()) {
                    PreferencesHelper.setReferrer(referrerValue)
                    trackInstallEvent()
                }
                uploadAppDownload()
                PreferencesHelper.setFirstStart(false)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("CheckResult")
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
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                Slog.e("第一次上传结果  $e")
            }
        }

    }


}