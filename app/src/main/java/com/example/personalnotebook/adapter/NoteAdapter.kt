package com.example.personalnotebook.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.personalnotebook.databinding.NoteItemBinding
import com.example.personalnotebook.model.Note

class NoteAdapter(
    private val onClick: (Note, Int) -> Unit
) : ListAdapter<Note, NoteViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            NoteItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onClick = onClick
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }

        }
    }
}

class NoteViewHolder(
    private val binding: NoteItemBinding,
    private val onClick: (Note, Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Note) {
        binding.tvTitle.text = item.title
        binding.tvDescription.text = item.description
        binding.root.setOnLongClickListener {
            onClick(item, BUTTON_DELETE)
            true
        }
        binding.root.setOnClickListener {
            onClick(item, BUTTON_ROOT)
        }
    }

    companion object {
        const val BUTTON_ROOT = 0
        const val BUTTON_DELETE = 1
    }
}
