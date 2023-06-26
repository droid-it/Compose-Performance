package me.uditverma.sample.composeperformance.ui

data class Item(
    val desc: String,
    val id: Int
): Comparable<Item> {
    val imageUrl: String
        get() {
            return "https://picsum.photos/id/$id/400"
        }

    override fun compareTo(other: Item): Int {
        return this.id.compareTo(other.id)
    }
}
