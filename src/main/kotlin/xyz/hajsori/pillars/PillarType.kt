package xyz.hajsori.pillars

import java.io.File

class PillarType(private val _file: File, private val _width: Int, private val _height: Int) {
    fun getFile(): File {
        return _file
    }

    fun getWidth(): Int {
        return _width
    }

    fun getHeight(): Int {
        return _height
    }
}