package mynotes.mynotes.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockNotesRepository implements NotesRepository {

    public static final NotesRepository INSTANCE = new MockNotesRepository();
    @Override
    public List<Note> getNoteList() {
        ArrayList<Note> data = new ArrayList();
        data.add(new Note("id1", "Name1", "dfjngkdjfjk", new Date()));
        data.add(new Note("id2", "Name2", "dknalkflekmflkemfl", new Date()));
        data.add(new Note("id3", "Name3", "tttttttttttttttttt", new Date()));
        data.add(new Note("id4", "Name4", "vnckxxxxxxxxxxx", new Date()));
        data.add(new Note("id5", "Name5", "66655e95534534543", new Date()));
        data.add(new Note("id6", "Name6", "5gr5dh54d5h65", new Date()));
        data.add(new Note("id7", "Name7", "5gr5dh54hxhxhfhghd5h65", new Date()));
        data.add(new Note("id8", "Name8", "5121561ty56ry561tr51610brt6yr60y6b0yrb00rtyb0gr5dh54d5h65", new Date()));
        data.add(new Note("id9", "Name9", "опять проблемы с гитом", new Date()));

        return data;
    }
}
