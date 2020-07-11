package io.github.wertylop5.recyclerViewUtil

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import io.github.wertylop5.adapters.InfoAdapter
import io.github.wertylop5.model.Info

class InfoDetailsLookup(private val recyclerView: RecyclerView): ItemDetailsLookup<Info>() {
    override fun getItemDetails(e: MotionEvent): ItemDetails<Info>? {
        return recyclerView.findChildViewUnder(e.x, e.y)?.let {
            val holder = recyclerView.getChildViewHolder(it)

            return (holder as? InfoAdapter.InfoViewHolder)?.getItemDetails()
        }
    }
}