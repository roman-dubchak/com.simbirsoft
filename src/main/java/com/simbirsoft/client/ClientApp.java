package com.simbirsoft.client;

import com.simbirsoft.web.UrlConnect;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class ClientApp {
    private static UrlConnect urlConnect;
    // TODO:
    //  1. Указать путь до папки, в которой сохраняем файл .html
    //  2. Указать url для скачивания и подсчета слов
    private static final String URLINPUT = "https://www.simbirsoft.com/";
    private static final String DIRNAME = "*/download";

    public static void main(String[] args) {
        try {
            urlConnect = new UrlConnect(URLINPUT);
            if (memoryInDisk(URLINPUT, urlConnect.getMemoryUrlFile())) {
                File fileHtml = createFileToURL(URLINPUT, urlConnect.getHttpConnection());
                String worlds = fileConvertString(fileHtml);
                splitWords(worlds);
                countUniqWord(splitWords(worlds));
            } else {
                String s = containInUrl(urlConnect.getHttpConnection());
                countUniqWord(splitWords(s));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnect.getHttpConnection().disconnect();
        }
    }

    private static String containInUrl(HttpURLConnection httpConnection) throws IOException {
        byte[] bytes = httpConnection.getInputStream().readAllBytes();
        return new String(bytes, StandardCharsets.UTF_8);
    }

    private static File createFileToURL(String urlConnect, HttpURLConnection httpURLConnectionGet) throws IOException {
        Path path = Path.of(DIRNAME);
        // TODO:
        //  3. Указать формат счиваемого файла,если требуется.
        File file = File.createTempFile("file-", ".html", new File(path.toString()));
        Files.copy(httpURLConnectionGet.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return file;
    }

    private static String fileConvertString(File file) throws IOException {
        StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader(
                new FileReader(file.getPath()));
        char[] buf = new char[1024];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        return fileData.toString();
    }

    private static String[] splitWords(String fileName) {
        String[] wordsInUrl = fileName.split("\\s*([^а-яА-ЯёЁa-zA-Z0-9]+)\\s*");
        return wordsInUrl;
    }

    private static void countUniqWord(String[] wordsInUrl) {
        String out;
        String result;
        String[] uniqWords = arrStringUniqWord(wordsInUrl);
        int count = 0;
        for (int i = 0; i < uniqWords.length - 1; i++) {
            if (!(uniqWords[i].equals(""))) {
                result = uniqWords[i];
                count++;
                for (int j = 1; j < wordsInUrl.length; j++) {
                    out = wordsInUrl[j];
                    if (result.equals(out)) count++;
                }
                System.out.printf("World: %s - %d items", result, count);
                System.out.println();
            }
        }
    }

    private static String[] arrStringUniqWord(String[] wordsInUrl) {
        return Arrays.stream(wordsInUrl).distinct().toArray(String[]::new);
    }

    private static boolean memoryInDisk(String urlInput, int fileSizeOnUrl) throws MalformedURLException {
        int memory = (int) Runtime.getRuntime().totalMemory();
        return memory > fileSizeOnUrl;
    }
}
