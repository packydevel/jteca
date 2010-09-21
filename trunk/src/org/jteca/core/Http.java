package org.jteca.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

/**
 *
 * @author luca
 */
public class Http {
    private DefaultHttpClient client;
    private HttpResponse response;
    private HttpEntity entity;
    private HttpGet get;
    private CookieStore cookies;
    /**Costruttore, inizializza il client http
     *
     */
    Http(){
        client = new DefaultHttpClient(new BasicHttpParams());
        client.getParams().setParameter(ClientPNames.COOKIE_POLICY,
                CookiePolicy.BROWSER_COMPATIBILITY);
    }
    /**Costruttore, inizializza il client http con timeout
     *
     * @param timeout tempo di scadenza connessione
     */
    Http(int timeout){
        HttpParams my_httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(my_httpParams, timeout);
        HttpConnectionParams.setSoTimeout(my_httpParams, timeout);
        client = new DefaultHttpClient(my_httpParams);
        client.getParams().setParameter(ClientPNames.COOKIE_POLICY,
                CookiePolicy.BROWSER_COMPATIBILITY);
    }

    private void queryTitle(String url, String query)
            throws ClientProtocolException, IOException{        
        String title_popular = " <p><b>Titoli popolari</b>";
        String title_approx = " <p><b>Titoli  (risultati approssimati)</b>";
        String table_begin = "<table>";
        String table_end = "</table>";
        String table_tr_begin = "<tr>";
        String a_href = "<a href=\"";

        get = new HttpGet(url);
        response = client.execute(get);
        entity = response.getEntity();
        cookies = client.getCookieStore();
        if (entity!=null)
            entity.consumeContent();
        
        String newUrl = url+query.replaceAll(" ", "+");
        get = new HttpGet(newUrl);
        response = client.execute(get);
        entity = response.getEntity();
        BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
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
            String[] array = line.split(table_begin);
            array = array[1].split(table_end);
            array = array[0].split(table_tr_begin);
            for (int i=1; i<array.length; i++){
                String[] array_single = array[i].split("<td ");
                array_single = array_single[array_single.length-1].split(a_href);
                String temp = array_single[1];
                String link = temp.split("\"")[0];
                System.out.println(link);
                array_single = temp.split("\">");
                String name = array_single[1].split("</a>")[0];
                System.out.println(name);
            }
        }
    } //end queryTitle

    public static void main(String args[]){        
        String url = "http://www.imdb.it/find?s=tt&q=";
        String query = "arma letale";
        try {
            Http h = new Http();
            h.queryTitle(url, query);            
        } catch (ClientProtocolException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}