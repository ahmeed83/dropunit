package net.lisanza.dropunit.client;

import net.lisanza.dropunit.server.rest.dto.DropUnitEndpointDto;
import net.lisanza.dropunit.server.rest.dto.DropUnitHeaderDto;
import net.lisanza.dropunit.server.rest.dto.DropUnitRequestDto;
import net.lisanza.dropunit.server.rest.dto.DropUnitRequestPatternsDto;
import net.lisanza.dropunit.server.rest.dto.DropUnitResponseDto;
import net.lisanza.dropunit.server.rest.dto.ReceivedRequestDto;
import org.apache.commons.lang3.StringUtils;

import javax.naming.CannotProceedException;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static net.lisanza.dropunit.server.utils.FileUtils.readFromFile;
import static net.lisanza.dropunit.server.utils.FileUtils.readListFromXml;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ClientDropUnit extends BaseDropUnitClient {

    private String id;

    private int count;

    private DropUnitEndpointDto dropUnitEndpointDto = null;
    private DropUnitRequestDto requestBodyInfo = null;
    private DropUnitRequestPatternsDto requestPatterns = null;
    private DropUnitResponseDto responseDto = null;

    // Constructor

    /**
     * The ClientDropUnit constructor
     * @param baseUrl The url where the dropunit simulator is reached.
     *                For instance, "http://127.0.0.1:9080/"
     */
    public ClientDropUnit(final String baseUrl) {
        super(baseUrl);
    }

    // Getters & Setters

    /**
     * Retrieves the internal Id of the dropunit with which one can collect data.
     * @return The Id of the dropunit in the simulator.
     */
    public String getId() {
        return id;
    }

    // Delegates

    /**
     * Get the uri path (url) of this dropunit where uri is the
     * url-path that the drop is accessible.
     * @return The uri path of this dropunit
     */
    public String getUrl()
            throws CannotProceedException {
        if (this.dropUnitEndpointDto == null) {
            throw new CannotProceedException("withEndpoint is not called before");
        }
        return dropUnitEndpointDto.getUrl();
    }

    // Operations

    /**
     * Assign GET method for the dropunit with specific URL.
     * @param uri The url path relative to the server
     * @return The client-drop-unit.
     */
    public ClientDropUnit withGet(String uri) {
        return withEndpoint(uri, HttpMethod.GET);
    }

    /**
     * Assign POST method for the dropunit with specific URL.
     * @param uri The url path relative to the server
     * @return The client-drop-unit.
     */
    public ClientDropUnit withPost(String uri) {
        return withEndpoint(uri, HttpMethod.POST);
    }

    /**
     * Assign PUT method for the dropunit with specific URL.
     * @param uri The url path relative to the server
     * @return The client-drop-unit.
     */
    public ClientDropUnit withPut(String uri) {
        return withEndpoint(uri, HttpMethod.PUT);
    }

    /**
     * Assign DELETE method for the dropunit with specific URL.
     * @param uri The url path relative to the server
     * @return The client-drop-unit.
     */
    public ClientDropUnit withDelete(String uri) {
        return withEndpoint(uri, HttpMethod.DELETE);
    }

    /**
     * Assign PATCH method for the dropunit with specific URL.
     * @param uri The url path relative to the server
     * @return The client-drop-unit.
     */
    public ClientDropUnit withPatch(String uri) {
        return withEndpoint(uri, HttpMethod.PATCH);
    }
    /**
     * Assign method for the dropunit with specific URL.
     * @param uri The url path relative to the server
     * @param method The method of the HTTP operation
     * @return The client-drop-unit.
     */
    public ClientDropUnit withEndpoint(String uri, String method) {
        this.dropUnitEndpointDto = new DropUnitEndpointDto();
        this.dropUnitEndpointDto.setUrl(uri);
        this.dropUnitEndpointDto.setMethod(method);
        return this;
    }

    /**
     * When HTTP request is invoked the simluator must match this HTTP header.
     * @param name The header key
     * @param value The header value
     * @return The client-drop-unit.
     */
    public ClientDropUnit withHeader(String name, String value) {
        this.dropUnitEndpointDto.addRequestHeader(new DropUnitHeaderDto(name, value));
        return this;
    }

    /**
     * When HTTP request is invoked the simulator must respond with this content-type and HTTP-body.
     * @param contentType The content-type of the body
     * @param body The HTTP-body
     * @return The client-drop-unit.
     * @throws CannotProceedException when the endpoint is not defined.
     */
    public ClientDropUnit withRequestBody(String contentType, String body)
            throws CannotProceedException {
        if (this.dropUnitEndpointDto == null) {
            throw new CannotProceedException("withEndpoint is not called before");
        }
        this.requestBodyInfo = new DropUnitRequestDto();
        this.requestBodyInfo.setRequestContentType(contentType);
        this.requestBodyInfo.setRequestBody(body);
        return this;
    }

    /**
     * When HTTP request is invoked the simulator must respond with this content-type and HTTP-body.
     * @param contentType The content-type of the body
     * @param filename The HTTP-body
     * @return The client-drop-unit.
     * @throws CannotProceedException when the endpoint is not defined.
     * @throws IOException
     */
    public ClientDropUnit withRequestBodyFromFile(String contentType, String filename)
            throws CannotProceedException, IOException {
        return withRequestBody(contentType, readFromFile(filename));
    }

    /**
     *
     * @param contentType
     * @param pattern
     * @return The client-drop-unit.
     * @throws CannotProceedException when the endpoint is not defined.
     */
    public ClientDropUnit withRequestPattern(String contentType, String pattern)
            throws CannotProceedException {
        ArrayList<String> list = new ArrayList<>();
        list.add(pattern);
        return withRequestPatterns(contentType, list);
    }

    /**
     *
     * @param contentType
     * @param patterns
     * @return The client-drop-unit.
     * @throws CannotProceedException when the endpoint is not defined.
     */
    public ClientDropUnit withRequestPatterns(String contentType, List<String> patterns)
            throws CannotProceedException {
        if (this.dropUnitEndpointDto == null) {
            throw new CannotProceedException("withEndpoint is not called before");
        }
        this.requestPatterns = new DropUnitRequestPatternsDto();
        this.requestPatterns.setRequestContentType(contentType);
        this.requestPatterns.setPatterns(patterns);
        return this;
    }

    /**
     *
     * @param contentType
     * @param filename
     * @return The client-drop-unit.
     * @throws CannotProceedException when the endpoint is not defined.
     */
    public ClientDropUnit withRequestPatternsFromFile(String contentType, String filename)
            throws CannotProceedException, IOException {
        if (this.dropUnitEndpointDto == null) {
            throw new CannotProceedException("withEndpoint is not called before");
        }
        this.requestPatterns = new DropUnitRequestPatternsDto();
        this.requestPatterns.setRequestContentType(contentType);
        this.requestPatterns.setPatterns(readListFromXml(filename));
        return this;
    }

    /**
     * When HTTP request is invoked the simulator must respond with this response-code, content-type and HTTP-body.
     * @param status The status to respond with
     * @param contentType The content-type
     * @param body The HTTP response body
     * @return The client-drop-unit.
     * @throws CannotProceedException when the endpoint is not defined.
     */
    public ClientDropUnit withResponse(Response.Status status, String contentType, String body)
            throws CannotProceedException {
        if (this.dropUnitEndpointDto == null) {
            throw new CannotProceedException("withEndpoint is not called before");
        }
        this.dropUnitEndpointDto.setResponseCode(status.getStatusCode());
        this.responseDto = new DropUnitResponseDto();
        this.responseDto.setResponseContentType(contentType);
        this.responseDto.setResponseBody(body);
        return this;
    }

    /**
     * When HTTP request is invoked the simulator must respond with this response-code, content-type and HTTP-body.
     * @return The client-drop-unit.
     * @throws CannotProceedException when the endpoint is not defined.
     */
    public ClientDropUnit withResponseNoContent()
            throws CannotProceedException {
        if (this.dropUnitEndpointDto == null) {
            throw new CannotProceedException("withEndpoint is not called before");
        }
        this.dropUnitEndpointDto.setResponseCode(Response.Status.NO_CONTENT.getStatusCode());
        this.responseDto = null;
        return this;
    }

    /**
     * When HTTP request is matched respond also with HTTP header.
     * @param name The header key
     * @param value The header value
     * @return The client-drop-unit.
     */
    public ClientDropUnit withResponseHeader(String name, String value)
            throws CannotProceedException {
        if (this.dropUnitEndpointDto == null) {
            throw new CannotProceedException("withEndpoint is not called before");
        }
        this.dropUnitEndpointDto.addResponseHeader(new DropUnitHeaderDto(name, value));
        return this;
    }


    /**
     *
     * @param contentType
     * @param body
     * @return The client-drop-unit.
     * @throws Exception
     */
    public ClientDropUnit withResponseOk(String contentType, String body)
            throws Exception {
        return withResponse(Response.Status.OK, contentType, body);
    }

    /**
     *
     * @param contentType
     * @param filename
     * @return The client-drop-unit.
     * @throws Exception
     */
    public ClientDropUnit withResponseOkFromFile(String contentType, String filename)
            throws Exception {
        return withResponseOk(contentType, readFromFile(filename));
    }

    /**
     *
     * @param contentType
     * @param body
     * @return The client-drop-unit.
     * @throws Exception
     */
    public ClientDropUnit withResponseBadRequest(String contentType, String body)
            throws Exception {
        return withResponse(Response.Status.BAD_REQUEST, contentType, body);
    }

    /**
     *
     * @param contentType
     * @param filename
     * @return The client-drop-unit.
     * @throws Exception
     */
    public ClientDropUnit withResponseBadRequestFromFile(String contentType, String filename)
            throws Exception {
        return withResponseBadRequest(contentType, readFromFile(filename));
    }

    /**
     *
     * @param contentType
     * @param body
     * @return The client-drop-unit.
     * @throws Exception
     */
    public ClientDropUnit withResponseBadGateway(String contentType, String body)
            throws Exception {
        return withResponse(Response.Status.GATEWAY_TIMEOUT, contentType, body);
    }

    /**
     *
     * @param contentType
     * @param filename
     * @return The client-drop-unit.
     * @throws Exception
     */
    public ClientDropUnit withResponseBadGatewayFromFile(String contentType, String filename)
            throws Exception {
        return withResponseBadGateway(contentType, readFromFile(filename));
    }

    /**
     *
     * @param responseDelay
     * @return The client-drop-unit.
     * @throws Exception
     */
    public ClientDropUnit withResponseDelay(int responseDelay)
            throws Exception {
        if (this.dropUnitEndpointDto == null) {
            throw new Exception("");
        }
        this.dropUnitEndpointDto.setResponseDelay(responseDelay);
        return this;
    }

    // Remote operations

    /**
     * Remove all 'remote-configured' endpoints in the simulator.
     * @return The client-drop-unit.
     * @throws IOException
     */
    public ClientDropUnit cleanup() throws IOException {
        executeEndpointDeletion();
        return this;
    }

    /**
     * Configure the dropunit (endpoint) in the simulator remotely.
     * @return The client-drop-unit.
     * @throws IOException
     */
    public ClientDropUnit drop()
            throws IOException {
        id = executeEndpointDelivery(dropUnitEndpointDto);
        if (requestBodyInfo != null) {
            executeRequestDelivery(id, requestBodyInfo);
        }
        if (this.requestPatterns != null) {
            executeRequestDelivery(id, requestPatterns);
        }
        executeResponseDelivery(id, responseDto);
        count = executeRetrieveCount(id);
        return this;
    }

    /**
     * Assert the amount of requests that match this drop unit
     * @param count The expected number of requests
     */
    public void assertCountRecievedRequests(int count) {
        assertThat("unexpected request count", executeRetrieveCount(id), is(count));
    }

    /**
     * Assert the amount of requests that match this drop unit
     * @param number The expected number of requests
     */
    public void assertReceived(int number) {
        if ((requestBodyInfo == null) || (requestBodyInfo.getRequestBody() == null)) {
            assertReceived(number, "");
        } else {
            assertReceived(number, requestBodyInfo.getRequestBody());
        }
    }

    /**
     *
     * @param number
     */
    public void assertReceivedFromPatterns(int number) {
        String requestBody = executeRetrieveReceived(id, number);
        for (String pattern : requestPatterns.getPatterns()) {
            assertTrue("missing pattern ->> '" + pattern + "'",
                    requestBody.contains(pattern));
        }
    }

    /**
     *
     * @param number
     * @param filename
     * @throws IOException
     */
    public void assertReceivedFromFile(int number, String filename)
            throws IOException {
        assertReceived(number, readFromFile(filename));
    }

    /**
     * Assert the amount of requests that match
     * @param number
     * @param toMatch
     */
    public void assertReceived(int number, String toMatch) {
        assertThat("unexpected amount of expected requests",
                executeRetrieveReceived(id, number), is(toMatch));
    }

    /**
     *
     * @param expected amount of expected not-found
     */
    public void assertNotFound(int expected) {
        ReceivedRequestDto[] notFound = executeRetrieveNotFound();
        assertThat("unexpected amount of 'not-found' ->> " + StringUtils.join(notFound, ","),
                notFound.length, is(expected));
    }

    // toString
    @Override
    public String toString() {
        return "ClientDropUnit =>\n" +
                " id         = '" + id + "'\n" +
                " url        = '" + dropUnitEndpointDto.getUrl() + "'\n" +
                " count      = " + count + "\n";
    }
}