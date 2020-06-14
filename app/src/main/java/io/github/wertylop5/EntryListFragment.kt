package io.github.wertylop5

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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
    private var TAG: String = EntryListFragment::class.java.name

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: EntryAdapter//RecyclerView.Adapter<*>

    private lateinit var viewModel: NoteEntryViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        entryClickListener = context as? OnEntryClickListener

        if (entryClickListener == null) {
            throw ClassCastException("$context must implement OnItemClickListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "created")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_entry_list, container, false)

        viewManager = LinearLayoutManager(activity)
        viewAdapter = EntryAdapter()
        recyclerView = rootView.findViewById<RecyclerView>(R.id.main_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        viewModel = ViewModelProvider(this).get(NoteEntryViewModel::class.java)
        viewModel.noteEntries.observe(viewLifecycleOwner, Observer {
            it?.let {
                //causes "it" to no longer be a platform type
                viewAdapter.setEntries(it)
            }
        })

        return rootView
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDetach() {
        super.onDetach()
        entryClickListener = null
    }

    interface OnEntryClickListener {
        fun onEntryClick()
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
