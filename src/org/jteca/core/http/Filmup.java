package org.jteca.core.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import org.jfacility.lang.Lang;
import org.jteca.core.Video;

/**
 *
 * @author luca
 */
public class Filmup extends AbstractHttp{
    private String urlTitle = "http://filmup.leonardo.it/cgi-bin/search.cgi?ps=10&fmt=long&q=&ul=%25%2Fsc_%25&x=51&y=11&m=all&wf=0020&wm=wrd&sy=0";
    private static String urlAll = "http://filmup.leonardo.it/cgi-bin/search.cgi?ps=10&fmt=long&q=&ul=%25%2Fsc_%25&x=25&y=8&m=all&wf=2221&wm=wrd&sy=0";

    public Filmup(int timeout) {
        super(timeout);
    }

    public Filmup() {
        super();
    }
    @Override
    ArrayList<String[]> queryTitle(URL u, String query)
            throws ClientProtocolException, IOException{
        String url = u.toString();
        String key = "q=";
        String newUrl = url.replaceAll(key, key + query.replaceAll(" ", "+"));
        ArrayList<String[]> results = null;
        String notfound = "Ci dispiace, la ricerca di <b>";
        String found = "	  <small>Trovati <b>";
        String scheda = "    FilmUP - Scheda: ";

        BufferedReader br = getEntityContent(newUrl);
        String line;
        int totale = -1;
        int conta = 0;
        while ((line = br.readLine()) != null) {
            if (line.length()>notfound.length() &&
                    line.substring(0, notfound.length()).equalsIgnoreCase(notfound))
                break;
            if (line.length()>found.length() &&
                    line.substring(0, found.length()).equalsIgnoreCase(found)){
                results = new ArrayList<String[]>();
                totale = Lang.stringToInt(line.split("<B>")[1].split("</B>")[0]);
            }
            String dt = "  <DT>"+(conta+1)+".";
            if (line.equalsIgnoreCase(dt)){
                line = jumpRows(1, br);
                String link = line.split("  <a class=\"filmup\" href=\"")[1].split("\"")[0];                
                line = jumpRows(1, br);
                if (line.substring(0, scheda.length()).equalsIgnoreCase(scheda)){
                    line = line.replaceAll("<span style=\"color: #f00; font-weight: bold;\">", "");
                    line = line.replaceAll("</span>", "");
                    results.add(new String[]{line.split(scheda)[1], link});
                }
                conta++;
            }
            if (conta==totale)
                break;
        }
        return results;
    }

    private void captureInfo(URL u, ArrayList<String[]> items) throws IOException {
        String title = "<font face=\"arial, helvetica\" size=\"3\"><b>";
        String base = "<td valign=\"top\" nowrap><font face=\"arial, helvetica\" size=\"2\">:&nbsp;</font></td>";
        String original = base.replaceAll("\">", "\">Titolo originale");
        String removefont = "<td valign=\"top\"><font face=\"arial, helvetica\" size=\"2\">";
        String country = base.replaceAll("\">", "\">Nazione");
        String year = base.replaceAll("\">", "\">Anno");
        String genere = base.replaceAll("\">", "\">Genere");
        String time = base.replaceAll("\">", "\">Durata");
        String director = base.replaceAll("\">", "\">Regia");
        String cast = base.replaceAll("\">", "\">Cast");
        String plot = "<font face=\"arial, helvetica\" size=\"2\">Trama:<br>";
        String playbill = "<font face=\"tahoma, arial, helvetica\" size=\"1\" color=\"#ffffff\"><b>La locandina</b></font><br>";
        String newUrl = u.getProtocol() + "://" + u.getHost();

        for (int i=0; i<items.size(); i++){
            boolean[] founds = {false, false, false, false, false, false, false, false, false, false};
            Video v = new Video();
            BufferedReader br = getEntityContent(items.get(i)[1]);
            String line;
            while ((line = br.readLine()) != null) {
                if (!founds[0] && line.length()>title.length() &&
                    line.substring(0, title.length()).equalsIgnoreCase(title)){
                    founds[0] = true;
                    v.setName(line.split(title)[1].split("</b>")[0]);
                } else if (!founds[1] && line.length()>=original.length() &&
                    line.substring(0, original.length()).equalsIgnoreCase(original)){
                    founds[1] = true;
                    line = jumpRows(1, br);
                    v.setOriginalTitle(line.split(removefont)[1].split("</font>")[0]);                    
                } else if (!founds[2] && line.length()>=country.length() &&
                    line.substring(0, country.length()).equalsIgnoreCase(country)){
                    founds[2] = true;
                    line = jumpRows(1, br);
                    v.setCountry(line.split(removefont)[1].split("</font>")[0]);
                } else if (!founds[3] && line.length()>=year.length() &&
                    line.substring(0, year.length()).equalsIgnoreCase(year)){
                    founds[3] = true;
                    line = jumpRows(1, br);
                    v.setYear(Lang.stringToInt(line.split(removefont)[1].split("</font>")[0]));
                } else if (!founds[4] && line.length()>=genere.length() &&
                    line.substring(0, genere.length()).equalsIgnoreCase(genere)){
                    founds[4] = true;
                    line = jumpRows(1, br);
                    System.out.println(line.split(removefont)[1].split("</font>")[0]);
                } else if (!founds[5] && line.length()>=time.length() &&
                    line.substring(0, time.length()).equalsIgnoreCase(time)){
                    founds[5] = true;
                    line = jumpRows(1, br);
                    String temp = line.split(removefont)[1].split("</font>")[0];
                    v.setLenght(Lang.stringToInt(temp.substring(0,temp.length()-1)));
                } else if (!founds[6] && line.length()>=director.length() &&
                    line.substring(0, director.length()).equalsIgnoreCase(director)){
                    founds[6] = true;
                    line = jumpRows(1, br);
                    System.out.println(line.split(removefont)[1].split("</font>")[0]);
                } else if (!founds[7] && line.length()>=cast.length() &&
                    line.substring(0, cast.length()).equalsIgnoreCase(cast)){
                    founds[7] = true;
                    line = jumpRows(1, br);
                    System.out.println(line.split(removefont)[1].split("</font>")[0]);
                } else if (!founds[8] && line.length()>=plot.length() &&
                    line.substring(0, plot.length()).equalsIgnoreCase(plot)){
                    founds[8] = true;
                    v.setPlot(line.split(plot)[1].split("</font>")[0]);
                } else if (!founds[9] && line.length()>=playbill.length() &&
                    line.substring(0, playbill.length()).equalsIgnoreCase(playbill)){
                    founds[9] = true;
                    line = jumpRows(8, br);
                    v.setPlaybill(newUrl+ "/"+ line.split("href=\"")[1].split("\"")[0]);
                    break;
                }
            }
            br = getEntityContent(v.getPlaybill());
            while ((line = br.readLine()) != null) {
                if (line.equalsIgnoreCase("<!-- POSTER / START -->")){
                    line = jumpRows(6, br);
                    v.setPlaybill(newUrl + line.split("\"")[1]);
                    break;
                }
            }
        } //end for
    }
    
    public static void main(String args[]){
        String query = "principe di persia";
        //String query = "arma letale";
        try {
            URL u = new URL(urlAll);
            Filmup h = new Filmup();
            ArrayList<String[]> results = h.queryTitle(u, query);
            h.captureInfo(u, results);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (ClientProtocolException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}