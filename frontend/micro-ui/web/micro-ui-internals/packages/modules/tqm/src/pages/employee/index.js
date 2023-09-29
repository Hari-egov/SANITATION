import React, { useEffect } from "react";
import { Switch, useLocation } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { PrivateRoute, AppContainer, BreadCrumb, BackButton } from "@egovernments/digit-ui-react-components";
import SampleComp from "./SampleComp";
import TQMPendingTask from "./TQMPendingTask";
import TQMHome from "./TQMHome";
import TqmSearch from "./search-test-results/TqmSearch";

// import TQMSummary from "../../components/TQMSummary";

const TqmBreadCrumb = ({ location, defaultPath }) => {
  const { t } = useTranslation();
  const search = useLocation().search;

  const crumbs = [
    {
      path: `/${window?.contextPath}/employee`,
      content: t("TQM_HOME"),
      show: true,
    },
  ];
  return <BreadCrumb className="workbench-bredcrumb" crumbs={crumbs} spanStyle={{ maxWidth: "min-content" }} />;
};

const App = ({ path }) => {
  const { t } = useTranslation();
  const location = useLocation();

  const TqmInbox = Digit?.ComponentRegistryService?.getComponent("TqmInbox");

  return (
    <React.Fragment>
      <TqmBreadCrumb location={location} defaultPath={path} />
      <Switch>
        <AppContainer className="tqm">
          <PrivateRoute path={`${path}/home`} component={() => <TQMHome />} />
          <PrivateRoute path={`${path}/sample`} component={() => <SampleComp />} />
          <PrivateRoute path={`${path}/check`} component={() => <TQMPendingTask />} />
          <PrivateRoute path={`${path}/inbox`} component={() => <TqmInbox {...{ path }} />} />
          <PrivateRoute path={`${path}/search-test-results`} component={() => <TqmSearch {...{ path }} />} />
          {/* <PrivateRoute path={`${path}/summary/:id`} component={() => <TQMSummary />} /> */}
        </AppContainer>
      </Switch>
    </React.Fragment>
  );
};

export default App;
