package com.neutron.baselib.base

import android.app.Application
import android.content.Context
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.neutron.baselib.utils.BaseConstant
import com.neutron.baselib.utils.PreferencesHelper
import com.neutron.baselib.utils.Slog
import com.neutron.baselib.utils.trackAFActivationEvent

open class BaseApplication : Application() {

    companion object {

        lateinit var sContext: Context
        var IMEI: String = ""
    }

    override fun onCreate() {
        super.onCreate()
        sContext = this
        Slog.getSettings().setLogEnable(true).setBorderEnable(true)
        initAF()
//        AfPointUtils.trackEvent(BaseConstant.AF_APP_ACTIVATION, this)
        trackAFActivationEvent()


    }

    fun getIMEI(): String {
        if (IMEI.isEmpty()) {
            IMEI = PreferencesHelper.getIMEI()
        }
        return IMEI
    }


    private fun initAF() {
        if (BaseConstant.AF_APP_KEY.isNotEmpty()) {
            val conversionDataListener: AppsFlyerConversionListener =
                object : AppsFlyerConversionListener {
                    override fun onInstallConversionDataLoaded(map: Map<String, String>) {}
                    override fun onInstallConversionFailure(s: String) {}
                    override fun onAppOpenAttribution(map: Map<String, String>) {}
                    override fun onAttributionFailure(s: String) {}
                }
            AppsFlyerLib.getInstance()
                .init(BaseConstant.AF_APP_KEY, conversionDataListener, applicationContext)
            AppsFlyerLib.getInstance().startTracking(this)
            AppsFlyerLib.getInstance().reportTrackSession(this)
        }
    }

}