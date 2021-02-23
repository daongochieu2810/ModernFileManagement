package com.example.modernfilemanagement.models

import android.net.Uri

class Image constructor(
    override val name: String,
    override val memAmount: Int,
    val uri: Uri): File(name, memAmount)