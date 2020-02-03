package com.wojo.authservice.definition;

import cucumber.api.java.en.Then;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;

public class HttpResponseDef {

    public static ResponseEntity<String> response;

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int responseStatus) {
        assertEquals(responseStatus, response.getStatusCodeValue());
    }

}
