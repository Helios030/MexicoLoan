package com.neutron.mexicoloan.util

import android.app.Activity
import com.neutron.baselib.bean.BankInfoResult

import com.neutron.baselib.utils.hideSoftInputWindow
import com.neutron.mexicoloan.ui.view.BottomBar
import com.neutron.mexicoloan.ui.view.dialog.CommDialog
import com.neutron.mexicoloan.ui.view.dialog.MenuItem


fun Activity.showCommSelectDialog(
    title: String,
    opentionList: List<MenuItem>,
    listener: (Any?) -> Unit,
    itemSelected: ((Any?) -> Unit)? = null
) {
    this.hideSoftInputWindow()
    CommDialog(this)
        .setTitle(title)
        .setIsList(true)
        .setOpentionList(opentionList)
        .setOnOkClick(listener)
        .setonSelectedListener(itemSelected)
        .show()
}


fun Activity.getCommSelectDialog(
    title: String,
    opentionList: List<MenuItem>,
    listener: (Any?) -> Unit,
    itemSelected: ((Any?) -> Unit)? = null
): CommDialog {
    this.hideSoftInputWindow()

    return CommDialog(this).setTitle(title).setIsList(true).setOpentionList(opentionList)
        .setOnOkClick(listener).setonSelectedListener(itemSelected)

}

fun Activity.showCommMessageDialog(
    message: String,
    listener: (Any?) -> Unit
) {
    this.hideSoftInputWindow()
    CommDialog(this)
        .setMessage(message)
        .setIsList(false)
        .setOnOkClick(listener)
        .show()
}


fun Activity.showBankDialog(
    title: String,
    list: List<BankInfoResult>,
    listener: (Any?) -> Unit
) {
    this.hideSoftInputWindow()
    val dialog = CommDialog(this).setTitle(title)
    dialog.show()
    dialog.setBankAdapter()
        .setBankList(list)
        .setOnOkClick(listener)
}


// 数组下标对应字典标志  默认offset等于1
fun List<String>.Str2MenuItem(offset: Int = 1): List<MenuItem> {
    return this.map {
        MenuItem(this.indexOf(it) + offset, it, false)
    }
}

// 根据字典标志 还原数组下标
fun List<String>.getStrByIndex(index: Int, offset: Int = 1): String = this[index - offset]
















