package com.mastercard.locationsmerchantsapireferenceapplication.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.api.CategoriesApi;
import org.openapitools.client.api.MerchantsApi;
import org.openapitools.client.api.CountriesApi;
import org.openapitools.client.model.Categories;
import org.openapitools.client.model.MerchantSearch;
import org.openapitools.client.model.Merchants;
import org.openapitools.client.model.Countries;
import org.openapitools.client.model.CountrySubdivisions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultLocationsMerchantsApiReferenceService implements LocationsMerchantsApiReferenceService {


    private static final Logger logger = LoggerFactory.getLogger(DefaultLocationsMerchantsApiReferenceService.class);


    private CountriesApi countriesListApi;
    private MerchantsApi merchantsApi;
    private CategoriesApi categoriesListAPI;


    @Autowired
    public DefaultLocationsMerchantsApiReferenceService(ApiClient apiClient) {
        logger.info("-->> INITIALIZING APIS");

        countriesListApi = new CountriesApi(apiClient);
        merchantsApi = new MerchantsApi(apiClient);
        categoriesListAPI = new CategoriesApi(apiClient);

    }
    @Override
    public Countries getCountries()  throws ApiException {
            return countriesListApi.getCountries();
    }

    @Override
    public CountrySubdivisions getCountrySubdivisions(String countryCode) throws ApiException {
            return  countriesListApi.getCountrySubdivisions(countryCode);
    }

    @Override
    public Categories getMerchantCategories()  throws ApiException {
        return categoriesListAPI.getMerchantCategories();
    }

    @Override
    public Merchants getMerchants (MerchantSearch merchantSearch, Integer limit, Integer offset, Integer distance, String distanceUnit)  throws ApiException{

        return merchantsApi.getMerchants(merchantSearch, limit, offset, distance, distanceUnit);
    }

    public String getErrorAttributes(Exception e) {
        String errorDetails;
        if(e instanceof ApiException) {
            ApiException apiException = ((ApiException) e);

            //Attempt to parse as JSON
            try {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                errorDetails = gson.toJson(JsonParser.parseString(apiException.getResponseBody()));

            } catch (Exception ignoredException) {

                //Print full string in case of not JSON
                errorDetails = apiException.getResponseBody();
            }

        } else {

            logger.error("Error occurred!",e);
            errorDetails = e.toString();

        }

        return errorDetails;
    }


}
