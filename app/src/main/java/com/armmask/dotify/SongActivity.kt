package com.armmask.dotify
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentSong = intent.getParcelableExtra<Song>(SONG_KEY)
        songTitle.text = currentSong.title
        tvArtistNames.text = currentSong.artist


        randNum = Random.nextInt(0, 9999999)
        val tvPlayCount = findViewById<TextView>(R.id.tvPlayCount)
        tvPlayCount.text = "$randNum plays"
        val imgAlbum = findViewById<ImageView>(R.id.imgAlbumCover)
        imgAlbum.setImageResource(currentSong.largeImageID)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
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

    fun makeToast(str: String) {
        Toast.makeText(this, "Skipping to $str track", Toast.LENGTH_SHORT).show()
    }

    fun toastPrev(view: View) {
        makeToast("previous")
    }

    fun toastNext(view: View) {
        makeToast("next")
    }

    companion object {
        // Keys for intents
        const val SONG_KEY = "song_key"
        const val SONG_LIST_KEY = "song_list_key"

    }
}

