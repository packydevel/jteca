package org.jteca.core;

import java.util.ArrayList;

import org.jteca.core.http.Cast;
/**
 * @author packyuser
 *
 */
public class Video extends Resource {
    private String originalTitle, plot, format, support, country, languages, subtitles;
    private int lenght, year;
    private ArrayList<Cast> cast;
    /**restituisce la nazione
     *
     * @return
     */
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    /**restituisce il formato
     *
     * @return
     */
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
    /**Restituisce la durata
     *
     * @return
     */
    public int getLenght() {
        return lenght;
    }

    public void setLenght(int lenght) {
        this.lenght = lenght;
    }
    /**restituisce il titolo originale
     *
     * @return
     */
    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }
    /**Restituisce la trama
     *
     * @return
     */
    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }
    /**Restituisce il supporto
     *
     * @return
     */
    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }
    /**Restituisce l'anno
     *
     * @return
     */
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    /**Restituisce le lingue
     *
     * @return
     */
    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }
    /**Restituisce i sottotitoli
     *
     * @return
     */
    public String getSubtitles() {
        return subtitles;
    }

    public void setSubtitles(String subtitles) {
        this.subtitles = subtitles;
    }

    public ArrayList<Cast> getCast() {
        return cast;
    }

    public void setCast(ArrayList<Cast> cast) {
        this.cast = cast;
    }
}