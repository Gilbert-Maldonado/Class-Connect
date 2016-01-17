package net.classconnect.classconnect;

/**
 * Created by Sagar on 6/14/2015.
 */
public class CardItemModel {

    public String title;
    public String content;
    public String id;
    public boolean empty;

    public CardItemModel(String title, String content, String id, boolean empty) {
        this.title = title;
        this.content = content;
        this.id = id;
        this.empty = empty;
    }
}
