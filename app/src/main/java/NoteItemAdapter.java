import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by ianar on 28/08/2014.
 */
public class NoteItemAdapter extends ArrayAdapter {

    private ArrayList notes;

    public NoteItemAdapter(Context context, int resource, ArrayList notes) {
        super(context, resource, notes);
        this.notes = notes;
    }

}
