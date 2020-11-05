package com.tom.zhang.mavenProject.json2class.loader;

import com.tom.zhang.mavenProject.json2class.java2class.Java2Class;
import com.tom.zhang.mavenProject.json2class.java2class.JavaCompilerConfig;
import com.tom.zhang.mavenProject.json2class.json2java.Json2Java;
import com.tom.zhang.mavenProject.json2class.json2java.Json2JavaConfig;
import com.tom.zhang.mavenProject.json2class.json2java.RandomStringUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;


@Slf4j
public class JsonToClassLoader implements Loader {

    private com.tom.zhang.mavenProject.json2class.java2class.Java2Class compiler;
    private Json2Java json2Java;
    private final URL resource = JsonToClassLoader.class.getResource("/");
    private final String resourcePath = resource.getPath();

    public JsonToClassLoader(Java2Class compiler,
                             Json2Java json2Java) {
        this.compiler = compiler;
        this.json2Java = json2Java;
    }

    @Override
    public Class<?> jsonToClass(String json) {
        return jsonToClass(json,true);
    }

    @Override
    public Class<?> jsonToClass(String json, boolean ifClean) {
// 存放的包路径
        String randomChar = RandomStringUtil.randomChar();
//        String randomChar = "a";
        Json2JavaConfig jsonToJavaConfig = Json2JavaConfig.builder()
                .jsonString(json) // 加载的json
                .packageName("tmp." + randomChar ) // java的包名
//                .frame("fastJson")
                .outPath(resourcePath) // 存放的绝对路径
                .cleanPath(resourcePath + "tmp/" + randomChar) // 生成文件的路径
                .rootClass("Root") // 根类名
                .build();

        JavaCompilerConfig javaCompilerConfig = JavaCompilerConfig.builder()
                .dirPath(resourcePath + "tmp/" + randomChar ) //需要编译的路径
                .rootName("Root") //最后加载该类
                .loadUrl(resource)
                .packageName("tmp." + randomChar) // java包名
                .outPath(resourcePath)
                .build();
        try {
// 根据json创建java文件
            json2Java.create(jsonToJavaConfig);
            // 编译java文件成class
            return compiler.compile(javaCompilerConfig);
        } catch (Exception e) {
            log.warn(String.format("jsonToClass exception: %s", e.getMessage()), e);
        } finally {
            if(ifClean){
// 删除目录下的java文件和class文件
                json2Java.clean(jsonToJavaConfig);
            }
        }
        return null;
    }
}