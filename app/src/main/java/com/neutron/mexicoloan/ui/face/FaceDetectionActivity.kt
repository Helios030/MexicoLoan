package com.neutron.mexicoloan.ui.face

import ai.advance.liveness.lib.GuardianLivenessDetectionSDK
import ai.advance.liveness.lib.LivenessResult
import ai.advance.liveness.sdk.activity.LivenessActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neutron.baselib.base.BaseVMActivity
import com.neutron.baselib.utils.PreferencesHelper
import com.neutron.baselib.utils.Slog
import com.neutron.baselib.utils.startTo
import com.neutron.baselib.utils.toast
import com.neutron.mexicoloan.R
import com.neutron.mexicoloan.ui.confirm.ConfirmActivity
import kotlinx.android.synthetic.main.activity_face_detection.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class FaceDetectionActivity : BaseVMActivity<FaceDetectionVM>(FaceDetectionVM::class.java) {

    override fun getLayoutId(): Int {
        return R.layout.activity_face_detection
    }
    private  val REQUEST_LIVENESS_CODE = 99
    override fun initView() {
        iv_back.setOnClickListener { finish() }
        tv_title.text = getString(R.string.face_detection)
        btn_next.setOnClickListener {

       startActivityForResult(Intent().apply {
           LivenessActivity::class.java
       }, REQUEST_LIVENESS_CODE)
}

    }

    override fun initData() {
        mViewModel.getAdvancelicense()
    }
    var isSupprotFace = false
    override fun observeValue() {
        mViewModel.advanceLicenseResult.observe(this,{
            val license = it.license

            val checkResult = GuardianLivenessDetectionSDK.setLicenseAndCheck(license)
            Slog.d("活体检测key认证 $checkResult")
            if ("SUCCESS" == checkResult) {

            }
            isSupprotFace = true
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {

            REQUEST_LIVENESS_CODE ->
                if (LivenessResult.isSuccess()) {
                    val livenessId = LivenessResult.getLivenessId() // 本次活体id
                    PreferencesHelper.setLivenessID(livenessId)
                    Slog.d("活体认证  $livenessId  ")

                    startTo(ConfirmActivity::class.java)
                } else {

                    if (LivenessResult.getErrorMsg() != null) {
                        toast(LivenessResult.getErrorMsg())
                        Slog.d("活体认证失败  ${LivenessResult.getErrorMsg()}")
                    }

                }
        }

    }
}