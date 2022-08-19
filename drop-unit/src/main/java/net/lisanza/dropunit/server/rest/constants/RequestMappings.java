package net.lisanza.dropunit.server.rest.constants;

public final class RequestMappings {

    // config service
    public static final String DROP_UNIT_SERVICE = "/dropunit";

    public static final String URI_DELIVERY_ENDPOINT = "/delivery/endpoint";
    public static final String URI_DELIVERY_ENDPOINT_DROPID = "/delivery/endpoint/{dropId}";
    public static final String URI_DELIVERY_ENDPOINT_DROPID_REQUESTBODY = "/delivery/endpoint/{dropId}/request-body";
    public static final String URI_DELIVERY_ENDPOINT_DROPID_RESPONSEBODY = "/delivery/endpoint/{dropId}/response-body";
    public static final String URI_RECIEVED_DROPID_NUMBER = "/recieved/{dropId}/{number}";
    public static final String URI_COUNT_NOTFOUND = "/count/notFound";
    public static final String URI_COUNT_DROPID = "/count/{dropId}";
    public static final String URI_CLEARALLDROPS = "/clearAllDrops";
    public static final String URI_CLEAR_DROP_ID = "/clearDrop/{dropId}";
    public static final String URI_GETALLDROPS = "/getAllDrops";
    public static final String URI_GETALLNOTFOUNDS = "/getAllNotFounds";

    // root Service
    public static final String ROOT_SERVICE = "/";
}
