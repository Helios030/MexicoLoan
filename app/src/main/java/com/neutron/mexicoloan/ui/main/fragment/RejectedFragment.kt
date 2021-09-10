package  com.neutron.mexicoloan.ui.main.fragment

import com.neutron.baselib.base.BaseFragment
import com.neutron.baselib.bean.LoanStatusResult
import com.neutron.mexicoloan.R
import com.neutron.mexicoloan.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_approval_rejected.*
import kotlinx.android.synthetic.main.fragment_overdue.*


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

            csv_review_error.setTvAmount(it.amount2Account)
                .setAppTime(it.app_time)
                .setFeesService(it.risk)
                .setPayFee(it.pay)
                .setLoanAmount(it.principal)
                .setPayFee(it.service)

        }
    }




//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        return inflater.inflate(R.layout.fragment_approval_rejected, container, false)
//    }
//    override fun onResume() {
//        super.onResume()
//        val mainActivity = (activity as MainActivity)
//        val loanStatusResult=  mainActivity.getloanStatusResult()
//        loanStatusResult?.let {
//            tv_tip.text= UIUtils.getString(R.string.state_review_reject_tip)
//
//            tv_amount.text = it.principal?:""
//            tv_loan_date.text = "${it.duration?:""} ${Constants.Unit}"
//            civ_request_time.setTextStr(it.app_time?:"")
//            civ_fees_for_technical_services.setTextStr(it.risk?:"")
//            civ_pay_the_fee.setTextStr(it.pay?:"")
//            civ_bank_code.setTextStr(it.card_no?:"")
//            civ_audit_consulting_responsibility.setTextStr(it.service?:"")
//
//        }
//
//    }
}