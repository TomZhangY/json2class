package com.tom.zhang.mavenProject.json2class.json2java.output;

import com.tom.zhang.mavenProject.json2class.json2java.definition.AbstractDefinition;
import com.tom.zhang.mavenProject.json2class.json2java.definition.JavaDefinition;
import com.tom.zhang.mavenProject.json2class.json2java.Json2JavaConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JavaFileOutput implements JavaOutput {

    @Override
    public void print(Json2JavaConfig config, JavaDefinition root) throws Exception {
// 存放文件名和文件内容
        Map<String, StringBuilder> javaClassContent = new HashMap<>();
        buildClasses(root, javaClassContent);

        for (Map.Entry<String, StringBuilder> entry : javaClassContent.entrySet()) {
            String fileAbsPath = getAbsPath(config.getOutPath(), entry.getKey());
            File file = new File(fileAbsPath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            if (!file.exists()) {
                file.createNewFile();
                file.setWritable(true);
            }
            writeClassToFile(file, javaClassContent.get(entry.getKey()));
        }
    }

    @Override
    public void clean(Json2JavaConfig jsonToJavaConfig) {
        try {
            FileUtils.deleteDirectory(FileUtils.getFile(jsonToJavaConfig.getCleanPath()));
        } catch (IOException e) {
            log.warn(String.format("clean exception： %s", jsonToJavaConfig.getCleanPath()), e);
        }
    }

    private void writeClassToFile(File file, StringBuilder classContent) throws Exception {
        try (OutputStream os = new FileOutputStream(file)) {
            os.write(classContent.toString().getBytes("utf-8"));
        }
    }

    private String getAbsPath(String outPath, String path) {
        String ppath;
        if (outPath == null) {
            ppath = "";
        } else {
            ppath = outPath;
        }
        if (!ppath.endsWith(File.separator)) {
            ppath = ppath + File.separator;
        }
        return ppath + path;
    }

    protected void buildClasses(JavaDefinition javaDefinition, Map<String, StringBuilder> javaClassContent) {
        JavaClassBuilder builder = new JavaClassBuilder(javaDefinition);
        Map.Entry<String, StringBuilder> entry = builder.buildJavaClass();

        javaClassContent.put(entry.getKey(), entry.getValue());

        Map<String, JavaDefinition.FieldDefinition> fields = javaDefinition.getFieldMap();

        for (JavaDefinition.FieldDefinition definition : fields.values()) {
            AbstractDefinition fieldType = definition.getType();
            if (fieldType instanceof JavaDefinition) {
                buildClasses((JavaDefinition) fieldType, javaClassContent);
            }
        }
    }
}