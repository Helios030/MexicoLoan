package com.neutron.baselib.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.neutron.baselib.view.LoadingDialog


abstract class BaseFragment : Fragment() {

    /**
     * 加载布局
     */
    @LayoutRes
    abstract fun attachLayoutRes(): Int



    protected lateinit var mActivity: IBaseActivity
    private var isFirstLoad = true

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as IBaseActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(attachLayoutRes(), null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isFirstLoad = true
        initView()
        load()
        initData()
    }

    protected abstract fun initView()

    protected abstract fun lazyLoad()

    protected abstract fun initData()

    //懒加载
    private fun load() {
        if (lifecycle.currentState == Lifecycle.State.STARTED && isFirstLoad) {
            lazyLoad()
            isFirstLoad = false
        }
    }

    override fun onResume() {
        super.onResume()
        load()
    }


    var loadding: LoadingDialog? = null

    fun showLoading() {
        if(loadding==null){
            loadding = LoadingDialog(activity)

        }
        if (loadding != null && !loadding!!.isShowing) {
            loadding!!.show()
        }

    }

    fun hideLoading() {
        if (loadding != null && loadding!!.isShowing) {
            loadding!!.dismiss()
            loadding = null
        }
    }

}