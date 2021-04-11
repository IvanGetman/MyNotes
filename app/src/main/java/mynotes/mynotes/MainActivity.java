package mynotes.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import mynotes.mynotes.domain.Note;
import mynotes.mynotes.ui.item_note.ItemNoteFragment;
import mynotes.mynotes.ui.notes_list.NotesFragmentList;

public class MainActivity extends AppCompatActivity implements NotesFragmentList.OnNoteSelected, ItemNoteFragment.OnNoteSaved {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.fragment_main, new NotesFragmentList(), NotesFragmentList.TAG);
//            fragmentTransaction.commit();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_main, new NotesFragmentList(), NotesFragmentList.TAG)
                    .commit();
        }
    }

    @Override
    public void onNoteSelected(Note note) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_main, ItemNoteFragment.newInstance(note), ItemNoteFragment.TAG)
                .addToBackStack(ItemNoteFragment.TAG)
                .commit();
    }

    @Override
    public void onNoteSaved() {
        getSupportFragmentManager()
                .popBackStack();
    }
}
