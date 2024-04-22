package com.hearx.app.models

import java.io.Serializable

data class RoundData(
    var difficulty: Int,
    var triplet_played: String,
    var triplet_answered: String
) : Serializable
