import utils from "../utils";
import { useCustomMDMSV2 } from "./useCustomMDMSV2";
import { useViewTestResults } from "./useViewTestResults";
import { useViewTestSummary } from "./useViewTestSummary";
import { useSearchTest } from "./useSearchTest";
import useTestUpdate from "./useTestUpdate";
import useCreate from "./useCreate";

const tqm = {
  sampleTQMHook: () => {},
  useViewTestResults,
  useViewTestSummary,
  useCustomMDMSV2,
  useSearchTest,
  useTestUpdate,
  useCreate
};

const Hooks = {
  tqm,
};

const Utils = {
  browser: {
    sample: () => {},
  },
  tqm: {
    ...utils,
    sampleUtil: () => {},
  },
};

export const CustomisedHooks = {
  Hooks,
  Utils,
};
