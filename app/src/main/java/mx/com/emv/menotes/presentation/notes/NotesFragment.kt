package mx.com.emv.menotes.presentation.notes

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import mx.com.emv.menotes.R
import mx.com.emv.menotes.data.Note
import mx.com.emv.menotes.databinding.NotesFragmentBinding
import mx.com.emv.menotes.di.Injector
import mx.com.emv.menotes.presentation.addnote.AddNoteFragment
import mx.com.emv.menotes.presentation.common.MeNotesBaseFragment
import mx.com.emv.menotes.presentation.ext.createFactory

class NotesFragment : MeNotesBaseFragment() {
    private var _binding: NotesFragmentBinding? = null
    private val binding get() = _binding!!
    //private val viewModel: NotesViewModel by viewModels()
    private val viewModel by viewModels<NotesViewModel> {
        NotesViewModel( Injector.provideDataSource() ).createFactory()
    }
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
        StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL).apply {
            binding.notesList.layoutManager = this
        }
        setUpToolBar()
        setUpListeners()
        setUpViews()
        setUpObservers()
    }

    override fun setUpObservers() {
        viewModel.uiState.observe(viewLifecycleOwner){
            when(it){
                is NotesUIState.Loading -> {
                    showLoader(it.value)
                }
                is NotesUIState.Empty -> {
                    binding.tvWithoutNotes.visibility = View.VISIBLE
                }
                is NotesUIState.Success -> {
                    binding.tvWithoutNotes.visibility = View.GONE
                    updateData(it.notes)
                }
                is NotesUIState.Error -> {
                    showDialogError(it.error)
                }
            }
        }
    }

    private fun updateData(notes: List<Note>) {
        ((binding.notesList.adapter) as? NoteAdapter)?.update(notes)
    }


    private fun setUpViews() {
        binding.notesList.adapter = NoteAdapter(emptyList()){
            goToEditNote(it.id)
        }
    }

    private fun goToEditNote(id: Int?) {
        parentFragmentManager.commit {
            replace(R.id.fragmentContainerView, AddNoteFragment.newInstance(id), AddNoteFragment.TAG)
            addToBackStack(AddNoteFragment.TAG)
        }
    }

    private fun setUpListeners() {
        binding.btnAddNote.setOnClickListener {
            goToEditNote(null)
        }
    }

    override fun setUpToolBar() {
        binding.toolbar.title = resources.getString(R.string.notes_fragment_title)
        binding.toolbar.inflateMenu(R.menu.notes_menu)
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.app_bar_search -> {
                    //filterNotes()
                    true
                }
                else -> false
            }
        }
        val search = binding.toolbar.menu.findItem(R.id.app_bar_search)
        val searchView = search.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                filterNotes(newText ?: "")
                return true
            }
        })
    }

    private fun filterNotes(wordToSearch: String){
        if(wordToSearch.isNullOrEmpty()){
            ((binding.notesList.adapter) as? NoteAdapter)?.clearFilter()
        }else{
            ((binding.notesList.adapter) as? NoteAdapter)?.filter(wordToSearch)
        }
    }

    companion object {
        val TAG = NotesFragment::class.java.simpleName
        @JvmStatic
        fun newInstance() = NotesFragment()
    }
}