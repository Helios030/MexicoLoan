package com.neutron.mexicoloan.ui.view.verificationcodeview

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import com.blankj.utilcode.util.KeyboardUtils
import com.neutron.mexicoloan.R
import kotlinx.android.synthetic.main.verify_code.view.*

class VCView : LinearLayout, View.OnFocusChangeListener, TextWatcher, View.OnKeyListener {

    private val etList = ArrayList<EditText>()
    private var listener: OnVerifyCodeListener? = null

    constructor(context: Context?) : super(context!!) {
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!,
        attrs
    ) {
        LayoutInflater.from(context).inflate(R.layout.verify_code, this, true);


        etList.add(etOne)
        etList.add(etTwo)
        etList.add(etThree)
        etList.add(etFour)
        etList.add(etFive)
        etList.add(etSix)
        KeyboardUtils.showSoftInput(etOne)
        etOne.isFocusable = true

        etList.forEach {
            it.setOnKeyListener(this)
            it.addTextChangedListener(this)
            it.onFocusChangeListener = this
        }


    }


    private fun focus() {
        etList.forEachIndexed { index, editText ->
            if (editText.text.isEmpty()) {
                editText.isCursorVisible = true
                editText.requestFocus()
                return
            } else {
                editText.isCursorVisible = false
                if (index == etList.size - 1) {
                    editText.requestFocus()
                }
            }
        }
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
            backFocus()
        }
        return false
    }

    override fun afterTextChanged(s: Editable) {
        if (s.isNotEmpty()) {
            focus()
        }
        listener?.onTextChange(this, getResult())
        val lastEditText = etList[etList.size - 1]
        if (lastEditText.text.isNotEmpty()) {
            listener?.onComplete(this, getResult())
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (hasFocus) {
            focus()
        }
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        etList.forEach {
            it.isEnabled = enabled
        }
    }


    private fun getResult(): String {
        val stringBuffer = StringBuilder("")
        etList.forEach {
            stringBuffer.append(it.text.toString())
        }
        return stringBuffer.toString()
    }

    private fun backFocus() {
        for (index in etList.size - 1 downTo 0) {
            val editText = etList[index]
            if (editText.text.isNotEmpty()) {
                editText.setText("")
                editText.isCursorVisible = true
                editText.requestFocus()
                return
            }
        }
    }


    fun setOnVerifyCodeListener(listener: OnVerifyCodeListener) {
        this.listener = listener
    }

    interface OnVerifyCodeListener {

        fun onTextChange(view: View, content: String)

        fun onComplete(view: View, content: String)
    }

}