package mynotes.mynotes.domain;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public interface NotesRepository {

    void getNoteList(Callback<List<Note>> callback);

    void deleteNote(Note note, Callback<Object> objectCallback);

    void updateNote(Note note, Callback<Object> objectCallback);

    void addNewNote(Note note, Callback<Note> noteCallback);

}
