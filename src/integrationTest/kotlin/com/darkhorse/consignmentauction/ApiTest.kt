package com.darkhorse.consignmentauction

import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort


@SpringBootTest(classes = [Application::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class ApiTest: IntegrationTest(){

  @LocalServerPort
  private val port = 0

  fun given(): RequestSpecification {
    val spec = RequestSpecBuilder()
      .setContentType(ContentType.JSON)
      .setBaseUri(String.format("http://localhost:%d/", port))
      .addFilter(ResponseLoggingFilter()) // log request and response for better debugging. You can also
      // only log if a requests fails.
      .addFilter(RequestLoggingFilter())
      .build()
    return RestAssured.given().spec(spec)
  }

}
