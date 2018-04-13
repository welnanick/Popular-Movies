package com.nickwelna.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nickwelna.popularmovies.data.FavoritesContract.FavoritesEntry;

public class FavoritesProvider extends ContentProvider {

    private static final int CODE_FAVORITES = 100;
    private static final int CODE_FAVORITES_WITH_ID = 101;
    private static final UriMatcher uriMatcher = buildUriMatcher();
    private FavoritesDbHelper favoritesDbHelper;

    private static UriMatcher buildUriMatcher() {

        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = FavoritesContract.CONTENT_AUTHORITY;

        uriMatcher.addURI(authority, FavoritesContract.PATH_FAVORITES, CODE_FAVORITES);
        uriMatcher
                .addURI(authority, FavoritesContract.PATH_FAVORITES + "/#", CODE_FAVORITES_WITH_ID);

        return uriMatcher;

    }

    @Override
    public boolean onCreate() {

        favoritesDbHelper = new FavoritesDbHelper(getContext());
        return true;

    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor cursor;

        switch (uriMatcher.match(uri)) {

            case CODE_FAVORITES:
                cursor = favoritesDbHelper.getReadableDatabase()
                                          .query(FavoritesEntry.TABLE_NAME, projection, selection,
                                                  selectionArgs, null, null, sortOrder);
                break;

            case CODE_FAVORITES_WITH_ID:
                String id = uri.getPathSegments().get(1);
                String[] newSelectionArgs = new String[]{id};

                cursor = favoritesDbHelper.getReadableDatabase()
                                          .query(FavoritesEntry.TABLE_NAME, projection,
                                                  FavoritesEntry.MOVIE_ID + " = ?",
                                                  newSelectionArgs, null, null, sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        throw new RuntimeException("Method not implemented for this database");

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        final SQLiteDatabase db = favoritesDbHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);
        Uri returnUri;

        switch (match) {

            case CODE_FAVORITES:
                long id = db.insert(FavoritesEntry.TABLE_NAME, null, values);
                if (id > 0) {

                    returnUri = ContentUris.withAppendedId(FavoritesEntry.CONTENT_URI, id);

                }
                else {

                    throw new SQLException("Failed to insert row into " + uri);

                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection,
                      @Nullable String[] selectionArgs) {

        int numRowsDeleted;

        switch (uriMatcher.match(uri)) {

            case CODE_FAVORITES_WITH_ID:
                String id = uri.getPathSegments().get(1);
                String[] newSelectionArgs = new String[]{id};
                numRowsDeleted = favoritesDbHelper.getWritableDatabase()
                                                  .delete(FavoritesEntry.TABLE_NAME,
                                                          FavoritesEntry.MOVIE_ID + " = ?",
                                                          newSelectionArgs);

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (numRowsDeleted != 0) {

            getContext().getContentResolver().notifyChange(uri, null);

        }

        return numRowsDeleted;

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {

        throw new RuntimeException("Method not implemented for this database");

    }

}
