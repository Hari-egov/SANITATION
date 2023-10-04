package org.egov.pqm.web.controllers;

import javax.validation.Valid;

import org.egov.pqm.service.PqmService;
import org.egov.pqm.util.ResponseInfoFactory;
import org.egov.pqm.web.model.RequestInfoWrapper;
import org.egov.pqm.web.model.TestRequest;
import org.egov.pqm.web.model.TestResponse;
import org.egov.pqm.web.model.TestSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class PqmController {
	
	@Autowired
	private PqmService pqmService;
	
	@Autowired
	private ResponseInfoFactory responseInfoFactory;

  @PostMapping(value = "/_create", produces = {"*/*"}, consumes = {"application/json"})
  ResponseEntity<TestResponse> create(@Valid @RequestBody TestRequest testRequest) {
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
  }

  @PostMapping(value = "/_update", produces = {"*/*"}, consumes = {"application/json"})
  ResponseEntity<TestResponse> update(@Valid @RequestBody TestRequest testRequest) {
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
  }
  
  @PostMapping(value = "/_search", produces = {"*/*"}, consumes = {"application/json"})
  ResponseEntity<TestResponse> search(@Valid @RequestBody RequestInfoWrapper requestInfoWrapper,
      @Valid @RequestBody TestSearchRequest testSearchRequest) {
	  TestResponse response = pqmService.pqmSearch(testSearchRequest, requestInfoWrapper.getRequestInfo());

		response.setResponseInfo(
				responseInfoFactory.createResponseInfoFromRequestInfo(requestInfoWrapper.getRequestInfo(), true));
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
  }

}
