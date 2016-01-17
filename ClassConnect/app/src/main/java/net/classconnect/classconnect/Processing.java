package net.classconnect.classconnect;

/**
 * Created by Yms on 1/16/2016.
 */

import android.util.Log;

import java.util.*;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;

public class Processing
{
    private String name;
    private List<String> friends;
    private Student curObject;
    private List<String> curClasses;


    public Processing(String input, Student currentStudent)
    {
        name = input;
        curObject = currentStudent;
        curClasses = currentStudent.returnClasses();
    }

    public void setFriends()
    {
        friends = curObject.returnFriends();
    }

    public void getMatches()
    {
        for(int i = 0; i < friends.size(); i++)
        {
            String curName = friends.get(i);
            ParseQuery<ParseObject> curQuery = ParseQuery.getQuery("Student");
            curQuery.whereEqualTo("name", curName);
            curQuery.getFirstInBackground(new GetCallback<ParseObject>()
            {
                public void done(ParseObject object, ParseException e)
                {
                    if (e == null)
                    {
                        List<String> curList = curClasses;
                        List<String> otherList = object.getList("courses");
                        curList.retainAll(new HashSet<String>(otherList));

                        //TESTING CODE ONLY!!! DELETE WHEN DONE
                        ParseObject similar = new ParseObject("SimilarClasses");
                        JSONArray newArray = new JSONArray();
                        for (int j = 0; j < curList.size(); j++)
                        {
                            newArray.put(curList.get(j));
                        }
                        similar.put("Matched Classes", newArray);
                        similar.saveInBackground();
                        //TESTING CODE ENDS HERE
                    }
                    else
                    {
                        //Display that the user does not having matching friends/classes
                        //Do not throw exception or else stack will terminate/App crash
                    }
                }
            });
        }
    }


}
