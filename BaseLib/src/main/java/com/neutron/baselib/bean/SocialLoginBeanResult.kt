package com.neutron.baselib.bean

data class SocialLoginBeanResult(
    val blackErrorMsg: Any,
    val blackUserFlag: Any,
    val certIdentity: Any,
    val custId: String,
    val errorCode: Any,
    val errorMessage: Any,
    val firstLoginImg: Any,
    val firstLoginSkip: Any,
    val firstLoginTitle: Any,
    val goolge_url: Any,
    val hasLoanApp: Boolean,
    val mobile: String,
    val phone: String,
    val phonepre: String,
    val realName: String,
    val register: Boolean,
    val signKeyToken: String,
    val userInfoMap: UserInfoMapX,
    val userName: String,
    val user_id: String,
    val vcode: Any
)


data class UserInfoMapX(
    val loginStaticKey: String,
    val user_id: String
)