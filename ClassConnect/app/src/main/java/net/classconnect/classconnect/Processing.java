package net.classconnect.classconnect;

/**
 * Created by Yms on 1/16/2016.
 */

import java.util.*;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class Processing
{
    private String name;
    private List<String> friends;
    private ParseObject curObject;


    public Processing(String input, ParseObject currentStudent)
    {
        name = input;
        curObject = currentStudent;
    }

    public void setFriends()
    {
        friends = curObject.getList("friendsList");
    }

    public void getMatches()
    {
        for(int i = 0; i < friends.size(); i++)
        {
            String curName = friends.get(i);
            ParseQuery<ParseObject> curQuery = ParseQuery.getQuery("facebookID");
            curQuery.getInBackground(curName, new GetCallback<ParseObject>()
            {
                public void done(ParseObject object, ParseException e)
                {
                    if(e == null)
                    {

                    }
                    else
                    {

                    }
                }
            });
        }
    }


}
