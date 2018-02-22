package fr.istic.m2il.mmm.fetescience.models;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ramadan Soumaila
 */

public class Path {

    private Long authorId;
    private String author;
    private String comment;
    private List<Event> events;

    public Path(){
        events = new ArrayList<>();
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
