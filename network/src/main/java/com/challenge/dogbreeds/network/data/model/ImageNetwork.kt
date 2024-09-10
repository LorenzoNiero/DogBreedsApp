package com.challenge.dogbreeds.network.data.model

import com.challenge.dogbreeds.network.data.model.base.NetworkResponseBase
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageNetwork (
    val message: String,
    override val status: String
) : NetworkResponseBase()