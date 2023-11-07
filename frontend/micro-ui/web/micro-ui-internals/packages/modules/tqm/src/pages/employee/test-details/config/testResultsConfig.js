export const testResultsConfig = ({ t, testDetailsData, testCriteriaData }) => {
  const testParams = testDetailsData?.testCriteria?.map((i) => {
    return testCriteriaData?.find((h) => h?.code === i?.criteriaCode);
  });

  const testParamConfig = testParams?.map((i) => {
    return {
      label: t(i?.i18nKey) || "N/A",
      isMandatory: true,
      type: "number",
      disable: false,
      populators: {
        name: i?.code,
        error: t("ES_TQM_TEST_PARAM_ERROR_MESSAGE"),
        validation: { min: 0, max: 10 },
      },
    };
  });
  
  return testParamConfig
    ? [
        {
          inline: true,
          head: t("ES_TQM_ADD_TEST_RESULTS_TITLE"),
          body: [
            ...testParamConfig,
            {
              type: "multiupload",
              label: t("ES_TQM_TEST_PARAM_ATTACH_DOCUMENTS"),
              populators: {
                name: "documents",
                allowedMaxSizeInMB: 5,
                maxFilesAllowed: 1,
                customClass: "upload-margin-bottom",
                errorMessage: t("ES_TQM_TEST_PARAM_ATTACH_DOCUMENTS_ERROR_MSG"),
                allowedFileTypes: /(.*?)(pdf|jpg|png)$/i,
              },
            },
          ],
        },
      ]
    : null;
};