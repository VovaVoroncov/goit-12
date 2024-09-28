import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NoteServiceTest {

    private NoteService noteService;

    @BeforeEach
    void setUp() {
        noteService = new NoteService();
    }

    @Test
    void testAddNote() {
        Note note = new Note();
        note.setContent("Test Note");

        Note addedNote = noteService.add(note);

        assertNotNull(addedNote);
        assertNotNull(addedNote.getId());
        assertEquals("Test Note", addedNote.getContent());
    }

    @Test
    void testListAll() {
        Note note1 = new Note();
        note1.setContent("Note 1");
        noteService.add(note1);

        Note note2 = new Note();
        note2.setContent("Note 2");
        noteService.add(note2);

        List<Note> notes = noteService.listAll();

        assertEquals(2, notes.size());
        assertTrue(notes.stream().anyMatch(n -> n.getContent().equals("Note 1")));
        assertTrue(notes.stream().anyMatch(n -> n.getContent().equals("Note 2")));
    }

    @Test
    void testGetById_ExistingNote() {
        Note note = new Note();
        note.setContent("Test Note");
        Note addedNote = noteService.add(note);

        Note foundNote = noteService.getById(addedNote.getId());

        assertEquals(addedNote.getId(), foundNote.getId());
        assertEquals(addedNote.getContent(), foundNote.getContent());
    }

    @Test
    void testGetById_NoteNotFound() {
        assertThrows(IllegalArgumentException.class, () -> {
            noteService.getById(999L);
        });
    }

    @Test
    void testUpdate_ExistingNote() {
        Note note = new Note();
        note.setContent("Original Note");
        Note addedNote = noteService.add(note);

        addedNote.setContent("Updated Note");
        noteService.update(addedNote);

        Note updatedNote = noteService.getById(addedNote.getId());

        assertEquals("Updated Note", updatedNote.getContent());
    }

    @Test
    void testUpdate_NoteNotFound() {
        Note note = new Note();
        note.setId(999L);
        note.setContent("Non-existent Note");

        assertThrows(IllegalArgumentException.class, () -> {
            noteService.update(note);
        });
    }

    @Test
    void testDeleteById_ExistingNote() {
        Note note = new Note();
        note.setContent("Note to delete");
        Note addedNote = noteService.add(note);

        noteService.deleteById(addedNote.getId());

        assertThrows(IllegalArgumentException.class, () -> {
            noteService.getById(addedNote.getId());
        });
    }

    @Test
    void testDeleteById_NoteNotFound() {
        assertThrows(IllegalArgumentException.class, () -> {
            noteService.deleteById(999L);
        });
    }
}
