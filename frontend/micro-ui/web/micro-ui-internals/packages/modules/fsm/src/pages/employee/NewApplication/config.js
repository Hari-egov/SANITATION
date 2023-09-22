export const config = [
  {
    head: "ES_TITLE_APPLICANT_DETAILS",
    body: [
      {
        label: "ES_NEW_APPLICATION_APPLICATION_CHANNEL",
        isMandatory: true,
        type: "component",
        key: "channel",
        component: "SelectChannel",
        nextStep: "applicantName",
      },
      {
        type: "component",
        key: "applicationData",
        withoutLabel: true,
        component: "SelectName",
      },
    ],
  },
  {
    head: "ES_NEW_APPLICATION_PROPERTY_DETAILS",
    body: [
      {
        label: "ES_NEW_APPLICATION_PROPERTY_TYPE",
        isMandatory: true,
        type: "component",
        route: "property-type",
        key: "propertyType",
        component: "SelectPropertyType",
        texts: {
          headerCaption: "",
          header: "CS_FILE_APPLICATION_PROPERTY_LABEL",
          cardText: "CS_FILE_APPLICATION_PROPERTY_TEXT",
          submitBarLabel: "CS_COMMON_NEXT",
        },
        nextStep: "property-subtype",
      },
      {
        label: "ES_NEW_APPLICATION_PROPERTY_SUB-TYPE",
        isMandatory: true,
        type: "component",
        route: "property-subtype",
        key: "subtype",
        component: "SelectPropertySubtype",
        texts: {
          headerCaption: "",
          header: "CS_FILE_APPLICATION_PROPERTY_SUBTYPE_LABEL",
          cardText: "CS_FILE_APPLICATION_PROPERTY_SUBTYPE_TEXT",
          submitBarLabel: "CS_COMMON_NEXT",
        },
        nextStep: "map",
      },
    ],
  },
  {
    head: "ES_NEW_APPLICATION_LOCATION_DETAILS",
    body: [
      {
        route: "map",
        type: "component",
        withoutLabel: true,
        component: "SelectGeolocation",
        nextStep: "pincode",
        // hideInEmployee: true,
        key: "address",
      },
      {
        route: "pincode",
        component: "SelectPincode",
        texts: {
          headerCaption: "",
          header: "CS_FILE_APPLICATION_PINCODE_LABEL",
          cardText: "CS_FILE_APPLICATION_PINCODE_TEXT",
          submitBarLabel: "CS_COMMON_NEXT",
          skipText: "CORE_COMMON_SKIP_CONTINUE",
        },
        withoutLabel: true,
        key: "address",
        nextStep: "address",
        type: "component",
      },
      {
        route: "address",
        component: "SelectAddress",
        withoutLabel: true,
        texts: {
          headerCaption: "CS_FILE_APPLICATION_PROPERTY_LOCATION_LABEL",
          header: "CS_FILE_APPLICATION_PROPERTY_LOCATION_ADDRESS_TEXT",
          cardText: "CS_FILE_APPLICATION_PROPERTY_LOCATION_CITY_MOHALLA_TEXT",
          submitBarLabel: "CS_COMMON_NEXT",
        },
        key: "address",
        nextStep: "check-slum",
        isMandatory: true,
        type: "component",
      },
      {
        type: "component",
        route: "check-slum",
        isMandatory: true,
        component: "CheckSlum",
        texts: {
          header: "ES_NEW_APPLICATION_SLUM_CHECK",
          submitBarLabel: "CS_COMMON_NEXT",
        },
        key: "address",
        withoutLabel: true,
        nextStep: "slum-details",
        hideInEmployee: true,
      },
      {
        type: "component",
        route: "slum-details",
        isMandatory: true,
        component: "SelectSlumName",
        texts: {
          header: "CS_NEW_APPLICATION_SLUM_NAME",
          cardText: "CS_NEW_APPLICATION_SLUM_TEXT",
          submitBarLabel: "CS_COMMON_NEXT",
        },
        withoutLabel: true,
        key: "address",
        nextStep: "street",
      },
      {
        type: "component",
        route: "street",
        component: "SelectStreet",
        key: "address",
        withoutLabel: true,
        texts: {
          headerCaption: "CS_FILE_APPLICATION_PROPERTY_LOCATION_LABEL",
          header: "CS_FILE_APPLICATION_PROPERTY_LOCATION_ADDRESS_TEXT",
          cardText: "CS_FILE_APPLICATION_PROPERTY_LOCATION_STREET_DOOR_NO_LABEL",
          submitBarLabel: "CS_COMMON_NEXT",
          skipText: "CORE_COMMON_SKIP_CONTINUE",
        },
        nextStep: "landmark",
      },
      {
        type: "component",
        route: "landmark",
        component: "SelectLandmark",
        withoutLabel: true,
        texts: {
          headerCaption: "CS_FILE_APPLICATION_PROPERTY_LOCATION_LABEL",
          header: "CS_FILE_APPLICATION_PROPERTY_LOCATION_PROVIDE_LANDMARK_TITLE",
          cardText: "CS_FILE_APPLICATION_PROPERTY_LOCATION_PROVIDE_LANDMARK_TEXT",
          submitBarLabel: "CS_COMMON_NEXT",
          skipText: "CORE_COMMON_SKIP_CONTINUE",
        },
        key: "address",
        nextStep: "pit-type",
      },
    ],
  },
  {
    head: "CS_CHECK_PIT_SEPTIC_TANK_DETAILS",
    body: [
      {
        label: "ES_NEW_APPLICATION_PIT_TYPE",
        isMandatory: false,
        type: "component",
        route: "pit-type",
        key: "pitType",
        component: "SelectPitType",
        texts: {
          header: "CS_FILE_PROPERTY_PIT_TYPE",
          cardText: "CS_FILE_PROPERTY_PIT_TYPE_TEXT",
          submitBarLabel: "CS_COMMON_NEXT",
          skipText: "CORE_COMMON_SKIP_CONTINUE",
        },
        nextStep: "tank-size",
      },
      {
        route: "tank-size",
        component: "SelectTankSize",
        isMandatory: false,
        texts: {
          headerCaption: "",
          header: "CS_FILE_APPLICATION_PIT_SEPTIC_TANK_SIZE_TITLE",
          cardText: "CS_FILE_APPLICATION_PIT_SEPTIC_TANK_SIZE_TEXT",
          submitBarLabel: "CS_COMMON_NEXT",
          skipText: "CORE_COMMON_SKIP_CONTINUE",
        },
        type: "component",
        key: "pitDetail",
        nextStep: "select-gender",
        label: "ES_NEW_APPLICATION_PIT_DIMENSION",
      },
      {
        type: "component",
        key: "tripData",
        withoutLabel: true,
        component: "SelectTrips",
      },
    ],
  },
  {
    head: "CS_FILE_ADDITIONAL_DETAILS",
    hideInEmployee: true,
    body: [
      {
        label: "a",
        isMandatory: true,
        type: "component",
        route: "select-trip-number",
        key: "selectTripNo",
        component: "SelectTripNo",
        hideInEmployee: true,
        texts: {
          headerCaption: "",
          header: "ES_FSM_SERVICE_REQUEST",
          cardText: "ES_FSM_SERVICE_REQUEST_TEXT",
          skipText: "CORE_COMMON_SKIP_CONTINUE",
          submitBarLabel: "CS_COMMON_NEXT",
          skipLabel: "CS_COMMON_SERVICE_SKIP_INFO",
        },
        nextStep: "select-payment-preference",
      },
      {
        label: "a",
        isMandatory: false,
        type: "component",
        route: "select-gender",
        hideInEmployee: true,
        key: "selectGender",
        component: "SelectGender",
        texts: {
          headerCaption: "",
          header: "ES_FSM_REGISTRY_PERSONAL_DETAILS",
          cardText: "",
          submitBarLabel: "CS_COMMON_NEXT",
          skipText: "CORE_COMMON_SKIP_CONTINUE",
        },
        nextStep: "select-trip-number",
      },
      {
        label: "a",
        isMandatory: false,
        type: "component",
        route: "select-payment-preference",
        key: "selectPaymentPreference",
        hideInEmployee: true,
        component: "SelectPaymentPreference",
        texts: {
          headerCaption: "",
          header: "ES_FSM_PAYMENT_PREFERENCE_LABEL",
          cardText: "ES_FSM_PAYMENT_PREFERENCE_TEXT",
          submitBarLabel: "CS_COMMON_NEXT",
          skipText: "CORE_COMMON_SKIP_CONTINUE",
        },
        nextStep: null,
      },
    ],
  },
  {
    head: "ES_TITLE_PAYMENT_DETAILS",
    body: [
      {
        type: "component",
        key: "tripData",
        withoutLabel: true,
        component: "SelectTripData",
      },
      {
        type: "component",
        key: "advancepaymentPreference",
        withoutLabel: true,
        component: "AdvanceCollection",
      },
    ],
  },
];
