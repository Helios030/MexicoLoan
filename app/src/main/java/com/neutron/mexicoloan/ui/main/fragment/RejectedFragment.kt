package  com.neutron.mexicoloan.ui.main.fragment

import com.neutron.baselib.base.BaseFragment
import com.neutron.baselib.bean.LoanStatusResult
import com.neutron.mexicoloan.R
import com.neutron.mexicoloan.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_approval_rejected.*


class RejectedFragment : BaseFragment() {
    override fun attachLayoutRes(): Int {
        return R.layout.fragment_approval_rejected
    }


    var loanStatusResult: LoanStatusResult? = null


    override fun initView() {
        
    }

    override fun lazyLoad() {
        
    }

    override fun initData() {
        
    }
    override fun onResume() {
        super.onResume()
        val mainActivity = (activity as MainActivity)
        loanStatusResult = mainActivity.getloanStatusResult()
        loanStatusResult?.let {



            csv_review_error.setTvAmount(it.amount2Account?:"")
                .setAppTime(it.app_time?:"")
                .setFeesService(it.risk?:"")
                .setPayFee(it.pay?:"")
                .setLoanAmount(it.principal?:"")
                .setPayFee(it.service?:"")

        }
    }


}