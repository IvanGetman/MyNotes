package mynotes.mynotes.ui.notes_list;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import mynotes.mynotes.MainActivity;
import mynotes.mynotes.R;
import mynotes.mynotes.domain.Note;
import mynotes.mynotes.ui.item_note.ItemNoteFragment;

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
                Toast.makeText(requireContext(), note.getName(), Toast.LENGTH_LONG).show();
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
//        noteList.setLayoutManager(new LinearLayoutManager(requireContext()));


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
                openTab(new ItemNoteFragment(), ItemNoteFragment.TAG);
            }
        });

        BottomNavigationView navView = view.findViewById(R.id.nav_view);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                openTab(new NotesFragmentList(), NotesFragmentList.TAG);
                if (itemId == R.id.btn_notes) {
                    noteList.setLayoutManager(new LinearLayoutManager(requireContext()));
                    return true;
                } else if (itemId == R.id.btn_notes_grid) {
                    noteList.setLayoutManager(new GridLayoutManager(requireContext(), 3));
                    return true;
                }
                return false;
            }
        });

    }

    private void openTab(Fragment fragment, String tag) {
        Fragment addedFragment = requireActivity().getSupportFragmentManager().findFragmentByTag(tag);

        if (addedFragment == null) {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_main, fragment, tag)
                    .commit();
        }
    }
}

