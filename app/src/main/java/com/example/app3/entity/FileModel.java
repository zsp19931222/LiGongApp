package com.example.app3.entity;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by Administrator on 2017/11/24 0024.
 */

public class FileModel implements Serializable {

    private static final long serialVersionUID = 107180843872378188L;
    public String name;
    public String url;
    public int priority;

    public FileModel() {
        Random random = new Random();
        priority = random.nextInt(100);
    }
}
