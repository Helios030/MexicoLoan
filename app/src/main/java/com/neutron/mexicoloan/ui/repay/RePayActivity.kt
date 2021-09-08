package com.neutron.mexicoloan.ui.repay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayout
import com.neutron.baselib.base.BaseVMActivity
import com.neutron.baselib.utils.Slog
import com.neutron.baselib.utils.collapse
import com.neutron.baselib.utils.expand
import com.neutron.mexicoloan.R
import com.neutron.mexicoloan.ui.view.dialog.CommImgDialog
import kotlinx.android.synthetic.main.activity_card.*
import kotlinx.android.synthetic.main.activity_repay.*
import kotlinx.android.synthetic.main.activity_repay.civ_bank_code
import kotlinx.android.synthetic.main.activity_repay.tl_tab

class RePayActivity : BaseVMActivity<RePayVM>(RePayVM::class.java) {

    override fun initData() {

    }

    override fun observeValue() {

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_repay
    }

    override fun initView() {
        btn_submit.setOnClickListener {
            CommImgDialog(this).setTitle(getString(R.string.repay_submit_success))
                .setIcon(R.mipmap.img_repay_success).setOnOkClick {


                }.show()
        }
        initTab()
    }


    var tab1: TabLayout.Tab? = null
    var tab2: TabLayout.Tab? = null
    private fun initTab() {
        tab1 = tl_tab.newTab()
        tab2 = tl_tab.newTab()
        tab1?.let {
            it.text = getString(R.string.all_pay)
            tl_tab.addTab(it)
        }

        tab2?.let {
            it.text = getString(R.string.sub_pay)
            tl_tab.addTab(it)
        }
        tl_tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {


                tab?.let {

                    rl_sub.visibility = if (it == tab1) {
                        rl_sub.collapse()
                        View.GONE
                    } else {


                        rl_sub.expand()
                        View.VISIBLE
                    }

                }


            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })


    }

}