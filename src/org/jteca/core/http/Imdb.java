package org.jteca.core.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
/**
 *
 * @author luca
 */
public class Imdb extends AbstractHttp{
    private ArrayList<String[]> queryTitle(URL u, String query)
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

        for (int i=0; i<items.size(); i++){
            BufferedReader br = getEntityContent(url+items.get(i)[1]);
            String line;
            while ((line = br.readLine()) != null) {
                if (line.length()>tag_titleB.length() &&
                    line.substring(0, tag_titleB.length()).equalsIgnoreCase(tag_titleB)){
                    System.out.println(line);
                } else if (line.length()>poster.length() &&
                    line.substring(0, poster.length()).equalsIgnoreCase(poster)){
                    System.out.println(line);
                } 
                //System.out.println(line);
            }
        }
    }    

    public static void main(String args[]){
        String query = "principe di persia";        
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