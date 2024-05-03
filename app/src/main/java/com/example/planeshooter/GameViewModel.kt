package com.example.planeshooter

import androidx.lifecycle.ViewModel

class GameViewModel:ViewModel() {
    var life: Int = 3
    var points: Int = 0

    fun resetGame() {
        life = 3
        points = 0
    }

    fun decreaseLife() {
        life--
    }

    fun increasePoints(pointsToAdd: Int) {
        points += pointsToAdd
    }
}