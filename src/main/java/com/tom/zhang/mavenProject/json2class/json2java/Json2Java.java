package com.tom.zhang.mavenProject.json2class.json2java;

public interface Json2Java {

    /**
     * 生成java文件
     * @param json2JavaConfig
     * @throws Exception
     */
    void create(Json2JavaConfig json2JavaConfig) throws Exception;

    /**
     * 删除生成的文件
     * @param json2JavaConfig
     */
    void clean(Json2JavaConfig json2JavaConfig);


}



