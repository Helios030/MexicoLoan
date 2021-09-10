package com.neutron.mexicoloan.ui.authentication.work


import com.neutron.baselib.base.BaseVMActivity
import com.neutron.baselib.bean.CityBeanResult
import com.neutron.baselib.utils.*
import com.neutron.mexicoloan.R
import com.neutron.mexicoloan.ui.authentication.userinfo.PersonalInfoActivity

import com.neutron.mexicoloan.ui.view.dialog.CommDialog
import com.neutron.mexicoloan.ui.view.dialog.MenuItem
import com.neutron.mexicoloan.util.Str2MenuItem
import com.neutron.mexicoloan.util.getCommSelectDialog
import com.neutron.mexicoloan.util.showCommSelectDialog
import kotlinx.android.synthetic.main.activity_work.*
import kotlinx.android.synthetic.main.activity_work.btn_next
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlin.collections.HashMap

class WorkActivity : BaseVMActivity<WorkVM>(WorkVM::class.java) {
    override fun getLayoutId(): Int {
        return R.layout.activity_work
    }

    val dataMap = HashMap<String, Any>()
    override fun initData() {
        dataMap["user_id"] = PreferencesHelper.getUserID()
        mViewModel.getCityList("-1")
        mViewModel.getWorkInfo()

    }


    override fun initView() {
        iv_back.setOnClickListener { finish() }
        tv_title.text = getString(R.string.work_info)
        btn_next.setOnClickListener {
            dataMap["pay_type"] = "1"
            dataMap["company_name"] = civ_comp_name.getEditTextStr()
            dataMap["comp_address"] = civ_detail_address.getEditTextStr()
            dataMap["company_tel"] = civ_comp_phone.getEditTextStr()
            mViewModel.uploadUserWorkInfo(dataMap)

        }
        civ_job.setOnTVClickListener { showJobS() }
        civ_job_type.setOnTVClickListener { showJobTypeS() }
        civ_industry.setOnTVClickListener { showCTypeS() }
        civ_salary_range.setOnTVClickListener { showSalaryRangeS() }
        civ_source_income.setOnTVClickListener { showIncomeSourType() }
        civ_state.setOnTVClickListener { showAddressS() }
    }


    var region_1 = ""
    var region_2 = ""
    var cityBeanResults = mutableListOf<CityBeanResult>()





    // 根据字典 还原及设置
//    职务
    private fun showJobS() {
        showCommSelectDialog(
            getString(R.string.job), getStrArray(R.array.array_company_position).Str2MenuItem(), {
                if (it is MenuItem) {
                    setIdentity(it.menuCode)
                }
            }
        )
    }

    private fun setIdentity(position: Int) {
        civ_job.setTextStr(getStrByIndex(R.array.array_company_position, position))
        dataMap["position"] = "$position"
    }

    //职业类型
    private fun showJobTypeS() {
        showCommSelectDialog(
            getString(R.string.job_type), getStrArray(R.array.array_job_type).Str2MenuItem(), {
                if (it is MenuItem) {
                    setJobType(it.menuCode)
                }
            }
        )
    }

    private fun setJobType(position: Int) {
        civ_job_type.setTextStr(getStrByIndex(R.array.array_job_type, position))
        dataMap["custemer_type"] = "${position}"
    }


    //行业分类
    private fun showCTypeS() {
        showCommSelectDialog(
            getString(R.string.industry), getStrArray(R.array.array_industry).Str2MenuItem(), {
                if (it is MenuItem) {
                    setCType(it.menuCode)
                }
            }
        )
    }

    private fun setCType(position: Int) {
        var newPosition = if (position >= 50) position - 50 else position
        civ_industry.setTextStr(getStrByIndex(R.array.array_industry, newPosition))
        dataMap["industry"] = "${newPosition + 50}"
    }


    //薪资范围
    private fun showSalaryRangeS() {
        showCommSelectDialog(
            getString(R.string.salary_range),
            getStrArray(R.array.array_monthly_income_type).Str2MenuItem(),
            {
                if (it is MenuItem) {
                    setMonthlyType(it.menuCode)
                }
            }
        )
    }

    private fun setMonthlyType(position: Int) {
        civ_salary_range.setTextStr(getStrByIndex(R.array.array_monthly_income_type, position))
        dataMap["income_type"] = "$position"
    }


    //收入来源
    private fun showIncomeSourType() {
        showCommSelectDialog(
            getString(R.string.source_income),
            getStrArray(R.array.array_source_of_income).Str2MenuItem(),
            {
                if (it is MenuItem) {

                    setIncomeSourType(it.menuCode)

                }
            }
        )
    }

    private fun setIncomeSourType(position: Int) {
        civ_source_income.setTextStr(getStrByIndex(R.array.array_source_of_income, position))
        dataMap["incomeSource"] = "$position"
    }


    //选择地址
    private fun setCompCity2(no: String, addressName: String) {
        dataMap["comp_region_2"] = no
        region_2 = addressName
    }

    private fun setCompCity1(no: String, addressName: String) {
        dataMap["comp_region_1"] = no
        region_1 = addressName
    }


    var addressDialog: CommDialog? = null

    private fun showAddressS() {

        if (!cityBeanResults.isNullOrEmpty()) {
            val cityMenu = cityBeanResults.map {
                MenuItem(cityBeanResults.indexOf(it), it.address_name, false, it)
            }
            addressDialog = getCommSelectDialog(
                getString(R.string.state), cityMenu, {
                    if (dataMap["comp_region_1"] != null && dataMap["comp_region_2"] != null) {
                        civ_state.setTextStr("$region_1 $region_2")
                    }
                }, {
//                    单项选择
                    Slog.d(" showAddressS  ")
                    if (it is MenuItem) {
                        val city = it.cityBeanResult

                        if (city?.parent_id == -1) {
                            mViewModel.getCityList(city?.address_no.toString())
                            setCompCity1(city.address_no.toString(), city.address_name)
                        } else {
                            setCompCity2(city?.address_no.toString(), city!!.address_name)
                        }
                    }
                }
            )
            addressDialog?.show()
            addressDialog?.setOnDismissListener {
                mViewModel.getCityList("-1")
            }

        }


    }

    override fun observeValue() {
        mViewModel.CityBeanResults.observe(this, { result ->
            cityBeanResults.clear()
            cityBeanResults.addAll(result)

            if (result.first().parent_id != -1) {
                val cityList =
                    result.map { MenuItem(result.indexOf(it), it.address_name, false, it) }
                addressDialog?.setOpentionList2(cityList)

            }


        })

        mViewModel.sWorkInfoResult.observe(this, {
            civ_detail_address.setTextStr(it.comp_address.toString())
            civ_comp_phone.setTextStr(it.company_tel.toString())
            civ_comp_name.setTextStr(it.company_name.toString())
            setJobType(it.custemer_type.toString().toInt())
            setIdentity(it.position.toString().toInt())
            setCType(it.industry.toString().toInt())
            setMonthlyType(it.income_type.toString().toInt())
            setCompCity1(
                it.comp_region_1.toString(),
                it.comp_region_1_value.toString()
            )
            setCompCity2(
                it.comp_region_2.toString(),
                it.comp_region_2_value.toString()
            )

            if (dataMap["comp_region_1"] != null && dataMap["comp_region_2"] != null) {
                civ_state.setTextStr("$region_1 $region_2")
            }

        })

        mViewModel.isUploadSuccess.observe(this, {
            if (it) {
                startTo(PersonalInfoActivity::class.java)
            }
        })

    }

}


