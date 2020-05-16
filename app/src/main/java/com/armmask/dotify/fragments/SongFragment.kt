package com.armmask.dotify.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.armmask.dotify.R
import com.armmask.dotify.models.Song
import kotlin.random.Random
import kotlinx.android.synthetic.main.fragment_song.*
import com.squareup.picasso.Picasso

/**
 * A simple [Fragment] subclass.
 */
class SongFragment : Fragment() {
    private var randNum = 0
    private  var currentSong: Song? = null

    companion object {
        val TAG: String = SongFragment::class.java.simpleName
        const val SONG_KEY = "song_key"
        const val RAND_NUM = "randNum"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            with(savedInstanceState) {
                randNum = getInt(RAND_NUM)
            }
        } else {
            randNum = Random.nextInt(0, 9999999)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments.let { it ->
            currentSong = it?.getParcelable<Song>(SONG_KEY)
        }
        return inflater.inflate(R.layout.fragment_song, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentSong?.let {
            updateSong(it)
        }
        imgbtnPrev.setOnClickListener{v: View? ->
            v?.let {
                tPrev(it)
            }
        }
        imgbtnNext.setOnClickListener{v: View? ->
            v?.let {
                tNext(it)
            }
        }
        imgbtnPlay.setOnClickListener{v: View? ->
            v?.let {
                incrementPlayCount(v)
            }
        }
    }

    fun updateSong(it: Song) {
        songTitle.text = it.title
        tvArtistNames.text = it.artist
        Picasso.get().load(it.largeImageURL).into(imgAlbumCover);
        tvPlayCount.text = "$randNum plays"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(RAND_NUM, randNum)
    }



    fun incrementPlayCount(view: View) {
        randNum++
        tvPlayCount.text = "$randNum plays"
    }

    private fun makeToast(str: String) {
        Toast.makeText(activity, "Skipping to $str track", Toast.LENGTH_SHORT).show()
    }

    fun tPrev(view: View) {
        makeToast("previous")
    }

    fun tNext(view: View) {
        makeToast("next")
    }

}
