package io.github.wertylop5

import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.widget.RecyclerView
import io.github.wertylop5.model.Entry

class EntryKeyProvider(private val recyclerView: RecyclerView): ItemKeyProvider<Entry>(SCOPE_MAPPED) {
    override fun getKey(position: Int): Entry? {
        return (recyclerView.adapter as? EntryAdapter)?.entries?.get(position)
    }

    override fun getPosition(key: Entry): Int {
        return (recyclerView.adapter as? EntryAdapter)?.entries?.indexOf(key) ?: -1
    }
}