package com.dukeacademy.testexecutor.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class JavaFileTest {
    @TempDir
    public Path tempFolder;

    @Test
    public void testFileExists() throws IOException {
        String basePath = tempFolder.toUri().getPath();

        tempFolder.resolve("Foo.java").toFile().createNewFile();
        JavaFile file = new JavaFile("Foo", basePath);

        assertTrue(file.getFile().exists());
        assertEquals("Foo", file.getCanonicalName());
        assertEquals(basePath, file.getClassPath());

        tempFolder.resolve("nested").toFile().mkdir();
        tempFolder.resolve("nested").resolve("Bar.java").toFile().createNewFile();
        JavaFile file1 = new JavaFile("nested.Bar", basePath);

        assertTrue(file1.getFile().exists());
        assertEquals("nested.Bar", file1.getCanonicalName());
        assertEquals(basePath, file1.getClassPath());
    }

    @Test
    public void testFileDoesNotExist() {
        String basePath = tempFolder.toUri().getPath();

        assertThrows(FileNotFoundException.class, () -> new ClassFile("Foobar", basePath));
    }

    @Test
    public void testEquality() throws IOException {
        String basePath = tempFolder.toUri().getPath();
        tempFolder.resolve("nested").toFile().mkdir();
        tempFolder.resolve("nested").resolve("Foo.java").toFile().createNewFile();

        JavaFile javaFile1 = new JavaFile("nested.Foo", basePath);
        JavaFile javaFile2 = new JavaFile("nested.Foo", basePath);

        assertEquals(javaFile1, javaFile2);

        tempFolder.resolve("Foo.java").toFile().createNewFile();
        JavaFile javaFile3 = new JavaFile("Foo", basePath);
        assertNotEquals(javaFile1, javaFile3);
    }
}