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

package com.example.android.todolist;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.android.todolist.data.TaskContract;


public class AddTaskActivity extends AppCompatActivity {

    // Declare a member variable to keep track of a task's selected mPriority
    private int mPriority;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // Initialize to highest mPriority by default (mPriority = 1)
        ((RadioButton) findViewById(R.id.radButton1)).setChecked(true);
        mPriority = 1;
    }


    /**
     * onClickAddTask is called when the "ADD" button is clicked.
     * It retrieves user input and inserts that new task data into the underlying database.
     */
    public void onClickAddTask(View view) {
        // Not yet implemented
        // COMPLETED (6) Check if EditText is empty, if not retrieve input and store it in a ContentValues object
        // To retrieve a user's input description, we have to retrieve the text they
        // entered into the edit text view in the add task layout.
        String input = ((EditText) findViewById(R.id.editTextTaskDescription)).getText().toString();

        // We don't want any empty tasks being created
        // If someone presses the add button without typing in a task to do.
        if (input.length() == 0) {
            return;
        }

        // We'll create a new ContentValues object to place this task data into.
        ContentValues contentValues = new ContentValues();

        // Put the task description and selected mPriority into the ContentValues
        contentValues.put(TaskContract.TaskEntry.COLUMN_DESCRIPTION, input);
        contentValues.put(TaskContract.TaskEntry.COLUMN_PRIORITY, mPriority);

        // COMPLETED (7) Insert new task data via a ContentResolver
        // Then we need to insert these values into our database with
        // a call to a content resolver
        Uri uri = getContentResolver().insert(TaskContract.TaskEntry.CONTENT_URI, contentValues);

        // This insert method should return a URI if it works.

        // COMPLETED (8) Display the URI that's returned with a Toast
        // [Hint] Don't forget to call finish() to return to MainActivity after this insert is complete
        if (uri != null) {
            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
        }

        // call finish() to tell that this activity is over and so
        // we should return to the main activity after an insert is complete.
        finish();
    }


    /**
     * onPrioritySelected is called whenever a priority button is clicked.
     * It changes the value of mPriority based on the selected button.
     */
    public void onPrioritySelected(View view) {
        if (((RadioButton) findViewById(R.id.radButton1)).isChecked()) {
            mPriority = 1;
        } else if (((RadioButton) findViewById(R.id.radButton2)).isChecked()) {
            mPriority = 2;
        } else if (((RadioButton) findViewById(R.id.radButton3)).isChecked()) {
            mPriority = 3;
        }
    }
}
