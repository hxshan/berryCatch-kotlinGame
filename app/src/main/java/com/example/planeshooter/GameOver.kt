package com.example.planeshooter

import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class GameOver : AppCompatActivity() {

    lateinit var textPoints:TextView;
    lateinit var textHighest:TextView
    lateinit var sharedPreferences: SharedPreferences;
    private var mediaPlayer:MediaPlayer?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)
        textPoints=findViewById(R.id.score)
        textHighest=findViewById(R.id.highScore)
        if(mediaPlayer==null){
            mediaPlayer=MediaPlayer.create(this,R.raw.game_over)
            mediaPlayer?.isLooping=false
        }

        if (!mediaPlayer?.isPlaying!!){
            mediaPlayer?.start()
        }
        var point = intent.extras?.getInt("points");
        textPoints.text = point.toString();
        sharedPreferences = getSharedPreferences("my_pref",0);
        var highest=sharedPreferences.getInt("highest",0)
        if (point != null) {
            if(point>highest){
                highest = point
                val editor = sharedPreferences.edit()
                editor.putInt("highest", highest)
                editor.apply()
            }
        }
        textHighest.text=highest.toString();
    }
    fun restart(view: View){
        val intent =Intent(this,StartGame::class.java)
        startActivity(intent);
        finish();
    }
    fun exit(view: View){
        finish();
    }
}