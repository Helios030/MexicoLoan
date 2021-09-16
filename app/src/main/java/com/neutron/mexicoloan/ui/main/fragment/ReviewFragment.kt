package  com.neutron.mexicoloan.ui.main.fragment

import com.neutron.baselib.base.BaseFragment
import com.neutron.mexicoloan.R
import com.neutron.mexicoloan.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_review.*

class ReviewFragment : BaseFragment() {
    override fun attachLayoutRes(): Int {
        return R.layout.fragment_review
    }


    override fun initView() {



    }

    override fun lazyLoad() {
        
    }

    override fun initData() {
        
    }


    override fun onResume() {
        super.onResume()
        val mainActivity = (activity as MainActivity)
        val loanStatusResult=  mainActivity.getloanStatusResult()
        loanStatusResult?.let {
            csv_review.setTvAmount(it.principal?:"")
                .setAppTime(it.app_time?:"")
                .setFeesService(it.risk?:"")
                .setPayFee(it.pay?:"")
                .setAuditFee(it.service?:"")
        }
    }

}