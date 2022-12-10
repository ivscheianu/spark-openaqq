package com.ivscheianu.openaqq;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivscheianu.openaqq.reporting.request.ReportingRequest;
import com.ivscheianu.openaqq.reporting.response.ReportingResponse;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class TestUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final ClassLoader CLASS_LOADER = TestUtils.class.getClassLoader();

    @SneakyThrows
    public static ReportingRequest getRequestFromFile(final String fileLocation) {
        return getObjectFromFile(fileLocation, ReportingRequest.class);
    }

    @SneakyThrows
    public static ReportingResponse getResponseFromFile(final String fileLocation) {
        return getObjectFromFile(fileLocation, ReportingResponse.class);
    }

    @SneakyThrows
    public static String getFilePath(final String path) {
        return requireNonNull(CLASS_LOADER.getResource(path)).getFile();
    }

    public static File getFile(final String path) {
        return new File(getFilePath(path));
    }

    @SneakyThrows
    public static <E> E getObjectFromFile(final String fileLocation, final Class<E> returnType) {
        final String json = getStringFromFile(fileLocation);
        return OBJECT_MAPPER.readValue(json, returnType);
    }

    @SneakyThrows
    public static String getStringFromFile(final String fileLocation) {
        final InputStream inputStream = requireNonNull(CLASS_LOADER.getResourceAsStream(fileLocation));
        return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
    }
}
