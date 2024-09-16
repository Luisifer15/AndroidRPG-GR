package org.example

fun main() {
    //INSERT STATS HERE
    val hero = Hero(CharacterConfig.heroName, CharacterConfig.heroStats)
    val enemy = Enemy(CharacterConfig.enemyName, CharacterConfig.enemyStats)

    val game = Game(hero, enemy)
    game.start()
}