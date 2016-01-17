package net.classconnect.classconnect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.model.AppGroupCreationContent;
import com.facebook.share.widget.CreateAppGroupDialog;
import com.facebook.share.widget.JoinAppGroupDialog;

public class CourseListActivity extends AppCompatActivity  {

    CreateAppGroupDialog createAppGroupDialog;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                new CollapsingToolbarFragment(),"Collapsing Fragment").commit();

        callbackManager = CallbackManager.Factory.create();
        createAppGroupDialog = new CreateAppGroupDialog(this);
        createAppGroupDialog.registerCallback(callbackManager, new FacebookCallback<CreateAppGroupDialog.Result>() {
            public void onSuccess(CreateAppGroupDialog.Result result) {
                String id = result.getId();
                Log.d("GROUP", id);
            }

            public void onCancel() {
            }

            public void onError(FacebookException error) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void createGroup(String groupname, String description){
        AppGroupCreationContent content = new AppGroupCreationContent.Builder()
                .setName(groupname)
                .setDescription(description)
                .setAppGroupPrivacy(AppGroupCreationContent.AppGroupPrivacy.Closed)
                .build();
        createAppGroupDialog.show(content);
    }

    public void addUser(String group_id){
        JoinAppGroupDialog.show(this, group_id);
    }

}