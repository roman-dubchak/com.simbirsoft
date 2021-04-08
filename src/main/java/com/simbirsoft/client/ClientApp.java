package com.simbirsoft.client;

import com.simbirsoft.service.ParserUrlToWords;
import com.simbirsoft.web.UrlConnect;

import java.io.*;
import java.net.*;

public class ClientApp {
    private static UrlConnect urlConnect;
    private static ParserUrlToWords parserUrlToWords;
    // TODO:
    //  1. Указать путь до папки, в которой сохраняем файл .html
    //  2. Указать url для скачивания и подсчета слов
    //  3. Указать префикс названия файла и суффикс - расширение файла

    private static final String URLINPUT = "https://www.simbirsoft.com/";
    private static final String DIRNAME = "d:/Java/test_task/simbirsoft/CountWordsInUrlApp/downloadhtml";
    private final static String PREFIXFILENAME = "file-";
    private final static String SUFFIXFILENAME = ".html";

    public static void main(String[] args) {
        try {
            urlConnect = new UrlConnect(URLINPUT);
            parserUrlToWords = new ParserUrlToWords(PREFIXFILENAME, SUFFIXFILENAME);
            if (memoryInDisk(URLINPUT, urlConnect.getMemoryUrlFile())) {
                File fileHtml = parserUrlToWords.createFileToURL(URLINPUT, urlConnect.getHttpConnection(), DIRNAME);
                String worlds = parserUrlToWords.fileConvertString(fileHtml);
                parserUrlToWords.countUniqWord(parserUrlToWords.splitWords(worlds));
            } else {
                String s = parserUrlToWords.containInUrl(urlConnect.getHttpConnection());
                parserUrlToWords.countUniqWord(parserUrlToWords.splitWords(s));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnect.getHttpConnection().disconnect();
        }
    }

    private static boolean memoryInDisk(String urlInput, int fileSizeOnUrl) throws MalformedURLException {
        int memory = (int) Runtime.getRuntime().totalMemory();
        return memory > fileSizeOnUrl;
    }

}
