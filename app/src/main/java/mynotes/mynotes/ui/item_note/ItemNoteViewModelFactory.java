package mynotes.mynotes.ui.item_note;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import mynotes.mynotes.domain.MockNotesRepository;
import mynotes.mynotes.ui.notes_list.NotesViewModel;

public class ItemNoteViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ItemNoteViewModel(MockNotesRepository.INSTANCE);
    }
}
