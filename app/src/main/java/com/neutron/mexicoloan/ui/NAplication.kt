package com.neutron.mexicoloan.ui

import com.fc.bioassay.api.Bioassay
import com.neutron.baselib.base.BaseApplication
import com.neutron.baselib.utils.Slog
import com.neutron.mexicoloan.R

class NAplication :BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        Bioassay.setCountry("MX", false)
        Bioassay.setLogo(R.mipmap.ic_launcher)
        Bioassay.setFastMode(true)
        Bioassay.setType(3)

    }

}