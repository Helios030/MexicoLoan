package com.neutron.mexicoloan.ui.authentication.contact


import android.view.View
import com.neutron.baselib.base.BaseVMActivity
import com.neutron.baselib.utils.Slog
import com.neutron.baselib.utils.startTo
import com.neutron.mexicoloan.R
import com.neutron.mexicoloan.ui.authentication.card.CardActivity
import com.neutron.mexicoloan.ui.view.CertificationItemView
import kotlinx.android.synthetic.main.activity_card.*
import kotlinx.android.synthetic.main.activity_contact.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class ContactActivity : BaseVMActivity<ContactVM>(ContactVM::class.java) {



    override fun getLayoutId(): Int {
       return  R.layout.activity_contact
    }

    override fun initView() {
        iv_back.setOnClickListener { finish() }
        tv_title.text = getString(R.string.contact_info)
        btn_next.setOnClickListener {
            startTo(CardActivity::class.java)
        }



    }

    override fun initData() {
        
    }


}