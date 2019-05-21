package com.codingwithluts.notes.async;

import android.os.AsyncTask;
import android.util.Log;

import com.codingwithluts.notes.models.Note;
import com.codingwithluts.notes.persistence.NoteDao;

public class InsertAsyncTask extends AsyncTask<Note, Void, Void> {

    private static final String TAG = "InsertAsyncTask";

    private NoteDao mNoteDao;

    public InsertAsyncTask(NoteDao dao) {
        mNoteDao = dao;
    }

    @Override
    protected Void doInBackground(Note... jotes) {
        Log.d(TAG, "doInBackground: thread: " + Thread.currentThread().getName());
        mNoteDao.insertNotes(jotes);
        return null;
    }

}