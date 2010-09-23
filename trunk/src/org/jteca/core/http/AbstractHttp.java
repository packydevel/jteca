package org.jteca.core.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
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
abstract class AbstractHttp {
    protected DefaultHttpClient client;
    protected HttpEntity entity;
    String tag_tableB = "<table>";
    String tag_tableE = "</table>";
    String tag_titleB = "<title>";
    String tag_titleE = "</title>";
    /**Costruttore, inizializza il client http
     *
     */
    AbstractHttp(){
        client = new DefaultHttpClient(new BasicHttpParams());
        client.getParams().setParameter(ClientPNames.COOKIE_POLICY,
                CookiePolicy.BROWSER_COMPATIBILITY);
    }
    /**Costruttore, inizializza il client http con timeout
     *
     * @param timeout tempo di scadenza connessione
     */
    AbstractHttp(int timeout){
        HttpParams my_httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(my_httpParams, timeout);
        HttpConnectionParams.setSoTimeout(my_httpParams, timeout);
        client = new DefaultHttpClient(my_httpParams);
        client.getParams().setParameter(ClientPNames.COOKIE_POLICY,
                CookiePolicy.BROWSER_COMPATIBILITY);
    }

    BufferedReader getEntityContent(String url) throws IOException{
        if (entity!=null)
            entity.consumeContent();
        entity = client.execute(new HttpGet(url)).getEntity();
        return new BufferedReader(new InputStreamReader(entity.getContent()));
    }
}