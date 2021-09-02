package  com.neutron.mexicoloan.ui.main.fragment.product


import android.view.View
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import com.neutron.baselib.base.BaseVMFragment
import com.neutron.baselib.bean.ProductsResult
import com.neutron.baselib.utils.PreferencesHelper
import com.neutron.baselib.utils.Slog
import com.neutron.mexicoloan.R
import com.neutron.mexicoloan.ui.main.MainActivity
import com.neutron.mexicoloan.ui.view.verificationcodeview.ProSeekBar
import kotlinx.android.synthetic.main.fragment_product.*


class ProductFragment : BaseVMFragment<ProductVM>(ProductVM::class.java) {

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_product
    }


    override fun onResume() {
        super.onResume()

        mViewModel.getProducts()

    }


    override fun initView() {
        btn_loan.setOnClickListener {

            mViewModel.getUserStatus()


        }

    }

    override fun lazyLoad() {

    }

    override fun initData() {

        observerProducts()
        observerUserState()


    }

    private fun observerUserState() {
        mViewModel.userStatus.observe(this, {

            if (it.person_status == "0") {
//                startTo(IDCardActivity::class.java)
            } else if (it.contact_status == "0") {
//                startTo(ContactPersonActivity::class.java)
            } else if (it.comp_status == "0") {
//                startTo(ProfessionActivity::class.java)
            } else if (it.card_status == "0") {
//                startTo(MoneyCardActivity::class.java)
            } else {
//                startTo(FaceDetectionActivity::class.java)
            }

        })
    }


    //    监听贷款产品返回
    private fun observerProducts() {
        mViewModel.products.observe(this, {
            val mMainActivity = mActivity as MainActivity
            it?.let { products ->
//                初始化数据
                etv_max.animateText("$${products.last().principal}")
                val first = products.first()
                etv_money.animateText("$${first.principal}")
                etv_date.animateText(getString(R.string.repay_date).format(first.duration))
                PreferencesHelper.setProductId(first.productId.toString())
                sb_money.setList(products)
                    .setOnSelectedLisenter(object : ProSeekBar.onSelectedLisenter {
                        override fun onSelected(productsResult: ProductsResult) {
                            Slog.d("选中商品  $productsResult")
                            etv_money.animateText("$${productsResult.principal}")
                            etv_date.animateText(
                                getString(R.string.repay_date).format(
                                    productsResult.duration
                                )
                            )
                            if (productsResult.enable == "2") {
                                btn_loan.background =
                                    getDrawable(activity!!, R.drawable.shape_gray_btn)
                                iv_lock.visibility = View.VISIBLE
                                etv_money.setTextColor(mActivity.getColor(R.color.blue_ff9f))
                            } else {
                                etv_money.setTextColor(mActivity.getColor(R.color.white))
                                iv_lock.visibility = View.GONE
                                btn_loan.background =
                                    getDrawable(activity!!, R.drawable.shape_pink_btn)
                                PreferencesHelper.setProductId(productsResult.productId.toString())
                            }
                        }

                        override fun onStopScroll() {
                            mMainActivity.getSFL().isEnabled = true
                        }

                        override fun onStartScroll() {
                            mMainActivity.getSFL().isEnabled = false

                        }
                    })
            }
        })
    }
}


