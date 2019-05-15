package com.github.iwataka;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.After;
import org.junit.Before;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ObjectGitTest {

    protected File dir;

    @Before
    public void before() throws IOException {
        dir = Files.createTempDirectory("").toFile();
    }

    @After
    public void after() {
        if (dir.exists()) {
            for (File file : dir.listFiles()) {
                file.delete();
            }
            dir.delete();
        }
    }

    protected void test_init(ObjectGit objectGit) throws IOException, InterruptedException, GitAPIException {
        objectGit.init();
        assertThat(dir.toPath().resolve(".git").toFile().exists(), equalTo(true));
    }

    protected void test_add(ObjectGit objectGit) throws IOException, InterruptedException, GitAPIException {
        objectGit.init();
        Map<String, Object> name2obj = new HashMap<>();
        String name1 = "obj1";
        name2obj.put(name1, new Object());
        String name2 = "obj2";
        name2obj.put(name2, new Object());
        objectGit.add(name2obj);
        assertThat(dir.toPath().resolve(name1).toFile().exists(), equalTo(true));
        assertThat(dir.toPath().resolve(name2).toFile().exists(), equalTo(true));
    }

    public void test_bundle(ObjectGit objectGit) throws IOException, InterruptedException, GitAPIException {
        objectGit.init();
        Map<String, Object> name2obj = new HashMap<>();
        String name1 = "obj1";
        name2obj.put(name1, new Object());
        String name2 = "obj2";
        name2obj.put(name2, new Object());
        objectGit.add(name2obj);
        objectGit.commit("msg");
        File bundle = objectGit.bundle();
        assertThat(bundle.exists(), equalTo(true));
    }

}
