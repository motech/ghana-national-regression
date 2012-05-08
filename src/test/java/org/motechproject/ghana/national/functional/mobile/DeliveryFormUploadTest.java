package org.motechproject.ghana.national.functional.mobile;

import org.junit.runner.RunWith;
import org.motechproject.ghana.national.domain.*;
import org.motechproject.ghana.national.functional.OpenMRSAwareFunctionalTest;
import org.motechproject.ghana.national.functional.data.TestPatient;
import org.motechproject.ghana.national.functional.framework.XformHttpClient;
import org.motechproject.ghana.national.functional.mobileforms.MobileForm;
import org.motechproject.ghana.national.functional.pages.openmrs.OpenMRSEncounterPage;
import org.motechproject.ghana.national.functional.pages.openmrs.OpenMRSPatientPage;
import org.motechproject.ghana.national.functional.pages.openmrs.vo.OpenMRSObservationVO;
import org.motechproject.ghana.national.functional.pages.patient.PatientPage;
import org.motechproject.ghana.national.functional.util.DataGenerator;
import org.motechproject.util.DateUtil;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import static java.util.Arrays.asList;
import static org.testng.AssertJUnit.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-functional-tests.xml"})
public class DeliveryFormUploadTest extends OpenMRSAwareFunctionalTest {


    @Test
    public void shouldUploadDeliveryFormSuccessfully() throws Exception {
        // create
        final String staffId = staffGenerator.createStaff(browser, homePage);

        DataGenerator dataGenerator = new DataGenerator();
        String patientFirstName = "patient first name" + dataGenerator.randomString(5);
        final TestPatient testPatient = TestPatient.with(patientFirstName, staffId)
                .patientType(TestPatient.PATIENT_TYPE.PREGNANT_MOTHER)
                .estimatedDateOfBirth(false);

        final PatientPage patientPage = browser.toCreatePatient(homePage);
        final String babyName = "baby " + dataGenerator.randomString(5);
        patientPage.create(testPatient);
        final String motechId = patientPage.motechId();
        XformHttpClient.XformResponse xformResponse = mobile.upload(MobileForm.deliveryForm(), new HashMap<String, String>() {{
            put("staffId", staffId);
            put("facilityId", testPatient.facilityId());
            put("motechId", motechId);
            put("date", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(DateUtil.today().minusDays(2).toDate()));
            put("mode", ChildDeliveryMode.NORMAL.name());
            put("outcome", ChildDeliveryOutcome.SINGLETON.name());
            put("maleInvolved", "Y");
            put("deliveryLocation", ChildDeliveryLocation.CHAG.name());
            put("deliveredBy", ChildDeliveredBy.DOCTOR.name());
            put("maternalDeath", "N");
            put("child1Outcome", BirthOutcome.ALIVE.getValue());
            put("child1RegistrationType", RegistrationType.AUTO_GENERATE_ID.name());
            put("child1Sex", "M");
            put("child1FirstName", babyName);
            put("child1Weight", "3.5");
            put("sender", "0987654321");
            put("comments", "delivery form");
            put("complications", "ECLAMPSIA,VVF");
            put("vvf", "REPAIRED");
        }});
        assertEquals(1, xformResponse.getSuccessCount());

        OpenMRSPatientPage openMRSPatientPage = openMRSBrowser.toOpenMRSPatientPage(openMRSDB.getOpenMRSId(motechId));
        String encounterId = openMRSPatientPage.chooseEncounter("PREGDELVISIT");

        OpenMRSEncounterPage openMRSEncounterPage = openMRSBrowser.toOpenMRSEncounterPage(encounterId);
        openMRSEncounterPage.displaying(asList(
                new OpenMRSObservationVO("BIRTH OUTCOME", "A"),
                new OpenMRSObservationVO("DELIVERY LOCATION", "5.0"),
                new OpenMRSObservationVO("COMMENTS", "delivery form"),
                new OpenMRSObservationVO("DELIVERED BY", "1.0"),
                new OpenMRSObservationVO("DELIVERY MODE", "1.0"),
                new OpenMRSObservationVO("DELIVERY OUTCOME", "1.0"),
                new OpenMRSObservationVO("MALE INVOLVEMENT", "true"),
                new OpenMRSObservationVO("MATERNAL DEATH", "false"),
                new OpenMRSObservationVO("PREGNANCY STATUS", "false"),
                new OpenMRSObservationVO("DELIVERY COMPLICATION", "6.0"),
                new OpenMRSObservationVO("DELIVERY COMPLICATION", "1.0"),
                new OpenMRSObservationVO("VVF REPAIR", "2.0")
        ));

        String childMotechId = openMRSDB.getMotechIdByName(babyName);
        openMRSPatientPage = openMRSBrowser.toOpenMRSPatientPage(openMRSDB.getOpenMRSId(childMotechId));
        encounterId = openMRSPatientPage.chooseEncounter("BIRTHVISIT");

        openMRSEncounterPage = openMRSBrowser.toOpenMRSEncounterPage(encounterId);
        openMRSEncounterPage.displaying(asList(
                new OpenMRSObservationVO("WEIGHT (KG)", "3.5")
        ));


    }
}
