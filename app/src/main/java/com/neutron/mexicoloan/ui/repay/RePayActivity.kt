package com.neutron.mexicoloan.ui.repay

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.neutron.baselib.base.BaseVMActivity
import com.neutron.baselib.bean.UserInfo
import com.neutron.baselib.utils.*
import com.neutron.mexicoloan.R
import com.neutron.mexicoloan.ui.main.MainActivity
import com.neutron.mexicoloan.ui.view.dialog.CommImgDialog
import kotlinx.android.synthetic.main.activity_card.*
import kotlinx.android.synthetic.main.activity_repay.*
import kotlinx.android.synthetic.main.activity_repay.civ_bank_code
import kotlinx.android.synthetic.main.activity_repay.tl_tab
import kotlinx.android.synthetic.main.activity_repay.view.*
import kotlinx.android.synthetic.main.fragment_user.*
import java.io.File

class RePayActivity : BaseVMActivity<RePayVM>(RePayVM::class.java) {
    val dataMap = HashMap<String, Any>()

    var isPayAll: Boolean = true
    var applicationId: String? = null
    var amount: String? = null

    var userInfo:UserInfo?=null

    override fun initData() {
        val bundle: Bundle? = intent.extras?.getBundle("bundle")
        applicationId = bundle?.getString("applicationId")
        amount = bundle?.getString("amount")

        userInfo=  PreferencesHelper.getUserInfo()

        if (amount != null && applicationId != null ) {
            val vaMap = HashMap<String, Any>()
            vaMap["user_id"] = PreferencesHelper.getUserID()
            vaMap["amount"] = amount!!
            vaMap["atm_otc"] = "1"
            vaMap["bank_code"] = "OFFLINE"
            vaMap["application_id"] = applicationId!!
            vaMap["vaType"] = if (isPayAll) { "1" } else { "2" }
            userInfo?.let {
                trackClickVaEvent(it.phone)
            }

          mViewModel.getVa(vaMap)

        }
    }

    override fun observeValue() {

        mViewModel.isUploadSuccess.observe(this,{
            CommImgDialog(this).setTitle(getString(R.string.repay_submit_success))
                .setIcon(R.mipmap.img_repay_success).setOnOkClick {
                    startTo(MainActivity::class.java)
                }.show()
        })

        mViewModel.VABeanResult.observe(this,{
            userInfo?.let {
                trackVaSuccessEvent(it.phone)
            }

            tv_money.text=it.amount?:""
            tv_mark.text="${getString(R.string.repay_sign)} ${it.uniqueId?:""}"
            tv_sub_start_time.text=it.startTime?:""
            tv_sub_end_time.text=it.endTime?:""
            civ_account_name.setTextStr(it.account_name?:"")
            civ_bank.setTextStr(it.bank_name?:"")
            civ_bank_code.setTextStr(it.va?:"")
            dataMap["application_id"] = applicationId!!
            dataMap["amount"] = amount!!
            dataMap["bankCardNo"] = it!!.va
            dataMap["vaType"] = if (isPayAll!!) { "1" } else { "2"
            }

        })



    }

    override fun getLayoutId(): Int {
        return R.layout.activity_repay
    }

    override fun initView() {
        initTab()
        btn_submit.setOnClickListener {

            dataMap["cardNo"]=  civ_bank_number.getTextStr()
            if (dataMap["cardNo"] == null) {
                toast(R.string.input_code_tip)
            } else if (file == null) {
                toast(R.string.upload_repay_tip)
            } else {
                file?.let {
                    Slog.d("dataMap  $dataMap")
                    mViewModel.uploadRepayment(dataMap, it)
                }
            }


        }

        iv_upload.setOnClickListener { selectPhoto() }

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
                        isPayAll=true
                        rl_sub.collapse()
                        View.GONE
                    } else {
                        isPayAll=false
                        rl_sub.expand()
                        View.VISIBLE
                    }

                }


            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })


    }


    //    选择图片
    fun selectPhoto() {
        PictureSelector.create(this)
            .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
            .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
            .isWeChatStyle(true)// 是否开启微信图片选择风格
            .isPageStrategy(true)// 是否开启分页策略 & 每页多少条；默认开启
            .maxSelectNum(1)// 最大图片选择数量
            .minSelectNum(1)// 最小选择数量
            .imageSpanCount(4)// 每行显示个数
            .isReturnEmpty(false)// 未选择数据时点击按钮是否可以返回
            .closeAndroidQChangeWH(true)//如果图片有旋转角度则对换宽高,默认为true
            .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
            .isOriginalImageControl(false)// 是否显示原图控制按钮，如果设置为true则用户可以自由选择是否使用原图，压缩、裁剪功能将会失效
            .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
            .isPreviewImage(true)// 是否可预览图片
            .isCamera(true)// 是否显示拍照按钮
            //.isZoomAnim(true)// 图片列表点击 缩放效果 默认true
            //.isEnableCrop(true)// 是否裁剪
            .isCompress(true)// 是否压缩
            .synOrAsy(true)//同步true或异步false 压缩 默认同步
            //.withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
            //.freeStyleCropEnabled(true)// 裁剪框是否可拖拽
            //.showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
            .cutOutQuality(90)// 裁剪输出质量 默认100
            .minimumCompressSize(0)// 小于多少kb的图片不压缩
            .forResult(PictureConfig.CHOOSE_REQUEST)

    }
    var file: File? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                val localMediaList = PictureSelector.obtainMultipleResult(data)
                if (localMediaList != null && localMediaList.size > 0) {
                    val localMedia = localMediaList[0]
                    val imgPath = if (localMedia.isCut && !localMedia.isCompressed) {
                        // 裁剪过
                        localMedia.cutPath
                    } else if (localMedia.isCompressed || localMedia.isCut && localMedia.isCompressed) {
                        // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                        localMedia.compressPath
                    } else {
                        // 原图
                        localMedia.path
                    }
                    val file = File(imgPath)
                    if (file.exists()) {
                        this.file = file
                        Glide.with(this).load(file).into(iv_upload)
                        dataMap["file"] = file.name
                    }
                }
            }
        }


    }



}