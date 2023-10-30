package org.egov.pqm.util;

import org.apache.kafka.common.protocol.types.Field.Str;
import org.bouncycastle.pqc.crypto.newhope.NHOtherInfoGenerator.PartyU;

public class ErrorConstants {

  public static final String CREATE_ERROR = "CREATE_ERROR";
  public static final String UPDATE_ERROR = "UPDATE_ERROR";
  public static final String PARSING_ERROR = "PARSING_ERROR";
  public static final String IDGEN_ERROR = "IDGEN_ERROR";
  public static final String TEST_NOT_IN_DB = "TEST_NOT_PRESENT_IN_DB";
  public static final String TEST_CRITERIA_NOT_PRESENT = "TEST_CRITERIA_NOT_PRESENT";
  public static final String TEST_TYPE_CAN_ONLY_BE_LAB = "TEST_TYPE_CAN_ONLY_BE_LAB";
  public static final String FILE_STORE_ID_INVALID_CODE = "FILE_STORE_ID_INVALID";
  public static final String FILE_STORE_ID_INVALID_MESSAGE = "FileStoreId can only be small case alphabets and digits ";
  public static final String TEST_TYPE_INVALID_CODE="TEST_TYPE_DIFFERENT";

  //ERROR CONSTANTS WHILE UPDATE API
  public static final String TEST_TYPE_INVALID_MESSAGE="Test type did not match";
  public static final String PLANT_CODE_INVALID_CODE = "PLANT_CODE_DIFFERENT";
  public static final String PLANT_CODE_INVALID_MESSAGE = "Plant code did not match";
  public static final String PROCESS_CODE_INVALID_CODE = "PROCESS_CODE_DIFFERENT";
  public static final String PROCESS_CODE_INVALID_MESSAGE = "Process code did not match";
  public static final String MATERIAL_CODE_INVALID_CODE = "MATERIAL_CODE_DIFFERENT";
  public static final String MATERIAL_CODE_INVALID_MESSAGE = "Material code did not match";
  public static final String STAGE_CODE_INVALID_CODE = "STAGE_CODE_DIFFERENT";
  public static final String STAGE_CODE_INVALID_MESSAGE = "Stage code did not match";
  public static final String CRITERIA_CODE_INVALID_CODE = "CRITERIA_CODE_DIFFERENT";
  public static final String CRITERIA_CODE_INVALID_MESSAGE = "Criteria code did not match";
  public static final String STATUS_ERROR_CODE = "STATUS CAN ONLY BE PENDING IF WORKFLOW STATUS IS ANYHTING OTHER THAN UPDATE_RESULT ";
  public static final String STATUS_ERROR_MESSAGE = "Status can only be pending if workflow status is anything other than update result";




}