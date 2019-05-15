package com.github.iwataka;

import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.util.function.Function;

public class DefaultObjectGit extends AbstractObjectGit {

    public DefaultObjectGit(File dir, Function<Object, String> toTextConverter) {
        this.dir = dir;
        this.toTextConverter = toTextConverter;
    }

    protected void addAll() throws IOException, InterruptedException, GitAPIException {
        run("git add --all");
    }

    protected void bundleCreate(String name) throws IOException, InterruptedException {
        run(String.format("git bundle create %s master", name));
    }

    private void run(String cmd) throws IOException, InterruptedException {
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec(cmd, null, dir);
        pr.waitFor();
    }

    @Override
    public void init() throws IOException, InterruptedException, GitAPIException {
        run("git init");
    }

    @Override
    public void commit(String msg) throws IOException, InterruptedException, GitAPIException {
        run(String.format("git commit -m \"%s\"", msg));
    }
}
