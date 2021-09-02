package  com.neutron.mexicoloan.ui.main.fragment


import android.view.ViewTreeObserver
import com.neutron.baselib.base.BaseFragment
import com.neutron.mexicoloan.R

class RepaymentFragment : BaseFragment() {


//    var loanStatusResult: LoanStatusResult? = null
//
//    override fun onResume() {
//        super.onResume()
//        val mainActivity = (activity as MainActivity)
//        loanStatusResult = mainActivity.getloanStatusResult()
//        loanStatusResult?.let {
//            val str=    UIUtils.getString(R.string.state_pr_tip)
//
//           Slog.d("onResume $it")
//
//            if(!it.deposit_time.isNullOrBlank()){
//                val days= Utils.checkDateInValid(it.deposit_time,SimpleDateFormat("yyyy-MM-dd").format(Date()))
//                tv_tip.text= str.format(days)
//
//                Slog.d("日期之差  days $days ${it.deposit_time}  ${SimpleDateFormat("yyyy-MM-dd").format(Date())}  ")
//            }
//            tv_amount.text = it.principal?:""
//            tv_loan_date.text = "${it.duration?:""} ${Constants.Unit}"
//            civ_due_time.setTextStr(it.deposit_time?:"")
//            civ_interest.setTextStr(it.interest?:"")
//            btn_next.text = "${it.deposit_time?:""}  ${getString(R.string.repayment)}"
//        }
//
//    }




    val lister = ViewTreeObserver.OnScrollChangedListener {
//        if (isNeed) {
//            (activity as MainActivity?)?.getRefresh()?.isEnabled = scrollView.scrollY === 0
//        }

    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//
//        btn_next.setOnClickListener {
//
//            activity?.let {
//                val mainActivity = (it as MainActivity)
//                mainActivity.showCommSelect(
//                    getString(R.string.select_repay_fun),
//                    mainActivity.Str2MenuItem(ListDictionary.PayFunList),
//                    object : SelectPoPView.OnPOPSelected {
//                        override fun OnSelected(item: Any, position: Int) {
//                            startToPay(position==0)
//                        }
//                    },
//                    ll_main
//                )
//            }
//
//        }
//
//    }

    override fun initView() {
        
    }

    override fun lazyLoad() {
        
    }

    override fun initData() {
        
    }

//    private fun startToPay(payAll: Boolean) {
//        loanStatusResult?.let {
//
//
//            val intent = Intent(activity, RepaymentActivity::class.java)
//            val bundle = Bundle()
//            bundle.putBoolean("isPayAll", payAll)
//            bundle.putString("applicationId", it.application_id)
//            bundle.putString("amount", it.remainAmount)
//            Slog.d("startToPay  $it")
//            intent.putExtra("bundle", bundle)
//            startActivity(intent)
//
//
//
//
//        }
//    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_pending_repayment
    }


//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(, container, false)
//    }

}