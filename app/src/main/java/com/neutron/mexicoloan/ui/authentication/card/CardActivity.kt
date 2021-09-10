package com.neutron.mexicoloan.ui.authentication.card

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
        dataMap["user_id"] = PreferencesHelper.getUserID()
        dataMap["card_hold_name"] = PreferencesHelper.getRealname()
        mViewModel.getBankInfo()

        userinfo = PreferencesHelper.getUserInfo()
    }

    override fun initView() {
        iv_back.setOnClickListener { finish() }
        tv_title.text = getString(R.string.money_caed)
        civ_bank_code.setOnTextListener {
            var newText = it.chunked(4).joinToString(" ")
            tv_bank_code.animateText(newText)
        }
        initTab()
        civ_bank_name.setOnTVClickListener {
            showBankS()
        }
        btn_submit_card.setOnClickListener {
            val bankCodeStr = civ_bank_code.getTextStr()
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
        }
        tab2?.let {
            it.text = "Tarjeta de débito"
            tl_tab.addTab(it)
        }
        tl_tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    if (it == tab1) {
                        isClabe = true
                        civ_bank_code.setEditHintStr(getString(R.string.clabe_tip)).setTitle(getString(R.string.clabe_code))
                    } else {
                        isClabe = false
                        civ_bank_code.setEditHintStr(getString(R.string.bank_tip)).setTitle(getString(R.string.bank_code))
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


        })

        mViewModel.sMoneyCardInfoResult.observe(this, {
            Slog.d("observeValue  $it")

            bankInfoResults.forEach { bank1 ->
                if (bank1.bank_code == it.bank_code) {
                    tv_bank_name.animateText(bank1.bank_name)
                    civ_bank_name.setTextStr(bank1.bank_name)
                }
            }


            civ_bank_code.setTextStr(it.card_no)
            dataMap["bank_code"] = it.bank_code
            dataMap["card_hold_name"] = it.card_hold_name
        })
    }
}