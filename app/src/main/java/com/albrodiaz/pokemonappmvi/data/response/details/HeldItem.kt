package com.albrodiaz.pokemonappmvi.data.response.details

data class HeldItem(
    val item: Item,
    val version_details: List<VersionDetail>
)