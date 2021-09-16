package com.neutron.baselib.utils

import com.neutron.baselib.bean.UserInfo


object PreferencesHelper {

     fun exitApp() {
//         setUserID("")
////         setPhone("")
//         setUploadTime(0L)
//         setUserInfo(UserInfo(null,null,null,null,null,null,null ))
//         setShowFeiled(true)
         PreferencesUtil.clear()
    }

    fun getUserInfo(): UserInfo? {
        val beanStr = PreferencesUtil.getString(PreferencesKey.USERINFO, "")
        return Utils.toBean(beanStr) as UserInfo?
    }

    fun setUserInfo(user: UserInfo) =
        PreferencesUtil.saveValue(PreferencesKey.USERINFO, Utils.fromBean(user))


//    fun getPhoneEPRE(): String = PreferencesUtil.getString(PreferencesKey.PHONEPRE, "")
//    fun setPhoneEPRE(phone: String) = PreferencesUtil.saveValue(PreferencesKey.PHONEPRE, phone)


//    fun getPhone(): String = PreferencesUtil.getString(PreferencesKey.USERPHONE, "")
//    fun setPhone(phone: String) = PreferencesUtil.saveValue(PreferencesKey.USERPHONE, phone)

    fun getUserID(): String = PreferencesUtil.getString(PreferencesKey.USERID, "")
    fun setUserID(id: String) = PreferencesUtil.saveValue(PreferencesKey.USERID, id)


    fun getIMEI(): String = PreferencesUtil.getString(PreferencesKey.DEVICE_IMEI, "")
    fun setIMEI(imei: String) = PreferencesUtil.saveValue(PreferencesKey.DEVICE_IMEI, imei)


    fun isFirstStart(): Boolean = PreferencesUtil.getBoolean(PreferencesKey.IS_FIRST, true)
    fun setFirstStart(isFirst: Boolean) =
        PreferencesUtil.saveValue(PreferencesKey.IS_FIRST, isFirst)


    fun getAboutUs(): String = PreferencesUtil.getString(PreferencesKey.ABOUT_US, "https://privacy.loanbeer.com")
    fun setAboutUs(str: String) = PreferencesUtil.saveValue(PreferencesKey.ABOUT_US, str)


    fun getPPrivate(): String =
        PreferencesUtil.getString(PreferencesKey.PPRIVATE, "https://privacy.loanbeer.com")

    fun setPPrivate(str: String) = PreferencesUtil.saveValue(PreferencesKey.PPRIVATE, str)

    fun getLine(): String = PreferencesUtil.getString(PreferencesKey.USERLINE, "")
    fun setLine(str: String) = PreferencesUtil.saveValue(PreferencesKey.USERLINE, str)


    fun getHotTel(): String = PreferencesUtil.getString(PreferencesKey.HOT_TEL, "")
    fun setHotTel(str: String) = PreferencesUtil.saveValue(PreferencesKey.HOT_TEL, str)

    fun getRealname(): String = PreferencesUtil.getString(PreferencesKey.NAME, "")
    fun setRealname(str: String) = PreferencesUtil.saveValue(PreferencesKey.NAME, str)

    fun getKTP(): String = PreferencesUtil.getString(PreferencesKey.KTP, "")
    fun setKTP(str: String) = PreferencesUtil.saveValue(PreferencesKey.KTP, str)

    fun getFName(): String = PreferencesUtil.getString(PreferencesKey.FNAME, "")
    fun setFName(str: String) = PreferencesUtil.saveValue(PreferencesKey.FNAME, str)

    fun getLNAME(): String = PreferencesUtil.getString(PreferencesKey.LNAME, "")
    fun setLNAME(str: String) = PreferencesUtil.saveValue(PreferencesKey.LNAME, str)

    fun getBirthday(): String = PreferencesUtil.getString(PreferencesKey.BIRTHDAY, "")
    fun setBirthday(str: String) = PreferencesUtil.saveValue(PreferencesKey.BIRTHDAY, str)

    fun getsex(): String = PreferencesUtil.getString(PreferencesKey.SEX, "")
    fun setsex(str: String) = PreferencesUtil.saveValue(PreferencesKey.SEX, str)






    fun getLivenessID(): String = PreferencesUtil.getString(PreferencesKey.LIVENESSID, "")
    fun setLivenessID(str: String) = PreferencesUtil.saveValue(PreferencesKey.LIVENESSID, str)

    fun getReferrer(): String = PreferencesUtil.getString(PreferencesKey.REFERRER, "")
    fun setReferrer(str: String) = PreferencesUtil.saveValue(PreferencesKey.REFERRER, str)

    fun getProductId(): String = PreferencesUtil.getString(PreferencesKey.PRODUCTID, "")
    fun setProductId(str: String) = PreferencesUtil.saveValue(PreferencesKey.PRODUCTID, str)


    fun isShowFeiled(): Boolean = PreferencesUtil.getBoolean(PreferencesKey.ISSHOWFEILED, true)
    fun setShowFeiled(isShow: Boolean) =
        PreferencesUtil.saveValue(PreferencesKey.ISSHOWFEILED, isShow)


    fun getDeviceIccid(): String = PreferencesUtil.getString(PreferencesKey.DEVICEICCID, "")
    fun setDeviceIccid(str: String) = PreferencesUtil.saveValue(PreferencesKey.DEVICEICCID, str)


    fun getDeviceId(): String = PreferencesUtil.getString(PreferencesKey.DEVICEID, "")
    fun setDeviceId(str: String) = PreferencesUtil.saveValue(PreferencesKey.DEVICEID, str)


    fun getUploadTime(): Long = PreferencesUtil.getLong(PreferencesKey.LIVENESSID, 0L)
    fun setUploadTime(time: Long) = PreferencesUtil.saveValue(PreferencesKey.LIVENESSID, time)

    fun getConfirmPP(): Boolean = PreferencesUtil.getBoolean(PreferencesKey.CONFIRM_PP, false)
    fun setConfirmPP(isCheck: Boolean) = PreferencesUtil.saveValue(PreferencesKey.CONFIRM_PP, isCheck)




}