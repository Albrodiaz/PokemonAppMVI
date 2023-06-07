package com.albrodiaz.pokemonappmvi.ui.components

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

    val icon: Int
        get() {
            return when(this) {
                BUG -> R.drawable.bug
                DARK -> R.drawable.dark
                DRAGON -> R.drawable.dragon
                ELECTRIC -> R.drawable.electric
                FAIRY -> R.drawable.fairy
                FIGHTING -> R.drawable.fighting
                FIRE -> R.drawable.fire
                FLYING -> R.drawable.flying
                GRASS -> R.drawable.grass
                GROUND -> R.drawable.ground
                ICE -> R.drawable.ice
                NORMAL -> R.drawable.normal
                POISON -> R.drawable.poison
                PSYCHIC -> R.drawable.psychic
                ROCK -> R.drawable.rock
                STEEL -> R.drawable.steel
                WATER -> R.drawable.water
            }
        }
}