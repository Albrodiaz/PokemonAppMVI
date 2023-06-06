package com.albrodiaz.pokemonappmvi.data.response.details

data class Move(
    val move: MoveX,
    val version_group_details: List<VersionGroupDetail>
)