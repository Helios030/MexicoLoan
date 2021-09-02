package com.neutron.baselib.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.neutron.baselib.R


class LoadingDialog : Dialog {


    constructor(context: Context?) : super(context!!, R.style.CustomDialog)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_load)
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false)

    }

}