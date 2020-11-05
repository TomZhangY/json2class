package com.tom.zhang.mavenProject.json2class.java2class;

import com.tom.zhang.mavenProject.json2class.java2class.engine.DynamicEngine;

public class Java2ClassImpl implements Java2Class {

    private DynamicEngine dynamicEngine;

    public Java2ClassImpl(DynamicEngine dynamicEngine) {
        this.dynamicEngine = dynamicEngine;
    }


    @Override
    public Class<?> compile(JavaCompilerConfig compilerConfig) throws Exception {
        dynamicEngine.compile(compilerConfig);
        return dynamicEngine.load(compilerConfig);
    }

}