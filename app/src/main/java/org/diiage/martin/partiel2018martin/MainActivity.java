package org.diiage.martin.partiel2018martin;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.diiage.martin.partiel2018martin.adapters.ReleasesAdapter;
import org.diiage.martin.partiel2018martin.models.Release;
import org.diiage.martin.partiel2018martin.utils.ReleasesDBHelper;
import org.diiage.martin.partiel2018martin.utils.ReleasesRetriever;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ReleasesRetriever.AsyncResponse {

    ListView lvReleases;
    ArrayList<Release> releases;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.releases = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvReleases = findViewById(R.id.lvReleases);

        ReleasesDBHelper dbHelper = new ReleasesDBHelper(this);
        db = dbHelper.getWritableDatabase();

        ReleasesRetriever retriever = new ReleasesRetriever();
        retriever.execute("https://my-json-server.typicode.com/lpotherat/discogs-data/releases");
        retriever.delegate = this;
    }

    @Override
    public void processFinish(ArrayList<Release> result) {
        this.releases = result;
        ReleasesAdapter adapter = new ReleasesAdapter(this, this.releases);
        adapter.notifyDataSetChanged();
        lvReleases.setAdapter(adapter);
        synchronizeDB(result);
    }

    private void synchronizeDB(ArrayList<Release> releases){
        for (int i = 0; i < releases.size(); i++) {
            ReleasesDBHelper.addOrReplaceRelease(db, releases.get(i));
        }
    }
}
