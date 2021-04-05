package com.simbirsoft.entity;

public class WordsStat {
    private String word;
    private int count;


    public WordsStat(String word, int count){
        this.word = word;
        this.count = count;
    }

    public WordsStat(){}

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setCount(String worldInFile) {
        if(this.word.equals(worldInFile))
            this.count++;
    }

    @Override
    public String toString() {
        return String.format("Words: %s - %d", word, count);
    }

//    public void countWords(String word, int count){
//        if(){
//        }
//        count++;
//    }
}
