import React from "react";
import { CardLabelError, Dropdown,MultiSelectDropdown,RemoveableTag,LabelFieldPair,CardLabel } from "@egovernments/digit-ui-react-components";

// steps
// get individualIds array from workers array in vendor object
// call individual service and get those individuals
// filter out drivers bases on system role
// show drivers and sanitation workers



export const configAcceptDso = ({
  t,
  dsoData,
  dso,
  selectVehicleNo,
  vehicleNoList,
  vehicleNo,
  vehicle,
  noOfTrips,
  action,
  workers,
  selectedDriver,
  selectedWorkers,
  setSelectedDriver,
  setSelectedWorkers,
  onRemoveWorkers,
  drivers
}) => {
  return {
    label: {
      heading: `ES_FSM_ACTION_TITLE_${action}`,
      submit: `CS_COMMON_${action}`,
      cancel: 'CS_COMMON_CLOSE',
    },
    form: [
      {
        body: [
          {
            label: t('ES_FSM_ACTION_VEHICLE_REGISTRATION_NO'),
            isMandatory: true,
            type: 'dropdown',
            populators: (
              <React.Fragment>
                {!vehicleNoList?.length ? (
                  <CardLabelError>
                    {t('ES_FSM_NO_VEHICLE_AVAILABLE')}
                  </CardLabelError>
                ) : null}
                <Dropdown
                  option={vehicleNoList}
                  autoComplete="off"
                  optionKey="registrationNumber"
                  id="vehicle"
                  select={selectVehicleNo}
                  selected={vehicleNo}
                  disable={vehicleNoList?.length > 0 ? false : true}
                />
              </React.Fragment>
            ),
          },
          {
            label: t('ES_FSM_ACTION_VEHICLE_CAPACITY_IN_LTRS'),
            isMandatory: true,
            type: 'text',
            populators: {
              name: 'capacity',
              validation: {
                required: true,
              },
            },
            disable: true,
          },
          // {
          //   label: t('ES_FSM_ACTION_NUMBER_OF_TRIPS'),
          //   isMandatory: true,
          //   type: 'text',
          //   populators: {
          //     name: 'noOfTrips',
          //     validation: {
          //       required: true,
          //     },
          //     defaultValue: noOfTrips,
          //   },
          //   disable: true,
          // },
          {
            label: t('ES_FSM_ACTION_ASSIGN_DRIVER'),
            isMandatory: true,
            type: 'dropdown',
            populators: (
              <React.Fragment>
                {(drivers?.length===0 || !drivers) ? (
                  <CardLabelError>
                    {t('ES_FSM_NO_DRIVER_AVAILABLE')}
                  </CardLabelError>
                ) : null}
                <Dropdown
                  option={drivers}
                  autoComplete="off"
                  optionKey="optionsKey"
                  // id="vehicle"
                  select={(option)=>{
                    setSelectedDriver(option)
                  }}
                  selected={selectedDriver}
                  disable={drivers?.length > 0 ? false : true}
                  placeholder={t("SW_SEARCH_BY_NAME_ID")}
                />
              </React.Fragment>
            ),
          },
          {
            label: t('ES_FSM_ACTION_ASSIGN_SW'),
            isMandatory: false,
            type: 'dropdown',
            populators: (
              <React.Fragment>
                <MultiSelectDropdown
                  placeholder={t("SW_SEARCH_BY_NAME_ID")}
                  className="form-field"
                  isMandatory={true}
                  defaultUnit="Selected"
                  selected={selectedWorkers}
                  options={workers}
                  onSelect={(data) => {
                    if(data.length>=0){
                    setSelectedWorkers(data?.map(arr=> arr?.[1]))
                    }else {
                      setSelectedWorkers([])
                    }
                  }}
                  optionsKey={"optionsKey"}
                  t={t}
                  // defaultLabel={t("SW_SEARCH_BY_NAME_ID")}
                  // defaultLabelClassName={"as-placeholder"}
                />
                <div className="tag-container">
                  {selectedWorkers?.map((value, index) => {
                    return (
                      <RemoveableTag
                        key={index}
                        text={`${t(value['givenName']).slice(0, 22)} ...`}
                        onClick={() => onRemoveWorkers(index, value)}
                      />
                    );
                  })}
                </div>
              </React.Fragment>
            ),
          },
        ],
      },
    ],
  };
};
