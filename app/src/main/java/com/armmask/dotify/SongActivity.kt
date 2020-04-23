package com.armmask.dotify
import androidx.appcompat.app.AppCompatActivity
import com.armmask.dotify.R
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.ericchee.songdataprovider.Song
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class SongActivity : AppCompatActivity() {
    private var randNum = 0
    private var color = R.color.colorBlack
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentSong = intent.getParcelableExtra<Song>(SONG_KEY)
        val stitle = findViewById<TextView>(R.id.songTitle)
        songTitle.text = currentSong.title
        tvArtistNames.text = currentSong.artist


        randNum = Random.nextInt(0, 9999999)
        val tvPlayCount = findViewById<TextView>(R.id.tvPlayCount)
        tvPlayCount.text = "$randNum plays"
        val imgAlbum = findViewById<ImageView>(R.id.imgAlbumCover)
        imgAlbum.setImageResource(currentSong.largeImageID)
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

    companion object {
        // Keys for intents
        const val SONG_KEY = "song_key"

    }
}

