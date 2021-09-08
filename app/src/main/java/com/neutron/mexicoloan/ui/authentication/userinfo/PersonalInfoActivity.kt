package com.neutron.mexicoloan.ui.authentication.userinfo

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import com.neutron.baselib.base.BaseVMActivity
import com.neutron.baselib.bean.CityBeanResult
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
        dataMap["user_id"] = PreferencesHelper.getUserID()

        mViewModel.getCityList("-1")
//        mViewModel.getWorkInfo()

    }



    override fun getLayoutId(): Int {
        return R.layout.activity_personal_info
    }

    override fun initView() {

        iv_back.setOnClickListener { finish() }
        tv_title.text = getString(R.string.user_info)

        btn_next.setOnClickListener {

//            civ_home_detail_address.getEditTextStr()
//            civ_email.getEditTextStr()
//            civ_postal_code.getEditTextStr()
            dataMap["home_address"] = civ_detail_address.getTextStr()
            dataMap["email"] = civ_email.getTextStr()
            mViewModel.uploadUserInfo(dataMap)

        }

        civ_education_level.setOnTVClickListener { showELS() }
        civ_children_number.setOnTVClickListener { showCNS() }
        civ_home_type.setOnTVClickListener { showHTS() }
//        civ_home_type.setOnTVClickListener { showHTS() }
        civ_location.setOnTVClickListener { getLocation(this) }

    }



//教育程度
    private fun showELS() {

    showCommSelectDialog(
        getString(R.string.job), getStrArray(R.array.array_education_level).Str2MenuItem(), {
            if (it is MenuItem) {
                setIdentity(it.menuCode)
            }
        }
    )
}
    private fun setIdentity(position: Int) {
        civ_education_level.setTextStr(getStrByIndex(R.array.array_education_level, position))
        dataMap["education"] = "$position"
    }

    //几个孩子
    private fun showCNS() {
        showCommSelectDialog(
            getString(R.string.job), getStrArray(R.array.array_children_num).Str2MenuItem(), {
                if (it is MenuItem) { setCN(it.menuCode) }
            }
        )
    }

    private fun setCN(position: Int) {
        civ_children_number.setTextStr(getStrByIndex(R.array.array_children_num, position))
        dataMap["number_children"] = "$position"
    }



    //居住类型
    private fun showHTS() {
        showCommSelectDialog(
            getString(R.string.job), getStrArray(R.array.array_children_num).Str2MenuItem(), {
                if (it is MenuItem) { setHT(it.menuCode) }
            }
        )
    }

    private fun setHT(position: Int) {
        civ_children_number.setTextStr(getStrByIndex(R.array.array_children_num, position))
        dataMap["home_type"] = "$position"
    }






    var region_1 = ""
    var region_2 = ""
    var cityBeanResults = mutableListOf<CityBeanResult>()

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
                    if (dataMap["home_region_1"] != null && dataMap["home_region_2"] != null) {
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






    var locationManager: LocationManager? = null
    var locationProvider: String = ""
    var gpscount = 0
    @SuppressLint("MissingPermission")
    private fun getLocation(context: Context) {
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager ?: return

        checkPerByX(listOf(Manifest.permission.ACCESS_COARSE_LOCATION),{
            //获取所有可用的位置提供器
            val providers: List<String> = locationManager!!.getProviders(true)
            if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
                locationProvider = LocationManager.NETWORK_PROVIDER
            }
            if (locationProvider.isNotEmpty()) {

                val location: Location? = locationManager?.getLastKnownLocation(locationProvider)
                location?.let {
                    civ_location.setTextStr("${it.latitude.getNoMoreThanTwoDigits()}/${it.longitude.getNoMoreThanTwoDigits()}")
                }
            }

        },R.string.not_order,R.string.dialog_ok,R.string.dialog_cancel)



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

        mViewModel.isUploadSuccess.observe(this,{

            startTo(ContactActivity::class.java)


        })

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