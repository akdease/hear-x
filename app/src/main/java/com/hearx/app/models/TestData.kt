package com.hearx.app.models

import java.io.Serializable

data class TestData (
    val mode: String,
    val raw: Raw
) : Serializable

data class Raw(
    val score: Int,
    val rounds: List<RoundData>
) : Serializable