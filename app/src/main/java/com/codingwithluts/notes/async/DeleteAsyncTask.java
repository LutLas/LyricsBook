package com.codingwithluts.notes.async;

import android.os.AsyncTask;

import com.codingwithluts.notes.models.Note;
import com.codingwithluts.notes.persistence.NoteDao;

public class DeleteAsyncTask extends AsyncTask<Note, Void, Void> {

    private NoteDao mNoteDao;

    public DeleteAsyncTask(NoteDao dao) {
        mNoteDao = dao;
    }

    @Override
    protected Void doInBackground(Note... jotes) {
        mNoteDao.delete(jotes);
        return null;
    }

}