package com.hearx.app.models

data class TestData (
    val mode: String,
    val raw: Raw
)

data class Raw(
    val score: Int,
    val rounds: List<RoundData>
)