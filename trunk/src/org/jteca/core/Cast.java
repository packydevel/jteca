package org.jteca.core;
/**
 *
 * @author luca
 */
public class Cast {
    private String actor, character, dubber;

    public Cast(String actor, String character, String dubber) {
        this.actor = actor;
        this.character = character;
        this.dubber = dubber;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getDubber() {
        return dubber;
    }

    public void setDubber(String dubber) {
        this.dubber = dubber;
    }
}