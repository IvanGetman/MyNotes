package mynotes.mynotes.domain;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class FirestoreNotesRepository implements NotesRepository {

    public static final NotesRepository INSTANCE = new FirestoreNotesRepository();

    private static final String NOTES_COLLECTION = "notes";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_DATE = "date";

    private FirebaseFirestore fireStore = FirebaseFirestore.getInstance();

    @Override
    public void getNoteList(Callback<List<Note>> callback) {
        fireStore.collection(NOTES_COLLECTION).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<DocumentSnapshot> documents = task.getResult().getDocuments();
                        ArrayList<Note> result = new ArrayList<>();
                        for (DocumentSnapshot doc : documents) {

                            String name = doc.getString(FIELD_NAME);
                            String description = doc.getString(FIELD_DESCRIPTION);
                            Date date = doc.getDate(FIELD_DATE);

                            Note note = new Note(doc.getId(), name, description, date);

                            result.add(note);
                        }
                        callback.onResult(result);
                    }
                });
    }

    @Override
    public void deleteNote(Note note, Callback<Object> objectCallback) {
        fireStore.collection(NOTES_COLLECTION)
                .document(note.getId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        objectCallback.onResult(new Object());
                    }
                });
    }

    @Override
    public void updateNote(Note note, Callback<Object> objectCallback) {

        HashMap<String, Object> data = new HashMap<>();
        data.put(FIELD_NAME, note.getName());
        data.put(FIELD_DESCRIPTION, note.getDescription());
        data.put(FIELD_DATE, note.getDate());

        fireStore.collection(NOTES_COLLECTION)
                .document(note.getId())
                .update(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        objectCallback.onResult(new Object());
                    }
                });
    }

    @Override
    public void addNewNote(Note note, Callback<Note> noteCallback) {

        HashMap<String, Object> data = new HashMap<>();
        data.put(FIELD_NAME, note.getName());
        data.put(FIELD_DESCRIPTION, note.getDescription());
        data.put(FIELD_DATE, note.getDate());

        fireStore.collection(NOTES_COLLECTION)
                .add(data)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        String id = task.getResult().getId();
                        note.setId(id);
                        noteCallback.onResult(note);
                    }
                });
    }
}
