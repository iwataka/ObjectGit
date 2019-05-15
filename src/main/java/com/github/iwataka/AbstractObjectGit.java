package com.github.iwataka;

import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.Function;

public abstract class AbstractObjectGit implements ObjectGit {

    protected File dir;

    protected Function<Object, String> toTextConverter;

    public void add(Map<String, Object> name2obj) throws IOException, InterruptedException, GitAPIException {
        for (Map.Entry<String, Object> entry : name2obj.entrySet()) {
            String name = entry.getKey();
            Object obj = entry.getValue();
            String text = toTextConverter.apply(obj);
            File file = dir.toPath().resolve(name).toFile();
            file.createNewFile();
            try (PrintWriter out = new PrintWriter(file)) {
                out.println(text);
            }
        }
        addAll();
    }

    protected abstract void addAll() throws IOException, InterruptedException, GitAPIException;

    @Override
    public File bundle(Function<String, String> nameGenerator) throws IOException, InterruptedException {
        String name = nameGenerator.apply(dir.getName());
        bundleCreate(name);
        Path target = dir.getParentFile().toPath().resolve(name);
        Files.move(dir.toPath().resolve(name), target);
        deleteRecursively(dir);
        return target.toFile();
    }

    protected abstract void bundleCreate(String name) throws IOException, InterruptedException;

    private void deleteRecursively(File dir) {
        for (File file : dir.listFiles()) {
            file.delete();
        }
        dir.delete();
    }
}
