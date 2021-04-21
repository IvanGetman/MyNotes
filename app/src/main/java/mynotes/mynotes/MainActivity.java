package mynotes.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import mynotes.mynotes.domain.Note;
import mynotes.mynotes.ui.item_note.ItemNoteFragment;
import mynotes.mynotes.ui.notes_list.NotesFragmentList;

public class MainActivity extends AppCompatActivity implements NotesFragmentList.OnNoteSelected, ItemNoteFragment.OnNoteSaved, ItemNoteFragment.OnNoteDelete {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            openTab(new NotesFragmentList(), NotesFragmentList.TAG);
        }


        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.btn_notes) {
                    openTab(new NotesFragmentList(), NotesFragmentList.TAG);
                    return true;
                } else if (itemId == R.id.btn_42) {
                    Toast.makeText(getApplicationContext(), R.string.hello, Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
    }

    private void openTab(Fragment fragment, String tag) {
        Fragment addedFragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (addedFragment == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_main, fragment, tag)
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


    @Override
    public void onNoteDelete() {
        getSupportFragmentManager()
                .popBackStack();
    }
}
