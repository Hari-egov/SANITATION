package org.egov.pqm.anomaly.finder.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.egov.common.contract.request.RequestInfo;
import org.egov.pqm.anomaly.finder.config.PqmAnomalyConfiguration;
import org.egov.pqm.anomaly.finder.util.AnomalyFinderConstants;
import org.egov.pqm.anomaly.finder.util.PqmAnomalyFinderUtil;
import org.egov.pqm.anomaly.finder.web.model.AnomalyType;
import org.egov.pqm.anomaly.finder.web.model.AuditDetails;
import org.egov.pqm.anomaly.finder.web.model.PqmAnomaly;
import org.egov.pqm.anomaly.finder.web.model.PqmAnomalyRequest;
import org.egov.pqm.anomaly.finder.web.model.SourceType;
import org.egov.pqm.anomaly.finder.web.model.Test;
import org.egov.pqm.anomaly.finder.web.model.TestRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EnrichmentService {

	@Autowired
	private PqmAnomalyConfiguration pqmAnomalyConfiguration;

	@Autowired
	private PqmAnomalyFinderUtil pqmAnomalyFinderUtil;

	/**
	 * enrich the create FSM request with the required data
	 * 
	 * @param testRequest
	 * @param mdmsData
	 */
	public PqmAnomalyRequest enrichPqmAnomalyCreateRequest(TestRequest testRequest, String topic) {
	    RequestInfo requestInfo = testRequest.getRequestInfo();
	    List<Test> tests = testRequest.getTests();
	    List<PqmAnomaly> pqmAnomalys = new ArrayList<>();

		for (Test test : tests) {
			PqmAnomaly pqmAnomaly = new PqmAnomaly();
			AuditDetails auditDetails = pqmAnomalyFinderUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), true);

			AnomalyType anomalyType = null;
			
			if(topic.equalsIgnoreCase(pqmAnomalyConfiguration.getNotAsPerBenchMark())) {
				anomalyType = AnomalyType.LAB_RESULTS_NOT_AS_PER_BENCHMARK;
			}
			if(topic.equalsIgnoreCase(pqmAnomalyConfiguration.getTestNotSubmitted())) {
				anomalyType = AnomalyType.TEST_RESULT_NOT_SUBMITTED;
			}
			
			pqmAnomalys.add(PqmAnomaly.builder()
					.id(UUID.randomUUID().toString())
					.testId(test.getTestId())
					.tenantId(test.getTenantId())
					.anomalyType(anomalyType)
					.isActive(test.getIsActive())
					.additionalDetails(test.getAdditionalDetails())
					.auditDetails(auditDetails)
					.build());
		}

	    PqmAnomalyRequest pqmAnomalyRequest =
	        PqmAnomalyRequest.builder()
	            .requestInfo(requestInfo)
	            .pqmAnomalys(pqmAnomalys)
	            .build();

	    return pqmAnomalyRequest;
	}


}