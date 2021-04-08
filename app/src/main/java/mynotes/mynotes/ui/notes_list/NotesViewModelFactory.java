package mynotes.mynotes.ui.notes_list;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import mynotes.mynotes.domain.MockNotesRepository;

public class NotesViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NotesViewModel(MockNotesRepository.INSTANCE);
    }
}
