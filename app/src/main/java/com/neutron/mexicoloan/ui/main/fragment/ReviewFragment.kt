package  com.neutron.mexicoloan.ui.main.fragment

import com.neutron.baselib.base.BaseFragment
import com.neutron.mexicoloan.R

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


//    @SuppressLint("SetTextI18n")
//    override fun onResume() {
//        super.onResume()
//        val mainActivity = (activity as MainActivity)
//        val loanStatusResult=  mainActivity.getloanStatusResult()
//        loanStatusResult?.let {
//            tv_tip.text=UIUtils.getString(R.string.state_review_tip)
//            tv_amount.text = it.principal?:""
//            tv_loan_date.text = "${it.duration?:""} ${Constants.Unit}"
//            civ_request_time.setTextStr(it.app_time?:"")
//            civ_fees_for_technical_services.setTextStr(it.risk?:"")
//            civ_pay_the_fee.setTextStr(it.pay?:"")
//            civ_bank_code.setTextStr(it.card_no?:"")
//            civ_audit_consulting_responsibility.setTextStr(it.service?:"")
//
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_review, container, false)
//    }

}