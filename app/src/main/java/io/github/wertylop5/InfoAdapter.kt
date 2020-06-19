package io.github.wertylop5

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.github.wertylop5.model.Info

class InfoAdapter: RecyclerView.Adapter<InfoAdapter.InfoViewHolder>() {
    private var infoElems: List<Info> = emptyList()

    class InfoViewHolder(
        val layout: LinearLayout,
        val key: TextView,
        val value: TextView
    ): RecyclerView.ViewHolder(layout)

    override fun getItemCount(): Int {
        return infoElems.size
    }

    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        holder.key.text = infoElems[position].key
        holder.value.text = infoElems[position].value
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val layout =
            inflater.inflate(R.layout.info_item, parent, false) as LinearLayout

        return InfoViewHolder(
            layout,
            layout.findViewById(R.id.info_key_text),
            layout.findViewById(R.id.info_value_text)
        )
    }

    fun setInfoElems(l: List<Info>) {
        infoElems = l

        notifyDataSetChanged()
    }
}