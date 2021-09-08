package com.neutron.mexicoloan.ui.authentication.idcard

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.bumptech.glide.Glide
import com.contrarywind.view.WheelView
import com.neutron.baselib.base.BaseVMActivity

import com.neutron.baselib.utils.Slog
import com.neutron.baselib.utils.UIUtils
import com.neutron.baselib.utils.getStrArray
import com.neutron.baselib.utils.startTo
import com.neutron.mexicoloan.R
import com.neutron.mexicoloan.ui.authentication.work.WorkActivity
import com.neutron.mexicoloan.ui.view.dialog.MenuItem

import com.neutron.mexicoloan.util.Str2MenuItem
import com.neutron.mexicoloan.util.showCommSelectDialog
import com.ronal.camera.camera.IDCardCamera
import kotlinx.android.synthetic.main.activity_idcard.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class IDCardActivity : BaseVMActivity<IDCardVM>(IDCardVM::class.java) {
    override fun initView() {
        iv_back.setOnClickListener { finish() }
        tv_title.text = getString(R.string.user_info_id)
        btn_next.setOnClickListener {
            startTo(WorkActivity::class.java)
        }

        iv_true.setOnClickListener {
            openCamera(true)
        }
        iv_false.setOnClickListener {
            openCamera(false)
        }
        civ_gender.setOnTVClickListener { showCommSelectDialog(getString(R.string.gender), getStrArray(R.array.array_genders).Str2MenuItem(),{

            if (it is MenuItem) {
                Slog.d("选中选项 ${it.menuName} ")
                civ_gender.setTextStr(it.menuName)
            }
        })  }

        civ_birthday.setOnTVClickListener {
            showBirthday(getString(R.string.birthday))
        }



    }


    private fun showBirthday(title: String) {
        val startDate = Calendar.getInstance()
        val endDate = Calendar.getInstance()
        startDate.set(1900, 0, 1)
        selectTime = TimePickerBuilder(this
        ) { date, _ -> //选中事件回调
            civ_birthday.setTextStr(SimpleDateFormat("dd/MM/yyyy").format(date))
        }.setLayoutRes(R.layout.view_select_birthday) { view ->
            val tvSubmit = view.findViewById<TextView>(R.id.tv_ok)
            val tvTitle = view.findViewById<TextView>(R.id.tv_title)

            tvTitle.text = title
            val tvClose = view.findViewById<TextView>(R.id.tv_cancel)
            tvSubmit.setOnClickListener {
                selectTime?.returnData()
                selectTime?.dismiss()
            }
            tvClose.setOnClickListener { selectTime?.dismiss() }
        }.setContentTextSize(15)
            .isDialog(true)
            .setType(booleanArrayOf(true, true, true, false, false, false))
            .setLabel("", "", "", "", "", "")
            .setLineSpacingMultiplier(2.5f)
//          .setDate(selectedDate)
            .setOutSideCancelable(true)
            .setRangDate(startDate, endDate)
            .setTextXOffset(0, 0, 0, 40, 0, -40)
            .isCenterLabel(true)
            .setItemVisibleCount(5)
            .setTextColorCenter(UIUtils.getColor(R.color.blue_ff32))
            .setDividerColor(UIUtils.getColor(R.color.blue_ffea))
            .setDividerType(WheelView.DividerType.FILL)
            .setDecorView(
                this.window.decorView.findViewById<View>(android.R.id.content) as ViewGroup
            )
            .build()
        selectTime?.show()

    }

    private var selectTime: TimePickerView? = null


    override fun getLayoutId(): Int {
        return R.layout.activity_idcard
    }


    private fun openCamera(isFront: Boolean) {
        IDCardCamera.create(this)
            .openCamera(if (isFront) IDCardCamera.TYPE_IDCARD_FRONT else IDCardCamera.TYPE_IDCARD_BACK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == IDCardCamera.RESULT_CODE) {
//            AutoSizeConfig.getInstance().restart()
            val imagePath = IDCardCamera.getImagePath(data)
            if (!TextUtils.isEmpty(imagePath)) {
                if (requestCode == IDCardCamera.TYPE_IDCARD_FRONT) {
                    //身份证正面
                    val file = File(imagePath)
                    if (file.exists()) {
                        Glide.with(this).load(file).into(iv_true)
                        mViewModel.uploadIDCard(file)
                        showLoading()
                    }
                } else if (requestCode == IDCardCamera.TYPE_IDCARD_BACK) {
                    val file = File(imagePath)
                    if (file.exists()) {
                        Glide.with(this).load(file).into(iv_false)
                    }
                }
            }
        }


    }

    override fun observeValue() {
        mViewModel.idCardInfoResult.observe(this, {

            Slog.d("数据上传返回 $this")

        })
    }

    override fun initData() {

    }
}