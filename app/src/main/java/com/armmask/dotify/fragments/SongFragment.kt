package com.armmask.dotify.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.armmask.dotify.R
import com.ericchee.songdataprovider.Song
import kotlin.random.Random
import kotlinx.android.synthetic.main.fragment_song.*

/**
 * A simple [Fragment] subclass.
 */
class SongFragment : Fragment() {

    companion object {
        val TAG: String = SongFragment::class.java.simpleName
        const val SONG_KEY = "song_key"
    }
    private var randNum = 0
    private  var currentSong: Song? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        arguments.let { it ->
            currentSong = it?.getParcelable<Song>(SONG_KEY)
        }
        return inflater.inflate(R.layout.fragment_song, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentSong?.let {
            songTitle.text = it.title
            tvArtistNames.text = it.artist
            imgAlbumCover.setImageResource(it.largeImageID!!)
        }
        randNum = Random.nextInt(0, 9999999)
        tvPlayCount.text = "$randNum plays"
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
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId == android.R.id.home) {
//            finish()
//            return true
//        }
//        return super.onOptionsItemSelected(item)
//    }




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
