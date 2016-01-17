package net.classconnect.classconnect;

import java.util.List;

/**
 * Created by Sagar on 6/14/2015.
 */
public class CardItemModel {

    public String title;
    public String content;
    public String id;
    public boolean empty;

    public CardItemModel(String title, List<String> content, String id, boolean empty) {
        this.title = title;
        this.empty = true;
        StringBuilder connected = new StringBuilder();
        if(!empty && content != null ){
            for(String s: content){
                connected.append(s + "\n");
            }
            this.empty = false;
        }
        this.content = connected.toString();
        this.id = id;
    }
    public CardItemModel(String title, String content, String id, boolean empty) {
        this.title = title;
        this.content = content;
        this.id = id;
        this.empty = empty;
    }
}
