package fr.istic.m2il.mmm.fetescience.models;

import java.util.Calendar;

/**
 * @author Ramadan Soumaila
 */

public class EventDuration {
    private Calendar start;
    private Calendar end;

    public Calendar getStart() {
        return start;
    }

    public void setStart(Calendar start) {
        this.start = start;
    }

    public Calendar getEnd() {
        return end;
    }

    public void setEnd(Calendar end) {
        this.end = end;
    }
}
