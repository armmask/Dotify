package com.armmask.dotify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.armmask.dotify.SongActivity.Companion.SONG_KEY
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.activity_song_list.*

class SongListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)

        title = "All Songs"

        val songList = mutableListOf<Song>()
        songList.addAll(SongDataProvider.getAllSongs())

        val songAdapter = SongListAdapter(songList)
        lateinit var currentSong: Song
        // Set on item Click for the adapter
        songAdapter.onSongClickListener = { someSong: Song ->
            currentSong = someSong
            songTitleBtn.text = someSong.title + " - " + someSong.artist
        }

        songAdapter.onSongLongClickListener = { currentSong: Song ->
            Toast.makeText(this, "${currentSong.title} deleted from your playlist", Toast.LENGTH_SHORT).show()
            songList.remove(currentSong)
            songAdapter.change(songList)
        }

        playButton.setOnClickListener {
            val intent = Intent(this, SongActivity::class.java)
            intent.putExtra(SONG_KEY, currentSong)
            startActivity(intent)
        }

        // endregion list

        btnChange.setOnClickListener {
            val newSongList = songList.apply {
                shuffle()
            }
            songAdapter.change(newSongList)
        }
        rvSong.adapter = songAdapter
    }
}
