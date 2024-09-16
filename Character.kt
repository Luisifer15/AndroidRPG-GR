package org.example

data class CharacterStats(
    var hp: Double,
    var def: Double,
    var attack: Double
)

interface CharacterInterface {
    fun attack(opponent: Character): Double
    fun defend(damage: Double): Double
    fun heal(): Double
    fun chooseAction(opponent: Character): String
}

class Hero(name: String, stats: CharacterStats) : Character(name, stats)
class Enemy(name: String, stats: CharacterStats) : Character(name, stats)


abstract class Character(
    val name: String,
    var stats: CharacterStats
) : CharacterInterface {

    override fun attack(opponent: Character): Double {
        //randomized attack power
        val attackPower = stats.attack.toInt()
        return (10..attackPower).random().toDouble()
    }

    override fun defend(damage: Double): Double {
        //calculate actual damage taken
        val reducedDamage = damage - stats.def
        val actualDamage = if (reducedDamage > 0) reducedDamage else 0.0
        stats.hp -= actualDamage
        return actualDamage
    }

    override fun heal(): Double {
        //heal amount is also randomized
        val healAmount = (10..20).random().toDouble()
        stats.hp += healAmount
        return healAmount
    }

    fun isAlive(): Boolean = stats.hp > 0

    override fun chooseAction(opponent: Character): String {
        val hpPercentage = stats.hp / 100.0
        val opponentHpPercentage = opponent.stats.hp / 100.0

        return when {
            hpPercentage < 0.2 -> "heal" //if character hp is below 20%, it should heal
            opponentHpPercentage <= 0.1 -> "attack" //if opponent's hp is below 10%, it should attack
            hpPercentage < 0.5 -> listOf("heal", "defend").random() //if character hp is below 50%, it should choose between healing or defending
            else -> "attack" //default action is to attack
        }
    }
}
