package net.classconnect.classconnect;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
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
    private List<String> coursesPlaced;

    public MasterClass(String facebookID, String name, List<String> friendsList, List<String> courses) {
        this.facebookID = facebookID;
        this.name = name;
        this.friendsList = friendsList;
        this.courses = courses;
        for(String current : courses) {
            coursesPlaced.add(current);
        }
        addToCourses();
    }

    public void addToCourses() {
        int i = 0;

        while(coursesPlaced.size() > 0 && i < friendsList.size()) {

            String currentFriend = friendsList.get(i);

            ParseQuery<ParseObject> currentQuery = ParseQuery.getQuery("Student");
            currentQuery.whereEqualTo("name", currentFriend);
            currentQuery.getFirstInBackground(new GetCallback<ParseObject>() {
                public void done(ParseObject object, ParseException e) {
                    if (e == null) {
                        List<String> currentList = courses;
                        List<String> otherList = object.getList("courses");
                        currentList.retainAll(new HashSet<String>(otherList));
                        // Classes that we share, add myself to that friend's classes

                        for (int j = 0; j < currentList.size(); j++) {
                            String currentValue = currentList.get(j);
                            // Check if the String that is in common is in coursesPlaced (Purpose to avoid repetition)
                            if (coursesPlaced.contains(currentValue)) {
                                // Add myself into that class

                                // Then remove from list
                                coursesPlaced.remove(currentValue);

                            }
                        }

                    } else {
                        //Display that the user does not having matching friends/classes
                        //Do not throw exception or else stack will terminate/App crash
                    }
                }
            });


            i++;
        }

        // If counter != coursesPlaced, iterate through coursesPlaced array and create groups with the original student in them
        if(coursesPlaced.size() > 0) {
            for(i = 0; i < coursesPlaced.length; i++) {
                // Then create a new group!

            }
        }

    }
}
