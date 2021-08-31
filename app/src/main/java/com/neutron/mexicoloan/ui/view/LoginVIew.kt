package com.neutron.mexicoloan.ui.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout

import com.neutron.mexicoloan.R
import com.neutron.mexicoloan.ui.view.verificationcodeview.VCView
import kotlinx.android.synthetic.main.view_login_view.view.*

class LoginVIew : RelativeLayout {


    constructor(context: Context?) : super(context!!) {
    }

    companion object {
        val VIEW_STYLE_BIND = 0
        val VIEW_STYLE_LOGIN = 1


    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!,
        attrs
    ) {
        LayoutInflater.from(context).inflate(R.layout.view_login_view, this, true);
        val ta = context.obtainStyledAttributes(attrs, R.styleable.LoginView);
        blueDrawable= context.getDrawable(R.drawable.shape_btn_blue_bg)
        grayDrawable= context.getDrawable(R.drawable.shape_btn_gray_bg)

        sendStr=context.getString(R.string.get_code)
        regetStr=context.getString(R.string.re_get)
        initAttr(ta)

    }





    fun getEditText() =  et_number.text.toString()


    var view_style = VIEW_STYLE_BIND


    var blueDrawable: Drawable?=null
    var grayDrawable: Drawable?=null

    var sendStr=""
    var regetStr=""


    private fun initAttr(ta: TypedArray) {
        view_style = ta.getInt(R.styleable.LoginView_view_style, view_style)
        when (view_style) {
            VIEW_STYLE_BIND -> {
                ll_bind.visibility = View.VISIBLE
            }
            VIEW_STYLE_LOGIN -> {
                tv_phone.visibility = View.VISIBLE
            }
        }
        btn_next.setOnClickListener {
            listener?.onClickStart()

        
        }

        vcv.setOnVerifyCodeListener(object :VCView.OnVerifyCodeListener{
            override fun onTextChange(view: View, content: String) {

            }

            override fun onComplete(view: View, content: String) {
                if(content.isNullOrEmpty()){
                    listener?.onInputError(content)
                }else{
                    listener?.onInputComplete(content)
                }
            }

        })
    }


    val timer = object : CountDownTimer(60000L, 1000L) {
        override fun onTick(millisUntilFinished: Long) {
            val count = "${millisUntilFinished / 1000 % 60} s"

            listener?.onChage(count)

            if(view_style== VIEW_STYLE_BIND){
                btn_next.text = "$regetStr ($count)"
            }else{
                btn_next.text = regetStr
            }


        }

        override fun onFinish() {
            listener?.onFinish()
            btn_next.isEnabled=true
            btn_next.background=blueDrawable
            btn_next.text = sendStr

        }
    }

    private var listener: loginViewListener? = null

    interface loginViewListener {
        
        fun onClickStart()

        fun onChage(count: String)

        fun onInputError(count: String?)

        fun onInputComplete(count: String)

        fun onFinish()

    }

    fun setloginViewListener(listener: loginViewListener) {
        this.listener = listener
    }

    fun startTime() {
        timer.cancel()
        timer.start()


        btn_next.isEnabled=false
        btn_next.background=grayDrawable
    }


}