export const tqmSearchConfigUlbAdmin = {
  tenantId: 'pg',
  moduleName: 'commonSanitationUiConfig',
  tqmSearchConfig: [
    {
      "label": "TQM_VIEW_PAST_RESULTS",
      "type": "search",
      "apiDetails": {
        "serviceName": "/pqm-service/v1/_search",
        "requestParam": {},
        "requestBody": {},
        "minParametersForSearchForm": 1,
        "masterName": "commonUiConfig",
        "moduleName": "SearchTestResultsUlbAdmin",
        "tableFormJsonPath": "requestBody.pagination",
        "filterFormJsonPath": "requestBody.custom",
        "searchFormJsonPath": "requestBody.custom"
      },
      "sections": {
        "search": {
          "uiConfig": {
            "headerLabel": "TQM_INBOX_FILTERS",
            "headerStyle": null,
            "primaryLabel": "ES_COMMON_SEARCH",
            "secondaryLabel": "ES_COMMON_CLEAR_SEARCH",
            "minReqFields": 1,
            "showFormInstruction": "TQM_SEARCH_HINT",
            "defaultValues": {
              "id": "",
              "plantCodes": [],
              "processCodes": [],
              "testType": "",
              "dateRange": {}
            },
            "fields": [
              {
                "label": "TQM_TEST_ID",
                "type": "text",
                "isMandatory": false,
                "disable": false,
                "populators": {
                  "name": "id"
                }
              },
              {
                "label": "TQM_PLANT_NAME",
                "type": "dropdown",
                "isMandatory": false,
                "disable": false,
                "populators": {
                  "optionsCustomStyle": {
                    "top": "2.3rem"
                  },
                  "name": "plantCodes",
                  "optionsKey": "i18nKey",
                  "allowMultiSelect": true,
                  "mdmsv2": {
                    "schemaCode": "PQM.Plant"
                  }
                }
              },
              {
                "label": "TQM_TREATMENT_PROCESS",
                "type": "dropdown",
                "isMandatory": false,
                "disable": false,
                "populators": {
                  "optionsCustomStyle": {
                    "top": "2.3rem"
                  },
                  "name": "processCodes",
                  "optionsKey": "i18nKey",
                  "allowMultiSelect": true,
                  "mdmsv2": {
                    "schemaCode": "PQM.Process"
                  }
                }
              },
              {
                "label": "TQM_TEST_TYPE",
                "type": "dropdown",
                "isMandatory": false,
                "disable": false,
                "populators": {
                  "optionsCustomStyle": {
                    "top": "2.3rem"
                  },
                  "name": "testType",
                  "optionsKey": "i18nKey",
                  "allowMultiSelect": false,
                  "mdmsv2": {
                    "schemaCode": "PQM.TestType"
                  }
                }
              },
              {
                "label": "TQM_INBOX_DATE_RANGE",
                "type": "dateRange",
                "isMandatory": false,
                "disable": false,
                "populators": {
                  "name": "dateRange"
                }
              }
            ]
          },
          "label": "",
          "children": {},
          "show": true,
          "labelMobile": "TQM_INBOX_FILTER"
        },
        "searchResult": {
          "uiConfig": {
            "columns": [
              {
                "label": "TQM_TEST_ID",
                "jsonPath": "testId",
                "additionalCustomization": true
              },
              {
                "label": "TQM_PLANT",
                "jsonPath": "plantCode",
                "additionalCustomization": false,
                "prefix": "PQM.PLANT_",
                "translate": true
              },
              {
                "label": "TQM_TREATMENT_PROCESS",
                "jsonPath": "processCode",
                "additionalCustomization": false,
                "prefix": "PQM.ProcessType_",
                "translate": true
              },
              {
                "label": "TQM_TEST_TYPE",
                "jsonPath": "testType",
                "additionalCustomization": false,
                "prefix": "PQM.TestType_",
                "translate": true
              },
              {
                "label": "TQM_TEST_DATE",
                "jsonPath": "scheduledDate",
                "additionalCustomization": true
              },
              {
                "label": "TQM_TEST_RESULTS",
                "jsonPath": "status",
                "additionalCustomization": true
              }
            ],
            "showActionBarMobileCard": true,
            "actionButtonLabelMobileCard": "TQM_VIEW_RESULTS",
            "enableGlobalSearch": false,
            "enableColumnSort": true,
            "resultsJsonPath": "tests"
          },
          "children": {},
          "show": true
        }
      },
      "additionalSections": {}
    },
  ],
};
