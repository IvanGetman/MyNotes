package mynotes.mynotes.ui.item_note;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

import mynotes.mynotes.R;
import mynotes.mynotes.domain.MockNotesRepository;
import mynotes.mynotes.domain.Note;
import mynotes.mynotes.domain.NotesRepository;


public class ItemNoteFragment extends Fragment {

    public static final String TAG = "ItemNoteFragment";
    private static final String ARG_NOTE = "ARG_NOTE";
    private ItemNoteViewModel itemNoteViewModel;
    private Note note;
    private OnNoteSaved listener;


    public static ItemNoteFragment newInstance(Note note) {
        ItemNoteFragment fragment = new ItemNoteFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_NOTE, note);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnNoteSaved) {
            listener = (OnNoteSaved) context;
        }
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_NOTE)) {
            note = getArguments().getParcelable(ARG_NOTE);
        } else {
            note = new Note();
        }
        itemNoteViewModel = new ViewModelProvider(this, new ItemNoteViewModelFactory()).get(ItemNoteViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_item_note, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText textNote = view.findViewById(R.id.input_text_note);
        EditText nameNote = view.findViewById(R.id.input_name_note);

        BottomNavigationView navView = view.findViewById(R.id.nav_view_2);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.btn_save_note) {
                    Toast.makeText(requireContext(), "Save", Toast.LENGTH_LONG).show();
                    itemNoteViewModel.saveNote(nameNote.getText(), textNote.getText(), note);
                    return true;
                } else if (itemId == R.id.btn_delete_note) {
                    Toast.makeText(requireContext(), "Delete", Toast.LENGTH_LONG).show();
                    itemNoteViewModel.deleteNote(note);
                    return true;
                }
                return false;
            }
        });


        nameNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                itemNoteViewModel.validateInput(s.toString());
            }
        });

        nameNote.setText(note.getName());
        textNote.setText(note.getDescription());

        itemNoteViewModel.saveEnabled().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                view.findViewById(R.id.btn_save_note).setEnabled(aBoolean);
            }
        });

        itemNoteViewModel.saveSucceed().observe(getViewLifecycleOwner(), new Observer<Object>() {
            @Override
            public void onChanged(Object o) {
                if (listener != null) {
                    listener.onNoteSaved();
                }
            }
        });
    }

    public interface OnNoteSaved {
        void onNoteSaved();
    }
}