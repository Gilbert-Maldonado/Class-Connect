package net.classconnect.classconnect;

import java.util.List;

/**
 * Created by Gilbert on 1/16/2016.
 */
public class Student {

    private String name;
    private List<String> classes;

    public Student(String name, List<String> classes) {
        this.name = name;
        this.classes = classes;
    }
    
    public String getName() {
        return name;
    }

    public List<String> getClasses() {
        return classes;
    }
}
