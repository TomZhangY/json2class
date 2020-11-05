package com.tom.zhang.mavenProject.json2class.json2java.definition;


public class BaseDefinition extends AbstractDefinition {
    public BaseDefinition(String name, String type) {
        super.name = name;
        super.packageName = type;
    }
}