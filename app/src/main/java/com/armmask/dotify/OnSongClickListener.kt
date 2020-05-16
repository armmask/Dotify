package com.armmask.dotify

import com.armmask.dotify.models.Song

interface OnSongClickListener {
    fun onSongClicked(song: Song)
}