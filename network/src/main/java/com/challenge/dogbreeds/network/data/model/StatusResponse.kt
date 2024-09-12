package com.challenge.dogbreeds.network.data.model

import com.squareup.moshi.Json

enum class StatusResponse (){
    @Json(name = "success")
    SUCCESS,

    //suppose result as to be fail
    @Json(name = "fail")
    FAIL
}