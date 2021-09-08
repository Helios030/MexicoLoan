package com.neutron.mexicoloan.ui.main.fragment

import com.neutron.baselib.base.BaseFragment
import com.neutron.baselib.bean.LoanStatusResult
import com.neutron.mexicoloan.R


class OverdueFragment : BaseFragment() {


//    var loanStatusResult: LoanStatusResult? = null

//    override fun onResume() {
//        super.onResume()
//        Slog.d("OverdueFragment onResume")
//
//        val mainActivity = (activity as MainActivity)
////        mainActivity.setStatusBarColor(mainActivity, R.color.red_ef)
//        loanStatusResult = mainActivity.getloanStatusResult()
//        loanStatusResult?.let { it ->
//
//            tv_amount.text = it.principal?:""
//            tv_loan_date.text = "${it.duration?:""} ${Constants.Unit}"
//
//            civ_due_time.setTextStr(it.deposit_time?:"")
//            civ_interest.setTextStr(it.interest?:"")
//            civ_late_fee.setTextStr(it.fale_fee?:"")
//            civ_fine.setTextStr(it.penalty?:"")
//
//
//        }
//
//    }


//    var applicationId: String? = null
//    var amount: String? = null

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
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
//    }

    override fun initView() {
    }

    override fun lazyLoad() {
    }

    override fun initData() {
    }

//    fun startToPay(payAll: Boolean) {
//        loanStatusResult?.let {
//            val intent = Intent(activity, RepaymentActivity::class.java)
//            val bundle = Bundle()
//            bundle.putBoolean("isPayAll", payAll)
//            bundle.putString("applicationId", it.application_id)
//            bundle.putString("amount", it.remainAmount)
//            Slog.d("startToPay  $it")
//            intent.putExtra("bundle", bundle)
//            startActivity(intent)
//        }
//    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_overdue
    }

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(, container, false)
//    }


}