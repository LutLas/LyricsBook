package com.codingwithluts.notes.persistence;


import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.codingwithluts.notes.async.DeleteAsyncTask;
import com.codingwithluts.notes.async.InsertAsyncTask;
import com.codingwithluts.notes.async.UpdateAsyncTask;
import com.codingwithluts.notes.models.Note;

import java.util.List;

public class NoteRepository {

    private NoteDatabase mNoteDatabase;

    public NoteRepository(Context context) {
        mNoteDatabase = NoteDatabase.getInstance(context);
    }

    public void insertNoteTask(Note note){
        new InsertAsyncTask(mNoteDatabase.getNoteDao()).execute(note);
    }

    public void updateNoteTask(Note note){
        new UpdateAsyncTask(mNoteDatabase.getNoteDao()).execute(note);
    }

    public LiveData<List<Note>> retrieveNotesTask() {
        return mNoteDatabase.getNoteDao().getJotes();
    }

    public void deleteNoteTask(Note note){
        new DeleteAsyncTask(mNoteDatabase.getNoteDao()).execute(note);
    }
}













