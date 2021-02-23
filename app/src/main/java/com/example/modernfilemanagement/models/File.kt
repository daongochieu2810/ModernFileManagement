package com.example.modernfilemanagement.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class File(open val name: String, open val memAmount: Int): Parcelable