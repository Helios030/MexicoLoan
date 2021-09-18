package  com.neutron.mexicoloan.ui.main.fragment

import com.neutron.baselib.base.BaseFragment
import com.neutron.baselib.utils.Slog
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
        Slog.d("Review  $loanStatusResult")


//        LoanStatusResult(amount2Account=1,680, app_time=2021-09-17, application_id=1020210917115015000000043,
//        bank_code=40014, borrow_time=null, card_holder=zhangsan, card_no=123**********145, data_status=2,
//        deposit_time=null, duration=7, fale_fee=null, fee=1,120, interest=10, loan_status=2,
//        no_ktp=CEX**********HXQ, paymentAmount=2,810, penalty=null, penalty_days=0, principal=2,800,
//        reach_amount=null, remainAmount=null, risk=700, service=280, pay=140, va=null, valid_time=null)
//不显示 todo

        loanStatusResult?.let {
            csv_review
                .setTvLoanMoney("$${it.principal?:""}")
                .setTvAmount  (it.amount2Account?:"")
                .setLoanAmount(it.principal?:"")
                .setAppTime(it.app_time?:"")
                .setFeesService(it.risk?:"")
                .setPayFee(it.pay?:"")
                .setAuditFee(it.service?:"")
                .setTvLoanTerm(it.duration?:"")
        }
    }

}