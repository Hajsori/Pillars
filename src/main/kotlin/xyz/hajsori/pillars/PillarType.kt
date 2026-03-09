package xyz.hajsori.pillars

class PillarType(private val _filePath: String, private val _width: Int, private val _height: Int) {
    fun getFile(): String {
        return _filePath
    }

    fun getWidth(): Int {
        return _width
    }

    fun getHeight(): Int {
        return _height
    }
}