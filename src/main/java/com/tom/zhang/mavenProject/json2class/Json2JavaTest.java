package com.tom.zhang.mavenProject.json2class;

import com.tom.zhang.mavenProject.json2class.java2class.Java2ClassImpl;
import com.tom.zhang.mavenProject.json2class.java2class.engine.DynamicEngine;
import com.tom.zhang.mavenProject.json2class.java2class.engine.JavaDynamicEngine;
import com.tom.zhang.mavenProject.json2class.json2java.Json2JavaImpl;
import com.tom.zhang.mavenProject.json2class.json2java.output.JavaFileOutput;
import com.tom.zhang.mavenProject.json2class.json2java.output.JavaOutput;
import com.tom.zhang.mavenProject.json2class.json2java.parser.FastJsonParser;
import com.tom.zhang.mavenProject.json2class.json2java.parser.JsonParser;
import com.tom.zhang.mavenProject.json2class.loader.JsonToClassLoader;

public class Json2JavaTest {
    public static void main(String[] args) {
        // json to java
        JavaOutput javaOutput = new JavaFileOutput();
        JsonParser jsonParser = new FastJsonParser();

        // java to class
        DynamicEngine dynamicEngine = new JavaDynamicEngine();
        Java2ClassImpl compiler = new Java2ClassImpl(dynamicEngine);

        JsonToClassLoader jsonToClassLoader = new JsonToClassLoader(compiler, new Json2JavaImpl(javaOutput, jsonParser));

        String json = "{\"data\":{\"info\":[1]},\"code\":\"100\"}";
        jsonToClassLoader.jsonToClass(json, false);
    }
}
