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
public class Imdb extends AbstractHttp{
    ArrayList<String[]> queryTitle(URL u, String query)
            throws ClientProtocolException, IOException{
        String title_popular = " <p><b>Titoli popolari</b>";
        String title_approx = " <p><b>Titoli  (risultati approssimati)</b>";        
        String table_tr_begin = "<tr>";
        String a_href = "<a href=\"";
        String url = u.toString();
        ArrayList<String[]> results = null;
        
        String newUrl = url+query.replaceAll(" ", "+");        
        BufferedReader br = getEntityContent(newUrl);
        String line;
        int len = title_popular.length();
        boolean found_popular = false;
        while ((line = br.readLine()) != null) {
            if (line.length()>len &&
                    line.substring(0, len).equalsIgnoreCase(title_popular)){
                found_popular = true;
                break;
            }
        }
        if (found_popular){
            results = new ArrayList<String[]>();
            String[] array = line.split(tag_tableB);
            array = array[1].split(tag_tableE);
            array = array[0].split(table_tr_begin);
            for (int i=1; i<array.length; i++){
                String[] array_single = array[i].split("<td ");
                array_single = array_single[array_single.length-1].split(a_href);
                String temp = array_single[1];
                String link = temp.split("\"")[0];
                array_single = temp.split("\">");
                String name = array_single[1].split("</a>")[0];
                results.add(new String[]{name, link});
            }
        }
        return results;
    } //end queryTitle

    private void captureInfo(URL u, ArrayList<String[]> items) throws IOException{
        String url = "http://" + u.getHost();
        String poster = "<a name=\"poster\" href=\"";
        String regista = "        <h5>Regista:</h5>";
        String data = "<h5>Data di uscita:</h5>";
        String genere = "<h5>Genere:</h5>";
        String trama = "<h5>Trama:</h5>";
        String cast = "<hr/><div class=\"info\">  <div class=\"headerinline\"><h3>Cast</h3>";
        String dettagli = "<hr />      <h3>Dettagli aggiuntivi</h3>";

        for (int i=0; i<items.size(); i++){
            boolean[] founds = {false, false, false, false, false, false, false, false};
            Video v = new Video();
            BufferedReader br = getEntityContent(url+items.get(i)[1]);
            String line;
            while ((line = br.readLine()) != null) {
                if (!founds[0] && line.length()>tag_titleB.length() &&
                    line.substring(0, tag_titleB.length()).equalsIgnoreCase(tag_titleB)){
                    founds[0] = true;
                    v.setName(line.split(tag_titleB)[1].split(tag_titleE)[0]);
                } else if (!founds[1] && line.length()>=poster.length() &&
                    line.substring(0, poster.length()).equalsIgnoreCase(poster)){
                    founds[1] = true;
                    v.setPlaybill(url + line.split(poster)[1].split("\"")[0]);
                } else if (!founds[2] && line.equalsIgnoreCase(regista)){
                    founds[2] = true;
                    line = jumpRows(6, br);
                    System.out.println(line.split(">")[1].split("<")[0]);
                } else if (!founds[3] && line.equalsIgnoreCase(data)){
                    founds[3] = true;
                    line = jumpRows(2, br);
                    v.setYear(Lang.stringToInt(line.split(" ")[2]));
                } else if (!founds[4] && line.equalsIgnoreCase(genere)){
                    founds[4] = true;
                    line = jumpRows(2, br);
                    System.out.println(line);
                } else if (!founds[5] && line.equalsIgnoreCase(trama)){
                    String temp = "<a class=\"tn15more inline\" href=\"";
                    founds[5] = true;
                    line = jumpRows(3, br);
                    if (line.substring(0, temp.length()).equals(temp))
                        v.setPlot(url + line.split(temp)[1].split("\"")[0]);
                } else if (!founds[6] && line.length()>=cast.length() &&
                    line.substring(0, cast.length()).equalsIgnoreCase(cast)){
                    founds[6] = true;
                    System.out.println(line);
                } else if (!founds[7] && line.length()>=dettagli.length() &&
                    line.substring(0, dettagli.length()).equalsIgnoreCase(dettagli)){
                    founds[7] = true;
                    System.out.println(line);
                    break;
                }
            } // end while
            System.out.println(v.getPlaybill());
            System.out.println(v.getPlot());
        }
    }    

    public static void main(String args[]){
        String query = "arma letale";
        try {
            URL u = new URL("http://www.imdb.it/find?s=tt&q=");
            Imdb h = new Imdb();
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