package com.neutron.mexicoloan.ui.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemLongClickListener
import com.neutron.baselib.bean.BankInfoResult
import com.neutron.baselib.utils.Slog
import com.neutron.baselib.utils.sethalfWith
import com.neutron.baselib.utils.toast
import com.neutron.mexicoloan.R


class CommDialog : Dialog {
    private var btn_ok: TextView? = null
    private var btn_cancel: TextView? = null
    private var tvTitle: TextView? = null
    private var tvMessage: TextView? = null
    private var llList: LinearLayout? = null
    private var rvMenu: RecyclerView? = null
    private var rvMenu2: RecyclerView? = null

    constructor(context: Context?) : super(context!!, R.style.CustomDialog) {
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.common_dialog_layout)
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false)
        //初始化界面控件
        initView()
    }


    var title: String? = null

    fun setTitle(title: String?): CommDialog {
        this.title = title
        return this
    }

    var message: String? = null

    fun setMessage(message: String?): CommDialog {

        this.message = message
        return this
    }

    var isList = true

    fun setIsList(isList: Boolean): CommDialog {
        this.isList = isList
        return this
    }


    override fun show() {
        super.show()
        refreshView()
    }

    private fun refreshView() {

        if (isList) {
            llList?.visibility = View.VISIBLE
            tvMessage?.visibility = View.GONE
        } else {
            llList?.visibility = View.GONE
            tvMessage?.visibility = View.VISIBLE
        }

        if (!TextUtils.isEmpty(title)) {
            tvTitle?.text = title
        }
        if (!TextUtils.isEmpty(message)) {
            tvMessage?.text = message
        }
    }

    /**
     * 初始化界面控件
     */
    private fun initView() {
        btn_ok = findViewById<View>(R.id.tv_ok) as TextView
        btn_cancel = findViewById<View>(R.id.tv_cancel) as TextView
        tvMessage = findViewById<View>(R.id.tv_message) as TextView
        llList = findViewById<View>(R.id.ll_list) as LinearLayout
        tvTitle = findViewById<View>(R.id.tv_title) as TextView
        rvMenu = findViewById<RecyclerView>(R.id.rv_menu)

        Slog.d("=========  initView ")

        rvMenu2 = findViewById(R.id.rv_menu_2)

        initMenuAdapter(rvMenu)
        initMenuAdapter2(rvMenu2)
        btn_ok?.setOnClickListener {

            if(currItem==null){
                mContext?.toast(R.string.please_select)
            }else{
                onOkListener?.invoke(currItem)
                dismiss()
            }
        }
        btn_cancel?.setOnClickListener {
            dismiss()
        }
        refreshView()
    }

    var mContext: Context? = null
    var adapter: MenuAdapter? = null
    var bankAdapter: BankAdapter? = null
    var adapter2: MenuAdapter? = null
    var currMenu: MenuItem? = null
    var currBank: BankInfoResult? = null
    var menuList = mutableListOf<MenuItem>()
    var menuList2 = mutableListOf<MenuItem>()
    var bankList = mutableListOf<BankInfoResult>()

    fun setOpentionList(list: List<MenuItem>): CommDialog {
        menuList.clear()
        menuList.addAll(list)
        adapter?.notifyDataSetChanged()
        return this
    }

    fun setOpentionList2(list2: List<MenuItem>): CommDialog {
        rvMenu2?.visibility = View.VISIBLE
        menuList2.clear()
        list2?.let {
            menuList2.addAll(it)
        }
        rvMenu?.sethalfWith()
        adapter2?.notifyDataSetChanged()
        adapter?.notifyDataSetChanged()
        return this
    }


    fun setBankList(list: List<BankInfoResult>): CommDialog {
        bankList.clear()
        bankList.addAll(list)
        bankAdapter?.notifyDataSetChanged()
        return this
    }

    private fun initMenuAdapter2(rvMenu2: RecyclerView?) {
        rvMenu2?.let { rv ->
            adapter2 = MenuAdapter(R.layout.item_pop_menu, menuList2)
            rv.layoutManager = LinearLayoutManager(mContext)
            rv.adapter = adapter2
            adapter2?.setOnItemClickListener { _, _, position ->
                menuList2.forEach { it.isSelected = false }
                menuList2[position].isSelected = true
                adapter2?.notifyDataSetChanged()
                currItem=menuList2[position]
                onSelectedListener?.invoke(currItem)

            }

            adapter2?.setOnItemLongClickListener { _, _, position ->

                mContext?.toast( menuList2[position].menuName)
                true
            }

        }
    }

    private fun initMenuAdapter(rvMenu: RecyclerView?) {
        rvMenu?.let { rv ->
            adapter = MenuAdapter(R.layout.item_pop_menu, menuList)
            rv.layoutManager = LinearLayoutManager(mContext)
            rv.adapter = adapter
            adapter?.setOnItemClickListener { _, _, position ->
                menuList.forEach { it.isSelected = false }
                currMenu = menuList[position]
                currMenu?.isSelected = true
                adapter?.notifyDataSetChanged()
                currItem=currMenu
                onSelectedListener?.invoke(currItem)
            }
            adapter?.setOnItemLongClickListener { _, _, position ->
                mContext?.toast( menuList[position].menuName)
                true
            }
        }
    }

    fun setBankAdapter(): CommDialog {


        Slog.d("setBankAdapter ${rvMenu==null}    ${rvMenu?.visibility==View.VISIBLE}")

        rvMenu?.let { rv ->
            bankAdapter = BankAdapter(R.layout.item_bank, bankList)
            rv.layoutManager = LinearLayoutManager(mContext)
            rv.adapter = bankAdapter
            bankAdapter?.setOnItemClickListener { _, _, position ->
                bankList.forEach { it.isSelected = false }
                currBank = bankList[position]
                currBank?.isSelected = true
                bankAdapter?.notifyDataSetChanged()

                currItem=currBank
            }
        }
        return this
    }

    var currItem:Any?=null

    var  onOkListener:((Any?)->Unit)?=null

    fun setOnOkClick(listener:(Any?)->Unit): CommDialog{
        onOkListener=listener
        return this
    }


    var  onSelectedListener:((Any?)->Unit)?=null

    fun setonSelectedListener(listener: ((Any?) -> Unit)?): CommDialog{
        onSelectedListener=listener
        return this
    }

}