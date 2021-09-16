package com.neutron.mexicoloan.ui.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.neutron.mexicoloan.R
import com.neutron.mexicoloan.ui.view.LoginVIew


//    <com.neutron.mexicoloan.ui.view.LoginVIew
//        android:id="@+id/lv_login"
//        android:layout_width="wrap_content"
//        android:layout_height="wrap_content"
//        app:view_style="login"
//        android:layout_centerHorizontal="true" />
class BindDialog : Dialog {

    var mContext: Context? = null

    constructor(context: Context?) : super(context!!, R.style.CustomDialog) {
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bind_dialog_layout)
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false)
        //初始化界面控件
        initView()

    }
    var lv_bind: LoginVIew? = null
    private fun initView() {
        lv_bind = findViewById<LoginVIew>(R.id.lv_bind) as LoginVIew

    }
    fun getLoginView():LoginVIew? {
        return lv_bind
    }




}