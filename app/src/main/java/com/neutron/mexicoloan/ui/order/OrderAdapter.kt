package com.neutron.mexicoloan.ui.order
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.neutron.baselib.bean.OrderBeanResult
import com.neutron.mexicoloan.R


class OrderAdapter(layoutResId: Int, data: MutableList<OrderBeanResult>) :
    BaseQuickAdapter<OrderBeanResult, BaseViewHolder>(layoutResId, data) {
    override fun convert(holder: BaseViewHolder, item: OrderBeanResult) {

    }
//    override fun convert(holder: BaseViewHolder, item: OrderBeanResult) {
//        holder.setText(R.id.tv_money, item.principal)
//        val tvStrDate = holder.getView<TextView>(R.id.tv_str_date)
//        val iv_status = holder.getView<ImageView>(R.id.iv_status)
////        1、申请中
////        2、放弃借款
////        3、申请失败
////        4、申请成功
////        5、还款中
////        6、已还款
////        7、已逾期
////        8、已结清
////        9、借款失败
////        10. 还款审核中
//        when (item.loan_status) {
//            1,4-> {
//                tvStrDate.visibility= View.INVISIBLE
//                holder.setText(R.id.tv_first,"${UIUtils.getString(R.string.loan_amount)}")
//
//
//                holder.setText(R.id.tv_str_date,"${item.app_time}")
//                holder.setText(R.id.tv_money_date, "${UIUtils.getString(R.string.application_time)} ${item.app_time}")
//                iv_status.setImageResource(R.mipmap.img_review)
//            }
//
//
//            2 -> {
//                tvStrDate.visibility= View.INVISIBLE
//                holder.setText(R.id.tv_first,"${UIUtils.getString(R.string.loan_amount)}")
//
//                holder.setText(R.id.tv_str_date,"")
//                holder.setText(R.id.tv_money_date, "${UIUtils.getString(R.string.application_time)} ${item.app_time}")
//                iv_status.setImageResource(R.mipmap.img_cancel)
//            }
//
//
//            3 -> {
//                tvStrDate.visibility= View.INVISIBLE
//                holder.setText(R.id.tv_first,"${UIUtils.getString(R.string.loan_amount)}")
//                holder.setText(R.id.tv_money_date, "${UIUtils.getString(R.string.application_time)} ${item.app_time}")
//                iv_status.setImageResource(R.mipmap.img_review_feiled)
//            }
//
//
//            5 -> {
//
//                tvStrDate.visibility= View.VISIBLE
//                holder.setText(R.id.tv_first,"${UIUtils.getString(R.string.due_money)}")
//
//
//                holder.setText(R.id.tv_money, item.remainAmount)
//                holder.setText(R.id.tv_str_date,"${UIUtils.getString(R.string.loan_amount)} ${item.principal}")
//                holder.setText(R.id.tv_money_date, "${UIUtils.getString(R.string.due_time_order)} ${item.deposit_time}")
//                iv_status.setImageResource(R.mipmap.img_wait_pay)
//            }
//
//
//            7 -> {
//                tvStrDate.visibility= View.VISIBLE
//                holder.setText(R.id.tv_first,"${UIUtils.getString(R.string.due_money)}")
//
//                holder.setText(R.id.tv_str_date,"${UIUtils.getString(R.string.loan_amount)} ${item.principal}")
//                holder.setText(R.id.tv_money_date, "${UIUtils.getString(R.string.due_time_order)} ${item.deposit_time}")
//
//                iv_status.setImageResource(R.mipmap.img_over)
//            }
//            6,  8 -> {
//                tvStrDate.visibility= View.VISIBLE
//                holder.setText(R.id.tv_first,"${UIUtils.getString(R.string.repayment_amount)}")
//
//
//                holder.setText(R.id.tv_str_date,"${UIUtils.getString(R.string.loan_amount)} ${item.principal}")
//                holder.setText(R.id.tv_money_date, "${UIUtils.getString(R.string.repayment_time)} ${item.deposit_time}")
//                iv_status.setImageResource(R.mipmap.img_cler)
//            }
//            9 -> {
//                tvStrDate.visibility= View.INVISIBLE
//
//                holder.setText(R.id.tv_first,"${UIUtils.getString(R.string.loan_amount)}")
//                holder.setText(R.id.tv_money_date, "${UIUtils.getString(R.string.application_time)} ${item.app_time}")
//                iv_status.setImageResource(R.mipmap.img_review_feiled)
//            }
//            10 -> {
//                tvStrDate.visibility= View.INVISIBLE
//                holder.setText(R.id.tv_first,"${UIUtils.getString(R.string.loan_amount)}")
//                holder.setText(R.id.tv_money_date, "${UIUtils.getString(R.string.application_time)} ${item.app_time}")
//                holder.setText(R.id.tv_str_date,"${UIUtils.getString(R.string.loan_amount)} ${item.principal}")
//
//                holder.setText(R.id.tv_money_date, "${UIUtils.getString(R.string.repayment_time)} ${item.deposit_time}")
//                iv_status.setImageResource(R.mipmap.img_pay_review)
//            }
//            else -> {
//                Slog.e("OrderAdapter 未知状态  ${item.loan_status}")
//            }
//
//        }
//
//    }


}