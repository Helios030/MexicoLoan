package com.neutron.mexicoloan.ui.confirm

import com.neutron.baselib.base.BaseVMActivity
import com.neutron.baselib.utils.PreferencesHelper
import com.neutron.baselib.utils.Slog
import com.neutron.baselib.utils.UIUtils
import com.neutron.baselib.utils.startTo
import com.neutron.mexicoloan.R
import com.neutron.mexicoloan.ui.main.MainActivity
import com.neutron.mexicoloan.ui.view.dialog.CommImgDialog
import com.neutron.mexicoloan.util.showBankDialog
import kotlinx.android.synthetic.main.activity_confirm.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class ConfirmActivity : BaseVMActivity<ConfirmVM>(ConfirmVM::class.java) {

    var productID: String? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_confirm
    }

    override fun initView() {

        iv_back.setOnClickListener { finish() }
        tv_title.text = getString(R.string.confirm_order)

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
            dataMap["method"] = "flashRisk"
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

            tv_money.text="$${it.amount}"
            repay_time.text=getString(R.string.loan_time).format(it.duration)
            val ff32= UIUtils.getColor(R.color.blue_ff32)
            civ_fees_service.setRightText(it.risk)
            civ_fees_service.setRightTextColor(ff32)
            civ_audit_fee.setRightText(it.service)
            civ_pay_fee.setRightText(it.pay)
            civ_audit_fee.setRightTextColor(ff32)
            civ_pay_fee.setRightTextColor(ff32)

        })
        mViewModel.requestOrderResult.observe(this, {
            CommImgDialog(this).setTitle(getString(R.string.submit_ok))
                .setIcon(R.mipmap.img_submit_success).setOnOkClick {
                startTo(MainActivity::class.java, true)
            }.show()
        })

    }


}