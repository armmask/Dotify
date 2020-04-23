package com.armmask.dotify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil

import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song

class SongListAdapter(songListInitial: List<Song>): RecyclerView.Adapter<SongListAdapter.SongListViewHolder>() {

    private var songList: List<Song> = songListInitial.toList()  // This is so we create a duplicate of the list passed in
    var onSongClickListener: ((song: Song) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return SongListViewHolder(view)
    }

    override fun getItemCount() = songList.size

    override fun onBindViewHolder(holder: SongListViewHolder, position: Int): Unit {
        val song = songList[position]
        holder.bind(song, position)
    }

//    fun change(newSongList: List<Song>) {
//        // Normal way up applying updates to list
////        listOfPeople = newPeople
////        notifyDataSetChanged()
//
//        // Animated way of applying updates to list
//        val callback = SongDiffCallback(songList, newSongList)
//        val diffResult = DiffUtil.calculateDiff(callback)
//        diffResult.dispatchUpdatesTo(this)
//
//        // We update the list
//        songList = newSongList
//
//
//    }

    inner class SongListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val songTitle = itemView.findViewById<TextView>(R.id.title)
        private val songArtist = itemView.findViewById<TextView>(R.id.artist)
        private val songAlbum = itemView.findViewById<ImageView>(R.id.album)

        fun bind(song: Song, position: Int) {
            songTitle.text = song.title
            songArtist.text = song.artist
            songAlbum.setImageResource(song.smallImageID)

            itemView.setOnClickListener {
                onSongClickListener?.invoke(song)
            }
        }
    }

}