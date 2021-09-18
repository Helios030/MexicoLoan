package com.neutron.mexicoloan.ui.main

import android.Manifest
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.neutron.baselib.base.BaseVMActivity
import com.neutron.baselib.bean.LoanStatusResult
import com.neutron.baselib.bean.UserConfigResult
import com.neutron.baselib.utils.MoneyState
import com.neutron.baselib.utils.PreferencesHelper
import com.neutron.baselib.utils.checkPerByX
import com.neutron.baselib.utils.toast
import com.neutron.mexicoloan.R
import com.neutron.mexicoloan.ui.main.fragment.*
import com.neutron.mexicoloan.ui.main.fragment.product.ProductFragment
import com.neutron.mexicoloan.ui.view.BottomBar
import kotlinx.android.synthetic.main.activity_main.*

class
MainActivity : BaseVMActivity<MainVM>(MainVM::class.java) {

    val mProductFragment by lazy { ProductFragment() }
    val mUserFragment by lazy { UserFragment() }
    val mReviewFragment by lazy { ReviewFragment() }
    val mRejectedFragment by lazy { RejectedFragment() }
    val mRepaymentFragment by lazy { RepaymentFragment() }
    val mOverdueFragment by lazy { OverdueFragment() }


    var fragmentList = listOf(
        mProductFragment,
        mUserFragment,
        mReviewFragment,
        mRejectedFragment,
        mRepaymentFragment,
        mOverdueFragment
    )

    fun indexFragmentByName(name: String): Int {
        var index = 0
        fragmentList!!.forEach {
            if (it.javaClass.simpleName == name) {
                index = fragmentList!!.indexOf(it)
            }
        }
        return index
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        initVPAdapter()
        sfl_main.setOnRefreshListener {

            initData()

            Handler(Looper.getMainLooper()).postDelayed({
                if (sfl_main.isRefreshing) sfl_main.isRefreshing = false
            },2000)

        }

        bb_main.setonItemSelected(object : BottomBar.onItemSelected {
            override fun onSelected(item: Int) {
                when (item) {
                    0 -> {
                        if (sfl_main.isRefreshing) sfl_main.isRefreshing = false
                        showStateView(currLoanStatus); nsv_main.setCurrentItem(
                            indexFragmentByName(
                                currFragment.javaClass.simpleName
                            ), false
                        )
                    }
                    1 -> nsv_main.setCurrentItem(
                        indexFragmentByName(mUserFragment.javaClass.simpleName),
                        false
                    )
                }
            }
        })



        checkPerByX(listOf(Manifest.permission.ACCESS_NETWORK_STATE), {

        }, R.string.not_pp, R.string.dialog_ok, R.string.dialog_cancel)

    }

    fun getSFL(): SwipeRefreshLayout {
        return sfl_main
    }

    var currLoanStatus = MoneyState.STATE_BORROWABLE


    var currFragment: Fragment = mProductFragment


    private fun initVPAdapter() {
        nsv_main.adapter = MainAdapter(supportFragmentManager, fragmentList!!)
        nsv_main.offscreenPageLimit = fragmentList!!.size
        if (sfl_main.isRefreshing) {
            sfl_main.isRefreshing = false
        }
        showStateView(currLoanStatus)
        nsv_main.setCurrentItem(
            indexFragmentByName(currFragment!!.javaClass.simpleName),
            false
        )
    }

    fun showStateView(LoanStatus: Int) {
        when (LoanStatus) {
            MoneyState.STATE_LOANING, MoneyState.STATE_APPLYING -> {
                currFragment = mReviewFragment
                PreferencesHelper.setShowFeiled(true)
            }
            MoneyState.STATE_APPROVAL_REJECTED -> {
                currFragment = mRejectedFragment
                if (PreferencesHelper.isShowFeiled()) {
                    PreferencesHelper.setShowFeiled(false)
                } else {
                    currFragment = mProductFragment
                }
            }
            MoneyState.STATE_PENDING_REPAYMENT -> {
                currFragment = mRepaymentFragment
            }
            MoneyState.STATE_OVERDUE -> {
                currFragment = mOverdueFragment
            }
            MoneyState.STATE_BORROWABLE -> {
                currFragment = mProductFragment
            }
//            MoneyState.STATE_PAY_REVIEW -> {
//                currFragment = mProductFragment
//            }
        }
    }


    // 点击两次退出应用程序的第一个时间
    private var firstTime: Long = 0

    /**
     * 返回键按下两次退出应用程序
     */
    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                val secondTime = System.currentTimeMillis()
                return if (secondTime - firstTime > 2000) {
                    toast(R.string.quit_app)
                    firstTime = secondTime
                    true
                } else {
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    intent.addCategory(Intent.CATEGORY_HOME)
                    startActivity(intent)
                    true
                }
            }
        }
        return super.onKeyUp(keyCode, event)
    }

    override fun initData() {

        mViewModel.getRequestState()

        mViewModel.getUserConfig()


    }

    var loanStatusResult: LoanStatusResult? = null
    var configResult: UserConfigResult? = null


    fun getloanStatusResult(): LoanStatusResult? {
        return loanStatusResult
    }

    override fun observeValue() {
        mViewModel.statusResult.observe(this, {
            loanStatusResult = it
            it.loan_status?.let { it
                currLoanStatus=it.toInt()
                showStateView(currLoanStatus)
            }
            nsv_main.setCurrentItem(
                indexFragmentByName(currFragment.javaClass.simpleName),
                false
            )
            currFragment.onResume()

        })


        mViewModel.configResult.observe(this, {
            it?.let { config ->
                configResult = config
                PreferencesHelper.setAboutUs(config.about_us)
                PreferencesHelper.setHotTel(config.hot_tel)
                PreferencesHelper.setPPrivate(config.k_private)
            }
        })
    }

}

