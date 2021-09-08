package com.neutron.mexicoloan.ui.order

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.neutron.baselib.base.BaseVMActivity
import com.neutron.baselib.bean.OrderBeanResult
import com.neutron.baselib.utils.Slog
import com.neutron.mexicoloan.R
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class OrderActivity : BaseVMActivity<OrderVM>(OrderVM::class.java) {




    override fun getLayoutId(): Int {
        return R.layout.activity_order
    }

    override fun initView() {
        iv_back.setOnClickListener { finish() }
        tv_title.text = getString(R.string.my_loan)



    }

    override fun initData() {
        initTab()
        initRecycleView()
    }

    var tab1: TabLayout.Tab? = null
    var tab2: TabLayout.Tab? = null
    private fun initTab() {
        tab1 = tl_tab.newTab()
        tab2 = tl_tab.newTab()
        tab1?.let {
            it.setText(R.string.curr)
            tl_tab.addTab(it)
        }

        tab2?.let {
            it.setText(R.string.history)
            tl_tab.addTab(it)
        }


        tl_tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {

                tab?.let {
                    if (it == tab1) {
                        val inproOrder = mutableListOf<OrderBeanResult>()
                        orders.forEach {order->
                            if (order.loan_status != 2 && order.loan_status != 3 && order.loan_status != 8 && order.loan_status != 6 && order.loan_status != 9) {
                                inproOrder.add(order)
                            }
                        }
                        Slog.d("inproOrder $orders")
                        showRv(inproOrder)
                        adapter?.setNewData(inproOrder.toMutableList())
                    } else {
                        val successOrder = mutableListOf<OrderBeanResult>()
                        orders.forEach {order->
                            if (order.loan_status == 2 || order.loan_status == 3 || order.loan_status == 8 || order.loan_status == 6 || order.loan_status == 9) {
                                successOrder.add(order)
                            }
                        }

                        val newOder=orders.map {order->
                            order.loan_status == 2 || order.loan_status == 3 || order.loan_status == 8 || order.loan_status == 6 || order.loan_status == 9
                        }

                        Slog.d("successOrder  $orders")
                        Slog.d("successOrder  newOder $newOder")
                        showRv(successOrder)
                        adapter?.setNewData(successOrder.toMutableList())

                    }
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })


    }

    var orders = mutableListOf<OrderBeanResult>()
    var adapter: OrderAdapter? = null
    private fun initRecycleView() {
        Slog.d("initRecycleView ")
        adapter = OrderAdapter(R.layout.item_order, orders)
        rv_order.layoutManager = LinearLayoutManager(this)
        rv_order.adapter = adapter

    }
    private fun showRv(inproOrder: List<OrderBeanResult>) {
        if (inproOrder.isEmpty()) {
            tv_not_order.visibility = View.VISIBLE
            rv_order.visibility = View.GONE
        } else {
            tv_not_order.visibility = View.GONE
            rv_order.visibility = View.VISIBLE
        }
    }

    override fun observeValue() {

    }


}