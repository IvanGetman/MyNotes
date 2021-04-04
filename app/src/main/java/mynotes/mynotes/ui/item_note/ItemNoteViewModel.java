package mynotes.mynotes.ui.item_note;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ItemNoteViewModel extends ViewModel {

    private MutableLiveData<Boolean> mText = new MutableLiveData<>(false);

    public LiveData<Boolean> getText() {
        return mText;
    }
}
