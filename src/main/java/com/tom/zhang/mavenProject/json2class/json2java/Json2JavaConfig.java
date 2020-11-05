package com.tom.zhang.mavenProject.json2class.json2java;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Json2JavaConfig {

    private String jsonString;

    private String outPath;

    private String packageName;

    private String rootClass;

//    private String frame;

    private String cleanPath;

}