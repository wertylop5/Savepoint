package io.github.wertylop5

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [EntryListFragment.OnItemClickListener] interface
 * to handle interaction events.
 * Use the [EntryListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EntryListFragment : Fragment() {
    private var listener: OnItemClickListener? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: RecyclerView.Adapter<*>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnItemClickListener

        if (listener == null) {
            throw ClassCastException("$context must implement OnItemClickListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_entry_list, container, false)

        viewManager = LinearLayoutManager(activity)
        viewAdapter = EntryAdapter(listOf(NoteEntry(title="big title", description = "Test")))
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
        listener = null
    }

    interface OnItemClickListener {
        fun onItemClick()
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
