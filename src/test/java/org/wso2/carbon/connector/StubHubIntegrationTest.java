/*
*  Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/
package org.wso2.carbon.connector;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.connector.integration.test.base.ConnectorIntegrationTestBase;
import org.wso2.connector.integration.test.base.RestResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Sample integration test
 */
public class StubHubIntegrationTest extends ConnectorIntegrationTestBase {

    private Map<String, String> esbRequestHeadersMap = new HashMap<String, String>();

    private Map<String, String> apiRequestHeadersMap = new HashMap<String, String>();

    @BeforeClass(alwaysRun = true)
    public void setEnvironment() throws Exception {
        init("StubHub-connector-1.0.0");
        esbRequestHeadersMap.put("Accept-Charset", "UTF-8");
        esbRequestHeadersMap.put("Content-Type", "application/json");
        esbRequestHeadersMap.put("Accept", "application/json");
        apiRequestHeadersMap.put("Accept-Charset", "UTF-8");
        apiRequestHeadersMap.put("Accept", "application/json");
        apiRequestHeadersMap.put("Content-Type", "application/json");
    }

    @Test(priority = 1,enabled = true, groups = {"wso2.esb"}, description = "StubHub {getEventDetails} " +
            "integration test with mandatory parameters.")
    public void testGetAccessTokens() throws Exception {
        esbRequestHeadersMap.put("Action", "urn:getAccessToken");




        RestResponse<JSONObject> esbRestResponse =
                sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap, "esb_getEventDetails_mandatory.json");

        printer(esbRestResponse,"FROM ESB **************************");



    }



    /*@Test(priority = 1,enabled = true, groups = {"wso2.esb"}, description = "StubHub {getEventDetails} " +
            "integration test with mandatory parameters.")
    public void testGetEventDetailsWithMandatoryParameters() throws Exception {
        esbRequestHeadersMap.put("Action", "urn:getEventDetails");

        final String appToken = connectorProperties.getProperty("appToken");
        apiRequestHeadersMap.put("Authorization","Bearer " + appToken);

        String apiEndPoint =
                connectorProperties.getProperty("apiUrl") + "/catalog/events/v2/"
                        + connectorProperties.getProperty("eventId");
        printer(apiEndPoint,"");
        RestResponse< JSONObject > apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        printer(apiRestResponse,"FROM API **************************");
        Assert.assertEquals(apiRestResponse.getHttpStatusCode(),200);
        RestResponse<JSONObject> esbRestResponse =
                sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap, "esb_getEventDetails_mandatory.json");

        printer(esbRestResponse,"FROM ESB **************************");

        Assert.assertEquals(esbRestResponse.getHttpStatusCode(),200);
        Assert.assertEquals(esbRestResponse.getBody().get("id"),apiRestResponse.getBody().get("id"));
        Assert.assertEquals(esbRestResponse.getBody().get("eventUrl")
                ,apiRestResponse.getBody().get("eventUrl"));
        Assert.assertEquals(esbRestResponse.getBody().get("eventDateUTC")
                ,apiRestResponse.getBody().get("eventDateUTC"));
        Assert.assertEquals(esbRestResponse.getBody().getJSONObject("venue").get("id")
                ,apiRestResponse.getBody().getJSONObject("venue").get("id"));
        Assert.assertEquals(esbRestResponse.getBody().getJSONObject("eventMeta").get("seoDescription")
                ,apiRestResponse.getBody().getJSONObject("eventMeta").get("seoDescription"));

    }


    @Test(priority = 2,enabled = true, groups = {"wso2.esb"}, description = "StubHub {getEventDetails-Negative Case} integration test with mandatory parameters.")
    public void testGetEventDetailsWithMandatoryParametersNegativeCase() throws Exception {
        esbRequestHeadersMap.put("Action", "urn:getEventDetails");

        final String appToken = connectorProperties.getProperty("appToken");
        apiRequestHeadersMap.put("Authorization","Bearer " + appToken);

        String apiEndPoint =
                connectorProperties.getProperty("apiUrl") + "/catalog/events/v2/invalid";
        RestResponse<JSONObject> esbRestResponse =
                sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap, "esb_getEventDetails_negative.json");
        RestResponse< JSONObject > apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);

        Assert.assertEquals(esbRestResponse.getHttpStatusCode(),400);

        Assert.assertEquals(esbRestResponse.getBody().getJSONObject("errors").getJSONArray("error").getJSONObject(0).get("errorTypeId"),
                apiRestResponse.getBody().getJSONObject("errors").getJSONArray("error").getJSONObject(0).get("errorTypeId"));
    }

    @Test(priority = 3,enabled = true,groups = {"wso2.esb"},description = "StubHub {getVenueDetails} integration test with mandatory parameters.")
    public void testGetVenuesDetailsWithMandatoryParameters() throws Exception{
        esbRequestHeadersMap.put("Action", "urn:getVenueDetails");
        final String appToken = connectorProperties.getProperty("appToken");
        apiRequestHeadersMap.put("Authorization","Bearer " + appToken);

        String apiEndPoint =
                connectorProperties.getProperty("apiUrl") + "/catalog/venues/v2/"
                        + connectorProperties.getProperty("venueId");
        RestResponse<JSONObject> esbRestResponse =
                sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap, "esb_getVenueDetails_mandatory.json");
        RestResponse< JSONObject > apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);

        Assert.assertEquals(esbRestResponse.getHttpStatusCode(),200);
        Assert.assertEquals(esbRestResponse.getBody().get("id"), apiRestResponse.getBody().get("id"));
        Assert.assertEquals(esbRestResponse.getBody().get("name"), apiRestResponse.getBody().get("name"));
        Assert.assertEquals(esbRestResponse.getBody().get("description"), apiRestResponse.getBody().get("description"));
    }

    @Test(priority = 4,enabled = true,groups = {"wso2.esb"},description = "StubHub {getVenueDetails - Negative Test Case} integration test with mandatory parameters.")
    public void testGetVenuesDetailsWithMandatoryParametersNegativeTestCase() throws Exception{
        esbRequestHeadersMap.put("Action", "urn:getVenueDetails");
        final String appToken = connectorProperties.getProperty("appToken");
        apiRequestHeadersMap.put("Authorization","Bearer " + appToken);

        String apiEndPoint =
                connectorProperties.getProperty("apiUrl") + "/catalog/venues/v2/invalid";
        RestResponse<JSONObject> esbRestResponse =
                sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap, "esb_getVenueDetails_negative.json");
        RestResponse< JSONObject > apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);

        Assert.assertEquals(esbRestResponse.getHttpStatusCode(),400);
        Assert.assertEquals(esbRestResponse.getBody().getJSONObject("errors").
                        getJSONArray("error").getJSONObject(0).get("errorTypeId"),
                apiRestResponse.getBody().getJSONObject("errors").
                        getJSONArray("error").getJSONObject(0).get("errorTypeId"));
    }



    @Test(priority = 5,enabled = true,groups = {"wso2.esb"},description = "StubHub {searchInventory - Mandatory} integration test with mandatory parameters.")
    public void testSearchInventoryWithMandatoryParameters() throws Exception{
        esbRequestHeadersMap.put("Action", "urn:searchInventory");
        final String appToken = connectorProperties.getProperty("appToken");
        apiRequestHeadersMap.put("Authorization","Bearer " + appToken);

        String apiEndPoint =
                connectorProperties.getProperty("apiUrl") + "/search/inventory/v1?eventId="
                        + connectorProperties.getProperty("eventId");
        RestResponse<JSONObject> esbRestResponse =
                sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap, "esb_searchInventory_mandatory.json");
        RestResponse< JSONObject > apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);

        Assert.assertEquals(esbRestResponse.getHttpStatusCode(),200);
        Assert.assertEquals(esbRestResponse.getBody().get("totalListings"),apiRestResponse.getBody().get("totalListings"));
        Assert.assertEquals(esbRestResponse.getBody().get("start"),apiRestResponse.getBody().get("start"));
        Assert.assertEquals(esbRestResponse.getBody().get("rows"),apiRestResponse.getBody().get("rows"));
    }

    @Test(priority = 6,enabled = true,groups = {"wso2.esb"},description = "StubHub {searchInventory- Mandatory - Negative Case} integration test with mandatory parameters.")
    public void testSearchInventoryWithMandatoryParametersNegativeCase() throws Exception {
        esbRequestHeadersMap.put("Action", "urn:searchInventory");
        final String appToken = connectorProperties.getProperty("appToken");
        apiRequestHeadersMap.put("Authorization", "Bearer " + appToken);

        String apiEndPoint =
                connectorProperties.getProperty("apiUrl") + "/search/inventory/v1?eventId=invalid";
        RestResponse<JSONObject> esbRestResponse =
                sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap, "esb_searchInventory_negative.json");
        RestResponse<JSONObject> apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 400);
        Assert.assertEquals(esbRestResponse.getBody().get("code"),apiRestResponse.getBody().get("code"));
    }*/



   /* @Test(priority = 7,enabled = true,groups = {"wso2.esb"},description = "StubHub {searchInventory - Optional Parameters} integration test with optional parameters.")
    public void testSearchInventoryWithOptionalParameters() throws Exception{
        esbRequestHeadersMap.put("Action", "urn:searchInventory");
        final String appToken = connectorProperties.getProperty("appToken");
        apiRequestHeadersMap.put("Authorization","Bearer " + appToken);

        String apiEndPoint =
                connectorProperties.getProperty("apiUrl") + "/search/inventory/v1?eventId="
                        + connectorProperties.getProperty("eventId")
                        + "&zoneidlist=" + connectorProperties.getProperty("zoneIdList")
                        + "&sectionidlist=" + connectorProperties.getProperty("sectionIdList")
                        + "&quantity=" + connectorProperties.getProperty("quantity")
                        + "&pricemin=" + connectorProperties.getProperty("priceMin")
                        + "&pricemax=" + connectorProperties.getProperty("priceMax")
                        + "&listingattributelist=" + connectorProperties.getProperty("listingAttributeList")
                        + "&listingattributecategorylist=" + connectorProperties.getProperty("listingAttributeCategoryList")
                        + "&deliverytypelist=" + connectorProperties.getProperty("deliveryTypeList")
                        + "&zonestats=" + connectorProperties.getProperty("zoneStats")
                        + "&sectionstats=" + connectorProperties.getProperty("sectionStats")
                        + "&pricingsummary=" + connectorProperties.getProperty("pricingSummary")
                ;

        RestResponse<JSONObject> esbRestResponse =
                sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap, "esb_searchInventory_optional.json");
        RestResponse< JSONObject > apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);


        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(esbRestResponse.getBody().get("totalListings"),apiRestResponse.getBody().get("totalListings"));
        Assert.assertEquals(esbRestResponse.getBody().get("start"),apiRestResponse.getBody().get("start"));
        Assert.assertEquals(esbRestResponse.getBody().get("rows"),apiRestResponse.getBody().get("rows"));
    }*/


     /*@Test(priority = 8, enabled = true,groups = {"wso2.esb"},description = "StubHub {getMyListing - Mandatory} integration test with mandatory parameters.")
    public void testCreatePriceAlert() throws Exception{
        final String accessToken = connectorProperties.getProperty("accessToken");
        apiRequestHeadersMap.put("Authorization","Bearer " + accessToken);

        //TODO Create Price Alert Using esb
        esbRequestHeadersMap.put("Action", "urn:createPriceAlert");
        RestResponse<JSONObject> esbRestResponseCreatePriceAlert =
                sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap, "esb_createPriceAlert.json");

        printer(esbRestResponseCreatePriceAlert,"ESB CREATE");
        Assert.assertEquals(esbRestResponseCreatePriceAlert.getHttpStatusCode(),201);

        String priceAlertRequestId = esbRestResponseCreatePriceAlert.getBody().getJSONObject("priceAlert").getJSONObject("priceAlert")
                .get("priceAlertId").toString();



        connectorProperties.setProperty("priceAlertRequestId",priceAlertRequestId);
    }




    @Test(priority = 9,enabled = true, groups = {"wso2.esb"}, description = "StubHub {searchInventory - Mandatory} integration test with mandatory parameters.")
    public void testGetPriceAlertRequest() throws Exception {
        esbRequestHeadersMap.put("Action", "urn:getMyPriceAlertRequest");

        final String accessToken = connectorProperties.getProperty("accessToken");
        apiRequestHeadersMap.put("Authorization", "Bearer " + accessToken);
        String apiEndPoint =
                connectorProperties.getProperty("apiUrl") + "/user/customers/v1/"
                        + connectorProperties.getProperty("userId")
                        + "/pricealerts/" + connectorProperties.get("priceAlertRequestId");
        RestResponse<JSONObject> apiRestResponseGetPriceAlert = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);

        RestResponse<JSONObject> esbRestResponseGetPriceAlert =
                sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap, "esb_getMyPriceAlertRequest_manadatory.json");

        Assert.assertEquals(esbRestResponseGetPriceAlert.getHttpStatusCode(), 200);
        printer(esbRestResponseGetPriceAlert, "ESB GET PRICE ALERT REQUEST");
        printer(apiRestResponseGetPriceAlert, "API GET PRICE ALERT REQUEST");

        Assert.assertEquals(esbRestResponseGetPriceAlert.getBody().getJSONObject("priceAlert").getJSONObject("priceAlert").get("priceAlertId"),
                apiRestResponseGetPriceAlert.getBody().getJSONObject("priceAlert").getJSONObject("priceAlert").get("priceAlertId"));
        Assert.assertEquals(esbRestResponseGetPriceAlert.getBody().getJSONObject("priceAlert")
                        .getJSONObject("priceAlert").get("eventId"),
                apiRestResponseGetPriceAlert.getBody().getJSONObject("priceAlert")
                        .getJSONObject("priceAlert").get("eventId"));
        Assert.assertEquals(esbRestResponseGetPriceAlert.getBody().getJSONObject("priceAlert")
                        .getJSONObject("priceAlert").get("priceAlertId"),
                apiRestResponseGetPriceAlert.getBody().getJSONObject("priceAlert")
                        .getJSONObject("priceAlert").get("priceAlertId"));
    }*/

    /*
        @Test(priority = 10,enabled = true, groups = {"wso2.esb"}, description = "StubHub {searchInventory - Mandatory} integration test with mandatory parameters.")
        public void testGetPriceAlertRequests() throws Exception {
            esbRequestHeadersMap.put("Action", "urn:getMyPriceAlertRequests");


            final String accessToken = connectorProperties.getProperty("accessToken");
            apiRequestHeadersMap.put("Authorization", "Bearer " + accessToken);
            String apiEndPoint =
                    connectorProperties.getProperty("apiUrl") + "/user/customers/v1/"
                            + connectorProperties.getProperty("userId")
                            + "/pricealerts";
            RestResponse<JSONObject> apiRestResponseGetPriceAlerts = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);

            RestResponse<JSONObject> esbRestResponseGetPriceAlerts =
                    sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap, "esb_getMyPriceAlertRequests_mandatory.json");

            Assert.assertEquals(esbRestResponseGetPriceAlerts.getHttpStatusCode(),200);
            Assert.assertEquals(esbRestResponseGetPriceAlerts.getBody().getJSONObject("priceAlert")
                            .getJSONArray("priceAlert").getJSONObject(0).get("eventId"),
                    apiRestResponseGetPriceAlerts.getBody().getJSONObject("priceAlert")
                            .getJSONArray("priceAlert").getJSONObject(0).get("eventId"));

            printer(apiRestResponseGetPriceAlerts, "API ALL REQUEST **********************************");
            printer(esbRestResponseGetPriceAlerts, "ESB ALL REQUEST **********************************");

        }



        @Test(priority = 11,enabled = true,groups = {"wso2.esb"},description = "StubHub {getMyListing - Mandatory} integration test with mandatory parameters.")
        public void testDeletePriceAlert() throws Exception{
            //TODO Delete Earlier Created Price Alert
            final String accessToken = connectorProperties.getProperty("accessToken");
            apiRequestHeadersMap.put("Authorization","Bearer " + accessToken);

            esbRequestHeadersMap.put("Action", "urn:deleteMyPriceAlertRequest");

            RestResponse<JSONObject> esbRestResponseDeletePriceAlert =
                    sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap, "esb_deleteMyPriceAlertRequest.json");
            Assert.assertEquals(esbRestResponseDeletePriceAlert.getHttpStatusCode(),200);

            //TODO Check Price Alert is deleted or not
            String apiEndPoint =
                    connectorProperties.getProperty("apiUrl") + "/user/customers/v1/"
                            + connectorProperties.getProperty("userId")
                            + "/pricealerts?eventId=" + connectorProperties.getProperty("eventId");
            RestResponse<JSONObject> apiRestResponseGetPriceAlert = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);



                Assert.assertEquals(apiRestResponseGetPriceAlert.getHttpStatusCode(), 404);
                Assert.assertEquals(apiRestResponseGetPriceAlert.getBody().getJSONObject("priceAlert").getJSONObject("errors")
                                .getJSONArray("error").getJSONObject(0).get("errorTypeId"),
                        "101");

        }*/
/*

    @Test(enabled = true,groups = {"wso2.esb"},description = "StubHub {getMyListings - Mandatory} integration test with mandatory parameters.")
    public void testgetMyListingsWithMandatoryParameters() throws Exception{
        esbRequestHeadersMap.put("Action", "urn:getMyListings");
        final String accessToken = connectorProperties.getProperty("accessToken");
        apiRequestHeadersMap.put("Authorization","Bearer " + accessToken);

        String apiEndPoint = connectorProperties.getProperty("apiUrl")
                +"/accountmanagement/listings/v1/seller/" + connectorProperties.getProperty("userId");


        RestResponse<JSONObject> esbRestResponse =
                sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap, "esb_getMyListings_mandatory.json");
        RestResponse< JSONObject > apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);

        Assert.assertEquals(esbRestResponse.getHttpStatusCode(),200);
        Assert.assertEquals(esbRestResponse.getBody().getJSONObject("listings").get("numFound"),apiRestResponse.getBody().getJSONObject("listings").get("numFound"));
    }


    @Test(enabled = true,groups = {"wso2.esb"},description = "StubHub {getMyListing - Mandatory} integration test with mandatory parameters.")
    public void testgetMyListingWithMandatoryParameters() throws Exception{
        esbRequestHeadersMap.put("Action", "urn:getMyListing");
        final String accessToken = connectorProperties.getProperty("accessToken");
        apiRequestHeadersMap.put("Authorization","Bearer " + accessToken);

        String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/accountmanagement/listings/v1/" + connectorProperties.getProperty("listingId");

        RestResponse<JSONObject> esbRestResponse =
                sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap, "esb_getMyListing_mandatory.json");
        RestResponse< JSONObject > apiRestResponse = sendJsonRestRequest(apiEndPoint, "GET", apiRequestHeadersMap);
        Assert.assertEquals(esbRestResponse.getHttpStatusCode(), 200);
        Assert.assertEquals(esbRestResponse.getBody().getJSONObject("listing").get("id"),apiRestResponse.getBody().getJSONObject("listing").get("id"));
        Assert.assertEquals(esbRestResponse.getBody().getJSONObject("listing").get("quantity"),apiRestResponse.getBody().getJSONObject("listing").get("quantity"));
        Assert.assertEquals(esbRestResponse.getBody().getJSONObject("listing").get("venueConfigSectionsId"),apiRestResponse.getBody().getJSONObject("listing").get("venueConfigSectionsId"));
        Assert.assertEquals(esbRestResponse.getBody().getJSONObject("listing").get("saleEndDate"),apiRestResponse.getBody().getJSONObject("listing").get("saleEndDate"));

    }

*/



    private void printer(RestResponse<JSONObject> response, String from) {
        System.out.println("#\n#\n#\n#\n#\n#\n#\n#\n###########################");
        System.out.println("RESPONSE : [" + from + "]  " + response.getBody().toString());
        System.out.println("#\n#\n#\n#\n#\n#\n#\n#\n###########################");

    }

    private void printer(String response, String from) {
        System.out.println("#\n#\n#\n#\n#\n#\n#\n#\n###########################");
        System.out.println("RESPONSE : [" + from + "]  " + response);
        System.out.println("#\n#\n#\n#\n#\n#\n#\n#\n###########################");

    }


}