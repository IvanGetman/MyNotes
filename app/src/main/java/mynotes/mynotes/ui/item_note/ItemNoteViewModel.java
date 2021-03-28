package mynotes.mynotes.ui.item_note;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ItemNoteViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ItemNoteViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Item Note fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
