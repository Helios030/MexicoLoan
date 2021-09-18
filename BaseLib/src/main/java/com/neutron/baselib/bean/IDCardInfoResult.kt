package com.neutron.baselib.bean

import java.io.Serializable

data class IDCardInfoResult(
    var birthday: String,
    var no_ktp: String,
    var first_name: String,
    var last_name: String,
    var sex: String,
    var name: String,
    var name_mother: String,
    var CURP: String,
    var RFC: String
) : Serializable



//    var birthday: String,
//    var idNumber: String,
//    var first_name: String,
//    var last_name: String,
//    var sex: String,
//    var name: String,
//    var motherLastName: String,
//    var CURP: String,
//    var RFC: String