package com.example.planeshooter

import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi

class StartGame: Activity() {

    private lateinit var gameView: GameView
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameView=GameView(this)
        setContentView(gameView)
    }
}