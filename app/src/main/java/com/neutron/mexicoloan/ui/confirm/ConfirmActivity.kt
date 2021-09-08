package com.neutron.mexicoloan.ui.confirm

import com.neutron.baselib.base.BaseVMActivity
import com.neutron.mexicoloan.R
import com.neutron.mexicoloan.ui.view.dialog.CommImgDialog
import com.neutron.mexicoloan.util.showBankDialog

class ConfirmActivity : BaseVMActivity<ConfirmVM>(ConfirmVM::class.java) {


    override fun getLayoutId(): Int {
        return R.layout.activity_confirm
    }

    override fun initView() {


 CommImgDialog(this).setTitle(getString(R.string.submit_ok)).setIcon(R.mipmap.img_submit_success).setOnOkClick {


        }.show()
    }

    override fun initData() {

    }

    override fun observeValue() {

    }


}