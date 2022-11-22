package com.example.simplefirestore;

    //import android.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private ShowNoteActivity activity;
    private List<NoteModel> notesList;

    //public NoteAdapter() {

    //}

    public NoteAdapter(ShowNoteActivity activity, List<NoteModel> notesList) {
        this.activity = activity;
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_note, parent, false);

        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.title.setText(notesList.get(position).getTitle());
        holder.content.setText(notesList.get(position).getContent());

            }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView title, content;
        ImageView btnEdit, btnDelete;



        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvShowTitle);
            content = itemView.findViewById(R.id.tvShowContent);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);

            //title = itemView.findViewById(R.id.tvShowTitle);
            //content = itemView.findViewById(R.id.tvShowContent);

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateNote(getAdapterPosition());
                }
            });
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteNote();
                   // Toast.makeText(activity, "DELETE", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }



    private void updateNote(int position) {
        NoteModel note = notesList.get(position);
        // Creer un bundle pour envoyer les infos sur la page de modification

        Bundle bundle = new Bundle();
        bundle.putString("id", note.getId());
        bundle.putString("uTitle", note.getTitle());
        bundle.putString("uContent", note.getContent());

        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);

    }
    private void deleteNote() {
    }
}
