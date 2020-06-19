package io.github.wertylop5

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.github.wertylop5.model.Entry
import io.github.wertylop5.model.MilestoneEntryWithPhoto
import io.github.wertylop5.model.NoteEntry
import io.github.wertylop5.model.PictureEntryWithPhoto

class EntryAdapter: RecyclerView.Adapter<EntryAdapter.EntryViewHolder>() {

    private var entries: List<Entry> = emptyList()

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
                holder.entryImage.setImageURI(null)
                holder.entryDescription.text = data.description
            }
            is PictureEntryWithPhoto -> {
                holder.entryTitle.text = data.pictureEntry.title
                holder.entryImage.setImageURI(Uri.parse(data.picture?.uri))
                holder.entryDescription.text = data.pictureEntry.description
            }
            is MilestoneEntryWithPhoto -> {
                holder.entryTitle.text = data.milestoneEntry.name
                holder.entryImage.setImageURI(Uri.parse(data.picture?.uri))
                holder.entryDescription.text = data.milestoneEntry.description
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

        //Save references to each view, because findViewById is expensive
        val entryTitle = layout.findViewById<TextView>(R.id.entry_title)
        val entryImage = layout.findViewById<ImageView>(R.id.entry_image)
        val entryDescription = layout.findViewById<TextView>(R.id.entry_description)

        return EntryViewHolder(layout, entryTitle, entryImage, entryDescription)
    }

    fun setEntries(l: List<Entry>) {
        entries = l

        //necessary for observers
        notifyDataSetChanged()
    }
}