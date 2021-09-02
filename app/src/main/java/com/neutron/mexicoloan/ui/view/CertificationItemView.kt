package com.neutron.mexicoloan.ui.view

import android.content.Context
import android.content.res.TypedArray
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.core.widget.addTextChangedListener
import com.neutron.baselib.utils.Slog
import com.neutron.mexicoloan.R
import kotlinx.android.synthetic.main.view_certification_item.view.*

class CertificationItemView : RelativeLayout {

    constructor(context: Context?) : super(context!!) {
    }

    companion object {
        val VIEW_STYLE_INPUT = 0
        val VIEW_STYLE_TEXT = 1
        val VIEW_STYLE_SHOW = 2


    }

    var mContext: Context? = null

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!,
        attrs
    ) {
        mContext = context
        LayoutInflater.from(context).inflate(R.layout.view_certification_item, this, true);
        val ta = context.obtainStyledAttributes(attrs, R.styleable.CertificationItemView);
        initAttr(ta)
    }


    fun setEditTextStr(str: String) {

        if (str.isNotEmpty()) {
            et_input.setTextColor(mContext!!.getColor(R.color.black))
            et_input.setText(str)
        }
    }

    fun getEditTextStr(): String {

        return et_input.text.toString()

    }

    fun setSelectText(str: String) {
        if (str.isNotEmpty()) {

            tv_select.setTextColor(mContext!!.getColor(R.color.black))
            tv_select.text = str
        }

    }

    fun getSelectText(): String {
        return tv_select.text.toString()
    }

    fun setTextStr(str: String) {

        when (view_style) {
            VIEW_STYLE_INPUT -> {
                setEditTextStr(str)
            }
            VIEW_STYLE_TEXT -> {
                setSelectText(str)
            }

            VIEW_STYLE_SHOW -> {
                setRightText(str)
            }
        }
    }

    fun getTextStr(): String {
        if (view_style == VIEW_STYLE_INPUT) {
            return getEditTextStr()
        } else {
            return getSelectText()
        }

    }

    var view_style = VIEW_STYLE_INPUT
    private fun initAttr(ta: TypedArray) {
        view_style = ta.getInt(R.styleable.CertificationItemView_c_view_style, VIEW_STYLE_INPUT)
        val title_text = ta.getString(R.styleable.CertificationItemView_title_text)
        val isShowLine = ta.getBoolean(R.styleable.CertificationItemView_is_show_line, true)
        val isNumber = ta.getBoolean(R.styleable.CertificationItemView_is_number, false)
        val tv_left_text = ta.getString(R.styleable.CertificationItemView_tv_left_text)
        val tv_text = ta.getString(R.styleable.CertificationItemView_tv_text)
        val title_text_size = ta.getInt(R.styleable.CertificationItemView_title_text_size, 16)
        val title_text_color = ta.getColor(
            R.styleable.CertificationItemView_title_text_color,
            mContext!!.getColor(R.color.black_ff0d)
        )
        val et_text = ta.getString(R.styleable.CertificationItemView_et_text)
        val et_hint = ta.getString(R.styleable.CertificationItemView_et_hint)
        val et_text_color = ta.getColor(
            R.styleable.CertificationItemView_et_text_color,
            mContext!!.getColor(R.color.black)
        )
        val et_hint_text_color = ta.getColor(
            R.styleable.CertificationItemView_et_hint_text_color,
            mContext!!.getColor(R.color.blue_ff9f)
        )


        val et_text_size = ta.getInt(R.styleable.CertificationItemView_et_text_size, 16)

        when (view_style) {
            VIEW_STYLE_INPUT -> {
                tv_select.visibility = GONE
                rl_item.visibility = GONE
                et_input.visibility = VISIBLE
                tv_title.visibility = VISIBLE
            }
            VIEW_STYLE_TEXT -> {
                tv_select.visibility = VISIBLE
                tv_title.visibility = VISIBLE
                et_input.visibility = GONE
                rl_item.visibility = GONE
            }

            VIEW_STYLE_SHOW -> {
                tv_title.visibility = GONE
                rl_item.visibility = VISIBLE
                et_input.visibility = GONE
                tv_select.visibility = GONE
            }

        }



        if (rl_item.visibility == VISIBLE) {

            if (!tv_left_text.isNullOrEmpty()) {
                tv_item_left.text = tv_left_text

            }

        }


        if (title_text != null) {
            if (title_text.isNotBlank()) {
                tv_title.text = title_text
            }
        }
        if (et_text != null) {
            if (et_text.isNotBlank()) {
                et_input.setText(et_text)
            }
        }
        if (et_hint != null) {
            if (et_hint.isNotBlank()) {
                et_input.hint = et_hint
            }
        }

        if (isNumber) {
            et_input.inputType = InputType.TYPE_CLASS_NUMBER
        }

        if (!isShowLine) {
            line.visibility = GONE
        }




        tv_title.textSize = title_text_size.toFloat()
        tv_title.setTextColor(title_text_color)

        et_input.setTextColor(et_text_color)
        et_input.setHintTextColor(et_hint_text_color)
        et_input.textSize = et_text_size.toFloat()

        et_input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(str: CharSequence?, p1: Int, p2: Int, p3: Int) {
                str?.let {
                    listener?.onTextChage(it.toString())
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        if (!tv_text.isNullOrEmpty()) {
            tv_select.text = tv_text
        }



        tv_select.setOnClickListener {
            listener?.onClick(this)
        }


    }


    fun setRightText(str: String) {


        if (str.isNotEmpty()) {
            tv_item_right.text = str

        }

    }

    fun hideLine(isHide: Boolean) {
        line.visibility = if (isHide) GONE else VISIBLE
    }


//

    private var listener: CIVListener? = null

    interface CIVListener {
        fun onClick(view: View)
        fun onTextChage(str: String)

    }

    fun setCIVListener(listener: CIVListener) {
        this.listener = listener
    }


}