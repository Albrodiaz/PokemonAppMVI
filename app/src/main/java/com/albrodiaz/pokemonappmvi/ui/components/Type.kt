package com.albrodiaz.pokemonappmvi.ui.components

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

val Type.pokemonResource: PokemonRes
    get() = when (this) {
        Type.BUG -> PokemonRes(icon = R.drawable.bug, background = R.drawable.bugbg)
        Type.DARK -> PokemonRes(icon = R.drawable.dark, background = R.drawable.darkbg)
        Type.DRAGON -> PokemonRes(icon = R.drawable.dragon, background = R.drawable.dragonbg)
        Type.ELECTRIC -> PokemonRes(icon = R.drawable.electric, background = R.drawable.electricbg)
        Type.FAIRY -> PokemonRes(icon = R.drawable.fairy, background = R.drawable.fairybg)
        Type.FIGHTING -> PokemonRes(icon = R.drawable.fighting, background = R.drawable.fightingbg)
        Type.FIRE -> PokemonRes(icon = R.drawable.fire, background = R.drawable.firebg)
        Type.FLYING -> PokemonRes(icon = R.drawable.flying, background = R.drawable.flyingbg)
        Type.GHOST -> PokemonRes(icon = R.drawable.ghost, background = R.drawable.ghostbg)
        Type.GRASS -> PokemonRes(icon = R.drawable.grass, background = R.drawable.grassbg)
        Type.GROUND -> PokemonRes(icon = R.drawable.ground, background = R.drawable.groundbg)
        Type.ICE -> PokemonRes(icon = R.drawable.ice, background = R.drawable.icebg)
        Type.NORMAL -> PokemonRes(icon = R.drawable.normal, background = R.drawable.normalbg)
        Type.POISON -> PokemonRes(icon = R.drawable.poison, background = R.drawable.poisonbg)
        Type.PSYCHIC -> PokemonRes(icon = R.drawable.psychic, background = R.drawable.psychicbg)
        Type.ROCK -> PokemonRes(icon = R.drawable.rock, background = R.drawable.rockbg)
        Type.STEEL -> PokemonRes(icon = R.drawable.steel, background = R.drawable.steelbg)
        Type.WATER -> PokemonRes(icon = R.drawable.water, background = R.drawable.waterbg)
    }

data class PokemonRes(
    val icon: Int = R.drawable.normal,
    val background: Int = R.drawable.normalbg
)