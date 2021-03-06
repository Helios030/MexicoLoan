package com.neutron.mexicoloan.ui.main.fragment


import com.neutron.baselib.base.BaseFragment
import com.neutron.baselib.utils.PreferencesHelper
import com.neutron.baselib.utils.makeCall
import com.neutron.baselib.utils.startTo
import com.neutron.mexicoloan.R
import com.neutron.mexicoloan.ui.authentication.card.CardActivity
import com.neutron.mexicoloan.ui.authentication.idcard.IDCardActivity
import com.neutron.mexicoloan.ui.order.OrderActivity
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
        tv_name.text=userInfo?.userName
        tv_phone.text=userInfo?.phone

        tv_loan.setOnClickListener {
            mActivity.startTo(OrderActivity::class.java)
        }

        tv_call.setOnClickListener {
            val phone = PreferencesHelper.getHotTel()
            if (phone.isNotEmpty()) {
                makeCall(phone)
            }
        }

        tv_exit.setOnClickListener {
            mActivity.showCommMessageDialog(
                getString(R.string.confirm_exit_app)){
                PreferencesHelper.exitApp()
                mActivity.startTo(WelcomeActivity::class.java, true)
            }
        }

        tv_info.setOnClickListener {
            mActivity.startTo(IDCardActivity::class.java)
        }
        tv_money_card.setOnClickListener {
            mActivity.startTo(CardActivity::class.java)

        }

    }

    override fun initData() {

    }

}


