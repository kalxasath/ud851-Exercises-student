/*
* Copyright (C) 2016 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.todolist.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

// Verify that TaskContentProvider extends from ContentProvider and implements required methods
public class TaskContentProvider extends ContentProvider {

    // Define final integer constants for the directory of tasks and a single item.
    // It's convention to use 100, 200, 300, etc for directories,
    // and related ints (101, 102, ..) for items in that directory.
    public static final int TASKS = 100;
    public static final int TASK_WITH_ID = 101;

    // CDeclare a static variable for the Uri matcher that you construct
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    // Define a static buildUriMatcher method that associates URI's with their int match
    /**
     Initialize a new matcher object without any matches,
     then use .addURI(String authority, String path, int match) to add matches
     */
    public static UriMatcher buildUriMatcher() {

        // Initialize a UriMatcher with no matches by passing in NO_MATCH to the constructor
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        /*
          All paths added to the UriMatcher have a corresponding int.
          For each kind of uri you may want to access, add the corresponding match with addURI.
          The two calls below add matches for the task directory and a single item by ID.
         */
        uriMatcher.addURI(TaskContract.AUTHORITY, TaskContract.PATH_TASKS, TASKS);
        uriMatcher.addURI(TaskContract.AUTHORITY, TaskContract.PATH_TASKS + "/#", TASK_WITH_ID);

        return uriMatcher;
    }

    // Member variable for a TaskDbHelper that's initialized in the onCreate() method
    private TaskDbHelper mTaskDbHelper;

    /* onCreate() is where you should initialize anything you’ll need to setup
    your underlying data source.
    In this case, you’re working with a SQLite database, so you’ll need to
    initialize a DbHelper to gain access to it.
     */
    @Override
    public boolean onCreate() {
        // Complete onCreate() and initialize a TaskDbhelper on startup
        // [Hint] Declare the DbHelper as a global variable

        Context context = getContext();
        mTaskDbHelper = new TaskDbHelper(context);
        return true;
    }


    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        // COMPLETED (1) Get access to the task database (to write new data to)
        // So that we can write new data to it, we'll use mTaskDbHelper.getWritableDatabase()
        final SQLiteDatabase db = mTaskDbHelper.getWritableDatabase();

        // COMPLETED (2) Write URI matching code to identify the match for the tasks directory
        // This match will be either 100 for all tasks or 101 for a task with ID, or an unrecognized URI
        int match = sUriMatcher.match(uri);

        // COMPLETED (3) Insert new values into the database
        // COMPLETED (4) Set the value for the returnedUri and write the default case for unknown URI's

        Uri returnUri;

        // We want to check these cases with a switch case and respond to only the tasks case.
        // If the tasks case is met, we can insert a new row of data into this directory.
        // We can't insert data into just one row like in the task with id case.
        // And if we receive any other type URI or an invalid one, the default behavior
        // will be to throw an UnsupportedOperationException and print out an
        // Unknown uri message.
        switch(match) {
            case TASKS:
                // We'll insert new data into the tasks directory by calling insert om our database.
                // Inserting values into tasks table
                long id = db.insert(TaskContract.TaskEntry.TABLE_NAME, null, values);
                // If the insert wasn't successful, this ID will be -1
                // But if ths insert is successful, we want the provider's insert method to take
                // that unique row ID and create and return a URI for that newly inserted data.

                // So first, let's write an if that checks that this insert was successful.
                if ( id > 0 ) {
                    // Success, the insert worked and we can construct the new URI
                    // that will be our main content URI, which has the authority
                    // and tasks path, with the id appended to it.
                    returnUri = ContentUris.withAppendedId(TaskContract.TaskEntry.CONTENT_URI, id);
                    // contentUris is an Android class that contains helper methods for
                    // constructing URIs
                } else {
                    // Otherwise, we'll throw a SQLiteException, because the insert failed.
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }

                break;
            // Default case throws an UnsupportedOperationException
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // COMPLETED (5) Notify the resolver if the uri has been changed, and return the newly inserted URI
        // To notify the resolver that a change has occurred at this particular URI,
        // you'll do this using the notify change function.
        // This is so that the resolver knows that something jas changed, and
        // can update the database and any associated UI accordingly
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }


    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public String getType(@NonNull Uri uri) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

}
