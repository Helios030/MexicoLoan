package com.neutron.baselib.bean

data class BankInfoResult(
    val bank_code: String,
    val bank_icon: String,
    val bank_name: String,
    var isSelected:Boolean=false
)