package com.codingwithluts.notes.async;

import android.os.AsyncTask;

import com.codingwithluts.notes.models.Note;
import com.codingwithluts.notes.persistence.NoteDao;

public class UpdateAsyncTask extends AsyncTask<Note, Void, Void> {

    private NoteDao mNoteDao;

    public UpdateAsyncTask(NoteDao dao) {
        mNoteDao = dao;
    }

    @Override
    protected Void doInBackground(Note... jotes) {
        mNoteDao.updateNotes(jotes);
        return null;
    }

}