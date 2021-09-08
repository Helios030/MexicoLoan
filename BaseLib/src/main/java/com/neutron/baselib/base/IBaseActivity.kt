package com.neutron.baselib.base

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import androidx.appcompat.app.AppCompatActivity
import com.leaf.library.StatusBarUtil
import com.neutron.baselib.R
import com.neutron.baselib.utils.checkNet
import com.neutron.baselib.utils.toast
import com.neutron.baselib.view.LoadingDialog
import com.permissionx.guolindev.PermissionX

abstract class IBaseActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        StatusBarUtil.setTransparentForWindow(this)
        StatusBarUtil.setDarkMode(this)
        initBaseView()
    }

    abstract fun getLayoutId(): Int
    abstract fun initBaseView()

    override fun onResume() {
        super.onResume()
        if (!checkNet()) {
            toast(R.string.not_net)
        }
    }

    var loadding: LoadingDialog? = null

     fun showLoading() {
        if(loadding==null){
            loadding = LoadingDialog(this)

        }
        if (loadding != null && !loadding!!.isShowing) {
            loadding!!.show()
        }

    }

     fun hideLoading() {
        if (loadding != null && loadding!!.isShowing) {
            loadding!!.dismiss()
            loadding = null
        }
    }











}