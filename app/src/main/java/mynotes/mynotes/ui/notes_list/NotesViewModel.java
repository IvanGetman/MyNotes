package mynotes.mynotes.ui.notes_list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import mynotes.mynotes.domain.MockNotesRepository;
import mynotes.mynotes.domain.Note;
import mynotes.mynotes.domain.NotesRepository;

public class NotesViewModel extends ViewModel {

    private final NotesRepository repository = MockNotesRepository.INSTANCE;
    private final MutableLiveData<List<Note>> notesLiveData = new MutableLiveData<>();

    public void fetchNotes() {
        notesLiveData.setValue(repository.getNoteList());
    }

    public LiveData<List<Note>> getNotesLiveData(){
        return notesLiveData;
    }
}

