package mx.com.emv.menotes.presentation.addnote

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import mx.com.emv.menotes.R
import mx.com.emv.menotes.data.Note
import mx.com.emv.menotes.databinding.AddNoteFragmentBinding
import mx.com.emv.menotes.di.Injector
import mx.com.emv.menotes.presentation.common.MeNotesBaseFragment
import mx.com.emv.menotes.presentation.ext.createFactory

private const val NOTE_ID = "noteId"

class AddNoteFragment : MeNotesBaseFragment() {
    private var noteId: Int = 0
    private var _binding: AddNoteFragmentBinding? = null
    private val binding get() = _binding!!
    //private val viewModel: AddNoteViewModel by viewModels()
    private val viewModel by viewModels<AddNoteViewModel> {
        AddNoteViewModel( Injector.provideDataSource() ).createFactory()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            noteId = it.getInt(NOTE_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddNoteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolBar()
        setUpObservers()
        if (noteId != 0){ getNote(noteId) }
    }

    private fun getNote(id: Int) {
        viewModel.getNote(id)
    }

    override fun setUpToolBar() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbar.title = resources.getString(R.string.details_note_label)

        binding.toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun setUpObservers() {
        viewModel.uiState.observe(viewLifecycleOwner){
            when(it){
                is AddNoteUIState.Loading -> {
                    showLoader(it.value)
                }
                is AddNoteUIState.Success -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    back()
                }
                is AddNoteUIState.Error -> {
                    showDialogError(it.error)
                }
                is AddNoteUIState.ObtainedNote -> {
                    updateUI(it.note)
                }
            }
        }
    }

    private fun updateUI(note: Note) {
        binding.inputTitle.setText(note.title)
        binding.inputDesc.setText(note.description)
    }

    private fun back(){
        parentFragmentManager.popBackStack()
    }

    companion object {
        val TAG = AddNoteFragment::class.java.simpleName
        @JvmStatic
        fun newInstance(noteId: Int?) =
            AddNoteFragment().apply {
                arguments = Bundle().apply {
                    putInt(NOTE_ID, noteId ?: 0)
                }
            }
    }
}