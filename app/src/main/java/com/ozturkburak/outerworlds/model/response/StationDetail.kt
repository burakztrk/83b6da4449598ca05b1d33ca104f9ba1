package com.ozturkburak.outerworlds.model.response

data class StationDetail(
    val name: String?,
    val coordinateX: Float?,
    val coordinateY: Float?,
    val capacity: Int?,
    val stock: Int?,
    val need: Int?
)