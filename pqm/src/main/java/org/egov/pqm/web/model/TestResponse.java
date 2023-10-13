package org.egov.pqm.web.model;

import java.util.List;

import javax.validation.Valid;

import org.egov.common.contract.response.ResponseInfo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestResponse {

  @JsonProperty("responseInfo")
  private ResponseInfo responseInfo = null;

  @JsonProperty("tests")
  @Valid
  private List<Test> tests = null;

  @JsonProperty("pagination")
  private Pagination pagination = null;
  
  @JsonProperty("totalCount")
  private Integer totalCount =0;

}
