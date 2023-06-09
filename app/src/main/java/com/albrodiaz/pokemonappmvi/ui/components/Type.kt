package com.albrodiaz.pokemonappmvi.ui.components

import androidx.annotation.DrawableRes
import com.albrodiaz.pokemonappmvi.R

enum class Type {
    BUG,
    DARK,
    DRAGON,
    ELECTRIC,
    FAIRY,
    FIGHTING,
    FIRE,
    FLYING,
    GHOST,
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

val Type.icon: Int @DrawableRes get() = when(this) {
    Type.BUG -> R.drawable.bug
    Type.DARK -> R.drawable.dark
    Type.DRAGON -> R.drawable.dragon
    Type.ELECTRIC -> R.drawable.electric
    Type.FAIRY -> R.drawable.fairy
    Type.FIGHTING -> R.drawable.fighting
    Type.FIRE -> R.drawable.fire
    Type.FLYING -> R.drawable.flying
    Type.GHOST -> R.drawable.ghost
    Type.GRASS -> R.drawable.grass
    Type.GROUND -> R.drawable.ground
    Type.ICE -> R.drawable.ice
    Type.NORMAL -> R.drawable.normal
    Type.POISON -> R.drawable.poison
    Type.PSYCHIC -> R.drawable.psychic
    Type.ROCK -> R.drawable.rock
    Type.STEEL -> R.drawable.steel
    Type.WATER -> R.drawable.water
}

val Type.background: Int @DrawableRes get() = when(this) {
    Type.BUG -> R.drawable.bugbg
//    Type.DARK -> R.drawable.dark
    Type.DRAGON -> R.drawable.dragonbg
    Type.ELECTRIC -> R.drawable.electricbg
    Type.FAIRY -> R.drawable.fairybg
    Type.FIGHTING -> R.drawable.fightingbg
    Type.FIRE -> R.drawable.firebg
//    Type.FLYING -> R.drawable.flying
    Type.GHOST -> R.drawable.ghostbg
    Type.GRASS -> R.drawable.grassbg
    Type.GROUND -> R.drawable.groundbg
//    Type.ICE -> R.drawable.ice
    Type.NORMAL -> R.drawable.normalbg
    Type.POISON -> R.drawable.poisonbg
    Type.PSYCHIC -> R.drawable.psychicbg
    Type.ROCK -> R.drawable.rockbg
//    Type.STEEL -> R.drawable.steel
    Type.WATER -> R.drawable.waterbg
    else -> R.drawable.normalbg
}