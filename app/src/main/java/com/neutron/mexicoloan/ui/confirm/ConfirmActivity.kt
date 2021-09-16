package com.neutron.mexicoloan.ui.confirm

import com.neutron.baselib.base.BaseVMActivity
import com.neutron.baselib.utils.PreferencesHelper
import com.neutron.baselib.utils.Slog
import com.neutron.baselib.utils.startTo
import com.neutron.mexicoloan.R
import com.neutron.mexicoloan.ui.main.MainActivity
import com.neutron.mexicoloan.ui.view.dialog.CommImgDialog
import com.neutron.mexicoloan.util.showBankDialog
import kotlinx.android.synthetic.main.activity_confirm.*

class ConfirmActivity : BaseVMActivity<ConfirmVM>(ConfirmVM::class.java) {

    var productID: String? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_confirm
    }

    override fun initView() {
        btn_upload.setOnClickListener {
            val userId = PreferencesHelper.getUserID()
            val livenessId = PreferencesHelper.getLivenessID()
            if (livenessId.isEmpty() || productID == null) {
                Slog.d("未进行活体认证 livenessId $livenessId  id $productID")
                return@setOnClickListener
            }
            val dataMap = HashMap<String, Any>()
            dataMap["user_id"] = userId
            dataMap["product_id"] = productID!!
            dataMap["file"] = livenessId!!
            dataMap["method"] = "advance"
            showLoading()
            mViewModel?.uploadRequest(dataMap)

        }

    }

    override fun initData() {
        productID = PreferencesHelper.getProductId()
        productID?.let {
            mViewModel.confirmInfo(it)
        }

    }

    override fun observeValue() {

        mViewModel.confirmationRequest.observe(this, {
            Slog.d("确认信息   $it")
            civ_fees_service.setRightText(it.risk)
            civ_audit_fee.setRightText(it.service)
            civ_pay_fee.setRightText(it.pay)
        })
        mViewModel.requestOrderResult.observe(this, {
            CommImgDialog(this).setTitle(getString(R.string.submit_ok))
                .setIcon(R.mipmap.img_submit_success).setOnOkClick {
                startTo(MainActivity::class.java, true)
            }.show()
        })

    }


}