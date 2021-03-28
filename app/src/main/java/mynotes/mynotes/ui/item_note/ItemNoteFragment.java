package mynotes.mynotes.ui.item_note;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mynotes.mynotes.R;


public class ItemNoteFragment extends Fragment {

    public static final String TAG = "ItemNoteFragment";

    private ItemNoteViewModel itemNoteViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        itemNoteViewModel =
                new ViewModelProvider(this).get(ItemNoteViewModel.class);
        View root = inflater.inflate(R.layout.fragment_item_note, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}