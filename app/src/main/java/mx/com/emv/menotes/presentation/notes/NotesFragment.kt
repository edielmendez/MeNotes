package mx.com.emv.menotes.presentation.notes

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.com.emv.menotes.R
import mx.com.emv.menotes.data.Note
import mx.com.emv.menotes.data.local.repository.NoteLocalDataSource
import mx.com.emv.menotes.databinding.NotesFragmentBinding
import mx.com.emv.menotes.di.Injector
import mx.com.emv.menotes.presentation.addnote.AddNoteFragment
import mx.com.emv.menotes.presentation.ext.createFactory

class NotesFragment : Fragment() {
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
        setUpViews()
        setUpObservers()
        viewModel.fetchNotes()
    }

    private fun setUpObservers() {
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

    private fun showDialogError(error: String) {
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton("OK") { dialog, id ->
                    // User clicked OK button
                }
                setNegativeButton("CANCEL") { dialog, id ->
                    // User cancelled the dialog
                }
            }
            builder.setTitle("Mensaje")
            builder.setMessage(error)
            // Create the AlertDialog
            builder.create()
        }

        alertDialog?.show()

    }

    private fun updateData(notes: List<Note>) {
        ((binding.notesList.adapter) as? NoteAdapter)?.update(notes)
    }

    private fun showLoader(b: Boolean) {
        binding.progressBar.visibility = if(b) View.VISIBLE else View.GONE
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