package org.diiage.martin.partiel2018martin.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.diiage.martin.partiel2018martin.models.Artist;
import org.diiage.martin.partiel2018martin.models.Release;

public class ReleasesDBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 2;
    private static String DB_NAME = "releases.db";

    public ReleasesDBHelper(Context context){
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE `artists` (\n" +
                "\t`id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t`name`\tTEXT\n" +
                ")");

        db.execSQL("CREATE TABLE `releases` (\n" +
                "\t`id`\tINTEGER,\n" +
                "\t`catno`\tTEXT,\n" +
                "\t`thumb`\tTEXT,\n" +
                "\t`format`\tTEXT,\n" +
                "\t`title`\tTEXT,\n" +
                "\t`year`\tINTEGER,\n" +
                "\t`resource_url`\tTEXT,\n" +
                "\t`artist_id`\tTEXT,\n" +
                "\t`status`\tTEXT,\n" +
                "\tPRIMARY KEY(id)\n" +
                ")");
    }

    public static Artist getArtistByName(SQLiteDatabase db, String artistName){
        Artist result = null;
        Cursor cursor = db.query(
            "artists",
            new String[]{"id", "name"},
            "name = ?",
            new String[]{artistName},
            null,
            null,
            null
        );

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);

            result = new Artist(id, name);
        }

        return result;
    }

    public static int addArtist(SQLiteDatabase db, Artist artist){
        ContentValues cv = new ContentValues();
        cv.put("name", artist.getName());

        return (int)db.insert("artists", null, cv);
    }

    public static void addOrReplaceRelease(SQLiteDatabase db, Release release){

        ContentValues cv = new ContentValues();
        cv.put("id", release.getId());
        cv.put("catno", release.getCatno());
        cv.put("thumb", release.getThumb());
        cv.put("format", release.getFormat());
        cv.put("title", release.getTitle());
        cv.put("year", release.getYear());
        cv.put("resource_url", release.getResourceUrl());
        cv.put("status", release.getStatus());

        //Get artist ID if existing
        Artist a = ReleasesDBHelper.getArtistByName(db, release.getArtist().getName());

        if(a == null){
            int artistId = addArtist(db, release.getArtist());
            cv.put("artist_id", artistId);
        } else{
            cv.put("artist_id", a.getId());
        }

        db.replace(
                "releases",
                null,
                cv
        );
        //db.insert("releases",null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        for (int i = oldVersion; i < newVersion; i++) {
            upgradeTo(db, i);
        }


    }

    private void upgradeTo(SQLiteDatabase db, int version){
        if(version == 2){

            //We need to make sure that the table has been modified
            db.beginTransaction();

            try {
                db.execSQL("ALTER TABLE releases RENAME TO tmp_releases");
                db.execSQL("CREATE TABLE `releases` (\n" +
                        "\t`id`\tINTEGER,\n" +
                        "\t`catno`\tTEXT,\n" +
                        "\t`thumb`\tTEXT,\n" +
                        "\t`format`\tTEXT,\n" +
                        "\t`title`\tTEXT,\n" +
                        "\t`year`\tINTEGER,\n" +
                        "\t`resource_url`\tTEXT,\n" +
                        "\t`artist_id`\tTEXT,\n" +
                        "\t`status`\tTEXT,\n" +
                        "\tPRIMARY KEY(id)\n" +
                        ")");

                db.execSQL("INSERT INTO releases SELECT id, catno, thumb, format, title, year, resource_url, status FROM tmp_releases;");
                db.execSQL("DROP TABLE tmp_releases");
                db.setTransactionSuccessful();
            } catch (Exception e){

            } finally {
                db.endTransaction();
            }

            db.execSQL("ALTER TABLE `releases` ADD artist_id INTEGER");

            db.execSQL("CREATE TABLE `artists` (\n" +
                    "\t`id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "\t`name`\tTEXT\n" +
                    ")"
            );
        }
    }


}
