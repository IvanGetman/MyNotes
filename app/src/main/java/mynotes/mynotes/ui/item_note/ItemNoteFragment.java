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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import mynotes.mynotes.R;
import mynotes.mynotes.domain.Note;

public class ItemNoteFragment extends Fragment {

    public static final String TAG = "ItemNoteFragment";
    private static final String ARG_NOTE = "ARG_NOTE";
    private ItemNoteViewModel itemNoteViewModel;
    private Note note;
    private OnNoteSaved listener;
    private OnNoteDelete listener_2;


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

        if (getArguments() != null) {
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

        Button buttonSave = view.findViewById(R.id.btn_save_note);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (note.getId() != null) {
                    itemNoteViewModel.saveNote(nameNote.getText(), textNote.getText(), note);
                } else {
                    itemNoteViewModel.addNewNote(nameNote.getText(), textNote.getText(), note);
                }
            }
        });

        Button buttonDelete = view.findViewById(R.id.btn_delete_note);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemNoteViewModel.deleteNote(note);
            }
        });

        itemNoteViewModel.saveEnabled().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                buttonSave.setEnabled(aBoolean);
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

        itemNoteViewModel.deleteEnabled().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                buttonDelete.setEnabled(aBoolean);
            }
        });

        itemNoteViewModel.deleteSucceed().observe(getViewLifecycleOwner(), new Observer<Object>() {
            @Override
            public void onChanged(Object o) {
                if (listener_2 != null) {
                    listener_2.onNoteDelete();
                }
            }
        });
    }

    public interface OnNoteSaved {
        void onNoteSaved();
    }

    public interface OnNoteDelete {
        void onNoteDelete();
    }
}