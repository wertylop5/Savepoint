package io.github.wertylop5

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EntryAdapter(private val entries: List<Entry>) : RecyclerView.Adapter<EntryAdapter.EntryViewHolder>() {

    class EntryViewHolder(
        val layout: LinearLayout,
        val entryTitle: TextView,
        val entryImage: ImageView,
        val entryDescription: TextView) : RecyclerView.ViewHolder(layout)

    override fun getItemCount(): Int {
        return entries.size
    }

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        when (val data = entries[position]) {
            is NoteEntry -> {
                holder.entryTitle.text = data.title
                holder.entryDescription.text = data.description
            }
            is PictureEntry -> {
                holder.entryTitle.text = data.title
                holder.entryImage.setImageURI(data.picture.uri)
                holder.entryDescription.text = data.description
            }
            is MilestoneEntry -> {
                holder.entryTitle.text = data.name
                holder.entryImage.setImageURI(data.picture?.uri)
                holder.entryDescription.text = data.description
            }
            else -> {
                throw ClassCastException("Class is not a valid Entry type")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val layout =
            inflater.inflate(R.layout.entry_generic, parent, false) as LinearLayout

        val entryTitle = layout.findViewById<TextView>(R.id.entry_title)
        val entryImage = layout.findViewById<ImageView>(R.id.entry_image)
        val entryDescription = layout.findViewById<TextView>(R.id.entry_description)

        return EntryViewHolder(layout, entryTitle, entryImage, entryDescription)
    }
}