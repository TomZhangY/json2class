package com.tom.zhang.mavenProject.json2class.loader;


public interface Loader {
    Class<?> jsonToClass(String json);

    Class<?> jsonToClass(String json,boolean ifClean);
}