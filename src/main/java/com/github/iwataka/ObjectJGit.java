package com.github.iwataka;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.BundleWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.function.Function;

public class ObjectJGit extends AbstractObjectGit {

    private Repository repo;

    public ObjectJGit(File dir, Function<Object, String> toTextConverter) {
        this.dir = dir;
        this.toTextConverter = toTextConverter;
    }

    @Override
    public void init() throws IOException, InterruptedException, GitAPIException {
        Git.init().setDirectory(dir).setBare(false).call();
        repo = new FileRepositoryBuilder().setGitDir(dir.toPath().resolve(".git").toFile()).build();
    }

    @Override
    protected void addAll() throws IOException, InterruptedException, GitAPIException {
        Git git = new Git(repo);
        git.add().addFilepattern(".").call();
    }

    @Override
    public void commit(String msg) throws IOException, InterruptedException, GitAPIException {
        Git git = new Git(repo);
        git.commit().setMessage(msg).call();
    }

    @Override
    protected void bundleCreate(String name) throws IOException, InterruptedException {
        File file = dir.toPath().resolve(name).toFile();
        try (OutputStream out = new FileOutputStream(file)) {
            BundleWriter writer = new BundleWriter(repo);
            writer.writeBundle(null, out);
        }
    }
}
