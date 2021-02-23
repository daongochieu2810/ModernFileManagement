package com.example.modernfilemanagement.models

import android.net.Uri
import kotlinx.parcelize.Parcelize

@Parcelize
class Video constructor(
    override val name: String,
    override val memAmount: Int,
    val duration: Int,
    val uri: Uri) : File(name, memAmount)