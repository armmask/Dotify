package com.armmask.dotify
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var randNum = 0
    private var color = R.color.colorBlack
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        randNum = Random.nextInt(0, 9999999)
        val tvPlayCount = findViewById<TextView>(R.id.tvPlayCount)
        tvPlayCount.text = "$randNum plays"
        val imgAlbum = findViewById<ImageView>(R.id.imgAlbumCover)
    }



    fun changeUsername(view: View) {
        val tvUsername = findViewById<TextView>(R.id.tvUsername)
        val evUsername = findViewById<TextView>(R.id.evUsername)
        val btnChangeUser = findViewById<Button>(R.id.btnChangeUser)
        if (evUsername.visibility == View.VISIBLE) {
            val username = evUsername.text
            if (username.isBlank() || username.isEmpty()) {
                Toast.makeText(this, "Please enter a username",
                    Toast.LENGTH_SHORT).show()
                return
            }
            tvUsername.text = username
            evUsername.visibility = View.GONE
            tvUsername.visibility = View.VISIBLE
            btnChangeUser.text = "CHANGE USER"
        } else {
            evUsername.visibility = View.VISIBLE
            tvUsername.visibility = View.GONE
            btnChangeUser.text = "APPLY"
        }

    }

    fun incrPlayCount(view: View) {
        randNum++
        val tvPlayCount = findViewById<TextView>(R.id.tvPlayCount)
        tvPlayCount.text = "$randNum plays"
    }

    fun toastPrev(view: View) {
        Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_SHORT).show()
    }

    fun toastNext(view: View) {
        Toast.makeText(this, "Skipping to next track", Toast.LENGTH_SHORT).show()
    }
}
