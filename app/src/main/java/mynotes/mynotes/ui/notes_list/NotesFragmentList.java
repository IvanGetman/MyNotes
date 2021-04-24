package mynotes.mynotes.ui.notes_list;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import mynotes.mynotes.R;
import mynotes.mynotes.domain.Note;
import mynotes.mynotes.ui.item_note.ItemNoteFragment;

public class NotesFragmentList extends Fragment {

    public static final String TAG = "NotesFragmentList";

    private NotesViewModel notesViewModel;

    private NoteAdapter adapter;

    private OnNoteSelected listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnNoteSelected) {
            listener = (OnNoteSelected) context;
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
        notesViewModel = new ViewModelProvider(this, new NotesViewModelFactory()).get(NotesViewModel.class);
        adapter = new NoteAdapter();
        adapter.setNoteClicked(new NoteAdapter.OnNoteClicked() {
            @Override
            public void onNoteClicked(Note note) {
                if (listener != null) {
                    listener.onNoteSelected(note);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        notesViewModel.fetchNotes();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        RecyclerView noteList = view.findViewById(R.id.notes_list);
        noteList.setAdapter(adapter);
        noteList.setLayoutManager(new LinearLayoutManager(requireContext()));

        notesViewModel.getNotesLiveData()
                .observe(getViewLifecycleOwner(), new Observer<List<Note>>() {
                    @Override
                    public void onChanged(List<Note> noteList) {
                        adapter.clear();
                        adapter.addItems(noteList);
                        adapter.notifyDataSetChanged();
                    }
                });

        FloatingActionButton floatingActionButton = view.findViewById(R.id.btn_add_note);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_main, new ItemNoteFragment(), ItemNoteFragment.TAG)
                        .addToBackStack(ItemNoteFragment.TAG)
                        .commit();

            }
        });
    }

    public interface OnNoteSelected {
        void onNoteSelected(Note note);
    }
}

