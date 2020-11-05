package com.tom.zhang.mavenProject.json2class.json2java.output;

import com.tom.zhang.mavenProject.json2class.json2java.definition.JavaDefinition;
import com.tom.zhang.mavenProject.json2class.json2java.Json2JavaConfig;

public interface JavaOutput {

    void print(Json2JavaConfig jsonToJavaConfig, JavaDefinition root) throws Exception;

    void clean(Json2JavaConfig jsonToJavaConfig);
}