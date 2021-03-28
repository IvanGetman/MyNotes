package mynotes.mynotes.ui.notes_list;

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
import android.widget.Toast;

import java.util.List;

import mynotes.mynotes.R;
import mynotes.mynotes.domain.Note;

public class NotesFragmentList extends Fragment {

    public static final String TAG = "NotesFragmentList";

    private NotesViewModel notesViewModel;

    private NoteAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);
        notesViewModel.fetchNotes();
        adapter = new NoteAdapter();
        adapter.setNoteClicked(new NoteAdapter.OnNoteClicked() {
            @Override
            public void onNoteClicked(Note note) {
                Toast.makeText(requireContext(),note.getName(),Toast.LENGTH_LONG).show();
            }
        });
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
    }
}