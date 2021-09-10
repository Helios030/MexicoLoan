package com.neutron.mexicoloan.ui.login.method

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.neutron.baselib.base.BaseVMActivity
import com.neutron.baselib.utils.startTo
import com.neutron.mexicoloan.R
import com.neutron.mexicoloan.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_login_method.*
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import android.R.attr.data

import com.google.android.gms.auth.api.signin.GoogleSignInAccount







class LoginMethodActivity : BaseVMActivity<LoginMethodVM>(LoginMethodVM::class.java) {

    override fun getLayoutId(): Int {
       return R.layout.activity_login_method
    }

    override fun initView() {

        btn_google.setOnClickListener {

            startGoogleSigin()

        }

        btn_facebook.setOnClickListener {

        }

        btn_msg.setOnClickListener {
            startTo(LoginActivity::class.java)
        }


    }

    val RC_SIGN_IN= 99

     fun startGoogleSigin() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }




    lateinit var  mGoogleSignInClient:GoogleSignInClient

    override fun initData() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    override fun observeValue() {
       
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        when (requestCode) {
            RC_SIGN_IN -> {
//                val task: Task<GoogleSignInAccount> =
//                    GoogleSignIn.getSignedInAccountFromIntent(android.R.attr.data)

            }

        }
    }

}