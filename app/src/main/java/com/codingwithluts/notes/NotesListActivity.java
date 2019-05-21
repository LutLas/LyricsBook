package com.codingwithluts.notes;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
//import android.support.design.widget.FloatingActionButton;

import com.codingwithluts.notes.adapters.NotesRecyclerAdapter;
import com.codingwithluts.notes.models.Note;
import com.codingwithluts.notes.persistence.NoteRepository;
import com.codingwithluts.notes.util.VerticalSpacingItemDecorator;

import java.util.ArrayList;
import java.util.List;

public class NotesListActivity extends AppCompatActivity implements
        NotesRecyclerAdapter.OnNoteListener,
        View.OnClickListener
        //(alternative floatingActionButton code)
        //FloatingActionButton.OnClickListener
{

    private static final String TAG = "NotesListActivity";

    // ui components
    private RecyclerView mRecyclerView;

    // vars
    private ArrayList<Note> mNotes = new ArrayList<>();
    private NotesRecyclerAdapter mNoteRecyclerAdapter;
    private NoteRepository mNoteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        mRecyclerView = findViewById(R.id.recyclerView);

        findViewById(R.id.fab).setOnClickListener(this);

        initRecyclerView();
        mNoteRepository = new NoteRepository(this);
        retrieveNotes();
        //insertFakeNotes();

        Log.d(TAG, "doInBackground: thread: " + Thread.currentThread().getName());

        setSupportActionBar((Toolbar)findViewById(R.id.notes_toolbar));
        setTitle("Composition");
    }

    private void retrieveNotes(){
        mNoteRepository.retrieveNotesTask().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> jotes) {
                if(mNotes.size() > 0){
                    mNotes.clear();
                }
                if(jotes != null){
                    mNotes.addAll(jotes);
                }
                mNoteRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }

    private void insertFakeNotes(){
        for(int i = 1; i < 11; i++){
            Note note = new Note();
            note.setTitle("Lyrics #" + i);
            note.setContent("content #: " + i);
            note.setTimestamp("May 2019");
            mNotes.add(note);
        }
        mNoteRecyclerAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(9);
        mRecyclerView.addItemDecoration(itemDecorator);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
        mNoteRecyclerAdapter = new NotesRecyclerAdapter(mNotes, this);
        mRecyclerView.setAdapter(mNoteRecyclerAdapter);
    }

    @Override
    public void onNoteClick(int position) {
        Log.d(TAG, "onNoteClick: " + position);
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra("selected_note", mNotes.get(position));
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, NoteActivity.class);
        startActivity(intent);
    }

    private void deleteNote(Note note) {
        mNotes.remove(note);
        mNoteRecyclerAdapter.notifyDataSetChanged();

        mNoteRepository.deleteNoteTask(note);
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            deleteNote(mNotes.get(viewHolder.getAdapterPosition()));
        }
    };
}
