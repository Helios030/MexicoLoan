package com.neutron.baselib.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Build
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.Transformation
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.neutron.baselib.base.BaseApplication
import com.permissionx.guolindev.PermissionX
import okhttp3.MediaType
import okhttp3.RequestBody
import java.math.RoundingMode
import java.text.DecimalFormat


/**
 * 屏幕截图
 */
fun Activity.screenShot(activity: Activity, isDeleteStatusBar: Boolean = true): Bitmap {
    val decorView = activity.window.decorView
    decorView.isDrawingCacheEnabled = true
    decorView.buildDrawingCache()
    val bmp = decorView.drawingCache
    val dm = DisplayMetrics()
    activity.windowManager.defaultDisplay.getMetrics(dm)
    var ret: Bitmap? = null
    if (isDeleteStatusBar) {
        val resources = activity.resources
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        val statusBarHeight = resources.getDimensionPixelSize(resourceId)
        ret = Bitmap.createBitmap(
            bmp,
            0,
            statusBarHeight,
            dm.widthPixels,
            dm.heightPixels - statusBarHeight
        )
    } else {
        ret = Bitmap.createBitmap(bmp, 0, 0, dm.widthPixels, dm.heightPixels)
    }
    decorView.destroyDrawingCache()
    return ret!!
}

/**
 * 是否竖屏
 */
fun Activity.isPortrait(): Boolean {
    return resources.configuration.orientation === Configuration.ORIENTATION_PORTRAIT
}

/**
 * 是否横屏
 */
fun Activity.isLandscape(): Boolean {
    return resources.configuration.orientation === Configuration.ORIENTATION_LANDSCAPE
}


/**
 * 设置竖屏
 */
fun Activity.setPortrait() {
    this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
}

/**
 * 设置横屏
 */
fun Activity.setLandscape() {
    this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
}

/**
 * 设置全屏
 */
fun Activity.setFullScreen() {
    this.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
}

/**
 * 显示软键盘
 */
fun Activity.showKeyboard() {
    var imm: InputMethodManager =
        this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            ?: return
    var view = this.currentFocus
    if (view == null) {
        view = View(this)
        view!!.isFocusable = true
        view!!.isFocusableInTouchMode = true
        view!!.requestFocus()
    }
    imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
}

/**
 * 隐藏软键盘
 */
fun Activity.hideKeyboard() {
    var imm: InputMethodManager =
        this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            ?: return
    var view = this.currentFocus
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}


/**
 * 加载图片
 */
fun ImageView.loadImage(
    context: Context,
    path: String,
    placeholder: Int ,
    useCache: Boolean = false
) {
    var options = getOptions(placeholder, useCache)
    Glide.with(context).load(path).apply(options).into(this)
}


/**
 * 加载圆形图片
 */
fun ImageView.loadCircleImage(
    context: Context,
    path: String,
    placeholder: Int  ,
    useCache: Boolean = false
) {
    var options = getOptions(placeholder, useCache)
    options.circleCrop()
    Glide.with(context).load(path).apply(options).into(this)
}

/**
 * 加载圆角图片
 */
fun ImageView.loadRoundCornerImage(
    context: Context,
    path: String,
    roundingRadius: Int = 20,
    placeholder: Int ,
    useCache: Boolean = false
) {
    var options = getOptions(placeholder, useCache)
    Glide.with(context).load(path)
        .apply(RequestOptions.bitmapTransform(RoundedCorners(roundingRadius))).apply(options)
        .into(this)
}

/**
 * 取消加载
 */
fun ImageView.loadClear(context: Context) {
    Glide.with(context).clear(this)
}


private fun ImageView.getOptions(
    placeholder: Int ,
    useCache: Boolean = false
): RequestOptions {
    var options = RequestOptions()
    options.placeholder(placeholder)
    options.priority(Priority.HIGH)
    if (useCache)
        options.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
    return options
}


//----------toast----------
fun Context.toast(text: CharSequence, duration: Int = Toast.LENGTH_LONG) {
     if(text.isNotEmpty()) {
         Toast.makeText(this, text, duration).show()
     }
}
fun BaseApplication.toast(text: CharSequence, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, text, duration).show()
}
fun Context.toast(resId: Int, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, resId, duration).show()
}

fun Context.centerToast(resId: Int, duration: Int = Toast.LENGTH_LONG) {
    var t = Toast.makeText(this, resId, duration)
    t.setGravity(Gravity.CENTER, 0, 0)
    t.show()
}

//----------尺寸转换----------

fun Context.dp2px(dpValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

fun Context.px2dp(pxValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

fun Context.sp2px(spValue: Float): Int {
    val scale = resources.displayMetrics.scaledDensity
    return (spValue * scale + 0.5f).toInt()
}

fun Context.px2sp(pxValue: Float): Int {
    val scale = resources.displayMetrics.scaledDensity
    return (pxValue / scale + 0.5f).toInt()
}

//----------屏幕尺寸----------

fun Context.getScreenWidth(): Int {
    var wm: WindowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        ?: return resources.displayMetrics.widthPixels
    var point = Point()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        wm.defaultDisplay.getRealSize(point)
    } else {
        wm.defaultDisplay.getSize(point)
    }
    return point.x
}

fun Context.getScreenHeight(): Int {
    var wm: WindowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        ?: return resources.displayMetrics.heightPixels
    var point = Point()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        wm.defaultDisplay.getRealSize(point)
    } else {
        wm.defaultDisplay.getSize(point)
    }
    return point.y
}


//----------NetWork----------

/**
 * 打开网络设置
 */
fun Context.openWirelessSettings() {
    startActivity(Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
}


fun Context.startTo(targetClass: Class<out Activity>, isNewTask: Boolean = false,isOnly:Boolean=false) {
    val intent = Intent(this, targetClass)
    if (isNewTask) {
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    intent.putExtra("isOnly", isOnly)

    startActivity(intent)

}

fun Context.checkPermissionAllGranted(permissions: Array<String>): Boolean {
    for (permission in permissions) {
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
    }
    return true
}

fun Context.checkOnePermission(permission:String):Boolean{
 return  ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}


/**
 * 网络是否连接
 */
fun Context.isConnected(): Boolean? {
    var info = this.getActiveNetworkInfo()
    return info?.isConnected
}

/**
 * 判断网络是否是移动数据
 */
fun Context.isMobileData(): Boolean {
    var info = this.getActiveNetworkInfo()
    return (null != info
            && info.isAvailable
            && info.type == ConnectivityManager.TYPE_MOBILE)
}

/**
 * 退回到桌面
 */
fun Context.startHomeActivity() {
    val homeIntent = Intent(Intent.ACTION_MAIN)
    homeIntent.addCategory(Intent.CATEGORY_HOME)
    startActivity(homeIntent)
}


@SuppressLint("MissingPermission")
private fun Context.getActiveNetworkInfo(): NetworkInfo? {
    var manager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return manager.activeNetworkInfo
}


fun Context.makeCall(phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL)
    val data: Uri = Uri.parse("tel:${phoneNumber}")
    intent.data = data
    startActivity(intent)
}


fun Fragment.makeCall(phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL)
    val data: Uri = Uri.parse("tel:${phoneNumber}")
    intent.data = data
    activity?.startActivity(intent)

}


/**
 * 设置颜色直接使用colors.xml中定义的颜色即可
 */
fun TextView.setColor(resId: Int) {
    this.setTextColor(resources.getColor(resId))
}

fun TextView.setDrawableLeft(resId: Int) {
    var drawable = this.context.resources.getDrawable(resId)
    drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
    this.setCompoundDrawables(drawable, null, null, null)
}

fun TextView.setDrawableRight(resId: Int) {
    var drawable = this.context.resources.getDrawable(resId)
    drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
    this.setCompoundDrawables(null, null, drawable, null)
}
fun TextView.setDrawableRight(drawable: Drawable) {

    this.setCompoundDrawables(null, null, drawable, null)
}

fun TextView.setDrawableTop(resId: Int) {
    var drawable = this.context.resources.getDrawable(resId)
    drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
    this.setCompoundDrawables(null, drawable, null, null)
}


fun TextView.setDrawableBottom(resId: Int) {
    var drawable = this.context.resources.getDrawable(resId)
    drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
    this.setCompoundDrawables(null, null, null, drawable)
}


fun HashMap<String, Any>.createCommonParams(): HashMap<String, Any> {



   val  sContext= BaseApplication.sContext
    val version = sContext.getVersionName()
    val IMEI = PreferencesHelper.getIMEI()
    this.apply {
        this["app_version"] = version
        this["appversion"] = version
        this["version"] = "1.0"
        this["channel"] = "1"
        this["imei"] = IMEI
        this["timestamp"] = System.currentTimeMillis().toString()
        this["pkg_name"] = sContext.packageName

    }
    return this
}


fun HashMap<String, Any>.createBody(): RequestBody {
    val newMap=this.createCommonParams()
    newMap["sign"] = newMap.signParameter()

    val json = Gson().toJson(newMap)
    Slog.e("当前API参数  json $json")
    return RequestBody.create(
        MediaType.parse("application/json;charset=UTF-8"),
        json
    )
}

fun SeekBar.scrollTo(process: Int,isAnimation:Boolean=true){

    if(Build.VERSION.SDK_INT <Build.VERSION_CODES.N){
        this.progress = process
    }else{
        this.setProgress(process,isAnimation)
    }

}



fun HashMap<String, Any>.signParameter(): String {
    val builder = StringBuilder()
    this.forEach { (_, value) ->
        if (value.toString().isNotEmpty()) {
            builder.append(value.toString())
        }
    }
    builder.append(BaseConstant.SIGNKEY)
    val mD5 = MD5Utils.md5(MD5Utils.md5(builder.toString()).plus(BaseConstant.SIGNRANDOMCODE))
    return mD5
}




fun Context.getVersionName(): String {
    try {
        val packageManager = this.packageManager
        val packageInfo = packageManager.getPackageInfo(
            this.packageName, 0
        )
        return packageInfo.versionName
    } catch (e: java.lang.Exception) {
        e.printStackTrace()

    }
    return ""
}



fun Context.checkNet(): Boolean {
    // 判断是否具有可以用于通信渠道
    val mobileConnection = isMobileConnection(this)
    val wifiConnection = isWIFIConnection(this)
    return if (mobileConnection == false && wifiConnection == false) {
        // 没有网络
        false
    } else true
}



fun Context.getStr(resId:Int):String = this.resources.getString(resId)
fun Context.getStrArray(resId:Int):List<String> = this.resources.getStringArray(resId).toList()

fun Context.getStrByIndex(resId:Int,index:Int,offset:Int=1):String = getStrArray(resId)[index-offset]

fun Context.getIndexByStr(resId:Int,str:String,offset:Int=1):Int = getStrArray(resId).indexOf(str)+offset



/**
 * 判断手机接入点（APN）是否处于可以使用的状态
 *
 * @param context
 * @return
 */
fun isMobileConnection(context: Context): Boolean {
    val manager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
    return networkInfo != null && networkInfo.isConnected
}

/**
 * 判断当前wifi是否是处于可以使用状态
 *
 * @param context
 * @return
 */
fun isWIFIConnection(context: Context): Boolean {
    val manager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
    return networkInfo != null && networkInfo.isConnected
}


fun View.expand() {

  val   v=this

    val matchParentMeasureSpec =
        View.MeasureSpec.makeMeasureSpec((v.parent as View).width, View.MeasureSpec.EXACTLY)
    val wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    v.measure(matchParentMeasureSpec, wrapContentMeasureSpec)
    val targetHeight = v.measuredHeight

    // Older versions of android (pre API 21) cancel animations for views with a height of 0.
    v.layoutParams.height = 1
    v.visibility = View.VISIBLE
    val a: Animation = object : Animation() {
         override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            v.layoutParams.height =
                if (interpolatedTime == 1f) ViewGroup.LayoutParams.WRAP_CONTENT else (targetHeight * interpolatedTime).toInt()
            v.requestLayout()
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }

    // Expansion speed of 1dp/ms
    a.setDuration((targetHeight / v.context.resources.displayMetrics.density).toLong())
    v.startAnimation(a)
}

//收起
fun View.collapse() {
    val   v=this
    val initialHeight = v.measuredHeight
    val a: Animation = object : Animation() {
         override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            if (interpolatedTime == 1f) {
                v.visibility = View.GONE
            } else {
                v.layoutParams.height = initialHeight - (initialHeight * interpolatedTime).toInt()
                v.requestLayout()
            }
        }
        override fun willChangeBounds(): Boolean {
            return true
        }
    }

    // Collapse speed of 1dp/ms
    a.duration = (initialHeight / v.context.resources.displayMetrics.density).toLong()
    v.startAnimation(a)
}

fun Activity.hideSoftInputWindow(){
    val imm: InputMethodManager =
        this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (this.isInputMethodShowing()) {
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}
fun Activity.isInputMethodShowing(): Boolean{
    //获取当前屏幕内容的高度
    val screenHeight: Int = this.getWindow().getDecorView().getHeight()
    //获取View可见区域的bottom
    val rect = Rect()
    this.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect)
    return (screenHeight - rect.bottom - this.getSoftButtonsBarHeight()) > 0
}


fun Activity.getSoftButtonsBarHeight(): Int{
    val metrics = DisplayMetrics()
    //这个方法获取可能不是真实屏幕的高度
    this.getWindowManager().getDefaultDisplay().getMetrics(metrics)
    val usableHeight = metrics.heightPixels
    //获取当前屏幕的真实高度
    this.getWindowManager().getDefaultDisplay().getRealMetrics(metrics)
    val realHeight = metrics.heightPixels
    return if (realHeight > usableHeight) {
        realHeight - usableHeight
    } else {
        0
    }
}



fun FragmentActivity.checkPerByX(permissions:List<String>,onAllGranted:() -> Unit,notTip:Int,ok:Int,calcel:Int){

    val notTipStr=UIUtils.getString(notTip)
    val okStr=UIUtils.getString(ok)
    val cancelStr=UIUtils.getString(calcel)

        PermissionX.init(this)
            .permissions(permissions)
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(
                    deniedList,
                    notTipStr,
                    okStr,
                    cancelStr
                )
            }.onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(
                    deniedList,
                    notTipStr,
                    okStr,
                    cancelStr
                )
            }  .request { allGranted, _, _ ->
                if (allGranted) {
                    onAllGranted.invoke()

                } else {
                    toast(notTipStr)
                }
            }



}



fun Double.getNoMoreThanTwoDigits(): Double {
    val format = DecimalFormat("0.########")
    //未保留小数的舍弃规则，RoundingMode.FLOOR表示直接舍弃。
    format.roundingMode = RoundingMode.FLOOR
    return format.format(this).toDouble()
}


var rawWidth=0

fun RecyclerView.sethalfWith(){

    if(rawWidth==0){
        rawWidth=width/2
        this.layoutParams.width=rawWidth
    }


}



