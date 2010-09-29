package org.jteca.core;

import java.util.ArrayList;
/**
 *
 * @author luca
 */
public class Film extends Video{
    private ArrayList<String> actors, directors;

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(ArrayList<String> actors) {
        this.actors = actors;
    }

    public ArrayList<String> getDirectors() {
        return directors;
    }

    public void setDirectors(ArrayList<String> directors) {
        this.directors = directors;
    }
}