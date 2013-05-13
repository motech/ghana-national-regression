package org.motechproject.ghana.national.functional.mobile;

import org.junit.runner.RunWith;
import org.motechproject.ghana.national.functional.OpenMRSAwareFunctionalTest;
import org.motechproject.ghana.national.functional.data.TestANCEnrollment;
import org.motechproject.ghana.national.functional.data.TestPatient;
import org.motechproject.ghana.national.functional.framework.XformHttpClient;
import org.motechproject.ghana.national.functional.mobileforms.MobileForm;
import org.motechproject.ghana.national.functional.pages.openmrs.OpenMRSEncounterPage;
import org.motechproject.ghana.national.functional.pages.openmrs.OpenMRSPatientPage;
import org.motechproject.ghana.national.functional.pages.openmrs.vo.OpenMRSObservationVO;
import org.motechproject.ghana.national.functional.util.DataGenerator;
import org.motechproject.util.DateUtil;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;

import static junit.framework.Assert.assertEquals;
import static org.motechproject.util.DateUtil.today;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-functional-tests.xml"})
public class DeliveryNotificationFormUploadTest extends OpenMRSAwareFunctionalTest{

    @Test
    public void shouldUploadDeliveryStatusNotificationSuccessfully() throws Exception {
        DataGenerator dataGenerator = new DataGenerator();
        final String staffId = staffGenerator.createStaff(browser, homePage);
        final String facilityId = facilityGenerator.createFacility(browser, homePage);
        final TestPatient testPatient = TestPatient.with("patient name" + dataGenerator.randomString(5), staffId)
                .patientType(TestPatient.PATIENT_TYPE.PREGNANT_MOTHER)
                .estimatedDateOfBirth(false);
        TestANCEnrollment testANCEnrollment = TestANCEnrollment.createWithoutHistory().withStaffId(staffId)
                .withEstimatedDateOfDelivery(today().plusWeeks(12));
        final String motechId = patientGenerator.createPatientWithANC(testPatient, testANCEnrollment, browser, homePage);

        XformHttpClient.XformResponse xformResponse = mobile.upload(MobileForm.deliveryNotificationForm() , new HashMap<String, String>() {{
            put("staffId", staffId);
            put("motechId", motechId);
            put("facilityId", facilityId);
            put("datetime", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(DateUtil.today().minusDays(2).toDate()));
        }});

        assertEquals(1, xformResponse.getSuccessCount());

        OpenMRSPatientPage openMRSPatientPage = openMRSBrowser.toOpenMRSPatientPage(openMRSDB.getOpenMRSId(motechId));
        String encounterId = openMRSPatientPage.chooseEncounter("PREGDELNOTIFYVISIT");
        OpenMRSEncounterPage openMRSEncounterPage = openMRSBrowser.toOpenMRSEncounterPage(encounterId);

        openMRSEncounterPage.displaying(Collections.<OpenMRSObservationVO>emptyList());

    }
}
