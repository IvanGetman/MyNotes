package mynotes.mynotes.ui.item_note;

import android.text.Editable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import mynotes.mynotes.domain.Callback;
import mynotes.mynotes.domain.Note;
import mynotes.mynotes.domain.NotesRepository;

public class ItemNoteViewModel extends ViewModel {

    public ItemNoteViewModel(NotesRepository repository) {
        this.repository = repository;
    }

    private final NotesRepository repository;

    private MutableLiveData<Boolean> saveEnabled = new MutableLiveData<>(false);

    public LiveData<Boolean> saveEnabled() {
        return saveEnabled;
    }

    private MutableLiveData<Object> saveSucceed = new MutableLiveData<>();

    public LiveData<Object> saveSucceed() {
        return saveSucceed;
    }

    public void saveNote(Editable nameNote, Editable textNote, Note note) {
        note.setName(nameNote.toString());
        note.setDescription(textNote.toString());
        repository.updateNote(note, new Callback<Object>() {
            @Override
            public void onResult(Object value) {
                saveSucceed.setValue(new Object());
            }
        });
    }

    public void deleteNote(Note note) {
        repository.deleteNote(note, new Callback<Object>() {
            @Override
            public void onResult(Object value) {

            }
        });
    }

    public void validateInput(String newName) {
        saveEnabled.setValue(newName.isEmpty());
    }
}
