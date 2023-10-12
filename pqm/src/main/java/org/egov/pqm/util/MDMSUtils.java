package org.egov.pqm.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.egov.common.contract.request.RequestInfo;
import org.egov.mdms.model.MasterDetail;
import org.egov.mdms.model.MdmsResponse;
import org.egov.mdms.model.ModuleDetail;
import org.egov.pqm.config.ServiceConfiguration;
import org.egov.pqm.repository.ServiceRequestRepository;
import org.egov.pqm.web.model.mdms.MdmsCriteriaReq;
import org.egov.pqm.web.model.mdms.MdmsCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@Slf4j
public class MDMSUtils {

  @Autowired
  private ServiceRequestRepository serviceRequestRepository;

  @Autowired
  private ServiceConfiguration config;

  @Autowired
  private ObjectMapper objectMapper;

  public static final String filterCode = "$.*.code";

  public Map<String, Map<String, JSONArray>> fetchMdmsData(RequestInfo requestInfo, String tenantId, String moduleName,
      List<String> masterNameList) {
    org.egov.mdms.model.MdmsCriteriaReq mdmsCriteriaReq = getMdmsRequest(requestInfo, tenantId, moduleName, masterNameList);
    Object response = new HashMap<>();
    Integer rate = 0;
    MdmsResponse mdmsResponse = new MdmsResponse();
    try {
      Object result = serviceRequestRepository.fetchResult(getMdmsSearchUrl(), mdmsCriteriaReq);
      mdmsResponse = objectMapper.convertValue(result, MdmsResponse.class);
    } catch (Exception e) {
      log.error("Exception occurred while fetching category lists from mdms: ", e);
    }

    return mdmsResponse.getMdmsRes();

  }

  private org.egov.mdms.model.MdmsCriteriaReq getMdmsRequest(RequestInfo requestInfo, String tenantId,
      String moduleName, List<String> masterNameList) {
    List<MasterDetail> masterDetailList = new ArrayList<>();
    for (String masterName : masterNameList) {
      MasterDetail masterDetail = new MasterDetail();
      masterDetail.setName(masterName);
      masterDetailList.add(masterDetail);
    }

    ModuleDetail moduleDetail = new ModuleDetail();
    moduleDetail.setMasterDetails(masterDetailList);
    moduleDetail.setModuleName(moduleName);
    List<ModuleDetail> moduleDetailList = new ArrayList<>();
    moduleDetailList.add(moduleDetail);

    org.egov.mdms.model.MdmsCriteria mdmsCriteria = new org.egov.mdms.model.MdmsCriteria();
    mdmsCriteria.setTenantId(tenantId.split("\\.")[0]);
    mdmsCriteria.setModuleDetails(moduleDetailList);

    org.egov.mdms.model.MdmsCriteriaReq mdmsCriteriaReq = new org.egov.mdms.model.MdmsCriteriaReq();
    mdmsCriteriaReq.setMdmsCriteria(mdmsCriteria);
    mdmsCriteriaReq.setRequestInfo(requestInfo);

    return mdmsCriteriaReq;
  }

  /**
   * Returns the url for mdms search v1 endpoint
   *
   * @return url for mdms search v1 endpoint
   */
  public StringBuilder getMdmsSearchUrl() {
    return new StringBuilder().append(config.getMdmsHost()).append(config.getMdmsEndPoint());
  }

  /**
   * Returns the url for mdms search v2 endpoint
   *
   * @return url for mdms search v2 endpoint
   */
  public StringBuilder getMdmsSearchUrl2() {
    return new StringBuilder().append(config.getMdmsHostv2()).append(config.getMdmsv2EndPoint());
  }

	private ModuleDetail getTenantModuleRequestData() {
		List<MasterDetail> tenantMasterDetails = new ArrayList<>();

		ModuleDetail tenantModuleDetail = null;
		return tenantModuleDetail;
	}

  public Object mdmsCallV2(RequestInfo requestInfo, String tenantId, String schemaCode){
    org.egov.pqm.web.model.mdms.MdmsCriteriaReq mdmsCriteriaReq = getMDMSRequestV2(requestInfo, tenantId, schemaCode);
    StringBuilder uri = getMdmsSearchUrl2();
    Object result = serviceRequestRepository.fetchResult(uri,mdmsCriteriaReq);
    return result;
  }

  public MdmsCriteriaReq getMDMSRequestV2(RequestInfo requestInfo , String  tenantId ,
      String schemaCode){
    org.egov.pqm.web.model.mdms.MdmsCriteria mdmsCriteria = org.egov.pqm.web.model.mdms.MdmsCriteria.builder().tenantId(tenantId).schemaCode(schemaCode).build();
    org.egov.pqm.web.model.mdms.MdmsCriteriaReq mdmsCriteriaReq =
        org.egov.pqm.web.model.mdms.MdmsCriteriaReq.builder().mdmsCriteria(mdmsCriteria).requestInfo(requestInfo).build();
    return mdmsCriteriaReq;
  }

}