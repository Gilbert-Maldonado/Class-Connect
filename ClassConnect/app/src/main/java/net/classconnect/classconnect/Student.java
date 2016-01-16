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
        this.facebookID = facebookID;
        this.name = name;
        this.classes = classes;
        this.friendsList = friendsList;
    }

    //Uploads data to parse server as student object
    public void parse()
    {
        ParseObject studentObj = new ParseObject(name);
        studentObj.put("ID: ", facebookID);
        studentObj.put("Classes: ", classes);
        studentObj.put("Friends: ", friendsList);
        studentObj.saveInBackground();
    }


}
