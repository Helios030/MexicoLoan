package com.neutron.mexicoloan.ui.authentication.work


import com.neutron.baselib.base.BaseVMActivity
import com.neutron.baselib.utils.startTo
import com.neutron.mexicoloan.R
import com.neutron.mexicoloan.ui.authentication.userinfo.PersonalInfoActivity
import kotlinx.android.synthetic.main.activity_work.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class WorkActivity : BaseVMActivity<WorkVM>(WorkVM::class.java) {
    override fun getLayoutId(): Int {
        return R.layout.activity_work
    }

    override fun initView() {
        iv_back.setOnClickListener { finish() }
        tv_title.text = getString(R.string.work_info)

        btn_next.setOnClickListener {
            startTo(PersonalInfoActivity::class.java)

        }

    }

    override fun initData() {

    }


}