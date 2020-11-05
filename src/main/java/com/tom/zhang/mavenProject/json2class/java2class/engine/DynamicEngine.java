package com.tom.zhang.mavenProject.json2class.java2class.engine;

import com.tom.zhang.mavenProject.json2class.java2class.JavaCompilerConfig;

public interface DynamicEngine {
    /**
     * @Description: 编译所有文件
     * @Author: Zhang Ying
     * @date: 2020-11-5 19:10
     */
    void compile(JavaCompilerConfig compilerConfig) throws Exception;

    /**
     * @Description: 加载class信息
     * @Author: Zhang Ying
     * @date: 2020-11-5 19:11
     */
    Class<?> load(JavaCompilerConfig compilerConfig) throws Exception;
}