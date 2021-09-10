package com.neutron.baselib.utils


class BaseConstant {
    companion object {
        const val Intent_URI: String = "Intent_URI"
        const val SIGNKEY = "signkey1"
        const val SIGNRANDOMCODE = "signkey2"
        const val AF_APP_KEY = "yGBMuxgzaU8tuLWvbjqrA8"
//        墨西哥系统 测试
        const val BaseUri = "http://api.mx.golden-union.top/app/"




        //AF中的eventName状态值
        const val AF_APP_INSTALL = "安装"
        const val AF_APP_ACTIVATION = "激活"
        const val AF_APP_LOGIN = "登录成功"
        const val AF_APP_REGISTER = "注册成功"
        const val AF_SUBMIT_BANK_SUCCESS = "提交银行卡成功"
        const val AF_APPPLY_SUCCESS = "提交借款成功"
        const val AF_VA_SUCCESS = "获取还款账号成功"
        const val AF_CLICK_VA = "点击获取还款账号"
        //AF中的eventCode状态值
        const val EVENT_CODE_INSTALL = "install"
        const val EVENT_CODE_LOGIN = "login"
        const val EVENT_CODE_CLICK_VA = "click_va"
        const val EVENT_CODE_VA_SUCCESS = "va_success"
        const val EVENT_CODE_APPLY_SUCCESS = "apply_success"
        const val EVENT_NEW_REGISTER = "register"
    }
}


class MoneyState {
    companion object {

        //可借款 ---显示借款界面
        const val STATE_BORROWABLE = 1

        //        申请中
        const val STATE_APPLYING = 2

        //        审批拒绝---显示借款界面
        const  val STATE_APPROVAL_REJECTED = 3

        //        4．待还款-未逾期
        const val STATE_PENDING_REPAYMENT = 4

        //        5．放款中
        const val STATE_LOANING = 5

        //        6．待还款-逾期
        const  val STATE_OVERDUE = 6

        //        7．确认申请中
        const val STATE_CONFIRM_APPLYING = 7

        //        8.订单取消
        const val STATE_ORDER = 10

        const val STATE_ORDER_CLEAR = 11



    }

}

class LoginType {
    companion object {
      const val type_register = 1
      const val type_login = 2
    }

}

class PreferencesKey {
    companion object {
        const val DEVICE_IMEI = "DEVICE_IMEI"
        const val IS_FIRST = "IS_FIRST"
        const val ABOUT_US = "ABOUT_US"
        const val PPRIVATE = "PPRIVATE"
        const val HOT_TEL = "HOT_TEL"
        const val NAME = "NAME"
        const val KTP = "KTP"
        const val FNAME = "FNAME"
        const val LNAME = "LNAME"
        const val BIRTHDAY = "BIRTHDAY"
        const val SEX = "SEX"
        const val LIVENESSID = "LIVENESSID"
        const val REFERRER = "REFERRER"
        const val ISSHOWFEILED = "ISSHOWFEILED"
        const val PRODUCTID = "PRODUCTID"
        const val DEVICEICCID = "DEVICEICCID"
        const val DEVICEID = "DEVICEID"
        const val USERLINE = "USERLINE"
        const val USERPHONE = "USERPHONE"
        const val USERID = "USERID"
        const val PHONEPRE = "PHONEPRE"
        const val USERINFO = "USERINFO"
    }
}

