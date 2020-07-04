package io.github.wertylop5

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import io.github.wertylop5.model.Entry
import io.github.wertylop5.model.MilestoneEntryWithPhoto
import io.github.wertylop5.model.NoteEntry
import io.github.wertylop5.model.PictureEntryWithPhoto

class EntryAdapter(/*private val tracker: SelectionTracker<Entry>*/)//,
                   //val entryClickListener: EntryClickListener)
    : RecyclerView.Adapter<EntryAdapter.EntryViewHolder>() {
    private val TAG: String = EntryAdapter::class.java.name

    var entries: List<Entry> = emptyList()
        private set

    lateinit var tracker: SelectionTracker<Entry>

    class EntryViewHolder(
        val layout: LinearLayout,
        val entryTitle: TextView,
        val entryImage: ImageView,
        val entryDescription: TextView,
        var entryPosition: Int,
        var entryData: Entry?
    ) : RecyclerView.ViewHolder(layout) {
        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Entry>
                = object: ItemDetailsLookup.ItemDetails<Entry>() {
            override fun getSelectionKey(): Entry? = entryData

            override fun getPosition(): Int = entryPosition
        }

        fun bind(entry: Entry, position: Int, isActivated: Boolean = false) {//,
                 //clicklistener: EntryClickListener) {
            entryPosition = position
            entryData = entry

            when (entry) {
                is NoteEntry -> {
                    entryTitle.text = entry.title
                    entryImage.setImageURI(null)
                    entryDescription.text = entry.description
                }
                is PictureEntryWithPhoto -> {
                    entryTitle.text = entry.pictureEntry.title
                    entryImage.setImageURI(Uri.parse(entry.picture?.uri))
                    entryDescription.text = entry.pictureEntry.description
                }
                is MilestoneEntryWithPhoto -> {
                    entryTitle.text = entry.milestoneEntry.name
                    entryImage.setImageURI(Uri.parse(entry.picture?.uri))
                    entryDescription.text = entry.milestoneEntry.description
                }
                else -> {
                    throw ClassCastException("Class is not a valid Entry type")
                }
            }

//            layout.setOnClickListener {
//                clicklistener.onEntryClick((entry))
//            }
            layout.isActivated = isActivated
        }
    }

    override fun getItemCount(): Int {
        return entries.size
    }

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        val data = entries[position]

        holder.bind(data, position, tracker.isSelected(data))//, entryClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val layout =
            inflater.inflate(R.layout.entry_generic, parent, false) as LinearLayout

        //Save references to each view, because findViewById is expensive
        val entryTitle = layout.findViewById<TextView>(R.id.entry_title)
        val entryImage = layout.findViewById<ImageView>(R.id.entry_image)
        val entryDescription = layout.findViewById<TextView>(R.id.entry_description)

        return EntryViewHolder(layout, entryTitle, entryImage,
            entryDescription, -1, null)
    }

    fun setEntries(l: List<Entry>) {
        entries = l

        //necessary for observers
        notifyDataSetChanged()
    }

//    interface EntryClickListener {
//        fun onEntryClick(entry: Entry)
//    }
}