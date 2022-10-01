package mx.com.emv.menotes.presentation.addnote

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import mx.com.emv.menotes.R
import mx.com.emv.menotes.data.Importance
import mx.com.emv.menotes.data.Note
import mx.com.emv.menotes.databinding.AddNoteFragmentBinding
import mx.com.emv.menotes.di.Injector
import mx.com.emv.menotes.presentation.ext.createFactory
import mx.com.emv.menotes.presentation.notes.NotesUIState
import mx.com.emv.menotes.presentation.notes.NotesViewModel

private const val NOTE_ID = "noteId"

class AddNoteFragment : Fragment() {
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
        //noteId?.let { getNote(it) }
    }

    private fun getNote(id: Int) {
        viewModel.getNote(id)
    }

    private fun setUpToolBar() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        if(noteId != 0){
            binding.toolbar.title = resources.getString(R.string.edit_note_label)
        }else{
            binding.toolbar.title = resources.getString(R.string.new_note_label)
        }
        binding.toolbar.inflateMenu(R.menu.add_note_menu)
        binding.toolbar.menu.findItem(R.id.action_delete_note).isVisible = noteId != null
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.action_save_note -> {
                    saveNote()
                    true
                }
                R.id.action_delete_note -> {
                    deleteNote()
                    true
                }
                else -> false
            }
        }
        binding.toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun deleteNote() {
        if(noteId != 0) showConfirmDialog()
    }

    private fun showConfirmDialog() {
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton("ELIMINAR") { dialog, id ->
                    viewModel.deleteNote(noteId)
                }
                setNegativeButton("CANCELAR"){ dialog, id ->
                    dialog.dismiss()
                }
            }
            builder.setTitle("Mensaje")
            builder.setMessage(resources.getString(R.string.confirm_delete_note_label))
            // Create the AlertDialog
            builder.create()
        }
        alertDialog?.show()
    }

    private fun saveNote() {
        val title = binding.inputTitle.text.toString()
        val desc = binding.inputDesc.text.toString()
        if(title.isNullOrEmpty()){
            binding.layoutTitle.error = "Introduzca el titulo de la nota"
            return
        }
        if(desc.isNullOrEmpty()){
            binding.layoutDesc.error = "Introduzca la descripción de la nota"
            return
        }
        //val id = noteId?.let { it.toInt() } ?: 0
        viewModel.saveNote(Note(id = noteId, title = title, description = desc, importance = Importance.HIGH.value))
    }

    private fun setUpObservers() {
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

    private fun showLoader(b: Boolean) {
        binding.progressBar.visibility = if(b) View.VISIBLE else View.GONE
    }

    private fun showDialogError(error: String) {
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton("OK") { dialog, id ->
                    // User clicked OK button
                }
            }
            builder.setTitle("Mensaje")
            builder.setMessage(error)
            // Create the AlertDialog
            builder.create()
        }
        alertDialog?.show()
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