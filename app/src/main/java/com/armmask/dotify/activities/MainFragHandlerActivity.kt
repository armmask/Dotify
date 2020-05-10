package com.armmask.dotify.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

    private var currentSong: Song? = null;
    private lateinit var songList: MutableList<Song>
    private lateinit var songFragment: SongFragment
    private lateinit var songListFragment: SongListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_frag_handler)

        if (savedInstanceState != null) {
            var tempSong: Song? = null
            var tempList: List<Song>? = null
            with(savedInstanceState) {
                tempSong = getParcelable(STATE_SONG)
                tempList = getParcelableArrayList(STATE_LIST)
            }
            tempSong?.let {
                val tempSong = it
                currentSong = tempSong
                updateMiniPlayer(tempSong)
            }
            tempList?.let {
                songList = it as MutableList<Song>
            }
        } else {
            songList = mutableListOf<Song>()
            songList.addAll(SongDataProvider.getAllSongs())
            createPlayList()
        }



        playButton.setOnClickListener {
            createPlayer()
        }

        // endregion list

        btnChange.setOnClickListener {
            getSongListFragment().listShuffler()
        }

        supportFragmentManager.addOnBackStackChangedListener {
            Log.i("armmask", supportFragmentManager.backStackEntryCount.toString())
            enablePlayerBack()
        }

        enablePlayerBack()

    }


    override fun onSongClicked(song: Song) {
        updateMiniPlayer(song)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            supportFragmentManager.popBackStack()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState?.run {
            putParcelable(STATE_SONG, currentSong)
            putParcelableArrayList(STATE_LIST, ArrayList(songList))
            Log.i("armmask", supportFragmentManager.backStackEntryCount.toString())
        }
    }

    private fun getSongFragment():SongFragment {
        return supportFragmentManager.findFragmentByTag(SongFragment.TAG) as SongFragment
    }

    private fun getSongListFragment():SongListFragment {
        return supportFragmentManager.findFragmentByTag(SongListFragment.TAG) as SongListFragment
    }

    private fun createPlayer() {
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

    private fun createPlayList() {
        songListFragment = SongListFragment()
        val songListBundle = Bundle().apply {
            putParcelableArrayList(SongListFragment.SONG_LIST_KEY, ArrayList(songList))
        }
        songListFragment.arguments = songListBundle

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragContainer, songListFragment, SongListFragment.TAG)
            .commit()
    }

    private fun updateMiniPlayer(song: Song) {
        currentSong = song
        songTitleBtn.text = song.title + " - " + song.artist
    }

    private fun enablePlayerBack() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            playButton.visibility = View.GONE
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            playButton.visibility = View.VISIBLE
        }
    }

    companion object {
        val STATE_SONG = "currentSong"
        val STATE_LIST = "songList"
        val STATE_INT = "stackSize"
    }


}
