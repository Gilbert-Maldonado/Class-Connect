package net.classconnect.classconnect;

import java.util.List;
import com.parse.ParseObject;

/**
 * Created by Gilbert on 1/16/2016.
 */
public class Student
{

    private String name;
    private String facebookID;
    private List<String> classes;
    private List<String> friendsList;

    public Student(String facebookID, String name, List<String> classes, List<String> friendsList)
    {
        this.facebookID = facebookID;
        this.name = name;
        this.classes = classes;
        this.friendsList = friendsList;
    }

}
