package com.tom.zhang.mavenProject.json2class.json2java.parser;


import com.tom.zhang.mavenProject.json2class.json2java.definition.JavaDefinition;
import com.tom.zhang.mavenProject.json2class.json2java.Json2JavaConfig;

public interface JsonParser {
    JavaDefinition parse(Json2JavaConfig config) ;
}