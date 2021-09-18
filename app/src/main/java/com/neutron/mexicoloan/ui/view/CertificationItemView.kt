package com.neutron.mexicoloan.ui.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.core.widget.addTextChangedListener
import com.neutron.baselib.utils.Slog
import com.neutron.baselib.utils.UIUtils
import com.neutron.baselib.utils.setDrawableRight
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


    var view_style = VIEW_STYLE_INPUT
    private fun initAttr(ta: TypedArray) {
        view_style = ta.getInt(R.styleable.CertificationItemView_c_view_style, VIEW_STYLE_INPUT)
        val title_text = ta.getString(R.styleable.CertificationItemView_title_text)
        val isShowLine = ta.getBoolean(R.styleable.CertificationItemView_is_show_line, true)
        val isShowIcon = ta.getBoolean(R.styleable.CertificationItemView_is_show_icon, true)
        val isNumber = ta.getBoolean(R.styleable.CertificationItemView_is_number, false)
        val tv_left_text = ta.getString(R.styleable.CertificationItemView_tv_left_text)
        val tv_text = ta.getString(R.styleable.CertificationItemView_tv_text)
        val title_text_size = ta.getInt(R.styleable.CertificationItemView_title_text_size, 16)
        val title_text_color = ta.getColor(
            R.styleable.CertificationItemView_title_text_color,
            UIUtils.getColor(R.color.black_ff0d)
        )
        val et_text = ta.getString(R.styleable.CertificationItemView_et_text)
        val et_hint = ta.getString(R.styleable.CertificationItemView_et_hint)
        val et_text_color = ta.getColor(
            R.styleable.CertificationItemView_et_text_color,
            UIUtils.getColor(R.color.black)
        )
        val et_hint_text_color = ta.getColor(
            R.styleable.CertificationItemView_et_hint_text_color,
            UIUtils.getColor(R.color.blue_ff9f)
        )

        val tv_right_text = ta.getString(R.styleable.CertificationItemView_tv_right_text)

        val tv_left_text_color = ta.getColor(
            R.styleable.CertificationItemView_tv_left_text_color,
            UIUtils.getColor(R.color.black_ff0d)
        )
        val tv_right_text_color = ta.getColor(
            R.styleable.CertificationItemView_tv_right_text_color,
            UIUtils.getColor(R.color.gray_b3ff)
        )

        val tv_right_icon =
            ta.getResourceId(R.styleable.CertificationItemView_tv_right_icon, R.mipmap.icon_arror)


        val et_text_size = ta.getInt(R.styleable.CertificationItemView_et_text_size, 16)
        val text_Style = ta.getInt(R.styleable.CertificationItemView_text_Style, 0)

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


//        if (rl_item.visibility == VISIBLE) {
        if (!tv_left_text.isNullOrEmpty()) {
            tv_item_left.text = tv_left_text
            tv_item_left.setTextColor(tv_left_text_color)
        }
        if (!tv_right_text.isNullOrEmpty()) {
            tv_item_right.text = tv_right_text
            tv_item_right.setTextColor(tv_right_text_color)
        }
//        }
        if (title_text != null) {
            if (title_text.isNotBlank()) {
                tv_title.text = title_text

                if (text_Style == 1) {
                    tv_title.typeface = Typeface.defaultFromStyle(Typeface.BOLD);
                }


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


        if (isShowIcon) {
            tv_select.setDrawableRight(tv_right_icon)

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
//                    listener?.onTextChage(it.toString())
                    mOnTextChageListener?.invoke(it.toString())
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        if (!tv_text.isNullOrEmpty()) {
            tv_select.text = tv_text
        }
        tv_select.setOnClickListener {
            mOnClickListener?.invoke(it)
        }

    }


    var mOnClickListener: ((View) -> Unit)? = null

    fun setOnTVClickListener(lisenter: (View) -> Unit) {
        mOnClickListener = lisenter
    }

    var mOnTextChageListener: ((String) -> Unit)? = null

    fun setOnTextListener(lisenter: (String) -> Unit) {
        mOnTextChageListener = lisenter
    }


//    fun setOnContextClickListener(l: (View) -> Unit) {
//        mOnContextClickListener = l;
//    }
//    var mOnContextClickListener: ((View) -> Unit)? = null

//=================


    fun setEditTextStr(str: String): CertificationItemView {

        if (str.isNotEmpty()) {
            et_input.setTextColor(UIUtils.getColor(R.color.blue_ff32))
            et_input.setText(str)

        }
        return this
    }

    fun setEditHintStr(str: String): CertificationItemView {
        if (str.isNotEmpty()) {
            et_input.setHint(str)
        }
        return this
    }


    fun getEditTextStr(): String {

        return et_input.text.toString()

    }
    fun getEditText(): EditText {

        return et_input

    }
    fun setSelectText(str: String) {
        if (str.isNotEmpty()) {

            tv_select.setTextColor(UIUtils.getColor(R.color.blue_ff32))
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

    fun setRightText(str: String) {

        if (str.isNotEmpty()) {
            tv_item_right.text = str

        }

    }

    fun getRightText(): String {
        return tv_item_right.text.toString()
    }

    fun setRightTextColor(color: Int) {

        tv_item_right.setTextColor(color)


    }

    fun setTitle(str: String): CertificationItemView {
        if (str.isNotEmpty()) {
            tv_title.text = str
        }
        return this
    }

    fun getTitle(): String {
        return tv_title.text.toString()
    }



    fun hideLine(isHide: Boolean) {
        line.visibility = if (isHide) GONE else VISIBLE
    }
}


