package com.neutron.mexicoloan.ui.order

import android.graphics.Typeface
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.neutron.baselib.base.BaseApplication
import com.neutron.baselib.bean.OrderBeanResult
import com.neutron.baselib.utils.MoneyState
import com.neutron.baselib.utils.Slog
import com.neutron.baselib.utils.UIUtils
import com.neutron.baselib.utils.UIUtils.Companion.getString
import com.neutron.mexicoloan.R
import com.neutron.mexicoloan.ui.view.CardStateView


class OrderAdapter(layoutResId: Int, data: MutableList<OrderBeanResult>) :
    BaseQuickAdapter<OrderBeanResult, BaseViewHolder>(layoutResId, data) {
    val mContext = BaseApplication.sContext
    override fun convert(holder: BaseViewHolder, item: OrderBeanResult) {


        val csv = holder.getView<CardStateView>(R.id.csv_order)

        showStyle(csv, item)


    }

    private fun showStyle(csv: CardStateView, item: OrderBeanResult) {


        val tv_Bottom = csv.getTvBottom()

        tv_Bottom.setTextColor(UIUtils.getColor(R.color.blue_ff32))
        tv_Bottom.textSize = 20F
        tv_Bottom.typeface = Typeface.defaultFromStyle(Typeface.BOLD);
        csv.setTvLoanMoney(item.principal)

//        2、放弃借款
//        3、申请失败

//        5、还款中
//        6、已还款
//        7、已逾期
//        8、已结清
//        9、借款失败
//        10. 还款审核中
        when (item.loan_status) {
            1, 4, 10 -> {
                //        1、申请中
                //        4、申请成功
                tv_Bottom.text = getString(R.string.review)
                csv.setAppTime(item.app_time)
                    .setTvAmount(item.amount2Account)
                    .setLoanAmount(item.principal)
                    .setPayFee(item.paymentAmount)
//                csv. setFeesService(item.risk)
//                csv. setAuditFee((item.set)
            }
            2 -> {
                csv.showStyle(MoneyState.STATE_ORDER, mContext)
                    .setTvOrderTip(getString(R.string.refuse))
                    .setAppTime(item.app_time)
            }


            3 -> {
                csv.showStyle(MoneyState.STATE_ORDER, mContext)
                    .setTvOrderTip(getString(R.string.cancel))
                    .setAppTime(item.app_time)
            }


            5 -> {
                csv.showStyle(MoneyState.STATE_OVERDUE, mContext)
                tv_Bottom.text = getString(R.string.repay)

                csv.setAppTime(item.app_time)
                    .setTvAmount(item.amount2Account)
                    .setLoanAmount(item.principal)
                    .setPayFee(item.paymentAmount)

            }


            7 -> {

                tv_Bottom.text = getString(R.string.over)
                csv.showStyle(MoneyState.STATE_OVERDUE, mContext).setAppTime(item.app_time)
                    .setTvAmount(item.amount2Account)
                    .setLoanAmount(item.principal)
                    .setPayFee(item.paymentAmount)


            }
            6, 8 -> {
                csv.showStyle(MoneyState.STATE_ORDER_CLEAR, mContext)
                    .setTvOrderTip(getString(R.string.settle))
                    .setAppTime(item.app_time)


            }
            9 -> {

                csv.showStyle(MoneyState.STATE_ORDER, mContext)
                    .setTvOrderTip(getString(R.string.refuse))
                    .setAppTime(item.app_time)
            }

            else -> {
                Slog.e("OrderAdapter 未知状态  ${item.loan_status}")
            }


        }

    }


}