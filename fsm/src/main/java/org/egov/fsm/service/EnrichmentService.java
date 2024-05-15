package org.egov.fsm.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.egov.common.contract.request.RequestInfo;
import org.egov.fsm.config.FSMConfiguration;
import org.egov.fsm.repository.FsmWorkerRepository;
import org.egov.fsm.repository.IdGenRepository;
import org.egov.fsm.util.ComparisionUtility;
import org.egov.fsm.util.FSMAuditUtil;
import org.egov.fsm.util.FSMConstants;
import org.egov.fsm.util.FSMErrorConstants;
import org.egov.fsm.util.FSMUtil;
import org.egov.fsm.util.SanitationWorkerUtils;
import org.egov.fsm.web.model.AuditDetails;
import org.egov.fsm.web.model.FSM;
import org.egov.fsm.web.model.FSMAudit;
import org.egov.fsm.web.model.FSMRequest;
import org.egov.fsm.web.model.FSMSearchCriteria;
import org.egov.fsm.web.model.Workflow;
import org.egov.fsm.web.model.garima.SanitationWorker;
import org.egov.fsm.web.model.garima.SanitationWorkerSearch;
import org.egov.fsm.web.model.garima.SanitationWorkerSearchCriteria;
import org.egov.fsm.web.model.garima.SanitationWorkerSearchResponse;
import org.egov.fsm.web.model.idgen.IdResponse;
import org.egov.fsm.web.model.user.User;
import org.egov.fsm.web.model.user.UserDetailResponse;
import org.egov.fsm.web.model.worker.Worker;
import org.egov.fsm.web.model.worker.WorkerSearchCriteria;
import org.egov.fsm.web.model.worker.WorkerStatus;
import org.egov.fsm.web.model.worker.WorkerType;
import org.egov.tracer.model.CustomException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EnrichmentService {
	@Autowired
	private FSMConfiguration config;

	@Autowired
	private IdGenRepository idGenRepository;
	@Autowired
	private BoundaryService boundaryService;

	@Autowired
	private UserService userService;

	@Autowired
	private FSMUtil fsmUtil;

	@Autowired
	private ComparisionUtility comparisionUtility;
	
	@Autowired
	private FsmWorkerRepository fsmWorkerRepository;
	
	@Autowired
	private GarimaSanitationWorkerService garimaSanitationWorkerService;
	
	@Autowired
	private SanitationWorkerUtils sanitationWorkerUtils;
	
	/**
	 * enrich the create FSM request with the required data
	 * 
	 * @param fsmRequest
	 * @param mdmsData
	 */
	public void enrichFSMCreateRequest(FSMRequest fsmRequest) {
		RequestInfo requestInfo = fsmRequest.getRequestInfo();

		if (fsmRequest.getRequestInfo().getUserInfo().getType().equalsIgnoreCase(FSMConstants.CITIZEN)) {
			User citzen = new User();
			BeanUtils.copyProperties(fsmRequest.getRequestInfo().getUserInfo(), citzen);
			if (fsmRequest.getFsm().getCitizen() != null && fsmRequest.getFsm().getCitizen().getGender() != null) {
				UserDetailResponse userDetailResponse = userService
						.updateApplicantsGender(fsmRequest.getFsm().getCitizen(), fsmRequest.getRequestInfo());
				citzen = userDetailResponse.getUser().get(0);
			}
			fsmRequest.getFsm().setCitizen(citzen);
		} else {
			userService.manageApplicant(fsmRequest);
		}
		setApplicationDetails(fsmRequest, requestInfo);

		if (fsmRequest.getWorkflow() == null) {
			String action = (fsmRequest.getRequestInfo().getUserInfo().getType()
					.equalsIgnoreCase(FSMConstants.EMPLOYEE))
					|| (fsmRequest.getRequestInfo().getUserInfo().getType().equalsIgnoreCase(FSMConstants.SYSTEM))
							? FSMConstants.WF_ACTION_APPLY
							: FSMConstants.WF_ACTION_CREATE;
			fsmRequest.setWorkflow(Workflow.builder().action(action).build());
		}

		setIdgenIds(fsmRequest);

	}

	private void setApplicationDetails(FSMRequest fsmRequest, RequestInfo requestInfo) {
		boundaryService.getAreaType(fsmRequest, config.getHierarchyTypeCode());
		fsmRequest.getFsm().setStatus(FSM.StatusEnum.ACTIVE);
		fsmRequest.getFsm().setApplicationStatus(FSMConstants.DRAFT);
		AuditDetails auditDetails = fsmUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), true);
		fsmRequest.getFsm().setAuditDetails(auditDetails);
		fsmRequest.getFsm().setId(UUID.randomUUID().toString());

		fsmRequest.getFsm().setAccountId(fsmRequest.getFsm().getCitizen().getUuid());

		if (fsmRequest.getFsm().getApplicationType() == null || fsmRequest.getFsm().getApplicationType().isEmpty()) {
			fsmRequest.getFsm().setApplicationType(FSMConstants.ADHOC_SERVICE);
		}
		if (fsmRequest.getFsm().getAddress() != null) {
			if (StringUtils.isEmpty(fsmRequest.getFsm().getAddress().getId()))
				fsmRequest.getFsm().getAddress().setId(UUID.randomUUID().toString());
			fsmRequest.getFsm().getAddress().setTenantId(fsmRequest.getFsm().getTenantId());
			fsmRequest.getFsm().getAddress().setAuditDetails(auditDetails);
			if (fsmRequest.getFsm().getAddress().getGeoLocation() != null
					&& StringUtils.isEmpty(fsmRequest.getFsm().getAddress().getGeoLocation().getId()))
				fsmRequest.getFsm().getAddress().getGeoLocation().setId(UUID.randomUUID().toString());
		} else {
			throw new CustomException(FSMErrorConstants.INVALID_ADDRES, " Address is mandatory");
		}

		if (fsmRequest.getFsm().getPitDetail() != null) {
			if (StringUtils.isEmpty(fsmRequest.getFsm().getPitDetail().getId()))
				fsmRequest.getFsm().getPitDetail().setId(UUID.randomUUID().toString());
			fsmRequest.getFsm().getPitDetail().setTenantId(fsmRequest.getFsm().getTenantId());
			fsmRequest.getFsm().getPitDetail().setAuditDetails(auditDetails);
		}
	}

	/**
	 * generate the applicationNo using the idGen serivce and populate
	 * 
	 * @param request
	 */
	private void setIdgenIds(FSMRequest request) {
		RequestInfo requestInfo = request.getRequestInfo();
		String tenantId = request.getFsm().getTenantId();
		FSM fsm = request.getFsm();

		List<String> applicationNumbers = getIdList(requestInfo, tenantId, config.getApplicationNoIdgenName(),
				config.getApplicationNoIdgenFormat());
		ListIterator<String> itr = applicationNumbers.listIterator();

		Map<String, String> errorMap = new HashMap<>();

		if (!errorMap.isEmpty())
			throw new CustomException(errorMap);

		fsm.setApplicationNo(itr.next());
	}

	/**
	 * Generate the id
	 * 
	 * @param requestInfo
	 * @param tenantId
	 * @param idKey
	 * @param idformat
	 * @param count
	 * @return
	 */
	private List<String> getIdList(RequestInfo requestInfo, String tenantId, String idKey, String idformat) {
		List<IdResponse> idResponses = idGenRepository.getId(requestInfo, tenantId, idKey, idformat).getIdResponses();

		if (CollectionUtils.isEmpty(idResponses))
			throw new CustomException(FSMErrorConstants.IDGEN_ERROR, "No ids returned from idgen Service");

		return idResponses.stream().map(IdResponse::getId).collect(Collectors.toList());
	}

	/**
	 * enrich the update request with the requied ata
	 * 
	 * @param fsmRequest
	 * @param mdmsData
	 */
	public void enrichFSMUpdateRequest(FSMRequest fsmRequest, FSM oldFsm) {
		RequestInfo requestInfo = fsmRequest.getRequestInfo();
		AuditDetails auditDetails = fsmUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), false);
		auditDetails.setCreatedBy(oldFsm.getAuditDetails().getCreatedBy());
		auditDetails.setCreatedTime(oldFsm.getAuditDetails().getCreatedTime());
		fsmRequest.getFsm().setAuditDetails(auditDetails);
		if (fsmRequest.getFsm().getAddress() != null) {
			if (StringUtils.isEmpty(fsmRequest.getFsm().getAddress().getId()))
				fsmRequest.getFsm().getAddress().setId(UUID.randomUUID().toString());
			fsmRequest.getFsm().getAddress().setTenantId(fsmRequest.getFsm().getTenantId());
			fsmRequest.getFsm().getAddress().setAuditDetails(auditDetails);
			if (fsmRequest.getFsm().getAddress().getGeoLocation() != null
					&& StringUtils.isEmpty(fsmRequest.getFsm().getAddress().getGeoLocation().getId()))
				fsmRequest.getFsm().getAddress().getGeoLocation().setId(UUID.randomUUID().toString());
		} else {
			throw new CustomException(FSMErrorConstants.INVALID_ADDRES, " Address is mandatory");
		}

		if (fsmRequest.getFsm().getPitDetail() != null) {
			if (StringUtils.isEmpty(fsmRequest.getFsm().getPitDetail().getId())) {
				fsmRequest.getFsm().getPitDetail().setId(UUID.randomUUID().toString());
				fsmRequest.getFsm().getPitDetail().setTenantId(fsmRequest.getFsm().getTenantId());
			}
			fsmRequest.getFsm().getPitDetail().setAuditDetails(auditDetails);
		}

	}

	/**
	 * 
	 * @param fsms
	 * @param requestInfo
	 */
	public void enrichFSMSearch(List<FSM> fsms, RequestInfo requestInfo, String tenantId) {

		enrichBoundarys(fsms, requestInfo);
		List<String> accountIds = fsms.stream().map(FSM::getAccountId).collect(Collectors.toList());
		FSMSearchCriteria fsmsearch = new FSMSearchCriteria();
		fsmsearch.setTenantId(tenantId);
		fsmsearch.setOwnerIds(accountIds);
		UserDetailResponse userDetailResponse = userService.getUser(fsmsearch, requestInfo);
		encrichApplicant(userDetailResponse, fsms);
		enrichFsmWorkersSearch(fsms);

	}

	/**
	 * enrich the bounday in the FSM Object
	 * 
	 * @param fsms
	 * @param requestInfo
	 */
	private void enrichBoundarys(List<FSM> fsms, RequestInfo requestInfo) {
		fsms.forEach(fsm -> {
			try {
				boundaryService.getAreaType(new FSMRequest(requestInfo, fsm, null), config.getHierarchyTypeCode());
			} catch (CustomException ce) {
				log.debug(ce.getMessage());
				log.info("Bound data not found for FSM" + fsm.getApplicationNo());
			}
		});

	}

	/**
	 * enrich the applicant information in FSM
	 * 
	 * @param userDetailResponse
	 * @param fsms
	 */
	private void encrichApplicant(UserDetailResponse userDetailResponse, List<FSM> fsms) {

		List<User> users = userDetailResponse.getUser();
		Map<String, User> userIdToApplicantMap = new HashMap<>();
		users.forEach(user -> userIdToApplicantMap.put(user.getUuid(), user));
		fsms.forEach(fsm -> {
			fsm.setCitizen(userIdToApplicantMap.get(fsm.getAccountId()));
		});
	}

	public List<FSMAudit> enrichFSMAudit(FSMAuditUtil sourceObject, List<FSMAuditUtil> targetObjects) {
		return comparisionUtility.compareData(sourceObject, targetObjects);
	}
	
	/**
	 * enrich the GarimaSanitationWorker information in FSM Worker
	 * 
	 * @param sanitationWorker
	 * @param fsms
	 */
	public Worker encrichWorker(SanitationWorker sanitationWorker,FSMRequest fsmRequest ,boolean flag) {
		RequestInfo requestInfo = fsmRequest.getRequestInfo();
		AuditDetails auditDetails = fsmUtil.getAuditDetails(requestInfo.getUserInfo().getUuid(), flag);
		Worker worker = new Worker();
		if (sanitationWorker != null) {
			worker.setId(UUID.randomUUID().toString());
			worker.setTenantId(fsmRequest.getFsm().getTenantId());
			worker.setApplicationId(fsmRequest.getFsm().getId());
//			worker.setGarimaId(sanitationWorker.getGarima_id());
			worker.setIndividualId(sanitationWorker.getGarima_id());
			worker.setStatus(WorkerStatus.ACTIVE);
			worker.setWorkerType(sanitationWorker.getWorkerType());
			worker.setAdditionalDetails(null);
			worker.setAuditDetails(auditDetails);

		}
		return worker;
	}
	
	public void enrichFsmWorkersSearch(List<FSM> fsmApplications) {fsmApplications.forEach(fsm -> {
	    List<Worker> workers = fsmWorkerRepository.getWorkersData(WorkerSearchCriteria.builder()
	            .tenantId(fsm.getTenantId())
	            .applicationIds(Collections.singletonList(fsm.getId()))
	            .status(Arrays.asList(WorkerStatus.ACTIVE.toString(), WorkerStatus.INACTIVE.toString()))
	            .build());
	    List<SanitationWorkerSearch> sanitationWorkerSearches = fetchGarimaData(workers);
	    
	    List<SanitationWorker> sanitationWorkers=new ArrayList<SanitationWorker>();

			if (sanitationWorkerSearches != null && !sanitationWorkerSearches.isEmpty()) {
				for (SanitationWorkerSearch sanitationWorkerSearch : sanitationWorkerSearches) {
					SanitationWorker sanitationWorker = sanitationWorkerUtils
							.createSanitationWorker(sanitationWorkerSearch);
					String garimaId = sanitationWorker.getGarima_id();

					 Optional<Worker> matchingWorker = Optional.empty();
					    Iterator<Worker> workerIterator = workers.iterator();
					    while (workerIterator.hasNext()) {
					        Worker worker = workerIterator.next();
					        if (garimaId.equals(worker.getIndividualId())) {
					            matchingWorker = Optional.of(worker);
					            workerIterator.remove(); // Remove the matched worker from the list
					            break; // Exit loop once the first match is found
					        }
					    }

					if (matchingWorker.isPresent()) {
						Worker worker = matchingWorker.get();
						WorkerType workerType = worker.getWorkerType();
						sanitationWorker.setWorkerType(workerType);
					}
					sanitationWorkers.add(sanitationWorker);
				}
	    }			

	    fsm.setSanitationWorker(sanitationWorkers);
	});
}
	
	
	private List<SanitationWorkerSearch> fetchGarimaData(List<Worker> workers) {
//		Set<String> garimaIds = workers.stream().map(Worker::getIndividualId).collect(Collectors.toSet());
		List<String> garimaIds = workers.stream()
		        .map(Worker::getIndividualId)
		        .collect(Collectors.toList());

		List<SanitationWorkerSearch> sanitationWorkerSearch = new ArrayList<>();
		for (String garimaId : garimaIds) {
			SanitationWorkerSearchResponse sanitationWorkerSearchResponse = garimaSanitationWorkerService
					.search(SanitationWorkerSearchCriteria.builder().garimaId(garimaId).build());
			try {
				List<SanitationWorkerSearch> sanitationWorkersList = sanitationWorkerSearchResponse.getData();
				if (!sanitationWorkersList.isEmpty()) {
					sanitationWorkerSearch.add(sanitationWorkersList.get(0));
				} else {
					throw new CustomException("INVALID GARIMA ID",
							"Sanitation Worker with id: " + garimaId + " is not registered ");
				}
			} catch (Exception e) {
				throw new CustomException("GARIMA Returned", e.getMessage());
			}
		}
		return sanitationWorkerSearch.isEmpty() ? null : sanitationWorkerSearch;
	}

}