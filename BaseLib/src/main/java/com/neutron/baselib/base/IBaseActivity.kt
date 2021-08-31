package com.neutron.baselib.base

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.ComponentActivity
import com.leaf.library.StatusBarUtil
import com.neutron.baselib.R
import com.neutron.baselib.utils.checkNet
import com.neutron.baselib.utils.toast

abstract class IBaseActivity: ComponentActivity() {
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


}