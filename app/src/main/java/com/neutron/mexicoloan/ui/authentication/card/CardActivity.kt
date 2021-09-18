package com.neutron.mexicoloan.ui.authentication.card

import android.text.Editable
import android.text.InputFilter
import android.text.Selection
import android.text.TextWatcher
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.google.android.material.tabs.TabLayout
import com.neutron.baselib.base.BaseVMActivity
import com.neutron.baselib.bean.BankInfoResult
import com.neutron.baselib.bean.UserInfo
import com.neutron.baselib.utils.PreferencesHelper
import com.neutron.baselib.utils.Slog
import com.neutron.baselib.utils.toast
import com.neutron.mexicoloan.R

import com.neutron.mexicoloan.util.showBankDialog

import kotlinx.android.synthetic.main.activity_card.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class CardActivity : BaseVMActivity<CardVM>(CardVM::class.java) {

    override fun getLayoutId(): Int {
        return R.layout.activity_card
    }

    var userinfo: UserInfo? = null

    override fun initData() {


        dataMap["accountBankType"] = "1"
        dataMap["user_id"] = PreferencesHelper.getUserID()
        dataMap["card_hold_name"] = PreferencesHelper.getRealname()
        mViewModel.getBankInfo()

        userinfo = PreferencesHelper.getUserInfo()
    }

    override fun initView() {
        iv_back.setOnClickListener { finish() }
        tv_title.text = getString(R.string.money_caed)
        civ_bank_code.setOnTextListener {
            var newText = it.replace("-", "").chunked(4).joinToString(" ")
            tv_bank_code.animateText(newText)
        }
        initTab()
        civ_bank_name.setOnTVClickListener {
            showBankS()
        }
        btn_submit_card.setOnClickListener {
            val bankCodeStr = civ_bank_code.getTextStr().replace("-", "")
            if (dataMap["bank_code"] == null) {
                toast(R.string.select_bank_account)
            } else if (bankCodeStr.isNullOrEmpty()) {
                toast(R.string.select_bank_number)
            } else {
                userinfo?.phone?.let {
                    dataMap["card_no"] = bankCodeStr
                    dataMap["card_phone"] = it
                    mViewModel.uploadMoneyCardInfo(dataMap)
                }
            }
        }

        val editT = civ_bank_code.getEditText()

        bankCardInput(editT)

    }


    /**
     * 设置银行卡输入时每隔4位多一位空格
     * @param cardEt
     */
    fun bankCardInput(cardEt: EditText) {
        //设置输入长度不超过24位(包含空格)
        cardEt.setFilters(arrayOf<InputFilter>(InputFilter.LengthFilter(24)))
        cardEt.addTextChangedListener(object : TextWatcher {
            var kongge = '-'

            //改变之前text长度
            var beforeTextLength = 0

            //改变之前的文字
            private val beforeChar: CharSequence? = null

            //改变之后text长度
            var onTextLength = 0

            //是否改变空格或光标
            var isChanged = false

            // 记录光标的位置
            var location = 0
            private var tempChar: CharArray? = null
            private val buffer = StringBuffer()

            //已有空格数量
            var konggeNumberB = 0

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                beforeTextLength = s.length
                if (buffer.length > 0) {
                    buffer.delete(0, buffer.length)
                }
                konggeNumberB = 0
                for (i in 0 until s.length) {
                    if (s[i] == kongge) {
                        konggeNumberB++
                    }
                }
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                onTextLength = s.length
                buffer.append(s.toString())
                if (onTextLength == beforeTextLength || onTextLength <= 3 || isChanged) {
                    isChanged = false
                    return
                }
                isChanged = true
            }

            override fun afterTextChanged(s: Editable?) {
                if (isChanged) {
                    location = cardEt.getSelectionEnd()
                    var index = 0
                    while (index < buffer.length) {
                        if (buffer[index] == kongge) {
                            buffer.deleteCharAt(index)
                        } else {
                            index++
                        }
                    }
                    index = 0
                    var konggeNumberC = 0
                    while (index < buffer.length) {
                        if (index == 4 || index == 9 || index == 14 || index == 19) {
                            buffer.insert(index, kongge)
                            konggeNumberC++
                        }
                        index++
                    }
                    if (konggeNumberC > konggeNumberB) {
                        location += konggeNumberC - konggeNumberB
                    }
                    tempChar = CharArray(buffer.length)
                    buffer.getChars(0, buffer.length, tempChar, 0)
                    val str = buffer.toString()
                    if (location > str.length) {
                        location = str.length
                    } else if (location < 0) {
                        location = 0
                    }
                    cardEt.setText(str)
                    val etable: Editable = cardEt.text
                    Selection.setSelection(etable, location)
                    isChanged = false
                }
            }
        })
    }


    private fun showBankS() {
        showBankDialog(getString(R.string.bank), bankInfoResults) {
            if (it is BankInfoResult) {
                dataMap["bank_code"] = it.bank_code
                tv_bank_name.animateText(it.bank_name)
                civ_bank_name.setTextStr(it.bank_name)
            }
        }
    }

    val dataMap = HashMap<String, Any>()
    var bankInfoResults = mutableListOf<BankInfoResult>()
    var isClabe = true
    var tab1: TabLayout.Tab? = null
    var tab2: TabLayout.Tab? = null
    private fun initTab() {
        tab1 = tl_tab.newTab()
        tab2 = tl_tab.newTab()
        tab1?.let {
            it.text = "CLABE"
            tl_tab.addTab(it)
            dataMap["accountBankType"] = "1"
        }
        tab2?.let {
            it.text = "Tarjeta de débito"
            dataMap["accountBankType"] = "2"
            tl_tab.addTab(it)
        }


        tl_tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    if (it == tab1) {
                        isClabe = true
                        civ_bank_code.setEditHintStr(getString(R.string.clabe_tip))
                            .setTitle(getString(R.string.clabe_code))
                    } else {
                        isClabe = false
                        civ_bank_code.setEditHintStr(getString(R.string.bank_tip))
                            .setTitle(getString(R.string.bank_code))
                    }
                }
                Slog.d("isClabe  $isClabe")
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    override fun observeValue() {
        mViewModel.BankInfoResults.observe(this, {

            bankInfoResults.clear()
            bankInfoResults.addAll(it)
            mViewModel.getServiceMoneyCard()
        })

        mViewModel.isUploadSuccess.observe(this, {
            toast(R.string.submit_ok)
            finish()
        })

        mViewModel.sMoneyCardInfoResult.observe(this, {
            Slog.d("observeValue  $it")

            bankInfoResults.forEach { bank1 ->
                if (bank1.bank_code == it.bank_code) {
                    tv_bank_name.animateText(bank1.bank_name)
                    civ_bank_name.setTextStr(bank1.bank_name)
                }
            }
            if (it.card_no.substring(0, 2) == "058") {
                tab1?.select()
                dataMap["accountBankType"] = "1"
            } else {
                tab2?.select()
                dataMap["accountBankType"] = "2"
            }
            civ_bank_code.setTextStr(it.card_no)
            dataMap["bank_code"] = it.bank_code
            dataMap["card_hold_name"] = it.card_hold_name
        })
    }
}