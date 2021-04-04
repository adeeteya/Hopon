package com.aditya.hopon;

public class Patterns {
    int mode;
    String name;
    String sequence;
    String author;
    String uid;
    String pid;
    public Patterns(){}
    public Patterns(int mode, String name, String sequence, String author,String uid,String pid) {
        this.mode = mode;
        this.name = name;
        this.sequence = sequence;
        this.author = author;
        this.uid=uid;
        this.pid=pid;
    }

    public String getUid(){return uid;}

    public String getPid(){return pid;}

    public int getMode() {
        return mode;
    }

    public String getName() {
        return name;
    }

    public String getSequence() {
        return sequence;
    }

    public String getAuthor() {
        return author;
    }
}
