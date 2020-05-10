package com.armmask.dotify.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.armmask.dotify.OnSongClickListener
import com.armmask.dotify.R
import com.armmask.dotify.SongListAdapter
import com.armmask.dotify.activities.MainFragHandlerActivity
import com.ericchee.songdataprovider.Song
import kotlinx.android.synthetic.main.fragment_song_list.*

class SongListFragment: Fragment() {

    private lateinit var songListAdapter: SongListAdapter
    private lateinit var currentSong: Song
    private var onSongClickListener: OnSongClickListener? = null
    private lateinit var songList: MutableList<Song>

    companion object {
        val TAG: String = SongFragment::class.java.simpleName
        const val SONG_LIST_KEY = "song_list_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            with(savedInstanceState) {
                songList = getParcelableArrayList<Song>(SONG_LIST_KEY) as MutableList<Song>
            }
        } else {
            var tempList: List<Song>? = null
            arguments?.let {
                tempList = it.getParcelableArrayList<Song>(SONG_LIST_KEY)
            }
            val immutableList = tempList
            if (immutableList != null) {
                songList = immutableList as MutableList<Song>
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnSongClickListener) {
            onSongClickListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_song_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        songListAdapter = SongListAdapter(songList)
        songListAdapter.onSongClickListener = { someSong: Song ->
            currentSong = someSong
            onSongClickListener?.onSongClicked(someSong)
        }

        songListAdapter.onSongLongClickListener = { currentSong: Song ->
            Toast.makeText(activity, "${currentSong.title} deleted from your playlist", Toast.LENGTH_SHORT).show()
            songList.remove(currentSong)
            songListAdapter.change(songList)

        }
        rvSong.adapter = songListAdapter
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState?.putParcelableArrayList(SONG_LIST_KEY, ArrayList(songList))
    }

    fun listShuffler() {
        songList.apply {
            shuffle()
        }
        songListAdapter.change(songList)
    }
}