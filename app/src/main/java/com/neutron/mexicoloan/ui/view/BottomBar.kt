package com.neutron.mexicoloan.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.neutron.baselib.utils.setDrawableTop
import com.neutron.mexicoloan.R
import kotlinx.android.synthetic.main.view_bottom_bar.view.*

class BottomBar : LinearLayout {

    companion object{
        val firstItem=0
        val secondItem=1

    }

    constructor(context: Context?) : super(context!!) {
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!,
        attrs
    ) {
        LayoutInflater.from(context).inflate(R.layout.view_bottom_bar, this, true);
        val ta = context.obtainStyledAttributes(attrs, R.styleable.BottomBar);

        tv_tab_main.setOnClickListener {
            showTabIsMain(true, context)
        }
        tv_tab_user.setOnClickListener {
            showTabIsMain(false, context)
        }
    }


    private fun showTabIsMain(b: Boolean, context: Context) {

        lisenter?.onSelected(if(b) firstItem else secondItem)

        val redFB = context.getColor(R.color.blue_ff32)
        val meetFF = context.getColor(R.color.gray_ffaa)

        if (b) {
            tv_tab_main.setDrawableTop(R.mipmap.icon_main_y)
            tv_tab_main.setTextColor(redFB)
            tv_tab_user.setTextColor(meetFF)
            tv_tab_user.setDrawableTop(R.mipmap.icon_user_n)
//            view_line.visibility=View.VISIBLE
        } else {
            tv_tab_main.setTextColor(meetFF)
            tv_tab_user.setTextColor(redFB)
            tv_tab_main.setDrawableTop(R.mipmap.icon_main_n)
            tv_tab_user.setDrawableTop(R.mipmap.icon_user_y)
//            view_line.visibility=View.GONE
        }
    }

    interface  onItemSelected{
       fun  onSelected(item:Int)

    }

    var lisenter:onItemSelected?=null

    fun setonItemSelected(lisenter:onItemSelected){
        this.lisenter=lisenter

    }



}