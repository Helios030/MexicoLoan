package com.neutron.mexicoloan.ui.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.neutron.baselib.utils.MoneyState
import com.neutron.baselib.utils.collapse
import com.neutron.baselib.utils.expand
import com.neutron.baselib.utils.setDrawableRight
import com.neutron.mexicoloan.R
import kotlinx.android.synthetic.main.fragment_review.*
import kotlinx.android.synthetic.main.view_card_state.view.*

class CardStateView : RelativeLayout {

    constructor(context: Context?) : super(context!!) {
    }

    var mContext: Context? = null

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!,
        attrs
    ) {
        mContext = context
        LayoutInflater.from(context).inflate(R.layout.view_card_state, this, true);
        val ta = context.obtainStyledAttributes(attrs, R.styleable.CardStateView);
        initAttr(ta)
    }

    var view_style = MoneyState.STATE_APPLYING
    var isShow=true
    private fun initAttr(ta: TypedArray) {

        view_style = ta.getInt(R.styleable.CardStateView_card_view_style, MoneyState.STATE_APPLYING)

        showStyle(view_style,context)

        tv_amount.setOnClickListener {
            isShow=!isShow
            ll_detail.visibility=if(isShow){
                tv_amount.setDrawableRight(R.mipmap.icon_close)
                ll_detail.expand()
                View.VISIBLE
            }else{
                tv_amount.setDrawableRight(R.mipmap.icon_open)
                ll_detail.collapse()
                View.GONE
            }
        }
    }

    private fun showStyle(viewStyle: Int,context: Context) {
        when (viewStyle) {
            MoneyState.STATE_APPLYING -> {
                tv_title.setDrawableRight(R.mipmap.icon_card_blue)
                tv_loan_money.setTextColor(context.getColor(R.color.blue_ff32))
                ll_detail_bg.setBackgroundResource(R.color.blue_ff32)
                tv_bottom.text=  context.getString(R.string.review_tip)

            }
            MoneyState.STATE_APPROVAL_REJECTED -> {
                tv_title.setDrawableRight(R.mipmap.img_card_gray)
                tv_loan_money.setTextColor(context.getColor(R.color.gray_ffaa))
                ll_detail_bg.setBackgroundResource(R.color.gray_ffaa)
                tv_bottom.text=  context.getString(R.string.review_error_tip)
                civ_app_time.setRightTextColor(R.color.gray_ffaa)
            }

            MoneyState.STATE_PENDING_REPAYMENT -> {
                tv_title.setDrawableRight(R.mipmap.icon_card_blue)
                tv_loan_money.setTextColor(context.getColor(R.color.blue_ff32))
                ll_detail_bg.setBackgroundResource(R.color.blue_ff32)
                tv_bottom.text=  context.getString(R.string.repay_tip)



                civ_audit_fee.visibility=View.GONE
                civ_fees_service.visibility=View.GONE
                civ_pay_fee.visibility=View.GONE
                civ_interest.visibility=View.VISIBLE

            }



            MoneyState.STATE_OVERDUE -> {
                tv_title.setDrawableRight(R.mipmap.img_card_pink)
                tv_loan_money.setTextColor(context.getColor(R.color.color_pink_f4))
                ll_detail_bg.setBackgroundResource(R.color.color_pink_f4)
                tv_bottom.text=  context.getString(R.string.over_tip)

                civ_app_time.setRightTextColor(R.color.color_pink_f4)

                civ_audit_fee.visibility=View.GONE
                civ_fees_service.visibility=View.GONE
                civ_pay_fee.visibility=View.GONE
                civ_interest.visibility=View.VISIBLE
                civ_late_fee.visibility=View.VISIBLE
                civ_pen_interset.visibility=View.VISIBLE



            }



        }
    }



//    设置参数

    fun setTvLoanMoney(str:String):CardStateView{
        if (!str.isNullOrEmpty()) {
            tv_loan_money.text = str
        }
        return this
    }


    fun setTvLoanTerm(str:String):CardStateView{
        if (!str.isNullOrEmpty()) {
            tv_loan_term.text = str
        }
        return this
    }
    fun setAppTime(str:String):CardStateView{
        if (!str.isNullOrEmpty()) {
            civ_app_time.setRightText(str)
        }
        return this
    }
    fun setTvAmount(str:String):CardStateView{
        if (!str.isNullOrEmpty()) {
            tv_amount.text = str
        }
        return this
    }



    fun setLoanAmount(str:String):CardStateView{
        if (!str.isNullOrEmpty()) {
            civ_loan_amount.setRightText(str)
        }
        return this
    }

    fun setPayFee(str:String):CardStateView{
        if (!str.isNullOrEmpty()) {
            civ_pay_fee.setRightText(str)
        }
        return this
    }
    fun setFeesService(str:String):CardStateView{
        if (!str.isNullOrEmpty()) {
            civ_fees_service.setRightText(str)
        }
        return this
    }
    fun setAuditFee(str:String):CardStateView{
        if (!str.isNullOrEmpty()) {
            civ_audit_fee.setRightText(str)
        }
        return this
    }
    fun setInterest(str:String):CardStateView{
        if (!str.isNullOrEmpty()) {
            civ_interest.setRightText(str)
        }
        return this
    }



}