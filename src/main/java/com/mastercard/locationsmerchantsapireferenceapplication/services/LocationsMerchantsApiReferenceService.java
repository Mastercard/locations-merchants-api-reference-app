package com.mastercard.locationsmerchantsapireferenceapplication.services;

import org.openapitools.client.ApiException;
import org.openapitools.client.model.Categories;
import org.openapitools.client.model.MerchantSearch;
import org.openapitools.client.model.Merchants;
import org.openapitools.client.model.Countries;
import org.openapitools.client.model.CountrySubdivisions;
import org.springframework.stereotype.Service;

@Service
public interface LocationsMerchantsApiReferenceService {

    Countries getCountries() throws ApiException;

    CountrySubdivisions getCountrySubdivisions(String countryCode)  throws ApiException;

    Categories getMerchantCategories() throws ApiException;

    Merchants getMerchants(MerchantSearch merchantSearch, Integer limit, Integer offset, Integer distance, String distanceUnit)  throws ApiException;
}
