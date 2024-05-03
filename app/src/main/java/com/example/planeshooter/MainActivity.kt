package com.example.planeshooter

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity() {
    private var mediaPlayer:MediaPlayer?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mediaPlayer=MediaPlayer.create(this,R.raw.background_music_chipmode)
        mediaPlayer?.isLooping=true
        mediaPlayer?.start()
    }

    fun  startGame(v:View){
       // Log.d("playbtn", "startGame: ")
        val intent =Intent(this,StartGame::class.java)
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer=null
        startActivity(intent)
        finish()
    }

}