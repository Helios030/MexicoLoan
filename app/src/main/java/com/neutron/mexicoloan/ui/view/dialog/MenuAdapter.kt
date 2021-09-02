package com.neutron.mexicoloan.ui.view.dialog

import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.neutron.baselib.bean.CityBeanResult
import com.neutron.baselib.utils.UIUtils
import com.neutron.mexicoloan.R


class MenuAdapter(layoutResId: Int, data: MutableList<MenuItem>) :
    BaseQuickAdapter<MenuItem, BaseViewHolder>(layoutResId, data) {
    override fun convert(holder: BaseViewHolder, item: MenuItem) {
        holder.setText(R.id.tv_menu, item.menuName)
      val ll_main=  holder.getView<LinearLayout>(R.id.ll_main)

        if (item.isSelected) {
            ll_main .setBackgroundColor(UIUtils.getColor(R.color.blue_ffea))
            holder.setTextColor(R.id.tv_menu, UIUtils.getColor(R.color.blue_ff32))
        }else{
            ll_main .background= UIUtils.getDrawable(R.drawable.ripple_bg)
            holder.setTextColor(R.id.tv_menu, UIUtils.getColor(R.color.gray_ffaa))


        }

    }


}

data class MenuItem(val menuName:String,var isSelected:Boolean=false,val cityBeanResult: CityBeanResult?=null)