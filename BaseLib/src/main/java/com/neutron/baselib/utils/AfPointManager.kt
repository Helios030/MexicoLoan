package com.neutron.baselib.utils

import com.appsflyer.AppsFlyerLib
import com.neutron.baselib.base.BaseApplication

import java.util.*


fun trackAFActivationEvent() {
    AppsFlyerLib.getInstance()
        .trackEvent(BaseApplication.sContext, BaseConstant.AF_APP_ACTIVATION, HashMap())
}

fun trackAFBankSuccessEvent() {
    AppsFlyerLib.getInstance()
        .trackEvent(BaseApplication.sContext, BaseConstant.AF_SUBMIT_BANK_SUCCESS, HashMap())
}

fun trackClickVaEvent(mobile: String?) {
    val eventValues: MutableMap<String, Any?> =
        HashMap()
    mobile?.let { phone ->
        eventValues["mobile"] = phone
    }
    eventValues["event_code"] = BaseConstant.EVENT_CODE_CLICK_VA
    AppsFlyerLib.getInstance().trackEvent(
        BaseApplication.sContext,
        BaseConstant.AF_CLICK_VA,
        eventValues
    )
}

fun trackVaSuccessEvent(mobile: String?) {
    val eventValues: MutableMap<String, Any?> =
        HashMap()
    mobile?.let { phone ->
        eventValues["mobile"] = phone
    }
    eventValues["event_code"] = BaseConstant.EVENT_CODE_VA_SUCCESS
    AppsFlyerLib.getInstance().trackEvent(
        BaseApplication.sContext,
        BaseConstant.AF_VA_SUCCESS,
        eventValues
    )
}

fun trackLoginEvent(mobile: String?) {
    val eventValues: MutableMap<String, Any?> =
        HashMap()
    mobile?.let { phone ->
        eventValues["mobile"] = phone
    }
    eventValues["event_code"] = BaseConstant.EVENT_CODE_LOGIN
    AppsFlyerLib.getInstance().trackEvent(
        BaseApplication.sContext,
        BaseConstant.AF_APP_LOGIN,
        eventValues
    )
}

fun trackRegisterEvent(mobile: String?) {
    val eventValues: MutableMap<String, Any?> =
        HashMap()
    mobile?.let { phone ->
        eventValues["mobile"] = phone
    }
    eventValues["event_code"] = BaseConstant.EVENT_NEW_REGISTER
    AppsFlyerLib.getInstance().trackEvent(
        BaseApplication.sContext,
        BaseConstant.AF_APP_REGISTER,
        eventValues
    )
}


fun trackInstallEvent() {
    val eventValues: MutableMap<String, Any?> =
        HashMap()
    eventValues["event_code"] = BaseConstant.EVENT_CODE_INSTALL
    AppsFlyerLib.getInstance().trackEvent(
        BaseApplication.sContext,
        BaseConstant.AF_APP_INSTALL,
        eventValues
    )
}

fun trackCommitLoanSuccessEvent(mobile: String?, loanId: String?) {
    mobile?.let { phone ->
        loanId?.let { loanId ->
            val eventValues: MutableMap<String, Any?> =
                HashMap()
            eventValues["mobile"] = phone
            eventValues["event_code"] = BaseConstant.EVENT_CODE_APPLY_SUCCESS
            eventValues["loan_id"] = loanId
            AppsFlyerLib.getInstance()
                .trackEvent(
                    BaseApplication.sContext,
                    BaseConstant.AF_APPPLY_SUCCESS,
                    eventValues
                )
        }
    }
}


fun trackAFEvent(eventName: String?) {
    eventName?.let {
        AppsFlyerLib.getInstance().trackEvent(BaseApplication.sContext, it, HashMap())
    }
}

/**
 * 需要手机号回传的数据
 */
fun trackAFMobileEvent(
    eventName: String?,
    eventCode: String?,
    mobile: String?
) {
    eventCode?.let { eventCodes ->
        val eventValues: MutableMap<String, Any?> =
            HashMap()
        mobile?.let { mobile ->
            eventValues["mobile"] = mobile
        }
        eventValues["event_code"] = eventCodes
        AppsFlyerLib.getInstance().trackEvent(BaseApplication.sContext, eventName, eventValues)
    }
}

/**
 * 借款成功回传数据
 */
fun trackAFLoanSuccessEvent(
    eventName: String?,
    loanId: String?,
    eventCode: String?,
    mobile: String?
) {
    mobile?.let { mobile ->
        loanId?.let { loanId ->
            val eventValues: MutableMap<String, Any?> =
                HashMap()
            eventValues["mobile"] = mobile
            eventValues["event_code"] = eventCode
            eventValues["loan_id"] = loanId
            AppsFlyerLib.getInstance()
                .trackEvent(BaseApplication.sContext, eventName, eventValues)
        }
    }
}
