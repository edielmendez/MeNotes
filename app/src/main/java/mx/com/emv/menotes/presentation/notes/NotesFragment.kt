package mx.com.emv.menotes.presentation.notes

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.com.emv.menotes.R
import mx.com.emv.menotes.data.MockData
import mx.com.emv.menotes.databinding.NotesFragmentBinding
import mx.com.emv.menotes.presentation.addnote.AddFragment

class NotesFragment : Fragment() {
    private var _binding: NotesFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = NotesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GridLayoutManager(
            requireContext(),
            2,
            RecyclerView.VERTICAL,
            false
        ).apply {
            binding.notesList.layoutManager = this
        }
        /*StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL).apply {
            binding.notesList.layoutManager = this
        }*/
        setUpToolBar()
        setUpListeners()
        binding.notesList.adapter = NoteAdapter(MockData.fakeNotes){
            goToEditNote(it.title)
        }
    }

    private fun goToEditNote(id: String?) {
        parentFragmentManager.commit {
            replace(R.id.fragmentContainerView, AddFragment.newInstance(id), AddFragment.TAG)
            addToBackStack(AddFragment.TAG)
        }
    }

    private fun setUpListeners() {
        binding.btnAddNote.setOnClickListener {
            goToEditNote(null)
        }
    }

    private fun setUpToolBar() {
        binding.toolbar.title = resources.getString(R.string.notes_fragment_title)
        binding.toolbar.inflateMenu(R.menu.notes_menu)
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.app_bar_search -> {
                    filterNotes()
                    true
                }
                else -> false
            }
        }
    }

    private fun filterNotes() {
        // TODO: Filters notes
    }

    /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.notes_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //return super.onOptionsItemSelected(item)
        return when (item.itemId) {
            R.id.add_note -> {
                true
            }
            R.id.app_bar_search -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }*/

    companion object {
        val TAG = NotesFragment::class.java.simpleName
        @JvmStatic
        fun newInstance() = NotesFragment()
    }
}