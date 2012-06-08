package org.motechproject.ghana.national.functional.mobile;

import junit.framework.Assert;
import org.joda.time.LocalDate;
import org.junit.runner.RunWith;
import org.motechproject.ghana.national.domain.IPTDose;
import org.motechproject.ghana.national.domain.IPTiDose;
import org.motechproject.ghana.national.domain.OPVDose;
import org.motechproject.ghana.national.domain.PentaDose;
import org.motechproject.ghana.national.functional.OpenMRSAwareFunctionalTest;
import org.motechproject.ghana.national.functional.data.*;
import org.motechproject.ghana.national.functional.framework.OpenMRSDB;
import org.motechproject.ghana.national.functional.framework.ScheduleTracker;
import org.motechproject.ghana.national.functional.framework.XformHttpClient;
import org.motechproject.ghana.national.functional.helper.ScheduleHelper;
import org.motechproject.ghana.national.functional.mobileforms.MobileForm;
import org.motechproject.ghana.national.functional.util.DataGenerator;
import org.motechproject.ghana.national.vo.Pregnancy;
import org.motechproject.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testng.annotations.Test;

import static org.motechproject.ghana.national.configuration.ScheduleNames.*;
import static org.motechproject.util.DateUtil.today;
import static org.testng.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-functional-tests.xml"})
public class CareHistoryFormUploadSchedulesTest extends OpenMRSAwareFunctionalTest {
    @Autowired
    ScheduleTracker scheduleTracker;
    @Autowired
    OpenMRSDB openMRSDB;
    @Autowired
    DataGenerator dataGenerator;

    @Test
    public void shouldNotCreateSchedulesWhileHistoryFormUploadIfThereAreActiveSchedules() {
        String staffId = staffGenerator.createStaff(browser, homePage);
        String patientId = patientGenerator.createPatient(browser, homePage, staffId);
        String openMRSId = openMRSDB.getOpenMRSId(patientId);
        LocalDate registrationDate = DateUtil.today();

        Pregnancy pregnancyIn12thWeekOfPregnancy = Pregnancy.basedOnConceptionDate(DateUtil.today().minusWeeks(12));
        TestANCEnrollment ancEnrollment = TestANCEnrollment.createWithoutHistory().withMotechPatientId(patientId).withStaffId(staffId)
                .withEstimatedDateOfDelivery(pregnancyIn12thWeekOfPregnancy.dateOfDelivery()).withRegistrationDate(registrationDate).addHistoryDetails();

        mobile.upload(MobileForm.registerANCForm(), ancEnrollment.withoutMobileMidwifeEnrollmentThroughMobile());

        ScheduleHelper.assertAlertDate(scheduleTracker.firstAlertScheduledFor(openMRSId, ANC_IPT_VACCINE.getName()).getAlertAsLocalDate(), expectedFirstAlertDate(ANC_IPT_VACCINE.getName(), pregnancyIn12thWeekOfPregnancy.dateOfConception()));
        ScheduleHelper.assertAlertDate(scheduleTracker.firstAlertScheduledFor(openMRSId, TT_VACCINATION.getName()).getAlertAsLocalDate(), expectedFirstAlertDate(TT_VACCINATION.getName(), registrationDate));

        final LocalDate dateAfterConception = pregnancyIn12thWeekOfPregnancy.dateOfConception().plusWeeks(3);

        TestCareHistory careHistory = TestCareHistory.withoutHistory(patientId);
        careHistory.withIPT("1", dateAfterConception);
        careHistory.withTT("2", dateAfterConception);

        mobile.upload(MobileForm.careHistoryForm(), careHistory.forMobile());

        // schedule dates should not change
        ScheduleHelper.assertAlertDate(scheduleTracker.firstAlertScheduledFor(openMRSId, ANC_IPT_VACCINE.getName()).getAlertAsLocalDate(), expectedFirstAlertDate(ANC_IPT_VACCINE.getName(), pregnancyIn12thWeekOfPregnancy.dateOfConception()));
        ScheduleHelper.assertAlertDate(scheduleTracker.firstAlertScheduledFor(openMRSId, TT_VACCINATION.getName()).getAlertAsLocalDate(), expectedFirstAlertDate(TT_VACCINATION.getName(), registrationDate));
    }

    @Test
    public void shouldNotCreateSchedulesIfHistoryIsIrrelevantForANC() {
        String staffId = staffGenerator.createStaff(browser, homePage);
        String patientId = patientGenerator.createPatient(browser, homePage, staffId);
        String openMRSId = openMRSDB.getOpenMRSId(patientId);

        TestCareHistory careHistory = TestCareHistory.withoutHistory(patientId);
        careHistory.withIPT("1", DateUtil.newDate(2000, 10, 10));
        careHistory.withTT("2", DateUtil.newDate(2011, 11, 11));

        mobile.upload(MobileForm.careHistoryForm(), careHistory.forMobile());

        Assert.assertNull(scheduleTracker.activeEnrollment(openMRSId, ANC_IPT_VACCINE.getName()));
        Assert.assertNull(scheduleTracker.activeEnrollment(openMRSId, TT_VACCINATION.getName()));
    }

    @Test
    public void shouldNotCreateOPV0ScheduleIfOPV1IsTaken() {
        String staffId = staffGenerator.createStaff(browser, homePage);
        LocalDate dateOfBirth = DateUtil.today().minusDays(2);
        TestPatient patient = TestPatient.with(dataGenerator.randomString(8), staffId).patientType(TestPatient.PATIENT_TYPE.CHILD_UNDER_FIVE).dateOfBirth(dateOfBirth);
        String patientId = patientGenerator.createPatient(patient, browser, homePage);
        String openMRSId = openMRSDB.getOpenMRSId(patientId);

        TestCWCEnrollment cwcEnrollment = TestCWCEnrollment.createWithoutHistory()
                .withMotechPatientId(patientId).withStaffId(staffId).withRegistrationDate(dateOfBirth)
                .withAddHistory(true).withLastOPV("1").withLastOPVDate(dateOfBirth);

        mobile.upload(MobileForm.registerCWCForm(), cwcEnrollment.withoutMobileMidwifeEnrollmentThroughMobile());
        Assert.assertNull(scheduleTracker.activeEnrollment(openMRSId, CWC_OPV_0.getName()));
        Assert.assertNotNull(scheduleTracker.activeEnrollment(openMRSId, CWC_OPV_OTHERS.getName()));
    }

    @Test
    public void shouldCreateAppropriateSchedulesWhenCareHistoryUploadedForPatientRegisteredWithANC() {
        String staffId = staffGenerator.createStaff(browser, homePage);
        String patientId = patientGenerator.createPatient(browser, homePage, staffId);
        String openMRSId = openMRSDB.getOpenMRSId(patientId);

        TestPatient patient = TestPatient.with("ANCHistory" + dataGenerator.randomString(5), staffId).motechId(patientId)
                .patientType(TestPatient.PATIENT_TYPE.PREGNANT_MOTHER).estimatedDateOfBirth(false);
        TestMobileMidwifeEnrollment mmEnrollmentDetails = TestMobileMidwifeEnrollment.with(staffId).consent(false);
        LocalDate registrationDate = today();
        LocalDate estimatedDateOfDelivery = registrationDate.plusWeeks(3);
        LocalDate conceptionDate = Pregnancy.basedOnDeliveryDate(estimatedDateOfDelivery).dateOfConception();
        TestANCEnrollment ancEnrollmentDetails = TestANCEnrollment.createWithoutHistory().withEstimatedDateOfDelivery(estimatedDateOfDelivery);

        TestClientRegistration<TestANCEnrollment> testClientRegistration = new TestClientRegistration<TestANCEnrollment>(patient, ancEnrollmentDetails, mmEnrollmentDetails);
        mobile.upload(MobileForm.registerClientForm(), testClientRegistration.withProgramEnrollmentThroughMobile());

        TestCareHistory careHistory = TestCareHistory.withoutHistory(patientId).staffId(staffId).withIPT("1", conceptionDate.plusWeeks(5));

        XformHttpClient.XformResponse response = mobile.upload(MobileForm.careHistoryForm(), careHistory.forMobile());

        assertEquals(1, response.getSuccessCount());

        ScheduleHelper.assertAlertDate(scheduleTracker.firstAlertScheduledFor(openMRSId, ANC_IPT_VACCINE.getName()).getAlertAsLocalDate(), scheduleTracker.getActiveMilestone(openMRSId, ANC_IPT_VACCINE.getName()),
                today().plusWeeks(1), IPTDose.SP2.milestone());
    }

    @Test
    public void shouldCreateAppropriateSchedulesWhenCareHistoryUploadedForPatientregisteredWithCWC() {
        String staffId = staffGenerator.createStaff(browser, homePage);

        LocalDate registrationDate = today();
        LocalDate birthDate = registrationDate.minusYears(3);
        TestPatient patient = TestPatient.with("CWCHistory " + dataGenerator.randomString(5), staffId)
                .patientType(TestPatient.PATIENT_TYPE.CHILD_UNDER_FIVE).estimatedDateOfBirth(false)
                .registrationDate(DateUtil.today()).dateOfBirth(birthDate);
        String patientId = patientGenerator.createPatient(patient,browser,homePage);
        TestMobileMidwifeEnrollment mmEnrollmentDetails = TestMobileMidwifeEnrollment.with(staffId, patient.facilityId()).consent(false);

        TestCWCEnrollment cwcEnrollmentDetails = TestCWCEnrollment.createWithoutHistory();

        TestClientRegistration<TestCWCEnrollment> testClientRegistration = new TestClientRegistration<TestCWCEnrollment>(patient, cwcEnrollmentDetails, mmEnrollmentDetails);

        mobile.upload(MobileForm.registerClientForm(), testClientRegistration.withProgramEnrollmentThroughMobile());

        TestCareHistory careHistory = TestCareHistory.withoutHistory(patientId).staffId(staffId)
                                                        .withPenta("1",birthDate.plusMonths(1)).withIPTi("1",birthDate.plusDays(10)).withOPV("1",birthDate.plusDays(2));

        XformHttpClient.XformResponse response = mobile.upload(MobileForm.careHistoryForm(),careHistory.forMobile());

        assertEquals(1,response.getSuccessCount());

        String openMRSId = openMRSDB.getOpenMRSId(patientId);
        ScheduleHelper.assertAlertDate(scheduleTracker.firstAlertScheduledFor(openMRSId,CWC_IPT_VACCINE.getName()).getAlertAsLocalDate(),scheduleTracker.getActiveMilestone(openMRSId,CWC_IPT_VACCINE.getName()),
                                        today().plusWeeks(1), IPTiDose.IPTi2.name());
        ScheduleHelper.assertAlertDate(scheduleTracker.firstAlertScheduledFor(openMRSId,CWC_PENTA.getName()).getAlertAsLocalDate(),scheduleTracker.getActiveMilestone(openMRSId,CWC_PENTA.getName()),
                                        today().plusWeeks(1), PentaDose.PENTA2.milestoneName());
        ScheduleHelper.assertAlertDate(scheduleTracker.firstAlertScheduledFor(openMRSId,CWC_OPV_OTHERS.getName()).getAlertAsLocalDate(),scheduleTracker.getActiveMilestone(openMRSId,CWC_OPV_OTHERS.getName()),
                                        today().plusWeeks(1), OPVDose.OPV_2.milestoneName());
    }

    LocalDate expectedFirstAlertDate(String scheduleName, LocalDate referenceDate) {
        return scheduleTracker.firstAlert(scheduleName, referenceDate);
    }
}