package io.github.wertylop5

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import io.github.wertylop5.model.Entry

class EntryDetailsLookup(private val recyclerView: RecyclerView): ItemDetailsLookup<Entry>() {
    override fun getItemDetails(e: MotionEvent): ItemDetails<Entry>? {
        return recyclerView.findChildViewUnder(e.x, e.y)?.let {
            val holder = recyclerView.getChildViewHolder(it)

            return (holder as? EntryAdapter.EntryViewHolder)?.getItemDetails()
        }
    }
}