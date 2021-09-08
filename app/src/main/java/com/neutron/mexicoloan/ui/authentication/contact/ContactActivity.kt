package com.neutron.mexicoloan.ui.authentication.contact


import android.Manifest
import android.content.Intent
import android.database.Cursor
import android.provider.ContactsContract
import android.view.View
import com.neutron.baselib.base.BaseVMActivity
import com.neutron.baselib.utils.*
import com.neutron.mexicoloan.R
import com.neutron.mexicoloan.ui.authentication.card.CardActivity
import com.neutron.mexicoloan.ui.view.CertificationItemView
import com.neutron.mexicoloan.ui.view.dialog.MenuItem
import com.neutron.mexicoloan.util.Str2MenuItem
import com.neutron.mexicoloan.util.showCommSelectDialog
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.activity_card.*
import kotlinx.android.synthetic.main.activity_contact.*
import kotlinx.android.synthetic.main.activity_contact.btn_next
import kotlinx.android.synthetic.main.activity_personal_info.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class ContactActivity : BaseVMActivity<ContactVM>(ContactVM::class.java) {
    val dataMap = HashMap<String, Any>()
    override fun initData() {
        dataMap["user_id"] = PreferencesHelper.getUserID()


    }


    override fun getLayoutId(): Int {
        return R.layout.activity_contact
    }

    override fun initView() {
        iv_back.setOnClickListener {
            finish()
        }
        tv_title.text = getString(R.string.contact_info)

        btn_next.setOnClickListener {

            mViewModel.uploadContactPerson(dataMap)


        }
        civ_relation.setOnTVClickListener { showFirstRelatation() }
        civ_contact_name.setOnTVClickListener { startToPhoneBook(PICK_CONTACT) }
        civ_contact_phone.setOnTVClickListener { startToPhoneBook(PICK_CONTACT) }
        civ_relation_2.setOnTVClickListener { showTwoRelatation() }
        civ_contact_name_2.setOnTVClickListener { startToPhoneBook(PICK_CONTACT_2) }
        civ_contact_phone_2.setOnTVClickListener { startToPhoneBook(PICK_CONTACT_2) }
    }

    //联系人关系
    private fun showFirstRelatation() {
        showCommSelectDialog(
            getString(R.string.job), getStrArray(R.array.array_first_contact).Str2MenuItem(), {
                if (it is MenuItem) {
                    setContact1(it.menuCode)
                }
            }
        )
    }

    private fun showTwoRelatation() {
        showCommSelectDialog(
            getString(R.string.job), getStrArray(R.array.array_second_contact).Str2MenuItem(), {
                if (it is MenuItem) {
                    setContact2(it.menuCode)
                }
            }
        )
    }


    private fun setContact1(position: Int) {
        civ_relation.setTextStr(getStrByIndex(R.array.array_first_contact, position))
        dataMap["relation_1"] = "$position"

    }

    private fun setContact2(position: Int) {
        civ_relation_2.setTextStr(getStrByIndex(R.array.array_second_contact, position))
        dataMap["relation_2"] = position
    }


    val PICK_CONTACT = 1
    val PICK_CONTACT_2 = 2
    private fun startToPhoneBook(pickContact: Int) {
        checkPerByX(listOf(Manifest.permission.READ_CONTACTS), {
            val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
            startActivityForResult(intent, pickContact)
        }, R.string.not_pp, R.string.dialog_ok, R.string.dialog_cancel)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_CONTACT || requestCode == PICK_CONTACT_2) {
            data?.let {
                getContacts(it, requestCode)
            }
        }
    }

    private fun getContacts(data: Intent, code: Int) {
        var name = ""
        var phoneNumber = ""
        val contactUri = data.data!!
        val cursor: Cursor? = contentResolver.query(contactUri, null, null, null, null)
        cursor?.let {
            if (it.moveToFirst()) {
                name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                var hasPhone: String =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                val id: String =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                hasPhone = if (hasPhone.equals("1", ignoreCase = true)) {
                    "true"
                } else {
                    "false"
                }
                if (java.lang.Boolean.parseBoolean(hasPhone)) {
                    val phones: Cursor = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                        null,
                        null
                    )!!
                    while (phones.moveToNext()) {
                        phoneNumber = phones.getString(
                            phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                        )
                    }
                    phones.close()
                }
                cursor.close()

                phoneNumber = phoneNumber.trim()
                when (code) {
                    PICK_CONTACT -> {
                        civ_contact_name .setTextStr(name)
                        civ_contact_phone.setTextStr(phoneNumber)
                        dataMap["name_relation_1"] = name
                        dataMap["phone_relation_1"] = phoneNumber

                    }
                    PICK_CONTACT_2 -> {
                        civ_contact_name_2.setTextStr(name)
                        civ_contact_phone_2.setTextStr(phoneNumber)
                        dataMap["name_relation_2"] = name
                        dataMap["phone_relation_2"] = phoneNumber
                    }
                }
            }
        }

    }

    override fun observeValue() {
        mViewModel.isUploadSuccess.observe(this,{

            startTo(CardActivity::class.java)

        })
    }


}