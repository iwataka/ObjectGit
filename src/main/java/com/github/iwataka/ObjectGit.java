package com.github.iwataka;

import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.function.Function;

public interface ObjectGit {

    void init() throws IOException, InterruptedException, GitAPIException;

    void add(Map<String, Object> name2obj) throws IOException, InterruptedException, GitAPIException;

    void commit(String msg) throws IOException, InterruptedException, GitAPIException;

    File bundle(Function<String, String> nameGenerator) throws IOException, InterruptedException;

    default File bundle() throws IOException, InterruptedException {
        return bundle(s -> s + ".bundle");
    }
}
