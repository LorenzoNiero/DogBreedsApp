package com.challenge.dogbreeds.network.data.model.base

import com.challenge.dogbreeds.network.data.model.StatusResponse

abstract class NetworkResponseBase {
    abstract val status: StatusResponse
}