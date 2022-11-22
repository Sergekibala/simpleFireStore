package com.example.simplefirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowNoteActivity extends AppCompatActivity {

    private static final String TAG = "ShowNoteActivity";

    private RecyclerView rvShowNotes;
    private FirebaseFirestore db;
    private NoteAdapter adapter;
    private List<NoteModel> notesList;

    private void initUI(){
        rvShowNotes = findViewById(R.id.rvShowAllNotes);
        rvShowNotes.hasFixedSize();
                rvShowNotes.setLayoutManager(new LinearLayoutManager(this));

    }

    private void initFirestore(){
        db = FirebaseFirestore.getInstance();

    }

    private void initAdapter(){
        notesList = new ArrayList<>();
        adapter = new NoteAdapter(this, notesList);
        rvShowNotes.setAdapter(adapter);
    }

    public void readDateFromFirestore(){
        db.collection("notes").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        notesList.clear();
                        for (DocumentSnapshot documentSnapshot:task.getResult()){
                            NoteModel noteModel = new NoteModel(
                                    documentSnapshot.getString("id"),
                                    documentSnapshot.getString("title"),
                                    documentSnapshot.getString("content"));


                            notesList.add(noteModel);
                            Log.i(TAG, "onComplete: " + documentSnapshot.getId());

                        }
                        adapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Toast.makeText(sho)
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);

initUI();
initFirestore();
initAdapter();
readDateFromFirestore();

    }
}