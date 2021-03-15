package com.simbirsoft.web;

import java.io.IOException;
import java.net.*;

public class UrlConnect {
    private HttpURLConnection httpConnection = null;
    private int memoryUrlFile = 0;
    private String urlConnect;

    public UrlConnect(String urlConnect) throws IOException {
        this.urlConnect = urlConnect;
        this.httpConnection = httpURLConnectionGet(urlConnect);
        this.memoryUrlFile = fileSizeOnUrl(urlConnect);
    }

    public UrlConnect() {
    }

    private HttpURLConnection httpURLConnectionGet(String urlConnect) throws IOException {
        URL url = new URL(urlConnect);
        httpConnection = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);
        httpConnection.setDoInput(true);
        httpConnection.setUseCaches(false);
        httpConnection.setConnectTimeout(10000);
        httpConnection.setRequestMethod("GET");
        return httpConnection;
    }

    private int fileSizeOnUrl(String urlConnect) throws IOException {
        return new URL(urlConnect).openConnection().getContentLength();
    }

    public HttpURLConnection getHttpConnection() {
        return httpConnection;
    }

    public int getMemoryUrlFile() {
        return memoryUrlFile;
    }

}
