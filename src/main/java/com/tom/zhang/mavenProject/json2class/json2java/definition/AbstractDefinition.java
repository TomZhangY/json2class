package com.tom.zhang.mavenProject.json2class.json2java.definition;


public abstract class AbstractDefinition {

    /**
     * 包名
     */
    protected String packageName;

    /**
     * 类名（）
     */
    protected String name;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
