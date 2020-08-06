package io.github.wertylop5.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import io.github.wertylop5.R
import io.github.wertylop5.model.Info

class InfoAdapter: RecyclerView.Adapter<InfoAdapter.InfoViewHolder>() {
    var infoElems: MutableList<Info> = ArrayList()
        private set

    lateinit var tracker: SelectionTracker<Info>

    class InfoViewHolder(
        val layout: LinearLayout,
        val key: TextView,
        val value: TextView,
        var infoPosition: Int,
        var infoData: Info?
    ): RecyclerView.ViewHolder(layout) {
        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Info>
                = object: ItemDetailsLookup.ItemDetails<Info>() {
            override fun getSelectionKey(): Info? = infoData

            override fun getPosition(): Int = infoPosition
        }

        fun bind(info: Info, position: Int, isActivated: Boolean = false) {
            key.text = info.key
            value.text = info.value

            infoPosition = position
            infoData = info
        }
    }

    override fun getItemCount(): Int {
        return infoElems.size
    }

    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        val data = infoElems[position]

        holder.bind(data, position, tracker.isSelected(data))
//        holder.key.text = infoElems[position].key
//        holder.value.text = infoElems[position].value
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val layout =
            inflater.inflate(R.layout.info_item, parent, false) as LinearLayout

        return InfoViewHolder(
            layout,
            layout.findViewById(R.id.info_key_text),
            layout.findViewById(R.id.info_value_text),
            -1,
            null
        )
    }

    fun setInfoElems(l: List<Info>) {
        infoElems.addAll(infoElems.size, l)

        notifyDataSetChanged()
    }

    fun insertInfoElem(i: Info) {
        infoElems.add(i)

        notifyDataSetChanged()
    }
}