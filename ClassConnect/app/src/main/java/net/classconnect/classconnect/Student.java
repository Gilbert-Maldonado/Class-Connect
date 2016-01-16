package net.classconnect.classconnect;

import java.util.List;
import com.parse.ParseObject;

/**
 * Created by Gilbert on 1/16/2016.
 */
public class Student
{

    //Class Variables
    private String name;
    private String facebookID;
    private List<String> classes;
    private List<String> friendsList;

    //Default constructor to implement all variables
    public Student(String facebookID, String name, List<String> classes, List<String> friendsList)
    {
        if(facebookID == null || name == null || classes == null || classes.size() == 0 || friendsList == null
                || friendsList.size() == 0) {
            throw new IllegalArgumentException("ERROR: Invalid parameters in Student class Constructor");
        }
        this.facebookID = facebookID;
        this.name = name;
        this.classes = classes;
        this.friendsList = friendsList;
        addToParse();
    }

    //Uploads data to parse server as student object
    private void addToParse()
    {
        ParseObject studentObj = new ParseObject("Student");
        studentObj.put("name", name);
        studentObj.put("facebookID", facebookID);
        studentObj.addAllUnique("courses", classes);
        studentObj.addAllUnique("friendsList", friendsList);
        studentObj.saveInBackground();
    }


}
