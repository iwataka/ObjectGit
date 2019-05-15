package com.github.iwataka;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.Test;

import java.io.IOException;

public class ObjectJGitTest extends ObjectGitTest {

    @Test
    public void test_init() throws IOException, InterruptedException, GitAPIException {
        ObjectGit og = new ObjectJGit(dir, Object::toString);
        test_init(og);
    }

    @Test
    public void test_add() throws InterruptedException, GitAPIException, IOException {
        ObjectGit og = new ObjectJGit(dir, Object::toString);
        test_add(og);
    }

    @Test
    public void test_bundle() throws InterruptedException, GitAPIException, IOException {
        ObjectGit og = new ObjectJGit(dir, Object::toString);
        test_bundle(og);
    }
}
