package com.example.tppokedex.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RandomData(
    val name: NameData,
    val picture: PictureData
)
