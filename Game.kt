package org.example
import java.util.Scanner

interface GameInterface {
    fun start()
}

class Game(
    val hero: Hero,
    val enemy: Enemy
) : GameInterface {

    private fun performAction(character: Character, opponent: Character, action: String) {
        if (character.isAlive()) {
            when (action) {
                "attack" -> {
                    val attackDamage = character.attack(opponent)
                    val diceRoll = (0..9).random()
                    val finalDamage = if (diceRoll == 9) {
                        println("Critical hit!")
                        attackDamage * 2
                    }else if (diceRoll == 0) {
                        println("Critical miss!")
                        0.0
                    }else if (opponent.chooseAction(character) == "defend") {
                        println("${opponent.name} defends against ${character.name}'s attack.")
                        opponent.defend(attackDamage)
                    } else {
                        opponent.stats.hp -= attackDamage
                        attackDamage
                    }
                    println("${character.name} attacks ${opponent.name} for $finalDamage damage.")
                }
                "defend" -> {
                    println("${character.name} is defending.")
                }
                "heal" -> {
                    val healAmount = character.heal()
                    println("${character.name} heals for $healAmount HP.")
                }
            }
        }
    }

    override fun start() {
        val scanner = Scanner(System.`in`)
        var turn = 1
        println("The battle begins!")
        println("${hero.name} vs ${enemy.name}")

        while (hero.isAlive() && enemy.isAlive()) {
            Thread.sleep(1000)

            var heroAction: String
            while (true) {
                println("\nChoose an action for ${hero.name}: 1 - Attack, 2 - Defend, 3 - Heal")
                heroAction = when (scanner.nextInt()) {
                    1 -> "attack"
                    2 -> "defend"
                    3 -> "heal"
                    else -> {
                        println("Invalid choice. Please enter 1, 2, or 3.")
                        continue
                    }
                }
                break
            }
            val enemyAction = enemy.chooseAction(hero)

            // Hero's turn

            println("\nTurn $turn: ${hero.name}'s turn")
            performAction(hero, enemy, heroAction)
            println("${hero.name} HP: ${hero.stats.hp} | ${enemy.name} HP: ${enemy.stats.hp}")
            turn++

            Thread.sleep(1000)

            // Enemy's turn

            println("\nTurn $turn: ${enemy.name}'s turn")
            performAction(enemy, hero, enemyAction)
            println("${hero.name} HP: ${hero.stats.hp} | ${enemy.name} HP: ${enemy.stats.hp}")
            turn++


            if (!hero.isAlive()) {
                println("${hero.name} has been defeated!")
                break
            }

            if (!enemy.isAlive()) {
                println("${enemy.name} has been defeated!")
                break
            }


        }

        if (hero.isAlive()) {
            println("${hero.name} wins!")
        } else if (enemy.isAlive()) {
            println("${enemy.name} wins!")
        }
    }
}