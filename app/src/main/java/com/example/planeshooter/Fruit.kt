package com.example.planeshooter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.Random

class Fruit(context: Context,private val dWidth:Int) {
    lateinit var fruit: Bitmap
    var velocity = 0
    var fruitX = 0
    var fruitY = 0
    var random: Random
    var fruittype:Int;

    init {

        random = Random()
        fruittype=random.nextInt(4)
        when(fruittype) {
            0-> fruit = BitmapFactory.decodeResource(context.resources, R.drawable.sprite_16)
            1-> fruit = BitmapFactory.decodeResource(context.resources, R.drawable.sprite_04)
            2-> fruit = BitmapFactory.decodeResource(context.resources, R.drawable.sprite_00)
            3-> fruit = BitmapFactory.decodeResource(context.resources, R.drawable.sprite_10)
        }
               resetPosition()
    }
    val fruitWidth: Int
        get() = fruit.width
    val fruitHieght: Int
        get() = fruit.width


    fun resetPosition() {
        fruitX = random.nextInt(dWidth -fruitWidth)
        fruitY = -200 + random.nextInt(600) * -1
        velocity = 15 + random.nextInt(16)
    }
}
