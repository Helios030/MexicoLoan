package com.neutron.mexicoloan.ui.authentication.userinfo

import com.neutron.baselib.base.BaseVMActivity
import com.neutron.baselib.utils.startTo
import com.neutron.mexicoloan.R
import com.neutron.mexicoloan.ui.authentication.contact.ContactActivity
import kotlinx.android.synthetic.main.activity_personal_info.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class PersonalInfoActivity : BaseVMActivity<PersonalInfoVM>(PersonalInfoVM::class.java) {

    override fun getLayoutId(): Int {
        return R.layout.activity_personal_info
    }

    override fun initView() {

        iv_back.setOnClickListener { finish() }
        tv_title.text = getString(R.string.user_info)

        btn_next.setOnClickListener {
            startTo(ContactActivity::class.java)
        }

    }

    override fun initData() {
    }
}