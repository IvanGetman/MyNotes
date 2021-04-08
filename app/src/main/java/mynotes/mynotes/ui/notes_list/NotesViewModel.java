package mynotes.mynotes.ui.notes_list;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import mynotes.mynotes.domain.Callback;
import mynotes.mynotes.domain.Note;
import mynotes.mynotes.domain.NotesRepository;

public class NotesViewModel extends ViewModel {

    private final NotesRepository repository;
    private final MutableLiveData<ArrayList<Note>> notesLiveData = new MutableLiveData<>();

    public NotesViewModel(NotesRepository repository) {
        this.repository = repository;
    }

    public void fetchNotes() {
        repository.getNoteList(new Callback<List<Note>>() {
            @Override
            public void onResult(List<Note> value) {
                notesLiveData.postValue(new ArrayList<>(value));
            }
        });
    }

    public MutableLiveData<ArrayList<Note>> getNotesLiveData(){
        return notesLiveData;
    }
}

