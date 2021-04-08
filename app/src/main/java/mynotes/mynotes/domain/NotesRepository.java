package mynotes.mynotes.domain;

import java.util.List;

public interface NotesRepository {

    void getNoteList(Callback<List<Note>> callback);

    void deleteNote(Callback<Object> voidCallback);

    void updateNote(Note note, Callback<Object> objectCallback);
}
