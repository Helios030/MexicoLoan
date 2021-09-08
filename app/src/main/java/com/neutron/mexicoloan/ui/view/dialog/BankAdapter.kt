package com.neutron.mexicoloan.ui.view.dialog
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.neutron.baselib.bean.BankInfoResult
import com.neutron.baselib.utils.Slog
import com.neutron.baselib.utils.UIUtils
import com.neutron.mexicoloan.R


class BankAdapter(layoutResId: Int, data: MutableList<BankInfoResult>) :
    BaseQuickAdapter<BankInfoResult, BaseViewHolder>(layoutResId, data) {
    override fun convert(holder: BaseViewHolder, item: BankInfoResult) {
        holder.setText(R.id.tv_money_number, item.bank_name)
        val imgeBank = holder.getView<ImageView>(R.id.iv_bank_img)
        Glide.with(UIUtils.getContext()).load(item.bank_icon).into(imgeBank)


        val ll_main = holder.getView<LinearLayout>(R.id.ll_bank_main)

        if (item.isSelected) {
            ll_main .setBackgroundColor(UIUtils.getColor(R.color.blue_ffea))
            holder.setTextColor(R.id.tv_money_number, UIUtils.getColor(R.color.blue_ff32))
        } else {
            ll_main.background = UIUtils.getDrawable( R.drawable.ripple_bg)
            holder.setTextColor(R.id.tv_money_number, UIUtils.getColor(R.color.gray_ffaa))

        }
    }


}