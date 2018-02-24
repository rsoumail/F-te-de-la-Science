package fr.istic.m2il.mmm.fetescience.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ramadan Soumaila
 */

public class Path {

    private String authorId;
    private String author;
    private String comment;
    private List<Integer> events;

    public Path(){
        events = new ArrayList<>();
    }

    public List<Integer> getEvents() {
        return events;
    }

    public void setEvents(List<Integer> events) {
        this.events = events;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public Map<String, Object> mapToFireBasePath(){
        Map<String, Object> fPath = new HashMap<>();
        fPath.put("authorId", this.authorId);
        fPath.put("author", this.author);
        fPath.put("comment", this.comment);
        fPath.put("events", this.events);
        return fPath;
    }
}
