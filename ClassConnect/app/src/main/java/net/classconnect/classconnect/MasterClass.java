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
    private int counter;
    private String currentFriend;

    public MasterClass(String facebookID, String name, List<String> friendsList, List<String> courses) {
        this.facebookID = facebookID;
        this.name = name;
        this.friendsList = friendsList;
        this.courses = courses;
        counter = 0;
        currentFriend = null;
        for(String current : courses) {
            coursesPlaced.add(current);
        }
        addToCourses();
    }

    public void addToCourses() {
        int i = 0;

        while(coursesPlaced.size() > 0 && i < friendsList.size()) {

            currentFriend = friendsList.get(i);

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
                                ParseQuery<ParseObject> querryToAdd = ParseQuery.getQuery("Classes");
                                querryToAdd.whereEqualTo("courseName", currentValue);
                                querryToAdd.getFirstInBackground(new GetCallback<ParseObject>() {
                                    public void done(ParseObject object2, ParseException e2) {
                                        if (e2 == null) {
                                            counter = 0;
                                            boolean isUpdated = false;

                                            while (!isUpdated) {
                                                String currentCourseGroup = "" + counter;
                                                List<String> temp = object2.getList(currentCourseGroup);
                                                if (temp.contains(currentFriend)) {
                                                    temp.add(name);
                                                    isUpdated = true;
                                                }
                                                counter++;
                                                object2.addAllUnique(currentCourseGroup, temp);
                                            }
                                        } else {
                                            //Exception that we are not going to throw lol
                                        }
                                    }
                                });
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

        // Create the class and add the group or just add the group
        // If the size is greater than 0, iterate through coursesPlaced list and create groups with the original student in them
        if(coursesPlaced.size() > 0) {
            for(i = 0; i < coursesPlaced.size(); i++) {
                // Then create a new group!
                ParseObject object = new ParseObject("Classes");
                
                currentQuery.whereEqualTo("name", currentFriend);

            }
        }

    }
}
