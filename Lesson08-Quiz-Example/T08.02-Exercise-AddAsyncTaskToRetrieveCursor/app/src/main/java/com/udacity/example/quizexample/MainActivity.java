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

package com.udacity.example.quizexample;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.udacity.example.droidtermsprovider.DroidTermsExampleContract;

/**
 * Gets the data from the ContentProvider and shows a series of flash cards.
 */

public class MainActivity extends AppCompatActivity {

    // The current state of the app
    private int mCurrentState;

    // TODO (3) Create an instance variable storing a Cursor called mCursor
    private Button mButton;

    // This state is when the word definition is hidden and clicking the button will therefore
    // show the definition
    private final int STATE_HIDDEN = 0;

    // This state is when the word definition is shown and clicking the button will therefore
    // advance the app to the next word
    private final int STATE_SHOWN = 1;
    private Cursor mCursor;
    private int mColumnIndexWord;
    private int mColumnIndexDefinition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the views
        mButton = (Button) findViewById(R.id.button_next);

        new WordsLoaderTasks().execute();
    }

    class WordsLoaderTasks extends AsyncTask<Void, Void, Cursor>{
        @Override
        protected Cursor doInBackground(Void... params) {
            return getContentResolver().query(DroidTermsExampleContract.CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            mCursor = cursor;
            mCursor.moveToPosition(mCursor.getCount() - 1);

            mColumnIndexWord = mCursor.getColumnIndex(DroidTermsExampleContract.COLUMN_WORD);
            mColumnIndexDefinition = mCursor.getColumnIndex(DroidTermsExampleContract.COLUMN_DEFINITION);
            nextWord();
        }
    }

    /**
     * This is called from the layout when the button is clicked and switches between the
     * two app states.
     * @param view The view that was clicked
     */
    public void onButtonClick(View view) {

        // Either show the definition of the current word, or if the definition is currently
        // showing, move to the next word.
        switch (mCurrentState) {
            case STATE_HIDDEN:
                showDefinition();
                break;
            case STATE_SHOWN:
                nextWord();
                break;
        }
    }

    public void nextWord() {

        // moving to the next word
        int position = mCursor.getPosition();
        if (position >= mCursor.getCount() - 1){
            position = 0;
        } else {
            position = position + 1;
        }
        mCursor.moveToPosition(position);


        // showing the word
        final String stringWord = mCursor.getString(mColumnIndexWord);

        mButton.setText(stringWord);

        mCurrentState = STATE_HIDDEN;

    }


    public void showDefinition() {

        final String stringDefinition = mCursor.getString(mColumnIndexDefinition);
        mButton.setText(stringDefinition);

        mCurrentState = STATE_SHOWN;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCursor.close();
    }
}
