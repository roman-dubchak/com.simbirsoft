package com.simbirsoft.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

public class ParserUrlToWords {
    private final String prefixFileName; // = "file-";
    private final String suffixFileName; // = ".html";

    public ParserUrlToWords(String prefixFileName, String suffixFileName) {
        this.prefixFileName = prefixFileName;
        this.suffixFileName = suffixFileName;
    }

    public String containInUrl(HttpURLConnection httpConnection) throws IOException {
        byte[] bytes = httpConnection.getInputStream().readAllBytes();
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public File createFileToURL(String urlConnect, HttpURLConnection httpURLConnectionGet, String dirname) throws IOException {
        Path path = Path.of(dirname);
        File file = File.createTempFile(prefixFileName, suffixFileName, new File(path.toString()));
        Files.copy(httpURLConnectionGet.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return file;
    }

    public String fileConvertString(File file) throws IOException {
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

    public String[] splitWords(String fileName) {
        String[] wordsInUrl = fileName.split("\\s*([^а-яА-ЯёЁa-zA-Z0-9]+)\\s*");
        return wordsInUrl;
    }

    public void countUniqWord(String[] wordsInUrl) {
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

    public String[] arrStringUniqWord(String[] wordsInUrl) {
        return Arrays.stream(wordsInUrl).distinct().toArray(String[]::new);
    }
}
