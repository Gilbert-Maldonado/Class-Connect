package net.classconnect.classconnect;


import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import co.dift.ui.SwipeToAction;

public class ClassEnterActivity extends AppCompatActivity {


    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    RecyclerAdapter mRecyclerAdapter;
    String classToAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_enter);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(getString(R.string.courses));
        mRecyclerView = (RecyclerView) findViewById(R.id.class_list);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerAdapter = new RecyclerAdapter(new ArrayList<String>());
        mRecyclerView.setAdapter(mRecyclerAdapter);

        SwipeToAction swipeToAction = new SwipeToAction(mRecyclerView, new SwipeToAction.SwipeListener<String>() {
            @Override
            public boolean swipeLeft(final String itemData) {
                final int pos = mRecyclerAdapter.removeItem(itemData);
                displaySnackbar(itemData + " removed", "Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mRecyclerAdapter.addItem(pos, itemData);
                    }
                });
                return true; //true will move the front view to its starting position
            }

            @Override
            public boolean swipeRight(final String itemData) {
                final int pos = mRecyclerAdapter.removeItem(itemData);
                displaySnackbar(itemData + " removed", "Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mRecyclerAdapter.addItem(pos, itemData);
                    }
                });
                return true;
            }

            @Override
            public void onClick(String itemData) {
                //do something
            }

            @Override
            public void onLongClick(String itemData) {
                //do something
            }
        });
    }

    private void displaySnackbar(String text, String actionName, View.OnClickListener action) {
        Snackbar snack = Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG)
                .setAction(actionName, action);

        View v = snack.getView();
        v.setBackgroundColor(ContextCompat.getColor(this, R.color.background_floating_material_light));
        ((TextView) v.findViewById(android.support.design.R.id.snackbar_text)).setTextColor(ContextCompat.getColor(this, R.color.primary_text_default_material_light));
        ((TextView) v.findViewById(android.support.design.R.id.snackbar_action)).setTextColor(ContextCompat.getColor(this, R.color.accent_material_light));

        snack.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_class_enter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_done) {
            String[] courses = mRecyclerAdapter.getFinalData();
            if (courses.length == 0){
                new MaterialDialog.Builder(this)
                        .title("You have no courses")
                        .content("Press the plus button to add a class.")
                        .positiveText("OK")
                        .show();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void add(View v){
        new MaterialDialog.Builder(this)
                .title("Add Course")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .positiveText("ADD")
                .input(null, null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        if (input.length() == 0) {
                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(false);
                            classToAdd = null;
                        } else {
                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(true);
                            classToAdd = input.toString();
                        }
                    }
                }).alwaysCallInputCallback()
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (classToAdd != null) {
                            mRecyclerAdapter.addItem(classToAdd);
                            classToAdd = null;
                        }
                    }
                }).show();
        // add a new dialog fragment for class name
    }


}
