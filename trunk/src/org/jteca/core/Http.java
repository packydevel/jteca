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

    private void queryTitle(String url, String query) throws ClientProtocolException, IOException{
        String newUrl = url+query.replaceAll(" ", "+");
        String result = " <p><b>Titoli popolari</b>";

        get = new HttpGet(url);
        response = client.execute(get);
        entity = response.getEntity();
        cookies = client.getCookieStore();
        if (entity!=null)
            entity.consumeContent();
        
        get = new HttpGet(newUrl);
        response = client.execute(get);
        entity = response.getEntity();
        BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
        String line;
        int len = result.length();
        while ((line = br.readLine()) != null) {            
            if (line.length()>len && line.substring(0, len).equalsIgnoreCase(result)){
                System.out.println(line);
                String[] array = line.split("<td");
                for (int x=0; x<array.length; x++)
                    System.out.println(array[x]);
                break;
            }
        }
        if (line!=null){

        }
    } //end queryTitle

    public static void main(String args[]){
        Http h = new Http();
        String url = "http://www.imdb.it/find?s=tt&q=";
        String query = "principe di persia";
        try {
            h.queryTitle(url, query);

        } catch (ClientProtocolException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}