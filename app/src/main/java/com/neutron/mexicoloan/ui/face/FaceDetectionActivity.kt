package com.neutron.mexicoloan.ui.face

import ai.advance.liveness.lib.GuardianLivenessDetectionSDK
import ai.advance.liveness.lib.LivenessResult
import ai.advance.liveness.sdk.activity.LivenessActivity
import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fc.bioassay.api.Bioassay

import com.neutron.baselib.base.BaseVMActivity
import com.neutron.baselib.utils.PreferencesHelper
import com.neutron.baselib.utils.Slog
import com.neutron.baselib.utils.startTo
import com.neutron.baselib.utils.toast
import com.neutron.mexicoloan.R
import com.neutron.mexicoloan.ui.confirm.ConfirmActivity
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.activity_face_detection.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class FaceDetectionActivity : BaseVMActivity<FaceDetectionVM>(FaceDetectionVM::class.java) {

    override fun getLayoutId(): Int {
        return R.layout.activity_face_detection
    }

    override fun initView() {
        iv_back.setOnClickListener { finish() }
        tv_title.text = getString(R.string.face_detection)


    }

    override fun initData() {


//todo 信息披露弹框
        uploadAppAndPhone(this)

        btn_next.setOnClickListener {

//            todo 暂时没有Key
            Bioassay.detectorFace(this@FaceDetectionActivity, object : Bioassay.BioassayListener {
                override fun onSuccess(
                    positiveFaceBitmap: Bitmap?,
                    eyeBitmap: Bitmap?,
                    leftFaceBitmap: Bitmap?,
                    rightFaceBitmap: Bitmap?
                ) {
                    mViewModel.transfer(positiveFaceBitmap)
                }

                override fun onFailed(errorCode: Int, errorMessage: String) {
                    toast(errorMessage)
                }
            })


        }
    }

    var isSupprotFace = false
    override fun observeValue() {
        mViewModel.liveBase64.observe(this, {
            PreferencesHelper.setLivenessID(it)
            startTo(ConfirmActivity::class.java)
        }
        )

    }




    fun uploadAppAndPhone(context: Context) {

        PermissionX.init(this)
            .permissions(Manifest.permission.READ_CONTACTS,Manifest.permission.READ_SMS)
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(
                    deniedList,
                    getString(R.string.not_pp),
                    getString(R.string.dialog_ok),
                    getString(R.string.dialog_cancel)
                )
            }   .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(
                    deniedList,
                    getString(R.string.not_pp),
                    getString(R.string.dialog_ok),
                    getString(R.string.dialog_cancel)
                )
            }  .request { allGranted, _, _ ->
                if (allGranted) {
                   mViewModel.uploadDeviceApp(this)
                } else {
                    toast(R.string.not_pp)
                }
            }




    }


}