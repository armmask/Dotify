package com.armmask.dotify.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.armmask.dotify.OnSongClickListener
import com.armmask.dotify.R
import com.armmask.dotify.models.Song
import com.armmask.dotify.fragments.SongFragment
import com.armmask.dotify.fragments.SongListFragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main_frag_handler.*

class MainFragHandlerActivity : AppCompatActivity(), OnSongClickListener {

    private var currentSong: Song? = null;
    private lateinit var songList: MutableList<Song>
    private lateinit var songFragment: SongFragment
    private lateinit var songListFragment: SongListFragment

    companion object {
        val STATE_SONG = "currentSong"
        val STATE_LIST = "songList"
        val STATE_INT = "stackSize"
        val LINK = "https://raw.githubusercontent.com/echeeUW/codesnippets/master/musiclibrary.json"
        val TAG = "armmask"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_frag_handler)
        fetchWithVolley()
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
            fetchWithVolley()
        }

        playButton.setOnClickListener {
            createPlayer()
        }

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

    private fun fetchWithVolley() {
        val queue = Volley.newRequestQueue(this)
        var list = mutableListOf<Song>()
        val request = JsonObjectRequest(Request.Method.GET, LINK, null,
        Response.Listener { response ->
            val jsonArray = response.getJSONArray("songs")
            repeat(jsonArray.length()) { index ->
                val songJson = jsonArray.getJSONObject(index)
                Log.i(TAG, songJson.toString())
                val gson = Gson()
                val song = gson.fromJson(songJson.toString(), Song::class.java)
                list.add(song)
            }
            songList = list
            createPlayList()
        },
        Response.ErrorListener {error ->
            Toast.makeText(this, error.toString(), Toast.LENGTH_LONG)
        })
        queue.add(request)
    }


}
