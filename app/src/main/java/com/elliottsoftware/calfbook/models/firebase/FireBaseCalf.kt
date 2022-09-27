package com.elliottsoftware.calfbook.models.firebase

import androidx.room.PrimaryKey
import com.google.firebase.database.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties
data class FireBaseCalf(val calfTag: String? = null,
                        val cowTag:String? = null,
                        val CCIANumber: String? = null,
                        val sex:String? = null,
                        val details:String?=null,
                        val date: Date? = null,
                        val birthWeight:Int? = null

) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}

