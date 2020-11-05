package com.tom.zhang.mavenProject.json2class.json2java.parser;

import com.alibaba.fastjson.JSONObject;
import com.tom.zhang.mavenProject.json2class.json2java.definition.AbstractDefinition;
import com.tom.zhang.mavenProject.json2class.json2java.definition.BaseDefinition;
import com.tom.zhang.mavenProject.json2class.json2java.definition.JavaDefinition;
import com.tom.zhang.mavenProject.json2class.json2java.Json2JavaConfig;

import java.util.Date;
import java.util.List;
import java.util.Set;


public class FastJsonParser extends AbstractJsonParser {

    @Override
    protected void doParse(Json2JavaConfig config, JavaDefinition rootDefinition) {
        JSONObject jsonObject = JSONObject.parseObject(config.getJsonString());
        parseParent(config, rootDefinition, jsonObject);
    }

    private void parseParent(Json2JavaConfig config, JavaDefinition pDefinition, JSONObject jsonObject) {
        for (String key : jsonObject.keySet()) {
            Object field = jsonObject.get(key);
            JavaDefinition.FieldDefinition fieldDefinition = new JavaDefinition.FieldDefinition();
            String fieldName = formatFieldName(key);
            fieldDefinition.setFieldName(fieldName);
            if (!key.equals(fieldName)) {
                fieldDefinition.addAnnotation(getJsonFieldAnntotation(key));
                pDefinition.addImport("com.alibaba.fastjson.annotation.JSONField");
            }

            if (field instanceof List) {
                fieldName = pluralToSingular(fieldName);
                fieldDefinition.setArray(true);
                pDefinition.addImport("java.util.List");
                List jsonArray = (List) field;
                if (jsonArray.size() == 0) {
                    field = new Object();
                } else {
                    field = jsonArray.get(0);
                }
            }

            AbstractDefinition type = getFieldType(config, fieldName, field);
            if (type.getPackageName() != null && !"".equals(type.getPackageName()) && !pDefinition.getPackageName().equals(type.getPackageName())) {
                pDefinition.addImport(type.getPackageName() + "." + type.getName());
            }

            fieldDefinition.setType(type);
            pDefinition.addField(fieldDefinition);
        }
    }

    private boolean fieldNameValidate(Set<String> keys) {
        for (String key : keys) {
            if (!key.matches("^[a-zA-Z][a-zA-Z0-9_]*$")) {
                return false;
            }
        }

        return true;
    }

    private AbstractDefinition getFieldType(Json2JavaConfig config, String fieldName, Object field) {
        if (field instanceof JSONObject) {
            if (!fieldNameValidate(((JSONObject) field).keySet())) {
//todo:value的类型？
                return new BaseDefinition("Map", "java.util");
            } else {
                JavaDefinition sDefinition = new JavaDefinition();
                sDefinition.setName(formatClassNameByField(fieldName));
                sDefinition.setPackageName(getDftPackage(config));
                sDefinition.setNote(buildDftNote());
                parseParent(config, sDefinition, (JSONObject) field);
                return sDefinition;
            }

        } else if (field instanceof String) {
            return new BaseDefinition("String", "");
        } else if (field instanceof Number) {
            if (field instanceof Integer) {
                return new BaseDefinition("Integer", "");
            } else if (field instanceof Long) {
                return new BaseDefinition("Long", "");
            } else if (field instanceof Float) {
                return new BaseDefinition("Float", "");
            } else if (field instanceof Short) {
                return new BaseDefinition("Short", "");
            } else {
                return new BaseDefinition("Double", "");
            }
        } else if (field instanceof Date) {
            return new BaseDefinition("Date", "java.util");
        } else if (field instanceof Boolean) {
            return new BaseDefinition("Boolean", "");
        } else {
//不支持的类型，存为object
            return new BaseDefinition("Object", "");
        }
    }

    private String getJsonFieldAnntotation(String paramName) {
        return String.format("@JSONField(name = \"%s\")", paramName);
    }

}