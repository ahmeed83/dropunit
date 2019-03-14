package net.lisanza.dropunit.client;

import net.lisanza.dropunit.impl.rest.DropUnitDto;

import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class DropFactory {

    public static DropUnitDto createDropUnit(String uri, String method,
                                             Response.Status responseCode,
                                             String responseContentType,
                                             String responseBodyFile)
            throws IOException {
        return createDropUnit(uri, method,
                responseCode, responseContentType, responseBodyFile, 0);
    }

    public static DropUnitDto createDropUnit(String uri, String method,
                                             Response.Status responseCode,
                                             String responseContentType,
                                             String responseBodyFile,
                                             int responseDelay)
            throws IOException {
        return createDropUnit(uri, method,
                null, null,
                responseCode, responseContentType, responseBodyFile, responseDelay);
    }

    public static DropUnitDto createDropUnit(String uri, String method,
                                             String requestContentType,
                                             String requestBodyFile,
                                             Response.Status responseCode,
                                             String responseContentType,
                                             String responseBodyFile)
            throws IOException {
        return createDropUnit(uri, method,
                requestContentType, requestBodyFile,
                responseCode, responseContentType, responseBodyFile, 0);
    }

    public static DropUnitDto createDropUnit(String uri, String method,
                                             String requestContentType,
                                             String requestBodyFile,
                                             Response.Status responseCode,
                                             String responseContentType,
                                             String responseBodyFile,
                                             int responseDelay)
            throws IOException {
        DropUnitDto dropUnitDto = new DropUnitDto();
        dropUnitDto.setUrl(uri);
        dropUnitDto.setMethod(method);
        if (requestContentType != null) {
            dropUnitDto.setRequestContentType(requestContentType);
        }
        if (requestBodyFile != null) {
            dropUnitDto.setRequestBody(readFromFile(requestBodyFile));
        }
        dropUnitDto.setResponseCode(responseCode.getStatusCode());
        dropUnitDto.setResponseContentType(responseContentType);
        if (responseBodyFile != null) {
            dropUnitDto.setResponseBody(readFromFile(responseBodyFile));
        }
        dropUnitDto.setResponseDelay(responseDelay);
        return dropUnitDto;
    }

    public static String readFromFile(String fileName) throws IOException {
        try (InputStream inputStream = new FileInputStream(new File(fileName))) {
            String result = new BufferedReader(new InputStreamReader(inputStream))
                    .lines().collect(Collectors.joining("\n"));
            return result;
        }
    }
}
    
