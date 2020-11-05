package com.tom.zhang.mavenProject.json2class.java2class.engine;


import com.tom.zhang.mavenProject.json2class.java2class.Java2ClassImpl;
import com.tom.zhang.mavenProject.json2class.java2class.JavaCompilerConfig;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import javax.script.ScriptException;
import javax.tools.*;
import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class JavaDynamicEngine implements DynamicEngine {

    @Override
    public void compile(JavaCompilerConfig compilerConfig) throws Exception {

        String dirPath = compilerConfig.getDirPath();
        compilerConfig.setDepNames(new ArrayList());

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        StandardJavaFileManager fm = compiler.getStandardFileManager(null, Locale.CHINA, Charset.forName("UTF-8"));

        List<JavaFileObject> jfiles = new ArrayList<>();
        Collection<File> files = FileUtils.listFiles(FileUtils.getFile(dirPath), new String[]{"java"}, true);
        // 找到除了Root的所有子类
        for (File file : files) {
            compilerConfig.getDepNames().add(file.getName().replace(".java", ""));
            jfiles.add(new CharSequenceJavaFileObject(file, FileUtils.readFileToString(file, "UTF-8")));
        }
        compilerConfig.getDepNames().remove("Root");

        List<String> options = new ArrayList<String>();
        addOptions(compilerConfig, options);

        JavaCompiler.CompilationTask task = compiler.getTask(null, fm, diagnostics, options, null, jfiles);
        Boolean success = task.call();
        if (!success) {
            StringBuilder builder = new StringBuilder();
            for (Diagnostic<?> diagnostic : diagnostics.getDiagnostics()) {
                builder.append(diagnostic).append("\n");
            }
            throw new ScriptException(builder.toString());
        }
    }

    @Override
    public Class<?> load(JavaCompilerConfig compilerConfig) throws ClassNotFoundException {
//防止内存泄漏，一个classloader加载一个class
        URL[] loaderUrl = new URL[]{compilerConfig.getLoadUrl()};
        DynamicClassLoader dynamicClassLoader = new DynamicClassLoader(loaderUrl, Java2ClassImpl.class.getClassLoader());
        List<String> depNames = compilerConfig.getDepNames();
        if (depNames != null && depNames.size() > 0) {
            for (String depName : depNames) {
                dynamicClassLoader.loadClass(compilerConfig.getPackageName() + "." + depName);
            }
        }
        return dynamicClassLoader.loadClass(compilerConfig.getPackageName() + "." + compilerConfig.getRootName());
    }

    private void addOptions(JavaCompilerConfig compilerConfig, List<String> options) {
        if (StringUtils.isNotEmpty(compilerConfig.getOutPath())) {
            options.add("-d");
            options.add(compilerConfig.getOutPath());
        }
        if (StringUtils.isNotEmpty(compilerConfig.getClassPath())) {
            options.add("-cp");
            options.add(compilerConfig.getClassPath());
        }
    }


}