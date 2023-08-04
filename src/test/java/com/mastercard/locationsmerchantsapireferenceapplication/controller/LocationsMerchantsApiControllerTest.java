package com.mastercard.locationsmerchantsapireferenceapplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mastercard.locationsmerchantsapireferenceapplication.services.LocationsMerchantsApiReferenceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.openapitools.client.ApiException;
import org.openapitools.client.model.Countries;
import org.openapitools.client.model.Country;
import org.openapitools.client.model.CountrySubdivision;
import org.openapitools.client.model.CountrySubdivisions;
import org.openapitools.client.model.MerchantSearch;
import org.openapitools.client.model.Merchants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = LocationsMerchantsApiController.class)
    public class LocationsMerchantsApiControllerTest {

    @MockBean
    private LocationsMerchantsApiReferenceService locationsMerchantsApiReferenceService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetCountries_successfulRequest() throws Exception {
        Countries mockedReturn = new Countries();
        Country country = new Country();
        country.countryCode("USA");
        List list = new ArrayList();
        list.add(country);
        mockedReturn.setCountries(list);

        given(locationsMerchantsApiReferenceService.getCountries()).willReturn(mockedReturn);

        mvc.perform(get("/locations/merchants/countries")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.countries[0].countryCode").value("USA"));
    }

    @Test
    public void testCountries_ErrorHandling() throws Exception {
        doThrow(new ApiException(400, "Something went wrong")).when(locationsMerchantsApiReferenceService).getCountries();
        mvc.perform(get("/locations/merchants/countries")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Something went wrong"));
    }

    @Test
    public void testGetCountrySubdivisions_successfulRequest() throws Exception {
        CountrySubdivisions mockedReturn = new CountrySubdivisions();
        CountrySubdivision country = new CountrySubdivision();
        country.countrySubdivisionCode("USA");
        List list = new ArrayList();
        list.add(country);
        mockedReturn.setCountrySubdivisions(list);

        given(locationsMerchantsApiReferenceService.getCountrySubdivisions(Mockito.anyString())).willReturn(mockedReturn);

        mvc.perform(get("/locations/merchants/country-subdivisions?country_code=USA")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.countrySubdivisions[0].countrySubdivisionCode").value("USA"));
    }

    @Test
    public void testGetCountrySubdivisions_ErrorHandling() throws Exception {
        doThrow(new ApiException(400, "Something went wrong")).when(locationsMerchantsApiReferenceService).getCountrySubdivisions(Mockito.any());
        mvc.perform(get("/locations/merchants/country-subdivisions?country_code=USA")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Something went wrong"));
    }


    @Test
    public void testGetMerchants_successfulRequest() throws Exception {
        Merchants mockedReturn = new Merchants();
        mockedReturn.setLimit(1);

        MerchantSearch merchantSearch = new MerchantSearch();
        merchantSearch.setAddressLine1("1 Oak Street");
        merchantSearch.setCity("OFallon");
        merchantSearch.setCountrySubdivisionCode("MO");
        merchantSearch.setCountryCode("USA");
        merchantSearch.setPostalCode("63368");
        merchantSearch.setMerchantType("paypass");

        String requestJson = objectMapper.writeValueAsString(merchantSearch);

        given(locationsMerchantsApiReferenceService.getMerchants(Mockito.any(MerchantSearch.class),
                Mockito.anyInt(),Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString())).willReturn(mockedReturn);

        mvc.perform(post("/locations/merchants/searches?limit=1&offset=6&distance=10&distanceUnit=MI")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.limit").value(1));
    }

    @Test
    public void testGetMerchantsSearch_ErrorHandling() throws Exception {

        MerchantSearch merchantSearch = new MerchantSearch();
        merchantSearch.setAddressLine1("1 Oak Street");

        String requestJson = objectMapper.writeValueAsString(merchantSearch);
        doThrow(new ApiException(400, "Something went wrong")).when(locationsMerchantsApiReferenceService).getMerchants(Mockito.any(MerchantSearch.class),
                Mockito.anyInt(),Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
        mvc.perform(post("/locations/merchants/searches?limit=1&offset=6&distance=10&distanceUnit=MI")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Something went wrong"));
    }

}