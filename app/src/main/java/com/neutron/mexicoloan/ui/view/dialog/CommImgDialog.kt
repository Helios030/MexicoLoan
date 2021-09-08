package com.neutron.mexicoloan.ui.view.dialog

import android.app.Dialog
import android.content.Context

import android.os.Bundle

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.neutron.mexicoloan.R

class CommImgDialog : Dialog {

    var mContext: Context? = null

    constructor(context: Context?) : super(context!!, R.style.CustomDialog) {
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.comm_img_dialog_layout)
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false)
        //初始化界面控件
        initView()
    }

    private var tv_ok: TextView? = null
    private var tv_title: TextView? = null
    private var iv_icon: ImageView? = null
    private fun initView() {
        tv_ok = findViewById<View>(R.id.tv_ok) as TextView
        tv_title = findViewById<View>(R.id.tv_title) as TextView
        iv_icon = findViewById<View>(R.id.iv_icon) as ImageView

        tv_ok?.setOnClickListener {

            dismiss()
            onOkListener?.invoke()
        }

    }

    var title: String? = null

    fun setTitle(title: String?): CommImgDialog {
        this.title = title
        return this
    }

    var resId: Int? = null

    fun setIcon(resId: Int?): CommImgDialog {
        this.resId = resId
        return this
    }


    private fun refreshView() {
        if (!title.isNullOrEmpty()) {
            tv_title?.text = title
        }
        resId?.let {
            iv_icon?.setImageResource(it)
        }

    }

    override fun show() {
        super.show()
        refreshView()
    }

    var  onOkListener:(()->Unit)?=null

    fun setOnOkClick(listener:()->Unit): CommImgDialog{
        onOkListener=listener
        return this
    }


}