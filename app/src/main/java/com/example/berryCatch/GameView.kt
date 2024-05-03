package com.example.berryCatch

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect
import android.media.MediaPlayer
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi
import java.util.Random

@RequiresApi(Build.VERSION_CODES.R)
class GameView(var gamecontext: Context) : View(gamecontext) {
    var background: Bitmap
    var ground: Bitmap
    var playerBitmap: Bitmap
    var player: Bitmap
    var rect: Rect
    var rectGround: Rect
    var gamehandler= Handler(Looper.getMainLooper())
    var runnable: Runnable
    var textPaint = Paint()
    var health = Paint()
    var random: Random
    var playerX: Float
    var playerY: Float
    var oldX = 0f
    var oldplayerX = 0f
    var fruits: ArrayList<Fruit>
    var viewModel = GameViewModel()
    var dWidth : Int
    var dHeight :Int
    var mediaPlayer:MediaPlayer?=null
    var gamemediaPlayer:MediaPlayer?=null
    var groundBitmap:Bitmap



    init {
        background = BitmapFactory.decodeResource(resources, R.drawable.clouds)
        groundBitmap = BitmapFactory.decodeResource(resources, R.drawable.ground1)
        ground= Bitmap.createScaledBitmap(groundBitmap,100,350,true)
        playerBitmap = BitmapFactory.decodeResource(resources, R.drawable.mouth)
        player=Bitmap.createScaledBitmap(playerBitmap,150,150,true)
        if(gamemediaPlayer==null){
            gamemediaPlayer=MediaPlayer.create(gamecontext,R.raw.berrycaught)
            gamemediaPlayer?.isLooping=false
        }

        if(mediaPlayer==null){
            mediaPlayer=MediaPlayer.create(gamecontext,R.raw.background_pookatori)
            mediaPlayer?.isLooping=true
        }

        if (!mediaPlayer?.isPlaying!!){
            mediaPlayer?.start()
        }

        val display = gamecontext.display
        val size = Point()
        if (display != null) {
            display.getSize(size)
        }

        dWidth = size.x
        dHeight = size.y

        rect = Rect(0, 0, dWidth, dHeight)
        rectGround = Rect(0, dHeight - ground.height, dWidth, dHeight)
//        handler = Handler(Looper.getMainLooper())
        runnable = Runnable { invalidate() }
        textPaint.color = Color.rgb(255, 165, 0)
        textPaint.textSize = 120f
        textPaint.textAlign = Paint.Align.LEFT
        health.color = Color.GREEN
        random = Random()
        playerX = dWidth.toFloat() / 2 - player.width.toFloat() / 2
        playerY = (dHeight - ground.height - player.height).toFloat()

        fruits = ArrayList()
        for (i in 0..2) {
            val fruit = Fruit(gamecontext,dWidth)
            fruits.add(fruit)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //draw the initial game screen
        canvas.drawBitmap(background, null, rect, null)
        canvas.drawBitmap(ground, null, rectGround, null)
        canvas.drawBitmap(player, playerX, playerY, null)

        //spawn in the strawberries
        for (i in fruits.indices) {
            canvas.drawBitmap(
                fruits[i].fruit,
                fruits[i].fruitX.toFloat(),
                fruits[i].fruitY.toFloat(),
                null
            )
            fruits[i].fruitY += fruits[i].velocity
            //check if player catches the fruit
            if (fruits[i].fruitX + fruits[i].fruitWidth >= playerX && fruits[i].fruitX <= playerX + player.width && fruits[i].fruitY + fruits[i].fruitWidth >= playerY && fruits[i].fruitY + fruits[i].fruitWidth <= playerY + player.height) {
                gamemediaPlayer?.start()
                viewModel.increasePoints(10)
                fruits[i].resetPosition()
            }
        }
        //checking for collisions with the ground
        for (i in fruits.indices) {
            if (fruits[i].fruitY + fruits[i].fruitHieght >= dHeight - ground.height) {
                viewModel.decreaseLife()
                fruits[i].resetPosition()
                if (viewModel.life == 0) {
                    val intent = Intent(gamecontext, GameOver::class.java)
                    intent.putExtra("points", viewModel.points)
                    mediaPlayer?.stop()
                    gamecontext.startActivity(intent)
                    (gamecontext as Activity).finish()
                }
            }
        }
        if (viewModel.life == 2) {
            health.color = Color.YELLOW
        }
        if (viewModel.life == 1) {
            health.color = Color.RED
        }
        canvas.drawRect(
            (dWidth - 200).toFloat(),
            30f,
            (dWidth - 200 + 60 * viewModel.life).toFloat(),
            80f,
            health
        )
        canvas.drawText("" + viewModel.points, 20f, 120f, textPaint)
        gamehandler.postDelayed(runnable, 30)
    }
//player movement
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchX = event.x
        val touchY = event.y
        if (touchY >= playerY) {
            val action = event.action
            if (action == MotionEvent.ACTION_DOWN) {
                oldX = event.x
                oldplayerX = playerX
            }
            if (action == MotionEvent.ACTION_MOVE) {
                val shift = oldX - touchX
                val newplayerX = oldplayerX - shift
                playerX = if (newplayerX <= 0) {
                    0f
                } else if (newplayerX >= dWidth - player.width) {
                    (dWidth - player.width).toFloat()
                } else newplayerX
            }
        }
        return true
    }


}
