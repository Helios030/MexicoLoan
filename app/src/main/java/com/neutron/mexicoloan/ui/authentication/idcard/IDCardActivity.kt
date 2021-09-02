package com.neutron.mexicoloan.ui.authentication.idcard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neutron.baselib.base.BaseVMActivity
import com.neutron.baselib.utils.startTo
import com.neutron.mexicoloan.R
import com.neutron.mexicoloan.ui.authentication.work.WorkActivity
import kotlinx.android.synthetic.main.activity_idcard.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class IDCardActivity : BaseVMActivity<IDCardVM>(IDCardVM::class.java) {
    override fun initView() {
        iv_back.setOnClickListener { finish() }
        tv_title.text = getString(R.string.user_info_id)

        btn_next.setOnClickListener {
            startTo(WorkActivity::class.java)
        }
    }

    override fun initData() {
        
    }

    override fun getLayoutId(): Int {
        return  R.layout.activity_idcard
    }



}