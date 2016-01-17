package net.classconnect.classconnect;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.HashSet;
import java.util.List;

/**
 * Created by Gilbert on 1/17/2016.
 */
public class MasterClass {

    private String facebookID;
    private String name;
    private List<String> friendsList;
    private List<String> courses;

    public MasterClass(String facebookID, String name, List<String> friendsList, List<String> courses) {
        this.facebookID = facebookID;
        this.name = name;
        this.friendsList = friendsList;
        this.courses = courses;
        addToCourses();
    }

    public void addToCourses() {

        // booleans of courses
        boolean [] coursesPlaced = new boolean[courses.size()];
        int counter = 0;
        int i = 0;

        while(counter < coursesPlaced.length && i < friendsList.size()) {

            String currentFriend = friendsList.get(i);

            ParseQuery<ParseObject> currentQuery = ParseQuery.getQuery("Student");
            currentQuery.whereEqualTo("name", currentFriend);
            currentQuery.getFirstInBackground(new GetCallback<ParseObject>()
            {
                public void done(ParseObject object, ParseException e)
                {
                    if (e == null)
                    {
                        List<String> currentList = courses;
                        List<String> otherList = object.getList("courses");
                        currentList.retainAll(new HashSet<String>(otherList));
                        // Classes that we share, add myself to that friend's classes

                        if(currentList.size() != 0) {
                            counter += currentList.size();
                            
                        }
                    }
                    else
                    {
                        //Display that the user does not having matching friends/classes
                        //Do not throw exception or else stack will terminate/App crash
                    }
                }
            });


            i++;
        }

        // If counter != coursesPlaced, iterate through coursesPlaced array and create groups with the original student in them
        if(counter < coursesPlaced.length) {
            for(i = 0; i < coursesPlaced.length; i++) {
                if(!coursesPlaced[i]) {
                    // Then create a new group!


                }
            }
        }

    }
}
