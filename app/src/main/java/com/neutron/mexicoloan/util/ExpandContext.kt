package com.neutron.mexicoloan.util

import android.app.Activity
import android.content.Context
import com.neutron.baselib.utils.Utils
import com.neutron.mexicoloan.ui.view.dialog.CommDialog
import com.neutron.mexicoloan.ui.view.dialog.MenuItem


fun Activity.showCommSelectDialog(
    title: String,
    opentionList: List<MenuItem>,
    listener: CommDialog.DialogListener
    ) {
        Utils.hideSoftInputWindow(this)
        CommDialog(this)
            .setTitle(title)
            .setIsList(true)
            .setOpentionList(opentionList)
            .setDialogListener(listener)
            .show()
}

fun Activity.showCommMessageDialog(
    message: String,
    listener: CommDialog.DialogListener
) {
    Utils.hideSoftInputWindow(this)
    CommDialog(this)
        .setMessage(message)
        .setIsList(false)
        .setDialogListener(listener)
        .show()
}





fun Str2MenuItem(strings: List<String>): List<MenuItem> {
    return strings.map { MenuItem(it, false) }
}