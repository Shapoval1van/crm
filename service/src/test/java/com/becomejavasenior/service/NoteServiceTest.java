package com.becomejavasenior.service;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.dao.impl.DaoFactoryImpl;
import com.becomejavasenior.dao.impl.NoteDaoImpl;
import com.becomejavasenior.model.Note;
import com.becomejavasenior.service.impl.NoteServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(NoteServiceImpl.class)
public class NoteServiceTest {


    private NoteService noteService;

    @Mock
    private NoteDaoImpl noteDao;

    @Mock
    private Note note;

    @Mock
    DaoFactoryImpl daoFactory;

    @Before
    public void setUp() throws Exception {
        whenNew(DaoFactoryImpl.class).withNoArguments().thenReturn(daoFactory);
        when(daoFactory.getNoteDao()).thenReturn(noteDao);
        noteService = new NoteServiceImpl();
    }

    @Test
    public void testCreate() throws Exception {
        when(noteDao.create(any(Note.class))).thenReturn(1);
        noteService.create(note);

        verify(noteDao).create(note);
    }

    @Test
    public void testGetNoteById() throws Exception {
        PowerMockito.when(noteDao.getNoteById(1)).thenReturn(note);

        Note resultNote = noteService.getNoteById(1);

        verify(noteDao).getNoteById(1);
        Assert.assertEquals(note, resultNote);

    }

    @Test
    public void testUpdate() throws Exception {
        when(noteDao.update(any(Note.class))).thenReturn(1);
        noteService.update(note);

        verify(noteDao).update(note);
    }

    @Test
    public void testDelete() throws Exception {
        when(noteDao.delete(any(Note.class))).thenReturn(1);
        noteService.delete(note);

        verify(noteDao).delete(note);
    }

    @Test
    public void testFindAll() throws Exception {
        List<Note> notes = new LinkedList<>();

        Note firstNote  = new Note();
        firstNote.setId(1);
        firstNote.setText("First  Note");
        notes.add(firstNote);

        Note secondNote = new  Note();
        secondNote.setId(2);
        secondNote.setText("Second  Note");
        notes.add(secondNote);

        when(noteDao.findAll()).thenReturn(notes);
        List<Note> result = noteService.findAll();

        verify(noteDao).findAll();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetMaxId() throws DatabaseException {
        PowerMockito.when(noteDao.getMaxId()).thenReturn(1);
        int result = noteService.getMaxId();

        verify(noteDao).getMaxId();
        assertEquals(1, result);
    }

    @Test
    public void testFindAllByCompanyId() throws  DatabaseException{
        List<Note> notes = new LinkedList<>();

        Note firstNote  = new Note();
        firstNote.setId(1);
        firstNote.setText("First  Note");
        notes.add(firstNote);

        Note secondNote = new  Note();
        secondNote.setId(2);
        secondNote.setText("Second  Note");
        notes.add(secondNote);

        when(noteDao.findAllByCompanyId(1)).thenReturn(notes);
        List<Note> resultNotes = noteService.findAllByCompanyId(1);

        verify(noteDao).findAllByCompanyId(1);
        assertEquals(notes.size(), resultNotes.size());
    }

}