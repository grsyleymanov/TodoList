package ru.itlab.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private List<Note> notes;
    private INoteClick noteClickListener;

    public NotesAdapter(){
        notes = new ArrayList<>();
    }

    public void setData(List<Note> notes){
        this.notes = notes;
        notifyDataSetChanged();
    }

    public void setOnNoteClickListener(INoteClick noteClickListener){
        this.noteClickListener = noteClickListener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.note,
                parent,
                false
        );
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.textViewNote.setOnClickListener(view -> {
            noteClickListener.onNoteClick(notes.get(position));
        });
        holder.textViewNote.setText(note.text);
        switch (note.priority) {
            case 0:
                holder.textViewNote.setBackgroundColor(holder.textViewNote.getResources().getColor(R.color.green));
                break;
            case 1:
                holder.textViewNote.setBackgroundColor(holder.textViewNote.getResources().getColor(R.color.yellow));
                break;
            case 2:
                holder.textViewNote.setBackgroundColor(holder.textViewNote.getResources().getColor(R.color.red));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewNote;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNote = itemView.findViewById(R.id.note);
        }
    }
}
