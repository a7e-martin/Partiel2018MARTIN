package org.diiage.martin.partiel2018martin.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.diiage.martin.partiel2018martin.R;
import org.diiage.martin.partiel2018martin.models.Release;

import java.util.ArrayList;

public class ReleasesAdapter extends BaseAdapter {
    ArrayList<Release> _releases;
    Activity _context;

    public ReleasesAdapter(Activity context, ArrayList<Release> releases){
        this._releases = releases;
        this._context = context;
    }

    @Override
    public int getCount() {
        return _releases.size();
    }

    @Override
    public Object getItem(int i) {
        return _releases.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;

    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view;
        ReleasesViewHolder releasesViewHolder;
        if(convertView != null){
            view = convertView;
            releasesViewHolder = (ReleasesViewHolder) view.getTag();
        } else{
            LayoutInflater layoutInflater = this._context.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.release_list_item, parent, false);

            TextView txtTitle = view.findViewById(R.id.txtTitle);
            TextView txtArtist = view.findViewById(R.id.txtArtist);
            TextView txtYear = view.findViewById(R.id.txtYear);

            releasesViewHolder = new ReleasesViewHolder(txtTitle, txtArtist, txtYear);

            view.setTag(releasesViewHolder);
        }

        Release release = _releases.get(i);
        releasesViewHolder.txtTitle.setText(release.getTitle());
        releasesViewHolder.txtArtist.setText(release.getArtist().getName());
        releasesViewHolder.txtYear.setText(String.valueOf(release.getYear()));

        return view;
    }
}
