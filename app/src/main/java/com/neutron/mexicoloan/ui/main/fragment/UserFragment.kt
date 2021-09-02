package com.neutron.mexicoloan.ui.main.fragment


import com.neutron.baselib.base.BaseFragment
import com.neutron.baselib.utils.PreferencesHelper
import com.neutron.baselib.utils.makeCall
import com.neutron.baselib.utils.startTo
import com.neutron.mexicoloan.R
import com.neutron.mexicoloan.ui.view.dialog.CommDialog
import com.neutron.mexicoloan.ui.welcome.WelcomeActivity
import com.neutron.mexicoloan.util.showCommMessageDialog
import kotlinx.android.synthetic.main.fragment_user.*


class UserFragment : BaseFragment() {
    override fun attachLayoutRes(): Int {
        return R.layout.fragment_user
    }

    override fun initView() {

    }


    override fun lazyLoad() {
        val userInfo=PreferencesHelper.getUserInfo()
        tv_name.text=userInfo.userName
        tv_phone.text=userInfo.phone


        tv_call.setOnClickListener {
            val phone = PreferencesHelper.getHotTel()
            if (phone.isNotEmpty()) {
                makeCall(phone)
            }
        }

        tv_exit.setOnClickListener {
            mActivity.showCommMessageDialog(
                getString(R.string.confirm_exit_app),
                object : CommDialog.DialogListener {
                    override fun OnOkListener(currItem: Any?) {
                        PreferencesHelper.exitApp()
                        mActivity.startTo(WelcomeActivity::class.java, true)
                    }
                })
        }


    }

    override fun initData() {

    }

}


