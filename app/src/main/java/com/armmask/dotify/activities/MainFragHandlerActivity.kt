package com.armmask.dotify.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.armmask.dotify.OnSongClickListener
import com.armmask.dotify.R
import com.armmask.dotify.fragments.SongFragment
import com.armmask.dotify.fragments.SongListFragment
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.activity_main_frag_handler.*

class MainFragHandlerActivity : AppCompatActivity(), OnSongClickListener {

    lateinit var currentSong: Song;
    private lateinit var songList: MutableList<Song>
    private lateinit var songFragment: SongFragment
    private lateinit var songListFragment: SongListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_frag_handler)

        songList = mutableListOf<Song>()
        songList.addAll(SongDataProvider.getAllSongs())

        songListFragment = SongListFragment()
        val songListBundle = Bundle().apply {
            putParcelableArrayList(SongListFragment.SONG_LIST_KEY, ArrayList(songList))
        }
        songListFragment.arguments = songListBundle

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragContainer, songListFragment)
            .commit()

        playButton.setOnClickListener {
            playButton.visibility = View.GONE
            songFragment = SongFragment()
            val songBundle = Bundle().apply {
                putParcelable(SongFragment.SONG_KEY, currentSong)
            }
            songFragment.arguments = songBundle
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragContainer, songFragment)
                .addToBackStack(SongFragment.TAG)
                .commit()
        }

        // endregion list

        btnChange.setOnClickListener {
            songListFragment.listShuffler()
        }

        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
        }

    }

    private fun updateMiniPlayer(song: Song) {
        currentSong = song
        songTitleBtn.text = song.title + " - " + song.artist
    }

    override fun onSongClicked(song: Song) {
        updateMiniPlayer(song)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        playButton.visibility = View.VISIBLE
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            supportFragmentManager
                .beginTransaction()
                .remove(songFragment)
                .commit()
            playButton.visibility = View.VISIBLE
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun getSongFragment():SongFragment {
        return supportFragmentManager.findFragmentByTag(SongFragment.TAG) as SongFragment
    }


}
