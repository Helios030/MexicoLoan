package com.neutron.mexicoloan.ui.authentication.card

import android.view.View
import com.neutron.baselib.base.BaseVMActivity
import com.neutron.baselib.utils.Slog
import com.neutron.mexicoloan.R
import com.neutron.mexicoloan.ui.view.CertificationItemView
import kotlinx.android.synthetic.main.activity_card.*

class CardActivity : BaseVMActivity<CardVM>(CardVM::class.java) {

    override fun getLayoutId(): Int {
        return R.layout.activity_card
    }

    override fun initView() {
        civ_bank_code.setCIVListener(object : CertificationItemView.CIVListener {
            override fun onClick(view: View) {

            }

            override fun onTextChage(str: String) {
                Slog.d("文字改变   $str")
                var newText = str.chunked(4).joinToString(" ")
                tv_bank_code.animateText(newText)

            }

        })
    }

    override fun initData() {

    }


}