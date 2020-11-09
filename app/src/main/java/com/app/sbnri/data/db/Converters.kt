package com.app.sbnri.data.db

import androidx.room.TypeConverter
import com.app.sbnri.data.models.License
import com.app.sbnri.data.models.Permissions

class Converters {

    @TypeConverter
    fun fromLicesnse (license: License?): String {
        return "${license?.key} ${license?.name} ${license?.node_id} ${license?.spdx_id} ${license?.url}"
    }

    @TypeConverter
    fun toLicense (license: String): License {
        val lic = license.split(" ")
        return License(lic[0], lic[1], lic[2], lic[3], lic[4])
    }

    @TypeConverter
    fun fromPermissions (permissions: Permissions?): String {
        return "${permissions?.admin} ${permissions?.pull} ${permissions?.push}"
    }

    @TypeConverter
    fun toPermissions (permissions: String): Permissions {
        val lic = permissions.split(" ")
        return Permissions(lic[0].toBoolean(), lic[1].toBoolean(), lic[2].toBoolean())
    }


}