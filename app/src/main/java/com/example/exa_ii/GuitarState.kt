package com.example.exa_ii

import kotlinx.coroutines.flow.emptyFlow

data class GuitarState(
    val guitarList: List<Guitar> = emptyList(),
    val selectedGuitar: Guitar = Guitar(id = 0, brand = "", model = "", price = 0.0, type = GuitarType.SIX_STRING)
)
