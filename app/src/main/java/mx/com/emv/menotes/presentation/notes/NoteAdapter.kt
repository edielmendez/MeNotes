package mx.com.emv.menotes.presentation.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mx.com.emv.menotes.data.Note
import mx.com.emv.menotes.databinding.NoteItemBinding

class NoteAdapter(
    private var notes: List<Note>,
    private val itemCallback: (note: Note) -> Unit?
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.NoteViewHolder {
        val viewBinding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: NoteAdapter.NoteViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount() = notes.size

    fun update(list: List<Note>){
        notes = list
        notifyDataSetChanged()
    }


    inner class NoteViewHolder(private val viewBinding: NoteItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(note: Note) {
            viewBinding.tvTitle.text = note.title
            viewBinding.tvDescription.text = note.description.substring(0, note.description.length / 2) + "..."
            viewBinding.root.setOnClickListener {
                itemCallback(note)
            }
        }
    }

}