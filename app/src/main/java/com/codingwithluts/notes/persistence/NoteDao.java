package com.codingwithluts.notes.persistence;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.codingwithluts.notes.models.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    long[] insertNotes(Note... jotes);

    @Query("SELECT * FROM jotes")
    LiveData<List<Note>> getJotes();

    @Delete
    int delete(Note... jotes);

    @Update
    int updateNotes(Note... jotes);
}
