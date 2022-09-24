package mx.com.emv.menotes.presentation.addnote

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mx.com.emv.menotes.R
import mx.com.emv.menotes.databinding.AddNoteFragmentBinding

private const val NOTE_ID = "noteId"

class AddFragment : Fragment() {
    private var noteId: String? = null
    private var _binding: AddNoteFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            noteId = it.getString(NOTE_ID)
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
    }

    private fun setUpToolBar() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        if(noteId != null){
            binding.toolbar.title = resources.getString(R.string.edit_note_label)
        }else{
            binding.toolbar.title = resources.getString(R.string.new_note_label)
        }
        binding.toolbar.inflateMenu(R.menu.add_note_menu)
        binding.toolbar.menu.findItem(R.id.action_delete_note).isVisible = noteId != null
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.action_save_note -> {
                    true
                }
                R.id.action_delete_note -> {
                    true
                }
                else -> false
            }
        }
        binding.toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    companion object {
        val TAG = AddFragment::class.java.simpleName
        @JvmStatic
        fun newInstance(noteId: String?) =
            AddFragment().apply {
                arguments = Bundle().apply {
                    putString(NOTE_ID, noteId)
                }
            }
    }
}