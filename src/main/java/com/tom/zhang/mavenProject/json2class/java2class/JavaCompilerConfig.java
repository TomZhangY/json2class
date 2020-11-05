package com.tom.zhang.mavenProject.json2class.java2class;

import lombok.Builder;
import lombok.Data;

import java.net.URL;
import java.util.List;

@Builder
@Data
public class JavaCompilerConfig {
    // 需要编译的目录
    private String dirPath;
    //
    private URL loadUrl;
    private String classPath;
    private String rootName;
    private List<String>  depNames;
    private String packageName;
    private String outPath;
}