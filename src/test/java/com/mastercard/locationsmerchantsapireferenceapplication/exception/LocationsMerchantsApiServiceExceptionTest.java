package com.mastercard.locationsmerchantsapireferenceapplication.exception;

import org.junit.jupiter.api.Test;

public class LocationsMerchantsApiServiceExceptionTest {

    private static final String MESSAGE = "Something went wrong";

    @Test
    void testServiceExceptionMessage() {
        Exception exception = new Exception(MESSAGE);
        LocationsMerchantsApiServiceException locationsMerchantsApiServiceException = new LocationsMerchantsApiServiceException(exception);
        assert(locationsMerchantsApiServiceException.getMessage().contains(MESSAGE));
        }}