package io.github.wertylop5.recyclerViewUtil

import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.widget.RecyclerView
import io.github.wertylop5.adapters.InfoAdapter
import io.github.wertylop5.model.Info

class InfoKeyProvider(private val recyclerView: RecyclerView): ItemKeyProvider<Info>(SCOPE_MAPPED) {
    override fun getKey(position: Int): Info? {
        return (recyclerView.adapter as? InfoAdapter)?.infoElems?.get(position)
    }

    override fun getPosition(key: Info): Int {
        return (recyclerView.adapter as? InfoAdapter)?.infoElems?.indexOf(key) ?: -1
    }
}