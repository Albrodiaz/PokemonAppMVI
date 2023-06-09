package com.albrodiaz.pokemonappmvi.ui.components

import androidx.annotation.DrawableRes
import com.albrodiaz.pokemonappmvi.R

enum class TypeIcon {
    BUG,
    DARK,
    DRAGON,
    ELECTRIC,
    FAIRY,
    FIGHTING,
    FIRE,
    FLYING,
    GRASS,
    GROUND,
    ICE,
    NORMAL,
    POISON,
    PSYCHIC,
    ROCK,
    STEEL,
    WATER;
}

val TypeIcon.icon: Int @DrawableRes get() = when(this) {
    TypeIcon.BUG -> R.drawable.bug
    TypeIcon.DARK -> R.drawable.dark
    TypeIcon.DRAGON -> R.drawable.dragon
    TypeIcon.ELECTRIC -> R.drawable.electric
    TypeIcon.FAIRY -> R.drawable.fairy
    TypeIcon.FIGHTING -> R.drawable.fighting
    TypeIcon.FIRE -> R.drawable.fire
    TypeIcon.FLYING -> R.drawable.flying
    TypeIcon.GRASS -> R.drawable.grass
    TypeIcon.GROUND -> R.drawable.ground
    TypeIcon.ICE -> R.drawable.ice
    TypeIcon.NORMAL -> R.drawable.normal
    TypeIcon.POISON -> R.drawable.poison
    TypeIcon.PSYCHIC -> R.drawable.psychic
    TypeIcon.ROCK -> R.drawable.rock
    TypeIcon.STEEL -> R.drawable.steel
    TypeIcon.WATER -> R.drawable.water
}