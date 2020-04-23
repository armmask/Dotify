package com.armmask.dotify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        // Set on item Click for the adapter
        songAdapter.onSongClickListener = { someSong: Song ->

            val intent = Intent(this, SongActivity::class.java)
            intent.putExtra(SONG_KEY, someSong)
            startActivity(intent)


        }

        // endregion list

//        btnChange.setOnClickListener {
//            val newPeople = songList.apply {
//                removeAt(0)
//                removeAt(6)
//                removeAt(10)
//                removeAt(6)
//
//                shuffle()
//            }
//
//            songAdapter.change(newPeople)
//
//        }


        rvSong.adapter = songAdapter
    }
}
