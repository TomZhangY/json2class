package com.tom.zhang.mavenProject.json2class.json2java.parser;

import com.tom.zhang.mavenProject.json2class.json2java.definition.JavaDefinition;
import com.tom.zhang.mavenProject.json2class.json2java.Json2JavaConfig;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Date;


@Slf4j
public abstract class AbstractJsonParser implements JsonParser {

    @Override
    public JavaDefinition parse(Json2JavaConfig config) {
        JavaDefinition rootDefinition = newDefultJavaDefinition(config);
        doParse(config, rootDefinition);
        return rootDefinition;
    }

    protected abstract void doParse(Json2JavaConfig config, JavaDefinition rootDefinition);

    protected JavaDefinition newDefultJavaDefinition(Json2JavaConfig config){
        JavaDefinition newDefinition = new JavaDefinition();
        newDefinition.setPackageName(getDftPackage(config));
        newDefinition.setName(config.getRootClass());
        newDefinition.setNote(buildDftNote());
        return newDefinition;
    }

    protected String getDftPackage(Json2JavaConfig config) {
        return config.getPackageName();
    }

    protected String buildDftNote() {
        return String.format("/**\n" +
                "  * Auto-generated: %tF \n" +
                "  * @author xxx \n" +
                "  */", new Date());
    }

    /**
     * javabean规范格式化field
     *
     * @param fieldName
     * @return
     */
    protected String formatFieldName(String fieldName) {
        if (fieldName.matches("[A-Z]*")) {//都是大写
            return fieldName.toLowerCase();
        } else if (fieldName.length() > 1 && fieldName.substring(0, 2).matches("[A-Z]*")) {
//首两个字母要么大小要么小写
            return fieldName.substring(0, 2).toLowerCase() + fieldName.substring(2);
        } else if ('A' <= fieldName.charAt(0) && 'Z' >= fieldName.charAt(0)) {
            return fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
        }

        return fieldName;
    }

    /**
     * 格式化类名
     *
     * @param fieldName
     * @return
     */
    protected String formatClassNameByField(String fieldName) {
        return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    /**
     * 复数变单数
     *
     * @param name
     * @return
     */
    protected String pluralToSingular(String name) {
        if (name.endsWith("ies")) {
            return name.substring(0, name.length() - 3) + "y";
        } else if (name.endsWith("ses")) {
            return name.substring(0, name.length() - 2);
        } else if (name.endsWith("s")) {
            return name.substring(0, name.length() - 1);
        }

        return name;
    }


    private String readJsonString(File jsonFile)  {
        StringBuilder sb = new StringBuilder();
        try (InputStream is = new FileInputStream(jsonFile);
             InputStreamReader isr = new InputStreamReader(is);
             BufferedReader br = new BufferedReader(isr);) {
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            log.info(String.format("readJsonString exception: %s",e.getMessage()),e);
        }
        return sb.toString();
    }
}