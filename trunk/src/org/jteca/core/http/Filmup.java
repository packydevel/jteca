package org.jteca.core.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.apache.http.client.ClientProtocolException;
import org.jfacility.lang.Lang;

/**
 *
 * @author luca
 */
public class Filmup extends AbstractHttp{
    private String urlTitle = "http://filmup.leonardo.it/cgi-bin/search.cgi?ps=10&fmt=long&q=&ul=%25%2Fsc_%25&x=51&y=11&m=all&wf=0020&wm=wrd&sy=0";
    private static String urlAll = "http://filmup.leonardo.it/cgi-bin/search.cgi?ps=10&fmt=long&q=&ul=%25%2Fsc_%25&x=25&y=8&m=all&wf=2221&wm=wrd&sy=0";

    ArrayList<String[]> queryTitle(URL u, String query)
            throws ClientProtocolException, IOException{
        String url = u.toString();
        String key = "q=";
        String newUrl = url.replaceAll(key, key + query.replaceAll(" ", "+"));
        ArrayList<String[]> results = null;
        String notfound = "Ci dispiace, la ricerca di <b>";
        String found = "	  <small>Trovati <b>";

        BufferedReader br = getEntityContent(newUrl);
        String line;
        int totale = -1;
        int conta = 0;
        while ((line = br.readLine()) != null) {
            if (line.length()>notfound.length() &&
                    line.substring(0, notfound.length()).equalsIgnoreCase(notfound))
                break;
            if (line.length()>found.length() &&
                    line.substring(0, found.length()).equalsIgnoreCase(found))
                totale = Lang.stringToInt(line.split("<B>")[1].split("</B>")[0]);
            String dt = "  <DT>"+(conta+1)+".";
            if (line.equalsIgnoreCase(dt)){
                line = jumpRows(1, br);
                String link = line.split("  <a class=\"filmup\" href=\"")[1].split("\"")[0];
                System.out.println(link);
                line = jumpRows(1, br);
                System.out.println(line);
                conta++;
            }
            if (conta==totale)
                break;

            /*
  <a class="filmup" href="http://filmup.leonardo.it/sc_princeofpersia.htm" TARGET="_blank">

    FilmUP - Scheda: Prince of <span style="color: #f00; font-weight: bold;">Persia</span>: Le sabbie del tempo
  </a>

             */
        }
        return results;
    }
    
    public static void main(String args[]){
        String query = "principe di persia";
        try {
            URL u = new URL(urlAll);
            Filmup h = new Filmup();
            ArrayList<String[]> results = h.queryTitle(u, query);
            //h.captureInfo(u, results);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (ClientProtocolException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}