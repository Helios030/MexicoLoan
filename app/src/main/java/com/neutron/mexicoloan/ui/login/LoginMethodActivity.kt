package com.neutron.mexicoloan.ui.login

import android.content.Intent
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.neutron.baselib.base.BaseVMActivity
import com.neutron.baselib.bean.UserInfo
import com.neutron.baselib.utils.*
import com.neutron.mexicoloan.R
import com.neutron.mexicoloan.ui.WebViewActivity
import com.neutron.mexicoloan.ui.main.MainActivity
import com.neutron.mexicoloan.ui.view.LoginVIew
import kotlinx.android.synthetic.main.activity_login_method.*
import java.util.*


class LoginMethodActivity : BaseVMActivity<LoginVM>(LoginVM::class.java) {

    val FACEBOOK = "facebook"
    val GOOGLE = "google"


    override fun getLayoutId(): Int {
        return R.layout.activity_login_method
    }

var loginType= LoginVIew.VIEW_STYLE_LOGIN
    var isSelected: Boolean = false

    override fun initView() {
        btn_google.setOnClickListener {
            if (isSelected) {
                startGoogleSigin()
            } else {
                toast(getString(R.string.pp_not_check))
            }



        }

        btn_facebook.setOnClickListener {

            if (isSelected) {
                startFaceBook()
            } else {
                toast(getString(R.string.pp_not_check))
            }
        }
        btn_msg.setOnClickListener {
            if (isSelected) {
                startActivity(Intent(this,LoginActivity::class.java).apply {
                    this.putExtra("loginType",loginType)
                })
            } else {
                toast(getString(R.string.pp_not_check))
            }
        }


        tv_select.setOnClickListener {
            isSelected = !isSelected
            if (isSelected) {
                tv_select.setDrawableLeft(R.mipmap.icon_selected_white)
            } else {
                tv_select.setDrawableLeft(R.mipmap.icon_selected_gray)
            }
        }

        tv_pp.setOnClickListener {
            startActivity(Intent(this, WebViewActivity::class.java).apply {
                this.putExtra(BaseConstant.Intent_URI,PreferencesHelper.getPPrivate())
            })
        }

    }

    private var mCallbackManager: CallbackManager? = null
    private fun startFaceBook() {
        mCallbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(mCallbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    Slog.d("loginResult  $loginResult")
                    Slog.d("loginResult ${loginResult?.accessToken?.userId}")
                    val id = loginResult?.accessToken?.userId
                    id?.let {
                        loginBySocial(it, FACEBOOK)
                    }
                }
                override fun onCancel() {
                    Slog.d("onCancel  ")
                }
                override fun onError(exception: FacebookException) {
                    Slog.d("onError  $exception")
                }
            })
        LoginManager.getInstance().logInWithReadPermissions(this, listOf("public_profile"));

    }

    var socialType = ""
    var socialId    = ""

    private fun loginBySocial(id: String, type: String) {

        id?.let {
            socialId = id
            socialType = type
            val map = HashMap<String, Any>()
            socialId = id
            map["socialType"] = socialType
            map["socialId"] = socialId
            map["name"] = ""
            map["firstName"] = ""
            map["lastName"] = ""
            map["email"] = ""
            mViewModel.socialLogin(map)

        }
    }

    val RC_SIGN_IN = 99

    fun startGoogleSigin() {

        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun initData() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


    }

    override fun observeValue() {

        mViewModel.loginResult.observe(this, {
            toast(getString(R.string.login_success))
            Slog.d("it  $it")
            val vCode = it.vcode ?: ""
            val register = it.register ?: ""
            PreferencesHelper.setUserID(it.user_id)
            PreferencesHelper.setUserInfo(
                UserInfo(
                    it.user_id,
                    it.userName,
                    it.signKeyToken,
                    vCode.toString(),
                    it.phone,
                    it.phonepre,
                    register.toString()
                )
            )
            startTo(MainActivity::class.java, true)
        })



        mViewModel.sLoginResult.observe(this, {
            PreferencesHelper.setUserID(it.user_id)
            val vCode = it.vcode ?: ""
            val register = it.register ?: ""
            PreferencesHelper.setUserInfo(
                UserInfo(
                    it.user_id,
                    it.userName,
                    it.signKeyToken,
                    vCode.toString(),
                    it.phone,
                    it.phonepre,
                    register.toString()
                )
            )
            if (!it.register) {
                trackRegisterEvent(it.mobile)
            }
            trackLoginEvent(it.mobile)
            startTo(MainActivity::class.java, true)
        })

        mViewModel.needBind.observe(this, {
         if(it){
             loginType=LoginVIew.VIEW_STYLE_BIND
             startActivity(Intent(this,LoginActivity::class.java).apply {
                 this.putExtra("loginType",loginType)
                 this.putExtra("socialType",socialType)
                 this.putExtra("socialId",socialId  )
             })
         }
        })

    }
    var phone = ""



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        mCallbackManager?.onActivityResult(requestCode, resultCode, data);
        when (requestCode) {
            RC_SIGN_IN -> {
                val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
                if (result.isSuccess) {
                    val account = result.signInAccount
                    Slog.d("account  $account")

                }
            }
        }

    }
}