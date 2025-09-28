package edu.quralai.daa.core;

import java.io.*;
import java.nio.file.*;

public final class CsvWriter implements Closeable, Flushable {
    private final BufferedWriter w;

    public CsvWriter(Path path, boolean append) throws IOException {
        Files.createDirectories(path.getParent());
        w = Files.newBufferedWriter(
                path,
                append ? StandardOpenOption.CREATE : StandardOpenOption.CREATE,
                append ? StandardOpenOption.APPEND : StandardOpenOption.TRUNCATE_EXISTING
        );
    }
    public void header() throws IOException {
        w.write("algo,n,seed,elapsed_ns,comparisons,allocations,max_depth,notes\n");
    }
    public void row(String line) throws IOException { w.write(line); w.write('\n'); }
    public void flush() throws IOException { w.flush(); }
    public void close() throws IOException { w.close(); }
}
