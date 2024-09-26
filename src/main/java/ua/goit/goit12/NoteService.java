package ua.goit.hw12;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.*;

@Service
public class NoteService {
    private Map<Long, Note> notes;

    public NoteService() {
        notes = new HashMap<>();
    }

    public List<Note> listAll() {
        return new ArrayList<>(notes.values());
    }

    public Note add(Note note) {
        long id = generateRandomId();
        note.setId(id);
        notes.put(id, note);
        return note;
    }

    public void deleteById(long id) {
        if (!notes.containsKey(id)) {
            throw new IllegalArgumentException("Note " + id + " not found.");
        }
        notes.remove(id);
    }

    public void update(Note note) {
        long id = note.getId();
        if (!notes.containsKey(id)) {
            throw new IllegalArgumentException("Note " + id + " not found.");
        }
        notes.put(id, note);
    }

    public Note getById(long id) {
        if (!notes.containsKey(id)) {
            throw new IllegalArgumentException("Note " + id + " not found.");
        }
        return notes.get(id);
    }

    private long generateRandomId() {
        Random random = new SecureRandom();
        return random.longs()
                .filter(id -> !notes.containsKey(id))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }
}
