package mynotes.mynotes.ui.notes_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import mynotes.mynotes.R;
import mynotes.mynotes.domain.Note;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    public void addItems(List<Note> toAdd) {
        items.addAll(toAdd);
    }

    public void clear() {
        items.clear();
    }

    private List<Note> items = new ArrayList<>();

    private OnNoteClicked noteClicked;

    @NonNull
    @Override
    public NoteAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);

        return new NoteViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.NoteViewHolder holder, int position) {
        Note item = items.get(position);
        holder.getTitle().setText(item.getName());
        holder.getText().setText(item.getDescription());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public OnNoteClicked getNoteClicked() {
        return noteClicked;
    }

    public void setNoteClicked(OnNoteClicked noteClicked) {
        this.noteClicked = noteClicked;
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView text;

        public TextView getTitle() {
            return title;
        }

        public TextView getText() {
            return text;
        }

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_note_name);
            text = itemView.findViewById(R.id.item_note_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (noteClicked != null) {
                        noteClicked.onNoteClicked(items.get(getAdapterPosition()));
                    }
                }
            });
        }
    }

    interface OnNoteClicked {
        void onNoteClicked(Note note);
    }
}
