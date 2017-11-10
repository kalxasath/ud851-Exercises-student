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
package com.example.android.favoritetoys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // COMPLETED (1) Declare a TextView variable called mToysListTextView
    TextView mToysListTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // COMPLETED (3) Use findViewById to get a reference to the TextView from the layout
        mToysListTextView = (TextView) findViewById(R.id.tv_toy_names);

        // COMPLETED (4) Use the static ToyBox.getToyNames method and store the names in a String array
        String[] toyNames = ToyBox.getToyNames();

        long start_time = System.currentTimeMillis();
        // COMPLETED (5) Loop through each toy and append the name to the TextView (add \n for spacing)
        for (int i=0; i < toyNames.length; i++) {
            mToysListTextView.append(toyNames[i] + "\n\n\n");
        }
        long end_time = System.currentTimeMillis();
        long difference = end_time-start_time;

        Log.i("GV", "diff1= " + difference);

        mToysListTextView.setText("");
        start_time = System.currentTimeMillis();
        for (String toyName : toyNames) {
            mToysListTextView.append(toyName + "\n\n\n");
        }
        end_time = System.currentTimeMillis();
        difference = end_time-start_time;

        Log.i("GV", "diff2= " + difference);

    }
}