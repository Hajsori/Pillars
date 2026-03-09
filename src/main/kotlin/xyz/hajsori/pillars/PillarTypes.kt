package xyz.hajsori.pillars

enum class PillarTypes(private val filePath: String, private val width: Int, private val height: Int) {
    STANDARD("pillars/standard.nbt", 1, 32),
    CACTUS("pillars/cactus.nbt", 1, 6),
    CAGE("pillars/cage.nbt", 3, 1);

    fun get(plugin: Pillars): PillarType {
        return PillarType(
            filePath,
            width,
            height
        )
    }

    companion object {
        fun random(): PillarTypes {
            return entries.random()
        }
    }
}