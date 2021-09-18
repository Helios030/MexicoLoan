package com.neutron.mexicoloan.ui.authentication.userinfo

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import com.neutron.baselib.base.BaseVMActivity
import com.neutron.baselib.bean.CityBeanResult
import com.neutron.baselib.bean.LatLng
import com.neutron.baselib.utils.*
import com.neutron.mexicoloan.R
import com.neutron.mexicoloan.ui.authentication.contact.ContactActivity
import com.neutron.mexicoloan.ui.view.dialog.CommDialog
import com.neutron.mexicoloan.ui.view.dialog.MenuItem
import com.neutron.mexicoloan.util.Str2MenuItem
import com.neutron.mexicoloan.util.getCommSelectDialog
import com.neutron.mexicoloan.util.showCommSelectDialog
import kotlinx.android.synthetic.main.activity_personal_info.*
import kotlinx.android.synthetic.main.activity_personal_info.btn_next
import kotlinx.android.synthetic.main.activity_work.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class PersonalInfoActivity : BaseVMActivity<PersonalInfoVM>(PersonalInfoVM::class.java) {

    val dataMap = HashMap<String, Any>()
    override fun initData() {






        mViewModel.getCityList("-1")
        mViewModel.getServiceUserInfo()

    }


    override fun getLayoutId(): Int {
        return R.layout.activity_personal_info
    }

    override fun initView() {

        iv_back.setOnClickListener { finish() }
        tv_title.text = getString(R.string.user_info)

        btn_next.setOnClickListener {
            dataMap["no_ktp"] = PreferencesHelper.getKTP()
            dataMap["real_name"] =PreferencesHelper.getRealname()
            dataMap["idType"] = "1"
            dataMap["first_name"] = PreferencesHelper.getFName()
            dataMap["last_name"] =PreferencesHelper.getLNAME()
            dataMap["name_mother"] =PreferencesHelper.getLNAME()
            dataMap["user_id"] = PreferencesHelper.getUserID()
            dataMap["gender"] = PreferencesHelper.getsex()
            dataMap["rfc"] = PreferencesHelper.getRFC()
            dataMap["incomeSource"] = PreferencesHelper.getIncomesource()
            dataMap["home_address"] = civ_home_detail_address.getTextStr()
            dataMap["email"] = civ_email.getTextStr()
            Slog.d("PersonalInfoActivity  ${dataMap} ")
            mViewModel.uploadUserInfo(dataMap)

        }

        civ_education_level.setOnTVClickListener { showELS() }
        civ_children_number.setOnTVClickListener { showCNS() }
        civ_home_type.setOnTVClickListener { showHTS() }
        civ_location.setOnTVClickListener { getLocation(this) }
        civ_marital_status.setOnTVClickListener { showMSS() }

        civ_info_state.setOnTVClickListener {
            showAddressS()
        }

    }


    //教育程度
    private fun showELS() {

        showCommSelectDialog(
            getString(R.string.education_level),
            getStrArray(R.array.array_education_level).Str2MenuItem(),
            {
                if (it is MenuItem) {
                    setEducation(it.menuCode)
                }
            }
        )
    }

    private fun setEducation(position: Int?) {
        position?:return
        civ_education_level.setTextStr(getStrByIndex(R.array.array_education_level, position))
        dataMap["education"] = "$position"
    }

    //几个孩子
    private fun showCNS() {
        showCommSelectDialog(
            getString(R.string.job), getStrArray(R.array.array_children_num).Str2MenuItem(), {
                if (it is MenuItem) {
                    setCN(it.menuCode)
                }
            }
        )
    }

    private fun setCN(position: Int?) {
        position?:return
        civ_children_number.setTextStr(getStrByIndex(R.array.array_children_num, position))
        dataMap["number_children"] = "$position"
    }


    //居住类型
    private fun showHTS() {
        showCommSelectDialog(
            getString(R.string.home_type), getStrArray(R.array.array_home_type).Str2MenuItem(), {
                if (it is MenuItem) {
                    setHT(it.menuCode)
                }
            }
        )
    }

    private fun setHT(position: Int?) {
        position?:return
        civ_home_type.setTextStr(getStrByIndex(R.array.array_home_type, position))
        dataMap["home_type"] = "$position"
    }

    //婚姻状况
    private fun showMSS() {
        showCommSelectDialog(
            getString(R.string.marital_status),
            getStrArray(R.array.array_marital_status).Str2MenuItem(),
            {
                if (it is MenuItem) {
                    setMS(it.menuCode)
                }
            }
        )
    }

    private fun setMS(position: Int?) {
        position?:return
        civ_marital_status.setTextStr(getStrByIndex(R.array.array_marital_status, position))
        dataMap["marital_status"] = "$position"
    }


    var region_1 = ""
    var region_2 = ""
    var cityBeanResults = mutableListOf<CityBeanResult>()

    //选择地址
    private fun setHomeCity2(no: String?, addressName: String) {
        no?:return
        dataMap["home_region_2"] = no
        region_2 = addressName
    }

    private fun setHomeCity1(no: String?, addressName: String) {
        no?:return
        dataMap["home_region_1"] = no
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
                    if (dataMap["home_region_1"] != null && dataMap["home_region_2"] != null) {
                        civ_info_state.setTextStr("$region_1 $region_2")
                    }
                }, {
//                    单项选择
                    Slog.d(" showAddressS  ")
                    if (it is MenuItem) {
                        val city = it.cityBeanResult

                        if (city?.parent_id == -1) {
                            mViewModel.getCityList(city?.address_no.toString())
                            setHomeCity1(city.address_no.toString(), city.address_name)
                        } else {
                            setHomeCity2(city?.address_no.toString(), city!!.address_name)
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


    var locationManager: LocationManager? = null
    var locationProvider: String = ""
    var gpscount = 0

    @SuppressLint("MissingPermission")
    private fun getLocation(context: Context) {
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager ?: return

        checkPerByX(listOf(Manifest.permission.ACCESS_COARSE_LOCATION), {
            //获取所有可用的位置提供器
            val providers: List<String> = locationManager!!.getProviders(true)
            if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
                locationProvider = LocationManager.NETWORK_PROVIDER
            }
            if (locationProvider.isNotEmpty()) {

                val location: Location? = locationManager?.getLastKnownLocation(locationProvider)
                location?.let {
                    civ_location.setTextStr("${it.latitude.getNoMoreThanTwoDigits()}/${it.longitude.getNoMoreThanTwoDigits()}")
                    PreferencesHelper.setLatLng(LatLng("${it.latitude.getNoMoreThanTwoDigits()}","${it.longitude.getNoMoreThanTwoDigits()}"))
                }
            }

        }, R.string.not_order, R.string.dialog_ok, R.string.dialog_cancel)


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

        mViewModel.isUploadSuccess.observe(this, {
            startTo(ContactActivity::class.java)
        })
        mViewModel.sUserInfoResult.observe(this, {
            setEmail(it.email)
            setHT(it.home_type?.toInt())
            setHA(it.home_address)
            setHomeCity1(it.home_region_1, it.home_region_1_value.toString())
            setHomeCity2(it.home_region_2, it.home_region_2_value.toString())
            setEducation(it.education?.toInt())
            setMS(it.marital_status?.toInt())
            setCN(it.number_children?.toInt())
            if (dataMap["home_region_1"] != null && dataMap["home_region_2"] != null) {
                civ_info_state.setTextStr("$region_1 $region_2")
            }
            dataMap["no_ktp"] = it.no_ktp?:""
            dataMap["real_name"] =it.real_name?:""
            dataMap["idType"] = it.idType?:""
            dataMap["first_name"] =it.first_name?:""
            dataMap["last_name"]  =it.last_name?:""
            dataMap["user_id"]    =it.user_id?:""
            dataMap["gender"]     =it.gender?:""

            dataMap["name_mother"]     =it.name_mother?:""

        })


    }

    fun setHA(str: String?) {
        str?:return
        civ_home_detail_address.setEditTextStr(str)
        dataMap["home_address"] = str
    }

    fun setEmail(str: String?) {
        str?:return
        civ_email.setEditTextStr(str)
        dataMap["email"] = str
    }

//        private fun uploadLocation(latitude: Double, longitude: Double) {
//        var hashMap = HashMap<String, Any>()
////        val user = UserInfoBean().queryFirst()
//        val id=PreferencesHelper.getUserID()
//        val phone=PreferencesHelper.getUserID()
//
////        user?.let { user ->
//            hashMap["user_id"] = getUserId()
//            hashMap["account_id"] = getUserPhone()
//            hashMap["record"] = arrayOf(LatLng(longitude.toString(), latitude.toString()))
//            hashMap["uuid"] = DeviceFactory.getInstance().getUUID() ?: ""
//            val requestBody = Utils.createBody(Utils.createCommonParams(hashMap))
//            RetrofitUtil.instance.getRetrofit(false, Constants.BaseUri)
//                .create(NetWorkService::class.java)
//                .uploadLocation(requestBody)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    Slog.d("位置上传  $it")
//                    PreferencesHelper.setUploadTime(System.currentTimeMillis())
//
//
//                }, {
//                    Slog.e("位置上传失败 ", it)
//
//                }, {
//
//                })
//
////        }
//
//    }

}