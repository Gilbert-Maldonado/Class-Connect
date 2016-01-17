package net.classconnect.classconnect;

/**
 * Created by Yms on 1/16/2016.
 */

import android.util.Log;

import java.util.*;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

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
            Log.d("Break 1", "Entered Loop");
            String curName = friends.get(i);
            Log.d("Break 2", "Before ParseQuery " + curName);
            ParseQuery<ParseObject> curQuery = ParseQuery.getQuery("Student");
            curQuery.getInBackground(curName, new GetCallback<ParseObject>()
            {
                public void done(ParseObject object, ParseException e)
                {
                    if(e == null)
                    {
                        Log.d("Break 3", "Entered e == null");
                        List<String> curList = curClasses;
                        List<String> otherList = object.getList("courses");
                        curList.retainAll(new HashSet<String>(otherList));

                        //TESTING CODE ONLY!!! DELETE WHEN DONE
                        ParseObject similar = new ParseObject("SimilarClasses");
                        for(int j = 0; j < curList.size(); j++)
                        {
                            Log.d("courses: ", curList.get(j));
                            similar.put("class " + j + ": ", curList.get(j));
                        }
                        similar.saveInBackground();
                        //TESTING CODE ENDS HERE
                    }
                    else
                    {
                        Log.d("Break 4", "Entered Exception MUST FIX");
                        //Display that the user does not having matching friends/classes
                        //Do not throw exception or else stack will terminate/App crash
                    }
                }
            });
        }
    }


}
