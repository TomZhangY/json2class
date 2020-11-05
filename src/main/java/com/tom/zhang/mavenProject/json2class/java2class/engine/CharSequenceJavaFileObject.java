package com.tom.zhang.mavenProject.json2class.java2class.engine;

import javax.tools.SimpleJavaFileObject;
import java.io.File;

public class CharSequenceJavaFileObject extends SimpleJavaFileObject {

    private CharSequence content;

    public CharSequenceJavaFileObject(File file, CharSequence content) {
        super(file.toURI(), Kind.SOURCE);
        this.content = content;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return content;
    }
}