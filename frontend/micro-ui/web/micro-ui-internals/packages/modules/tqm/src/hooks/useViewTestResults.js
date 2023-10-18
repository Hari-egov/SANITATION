import { useQuery } from "react-query";
import { tqmService } from "./services/tqmService";

export const useViewTestResults = ({ t, id, tenantId, config = {} }) => {
  return useQuery(["TQM_ADMIN_TEST_RESULTS"], () => tqmService.searchTestResultData({ t, id, tenantId }), config);
};
