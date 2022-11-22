package com.example.simplefirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private EditText etNoteTitle, etNoteContent;
    private Button btnSaveNote, btnShowNote;
    private TextView tvNoteState;
    private FirebaseFirestore db;

    private void initUI() {
        etNoteTitle = findViewById(R.id.etNoteTitle);
        etNoteContent = findViewById(R.id.etNoteContent);
        btnSaveNote = findViewById(R.id.btnSave);
        btnShowNote = findViewById(R.id.btnShow);
        tvNoteState = findViewById(R.id.tvNoteState);


    }

    private void initFirestoreTools() {
        db = FirebaseFirestore.getInstance();

    }

    private void clicSaveNote() {
        btnSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = etNoteTitle.getText().toString();
                String content = etNoteContent.getText().toString();
                //String id = UUID.randomUUID().toString();
                Bundle bundle = getIntent().getExtras();
                if (bundle != null) {
                    String id = bundle.getString(("uId"));
                    //String id = uId;
                    updateDocumentInFirestore(id, title, content);
                } else {

                    String id = UUID.randomUUID().toString();

                    createDocumentInfirestore(id, title, content);
                }


                // aPPLE de la methode pour la creation en bas


            }
        });
    }

    /**
     * UPDATE
     **/


    private void clicShowAllNotes() {
        btnShowNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Show all notes", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(MainActivity.this, ShowNoteActivity.class));

            }
        });
    }


    private void createDocumentInfirestore(String id, String title, String content) {
        if (!title.isEmpty() && !content.isEmpty()) {
            // Creation d'un tableau qui contient les datas
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("title", title);
            map.put("content", content);


            db.collection("notes").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(MainActivity.this, "Note saved !", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(MainActivity.this, "Failed" + e, Toast.LENGTH_SHORT);
                        }


                    });
        } else {

            Toast.makeText(MainActivity.this, "Empty fields are note allowed", Toast.LENGTH_SHORT);
        }
    }

    private void updateDocumentInFirestore(String id, String title, String content) {
        db.collection("notes").document(id).update("title", title, "content", content)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Data update", Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(MainActivity.this, "error" + task, Toast.LENGTH_SHORT).show();
                        }


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
                    }


                });
    }


    private void showDataToUpdateFromTheBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //  cHANGEMENT DU TEXT DES WIDGET
            btnSaveNote.setText("Update");
            tvNoteState.setText("Update note");
            btnShowNote.setVisibility(View.GONE);

            // Récuperation des données via le bundle
            String uId = bundle.getString("uId");
            String uTitle = bundle.getString("uTitle");
            String uContent = bundle.getString("uContent");
            etNoteTitle.setText(uTitle);
            etNoteContent.setText(uContent);

        } else {
            btnSaveNote.setText("Save note to Firestore");

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        initFirestoreTools();
        clicSaveNote();
        clicShowAllNotes();
        showDataToUpdateFromTheBundle();

    }
}