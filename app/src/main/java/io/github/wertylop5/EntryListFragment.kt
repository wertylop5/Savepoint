package io.github.wertylop5

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "EntryListFragment"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [EntryListFragment.OnEntryClickListener] interface
 * to handle interaction events.
 * Use the [EntryListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EntryListFragment : Fragment() {
    private var entryClickListener: OnEntryClickListener? = null
    private var createFragListener: OnCreateFragListener? = null
    private var TAG: String = EntryListFragment.javaClass.name

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: RecyclerView.Adapter<*>

    private var entries: MutableList<Entry> = ArrayList()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        entryClickListener = context as? OnEntryClickListener
        createFragListener = context as? OnCreateFragListener

        if (entryClickListener == null) {
            throw ClassCastException("$context must implement OnItemClickListener")
        }

        if (createFragListener == null) {
            throw ClassCastException("$context must implement OnCreateFragListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "created")
        createFragListener?.onCreateFrag();
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_entry_list, container, false)

        viewManager = LinearLayoutManager(activity)
        viewAdapter = EntryAdapter(entries)
        recyclerView = rootView.findViewById<RecyclerView>(R.id.main_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            this.adapter = viewAdapter
        }

        return rootView
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDetach() {
        super.onDetach()
        entryClickListener = null
    }

    fun initEntryList(entries: MutableList<Entry>) {
        this.entries.addAll(0, entries)
    }

    interface OnEntryClickListener {
        fun onEntryClick()
    }

    interface OnCreateFragListener {
        fun onCreateFrag()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EntryListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) = null
    }
}
