import React, { useState } from 'react';
import Joyride, { ACTIONS, EVENTS, LIFECYCLE, STATUS } from 'react-joyride';
import { useHistory } from 'react-router-dom';

const theme = {
  // primaryColor: '#ad7bff',
  // arrowColor: '#000',
  // textColor: '#fff',
  primaryColor: '#F47738',
  arrowColor: '#FFFFFF',
  textColor: '#505A5F',
};

const Tutorial = ({ tutorial, updateTutorial, ...props }) => {
  const history = useHistory()
  const { run, stepIndex, steps } = tutorial;
  // currently writing this fn here, this fn will be custom for every module so accept it as props
  
  // const handleCallback = (data) => {
  //   debugger
  //   console.log(ACTIONS, EVENTS, LIFECYCLE, STATUS);
    
  //   const { action, type, lifecycle, size, index, status } = data;
  //   const currentStep = tutorial?.steps?.[index]
    
  //   // console.log('here', ACTIONS, EVENTS, LIFECYCLE, STATUS);

  //   if(type === 'step:before'){
  //     if (action === 'next') {
  //       if(currentStep?.stopTour){
  //         updateTutorial({
  //           type: 'updateTourState',
  //           state: {
  //             ...tutorial,
  //             run:false,
  //             stepIndex: currentStep.stepIndex,
  //           },
  //         });
  //       }
  //     }
  //   }

  //   if (type === 'step:after') {
  //     if (action === 'next') {
  //       if(currentStep?.stopTour){
  //         updateTutorial({
  //           type: 'updateTourState',
  //           state: {
  //             ...tutorial,
  //             run:false,
  //             stepIndex: currentStep.stepIndex,
  //           },
  //         });
  //       }
  //       else if(currentStep.redirectTo){
  //         updateTutorial({
  //           type: 'updateTourState',
  //           state: {
  //             ...tutorial,
  //             run:true,
  //             stepIndex: tutorial.stepIndex + 1,
  //           },
  //         });
  //         history.push(currentStep.redirectTo)
  //       }else {
  //         updateTutorial({
  //           type: 'updateTourState',
  //           state: {
  //             ...tutorial,
  //             run:true,
  //             stepIndex: tutorial.stepIndex + 1,
  //           },
  //         });
  //       }
  //     } else if (action === 'skip') {
  //       updateTutorial({
  //         type: 'updateTourState',
  //         state: {
  //           ...tutorial,
  //           stepIndex: tutorial.stepIndex + 1,
  //         },
  //       });
  //     } else if (action === 'prev') {
  //       updateTutorial({
  //         type: 'updateTourState',
  //         state: {
  //           ...tutorial,
  //           stepIndex: tutorial.stepIndex - 1,
  //         },
  //       });
  //     } else if (action === 'close') {
  //       updateTutorial({
  //         type: 'updateTourState',
  //         state: {
  //           ...tutorial,
  //           run: false,
  //           tourActive: true,
  //           stepIndex: 0,
  //         },
  //       });
  //     }
  //   }

  //   else if (type === 'tour:end') {
  //     updateTutorial({
  //       type: 'updateTourState',
  //       state: {
  //         ...tutorial,
  //         run: false,
  //         tourActive: true,
  //         // stepIndex:0
  //       },
  //     });
  //   }

  //   else if (type === 'tour:start' && index!==0) {
  //     updateTutorial({
  //       type: 'updateTourState',
  //       state: {
  //         ...tutorial,
  //         run:true,
  //         // stepIndex: tutorial.stepIndex + 1,
  //       },
  //     });
  //   }

  //   else if (type === 'tour:status' && index!==0) {
  //     // updateTutorial({
  //     //   type: 'updateTourState',
  //     //   state: {
  //     //     ...tutorial,
  //     //     run:true,
  //     //     stepIndex: tutorial.stepIndex,
  //     //   },
  //     // });
  //   } 

  //   else if (type === "error:target_not_found"){
  //     updateTutorial({
  //       type: 'updateTourState',
  //       state: {
  //         ...tutorial,
  //         run:false,
  //         stepIndex: 0,
  //       },
  //     });
  //   }

  // };

  return (
    <Joyride
      // callback={handleCallback}
      continuous
      run={run}
      // stepIndex={stepIndex}
      steps={steps}
      styles={{
        options: {
          arrowColor: theme.arrowColor,
          backgroundColor: theme.arrowColor,
          primaryColor: theme.primaryColor,
          textColor: theme.textColor,
        },
      }}
      // showProgress={true}
      hideBackButton={false}
      disableOverlay={false}
      spotlightClicks={true}
    />
  );
};

export default Tutorial;