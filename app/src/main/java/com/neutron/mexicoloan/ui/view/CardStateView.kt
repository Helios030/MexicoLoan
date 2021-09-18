package com.neutron.mexicoloan.ui.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.neutron.baselib.utils.*
import com.neutron.baselib.utils.UIUtils.Companion.getColor
import com.neutron.baselib.utils.UIUtils.Companion.getString
import com.neutron.mexicoloan.R
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
    var isShow=false
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

     fun showStyle(viewStyle: Int,context: Context):CardStateView {
         
         val ffaa=getColor(R.color.gray_ffaa)
         val ff32=getColor(R.color.blue_ff32)
         val f4=getColor(R.color.color_pink_f4)

        when (viewStyle) {
            MoneyState.STATE_APPLYING -> {
                tv_title.setDrawableRight(R.mipmap.icon_card_blue)
                tv_loan_money.setTextColor(ff32)
                ll_detail_bg.setBackgroundResource(R.color.blue_ff32)
                tv_bottom.text=  context.getString(R.string.review_tip)

            }
            MoneyState.STATE_APPROVAL_REJECTED -> {
                tv_title.setDrawableRight(R.mipmap.img_card_gray)
                tv_loan_money.setTextColor(ffaa)
                ll_detail_bg.setBackgroundResource(R.color.gray_ffaa)
                tv_bottom.text=  context.getString(R.string.review_error_tip)
                civ_app_time.setRightTextColor(ffaa)
            }

            MoneyState.STATE_PENDING_REPAYMENT -> {
                tv_title.setDrawableRight(R.mipmap.icon_card_blue)
                tv_loan_money.setTextColor(ff32)
                ll_detail_bg.setBackgroundResource(R.color.blue_ff32)
                tv_bottom.text=  context.getString(R.string.repay_tip)
                civ_audit_fee.visibility=View.GONE
                civ_fees_service.visibility=View.GONE
                civ_pay_fee.visibility=View.GONE
                civ_interest.visibility=View.VISIBLE

            }



            MoneyState.STATE_OVERDUE -> {
                tv_title.setDrawableRight(R.mipmap.img_card_pink)
                tv_loan_money.setTextColor(f4)
                ll_detail_bg.setBackgroundResource(R.color.color_pink_f4)
                tv_bottom.text=  context.getString(R.string.over_tip)

                civ_app_time.setRightTextColor(f4)

                civ_audit_fee.visibility=View.GONE
                civ_fees_service.visibility=View.GONE
                civ_pay_fee.visibility=View.GONE
                civ_interest.visibility=View.VISIBLE
                civ_late_fee.visibility=View.VISIBLE
                civ_pen_interset.visibility=View.VISIBLE



            }

            MoneyState.STATE_ORDER ->{
                ll_detail_bg.visibility=View.GONE
                tv_order_tip.visibility=View.VISIBLE
                tv_title.setDrawableRight(R.mipmap.img_card_gray)
                tv_loan_money.setTextColor(ffaa)
                ll_detail_bg.setBackgroundResource(R.color.gray_ffaa)
                civ_app_time.setRightTextColor(ffaa)
            }

            MoneyState.STATE_ORDER_CLEAR ->{
                ll_detail_bg.visibility=View.GONE
                tv_order_tip.visibility=View.VISIBLE
                tv_order_tip.setBackgroundColor(getColor(R.color.green_ff12))
                tv_bottom.visibility=View.GONE
            }



        }
         return this
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
          val newstr=  getString(R.string.loan_time).format(str)
            tv_loan_term.text = newstr


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
        Slog.d("setFeesService $str")
        if (!str.isNullOrEmpty()) {
            civ_fees_service.setRightText(str)
        }
        return this
    }


    fun setFeesServiceGone():CardStateView{

            civ_fees_service.visibility=View.GONE
        return this
    }
    fun setAuditFeeGone():CardStateView{

        civ_audit_fee.visibility=View.GONE
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

    fun getTvBottom():TextView{
        return tv_bottom
    }
    fun setTvBottomGone():CardStateView{
         tv_bottom.visibility=View.GONE
        return this
    }

    fun  setTvOrderTip(str:String):CardStateView{

        tv_order_tip.text=str
        return this
    }


}