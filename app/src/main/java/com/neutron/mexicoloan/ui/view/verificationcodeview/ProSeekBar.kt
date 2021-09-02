package com.neutron.mexicoloan.ui.view.verificationcodeview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.SeekBar
import com.neutron.baselib.bean.ProductsResult
import com.neutron.baselib.utils.Slog
import com.neutron.baselib.utils.scrollTo
import com.neutron.mexicoloan.R
import kotlinx.android.synthetic.main.view_pro_seekbar.view.*

class ProSeekBar : RelativeLayout {
    constructor(context: Context?) : super(context!!) {
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        LayoutInflater.from(context).inflate(R.layout.view_pro_seekbar, this, true);
        seek_pro.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, p1: Int, p2: Boolean) {


            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {  selectedLisenter?.onStartScroll()}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                fixSeekBar(seekBar)
                selectedLisenter?.onStopScroll()
            }
        })
    }

    private var list = listOf<ProductsResult>()

    fun setList(list: List<ProductsResult>): ProSeekBar {
        this.list = list
        return this
    }

    fun fixSeekBar(seekBar: SeekBar?) {

        Slog.d("fixSeekBar  $list")

        if(list.isNullOrEmpty()){
            return
        }

        val onePro = 100 / list.size
        seekBar?.let {
            var index = (it.progress / onePro)
            if (index >= list.size) {
                index = list.size - 1
            }
            it.scrollTo(index * onePro)

            Slog.d("view 选中   ${list[index]} ")
            selectedLisenter?.onSelected(list[index])


        }
    }


    var selectedLisenter: onSelectedLisenter? = null


    interface onSelectedLisenter {
        fun onSelected(productsResult: ProductsResult)
        fun onStopScroll()
        fun onStartScroll()

    }

    fun setOnSelectedLisenter(isenter: onSelectedLisenter): ProSeekBar {
        this.selectedLisenter = isenter
        return this

    }


}