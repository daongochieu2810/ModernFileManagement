package com.example.modernfilemanagement.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class File(val name: String, val memAmount: Float): Parcelable