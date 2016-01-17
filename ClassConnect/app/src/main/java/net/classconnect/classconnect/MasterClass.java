package net.classconnect.classconnect;

import android.util.Log;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
    private String temp15;
    private Map<String, List<String>> courseAttendeesMap;
    private Map<String, Boolean> map;
    private String addToGroupName;
    private String addToGroupID;
    private String getGroupIDName;
    private String getGroupIDOutput;
    private static String IDOutput;

    public MasterClass(String facebookID, String name, List<String> friendsList, List<String> courses) {
        this.facebookID = facebookID;
        this.name = name;
        this.friendsList = friendsList;
        this.courses = courses;
        counter = 0;
        getGroupIDOutput = null;
        getGroupIDName = null;
        currentFriend = null;
        temp15 = null;
        IDOutput = null;

        courseAttendeesMap = new TreeMap<String, List<String>>();
        addToGroupName = null;
        addToGroupID = null;
        map = new TreeMap<String, Boolean>();
        for(String temp : courses) {
            map.put(temp, true);
            courseAttendeesMap.put(temp, null);
        }
        coursesPlaced = new ArrayList<>();
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
                            final String currentValue = currentList.get(j);
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
                                                String currentCourseGroup = "L" + counter;
                                                List<String> temp = object2.getList(currentCourseGroup);
                                                if (temp.contains(currentFriend)) {
                                                    courseAttendeesMap.put(currentValue, new ArrayList<String>(temp));
                                                    map.put(currentValue, false);
                                                    temp.add(name);
                                                    isUpdated = true;
                                                }
                                                counter++;
                                                object2.addAllUnique(currentCourseGroup, temp);
                                                object2.saveInBackground();
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
            for (i = 0; i < coursesPlaced.size(); i++) {
                temp15 = coursesPlaced.get(i);
                // Then create a new group!
                ParseQuery<ParseObject> currentQuery = ParseQuery.getQuery("Classes");
                currentQuery.whereEqualTo("courseName", temp15);
                currentQuery.getFirstInBackground(new GetCallback<ParseObject>() {
                    public void done(ParseObject object, ParseException e) {
                        // If e == null then it exists and we just add a group
                        if (e == null) {
                            courseAttendeesMap.put(temp15, new ArrayList<String>());
                            Log.e("MAP - ERROR", "omg i dont even know");
                            int current = object.getInt("counter");
                            List<String> list = new ArrayList<String>();
                            list.add(name);
                            object.add("L" + current, list);
                            current++;
                            object.put("counter", current);
                            try {
                                object.save();
                            } catch (ParseException a) {
                                a.printStackTrace();
                            }
                            map.put(temp15, true);

                        } else { // Else then we create a class/with counter and then add the group
                            courseAttendeesMap.put(temp15, new ArrayList<String>());
                            map.put(temp15, true);
                            Log.e("MAP - ERROR2 help help", "omg i dont even know" + temp15);
                            ParseObject newClass = new ParseObject("Classes");
                            newClass.put("courseName", new String(temp15));
                            List<String> temp = new ArrayList<String>();
                            temp.add(name);
                            newClass.addAllUnique("L0", temp);
                            newClass.put("counter", 1);
                            try {
                                newClass.save();
                            } catch (ParseException a) {
                                a.printStackTrace();
                            }
                            Log.e("MAP - ERROR!!!!!", "omg i dont even know");
                            courseAttendeesMap.put(temp15, new ArrayList<String>());
                        }
                    }

                });
            }
        }
        CourseListActivity.LcourseAttendeesMap = this.courseAttendeesMap;
        CourseListActivity.Lmap = this.map;
    }

    public Map<String, List<String>> getCourseAttendeesMap() {
        CourseListActivity.LcourseAttendeesMap = this.courseAttendeesMap;
        Log.d("MAP1", courseAttendeesMap.toString());
        return courseAttendeesMap;
    }

    public Map<String, Boolean> getMap() {
        CourseListActivity.Lmap = this.map;
        Log.d("MAP1", map.toString());
        return map;
    }


    public String getFacebookID() {
        return facebookID;
    }

    public String getName() {
        return name;
    }

    // Input: Name of person, course name, groupID
    // Output: Find the group by person name and course name and add the ID to it.
    public static void addGroupID(final String name, final String courseName, final String groupID) {

        ParseQuery<ParseObject> currentQuery = ParseQuery.getQuery("Classes");
        currentQuery.whereEqualTo("courseName", courseName);
        currentQuery.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                boolean found = false;
                int count = 0;

                while(!found) {
                    List<String> temp = object.getList("L" + count);
                    if(temp.contains(name)) {
                        object.put("group" + count, groupID);
                        object.saveInBackground();
                        found = true;
                    }
                    count++;
                }
            }
        });


    }

    // Input: CourseName, Student name
    // Output: GroupID
    public static String getGroupID(final String courseName, final String name) {

        ParseQuery<ParseObject> currentQuery = ParseQuery.getQuery("Classes");
        currentQuery.whereEqualTo("courseName", courseName);
        currentQuery.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {

                boolean found = false;
                int count = 0;

                while(!found) {
                    List<String> temp = object.getList("L" + count);
                    if(temp.contains(name)) {
                        IDOutput = object.getString("group" + count);
                        found = true;
                    }
                    count++;
                }
            }
        });
        return IDOutput;
    }


}



