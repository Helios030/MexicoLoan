package  com.neutron.mexicoloan.ui.main.fragment


import android.content.Intent
import android.os.Bundle
import com.neutron.baselib.base.BaseFragment
import com.neutron.baselib.bean.LoanStatusResult
import com.neutron.baselib.utils.Slog
import com.neutron.mexicoloan.R
import com.neutron.mexicoloan.ui.main.MainActivity
import com.neutron.mexicoloan.ui.repay.RePayActivity
import kotlinx.android.synthetic.main.fragment_pending_repayment.*
import kotlinx.android.synthetic.main.view_pay_button.*

class RepaymentFragment : BaseFragment() {

    override fun initView() {
        btn_pay_now.setOnClickListener { startToPay() }
    }

    override fun lazyLoad() {
        
    }

    override fun initData() {
        
    }

    fun startToPay() {
        loanStatusResult?.let {
            val intent = Intent(activity, RePayActivity::class.java)
            val bundle = Bundle()
            bundle.putString("applicationId", it.application_id)
            bundle.putString("amount", it.remainAmount)
            Slog.d("startToPay  $it")
            intent.putExtra("bundle", bundle)
            startActivity(intent)
        }
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_pending_repayment
    }

    var loanStatusResult: LoanStatusResult? = null

    override fun onResume() {
        super.onResume()

        val mainActivity = (activity as MainActivity)
        loanStatusResult = mainActivity.getloanStatusResult()
        loanStatusResult?.let {
            csv_repay
                .setTvAmount(it.amount2Account?:"")
                .setAppTime(it.app_time?:"")
                .setFeesService(it.risk?:"")
                .setPayFee(it.pay?:"")
                .setLoanAmount(it.principal?:"")
                .setPayFee(it.service?:"")

        }
    }
}