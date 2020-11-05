package com.tom.zhang.mavenProject.json2class.json2java;


import com.tom.zhang.mavenProject.json2class.json2java.definition.JavaDefinition;
import com.tom.zhang.mavenProject.json2class.json2java.output.JavaOutput;
import com.tom.zhang.mavenProject.json2class.json2java.parser.JsonParser;

public class Json2JavaImpl implements Json2Java {

    private JavaOutput javaOutput;
    private JsonParser jsonParser;

    public Json2JavaImpl(JavaOutput javaOutput,
                         JsonParser jsonParser) {
        this.javaOutput = javaOutput;
        this.jsonParser = jsonParser;
    }

    @Override
    public void create(com.tom.zhang.mavenProject.json2class.json2java.Json2JavaConfig json2JavaConfig) throws Exception {
        JavaDefinition root = jsonParser.parse(json2JavaConfig);
        javaOutput.print(json2JavaConfig, root);
    }

    @Override
    public void clean(com.tom.zhang.mavenProject.json2class.json2java.Json2JavaConfig json2JavaConfig) {
        javaOutput.clean(json2JavaConfig);
    }

}