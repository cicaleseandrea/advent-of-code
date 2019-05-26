package com.adventofcode;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.lang.ClassLoader.getSystemResource;

public interface Solution {
    static String getInput(final Solution sol) {
        final String fileName = sol.getClass().getPackage().getName() + File.separator + sol.getClass().getSimpleName() + ".txt";
        try {
            return new String(Files.readAllBytes(Path.of(getSystemResource(fileName).toURI())));
        } catch (IOException | URISyntaxException e) {
            throw new IllegalStateException(fileName + "not found");
        }
    }

    String solveFirstPart(final List<String> input);

    String solveSecondPart(final List<String> input);
}
