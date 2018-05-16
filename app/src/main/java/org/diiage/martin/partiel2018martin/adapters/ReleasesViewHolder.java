package org.diiage.martin.partiel2018martin.adapters;

import android.widget.TextView;

public class ReleasesViewHolder {
    public TextView txtTitle;
    public TextView txtArtist;
    public TextView txtYear;

    public ReleasesViewHolder(TextView txtTitle, TextView txtArtist, TextView txtYear) {
        this.txtTitle = txtTitle;
        this.txtArtist = txtArtist;
        this.txtYear = txtYear;
    }
}