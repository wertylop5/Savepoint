package io.github.wertylop5

abstract class Entry

data class NoteEntry(
    val title: String? = null,
    val description: String,
    val info: HashMap<String, Any>? = null) : Entry()

data class PictureEntry(
    val title: String? = null,
    val description: String? = null,
    val picture: Photo) : Entry()

data class MilestoneEntry(
    val name: String? = null,
    val description: String,
    val picture: Photo? = null) : Entry()
