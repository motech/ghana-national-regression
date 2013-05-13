package org.motechproject.ghana.national.functional.data;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.motechproject.ghana.national.domain.ANCCareHistory;
import org.motechproject.ghana.national.domain.RegistrationToday;
import org.motechproject.util.DateUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.motechproject.ghana.national.functional.util.MobileFormUtils.updateFieldByNameAndValue;
import static org.motechproject.ghana.national.functional.util.MobileFormUtils.updateFieldName;
import static org.motechproject.ghana.national.tools.Utility.nullSafeToString;

public class TestANCEnrollment implements CareEnrollment {
    private String staffId;
    private String facilityId;
    private String motechPatientId;
    private LocalDate registrationDate;
    private RegistrationToday registrationToday;
    private String serialNumber;
    private LocalDate estimatedDateOfDelivery;
    private Boolean addHistory;
    private List<ANCCareHistory> addCareHistory;
    private String height;
    private String gravida;
    private String parity;
    private Boolean deliveryDateConfirmed;
    private String lastIPT;
    private String lastTT;
    private LocalDate lastIPTDate;
    private LocalDate lastTTDate;
    private String region;
    private String district;
    private String subDistrict;
    private String facility;
    private String country;

    public String LastHbLevels() {
        return lastHbLevels;
    }

    public TestANCEnrollment withLastHbLevels(String lastHbLevels) {
        this.lastHbLevels = lastHbLevels;
        return this;
    }

    public String LastMotherVitaminA() {
        return lastMotherVitaminA;
    }

    public TestANCEnrollment withLastMotherVitaminA(String lastMotherVitaminA) {
        this.lastMotherVitaminA = lastMotherVitaminA;
        return this;
    }

    public String LastIronOrFolate() {
        return lastIronOrFolate;
    }

    public TestANCEnrollment withLastIronOrFolate(String lastIronOrFolate) {
        this.lastIronOrFolate = lastIronOrFolate;
        return this;
    }

    public String LastSyphilis() {
        return lastSyphilis;
    }

    public TestANCEnrollment withLastSyphilis(String lastSyphilis) {
        this.lastSyphilis = lastSyphilis;
        return this;
    }

    public String LastMalaria() {
        return lastMalaria;
    }

    public TestANCEnrollment withLastMalaria(String lastMalaria) {
        this.lastMalaria = lastMalaria;
        return this;
    }

    public String LastDiarrhea() {
        return lastDiarrhea;
    }

    public TestANCEnrollment withLastDiarrhea(String lastDiarrhea) {
        this.lastDiarrhea = lastDiarrhea;
        return this;
    }

    public String LastPnuemonia() {
        return lastPnuemonia;
    }

    public TestANCEnrollment withLastPnuemonia(String lastPnuemonia) {
        this.lastPnuemonia = lastPnuemonia;
        return this;
    }

    //ANC CARE OBSERVATIONS
    private String lastHbLevels;
    private String lastMotherVitaminA;
    private String lastIronOrFolate;
    private String lastSyphilis;
    private String lastMalaria;
    private String lastDiarrhea;
    private String lastPnuemonia;
    private LocalDate lastHbDate;
    private LocalDate lastMotherVitaminADate;
    private LocalDate lastIronOrFolateDate;
    private LocalDate lastSyphilisDate;
    private LocalDate lastMalariaDate;
    private LocalDate lastDiarrheaDate;
    private LocalDate lastPnuemoniaDate;

    public LocalDate LastHbDate() {
        return lastHbDate;
    }

    public TestANCEnrollment withLastHbDate(LocalDate lastHbDate) {
        this.lastHbDate = lastHbDate;
        return  this;
    }

    public LocalDate LastMotherVitaminADate() {
        return lastMotherVitaminADate;
    }

    public TestANCEnrollment withLastMotherVitaminADate(LocalDate lastMotherVitaminADate) {
        this.lastMotherVitaminADate = lastMotherVitaminADate;
        return this;
    }

    public LocalDate LastIronOrFolateDate() {
        return lastIronOrFolateDate;
    }

    public TestANCEnrollment withLastIronOrFolateDate(LocalDate lastIronOrFolateDate) {
        this.lastIronOrFolateDate = lastIronOrFolateDate;
        return this;
    }

    public LocalDate LastSyphilisDate() {
        return lastSyphilisDate;
    }

    public TestANCEnrollment withLastSyphilisDate(LocalDate lastSyphilisDate) {
        this.lastSyphilisDate = lastSyphilisDate;
        return this;
    }

    public LocalDate LastMalariaDate() {
        return lastMalariaDate;
    }

    public TestANCEnrollment withLastMalariaDate(LocalDate lastMalariaDate) {
        this.lastMalariaDate = lastMalariaDate;
        return this;
    }

    public LocalDate LastDiarrheaDate() {
        return lastDiarrheaDate;
    }

    public TestANCEnrollment withLastDiarrheaDate(LocalDate lastDiarrheaDate) {
        this.lastDiarrheaDate = lastDiarrheaDate;
        return  this;
    }

    public LocalDate LastPnuemoniaDate() {
        return lastPnuemoniaDate;
    }

    public TestANCEnrollment withLastPnuemoniaDate(LocalDate lastPnuemoniaDate) {
        this.lastPnuemoniaDate = lastPnuemoniaDate;
        return this;
    }



    private TestANCEnrollment(){}

    public static TestANCEnrollment create() {
        return TestANCEnrollment.createWithoutHistory().addHistoryDetails();
    }

    public static TestANCEnrollment createWithoutHistory() {
        final TestANCEnrollment enrollment = new TestANCEnrollment();
        enrollment.registrationDate = DateUtil.today();
        enrollment.registrationToday = RegistrationToday.TODAY;
        enrollment.serialNumber = "serialNumber";
        enrollment.estimatedDateOfDelivery = new LocalDate(2012, 2, 3);
        enrollment.height = "124.0";
        enrollment.gravida = "3";
        enrollment.parity = "4";
        enrollment.deliveryDateConfirmed = true;
        enrollment.country = "Ghana";
        enrollment.region = "Central Region";
        enrollment.district = "Awutu Senya";
        enrollment.subDistrict = "Kasoa";
        enrollment.facility = "Papaase CHPS";
        enrollment.facilityId = "13212";
        enrollment.addHistory = false;
        return enrollment;
    }

    public TestANCEnrollment addHistoryDetails() {
        this.addHistory = true;
        this.addCareHistory = Arrays.asList(ANCCareHistory.values());
        this.lastIPT = "1";
        this.lastTT = "1";

        this.lastHbLevels = "14";
        this.lastMotherVitaminA = "1";
        this.lastIronOrFolate = "1";
        this.lastSyphilis = "1";
        this.lastMalaria = "1";
        this.lastDiarrhea = "1";
        this.lastPnuemonia = "0";

        this.lastIPTDate = new LocalDate(2011, 2, 3);
        this.lastTTDate = new LocalDate(2011, 2, 4);

        this.lastHbDate = new LocalDate(2011, 2, 3);
        this.lastMotherVitaminADate = new LocalDate(2011, 2, 3);
        this.lastIronOrFolateDate = new LocalDate(2011, 2, 3);
        this.lastSyphilisDate = new LocalDate(2011, 2, 3);
        this.lastMalariaDate = new LocalDate(2011, 2, 3);
        this.lastDiarrheaDate = new LocalDate(2011, 2, 3);
        this.lastPnuemoniaDate = new LocalDate(2011, 2, 3);
        return this;
    }

    public Map<String, String> forMobile() {
        return new HashMap<String, String>() {{
            put("staffId", staffId);
            put("facilityId", facilityId);
            put("motechId", motechPatientId);
            put("registrationDate", safe(registrationDate));
            put("regDateToday", registrationToday.name());
            put("ancRegNumber", serialNumber);
            put("estDeliveryDate", safe(estimatedDateOfDelivery));
            put("height", height);
            put("gravida", gravida);
            put("parity", parity);
            put("addHistory", booleanCodeForAddHistory(addHistory));
            put("deliveryDateConfirmed", booleanCodeForDateConfirmed(deliveryDateConfirmed));
            put("addCareHistory", "IPT_SP,TT");
            put("lastIPT", lastIPT);
            put("lastTT", lastTT);

            put("lastHbLevels", lastHbLevels);
            put("lastMotherVitaminA", lastMotherVitaminA);
            put("lastIronOrFolate", lastIronOrFolate);
            put("lastSyphilis", lastSyphilis);
            put("lastMalaria", lastMalaria);
            put("lastDiarrhea",lastDiarrhea);
            put("lastPnuemonia", lastPnuemonia);

            put("lastIPTDate", safe(lastIPTDate));
            put("lastTTDate", safe(lastTTDate));
            put("lastHbDate", safe(lastHbDate));
            put("lastMotherVitaminADate", safe(lastMotherVitaminADate));
            put("lastIronOrFolateDate", safe(lastIronOrFolateDate));
            put("lastSyphilisDate", safe(lastSyphilisDate));
            put("lastMalariaDate", safe(lastMalariaDate));
            put("lastDiarrheaDate", safe(lastDiarrheaDate));
            put("lastPnuemoniaDate", safe(lastPnuemoniaDate));

            put("serialNumber", serialNumber);

        }};
    }

    public Map<String, String> withMobileMidwifeEnrollmentThroughMobile(TestMobileMidwifeEnrollment mmEnrollmentDetails) {
        Map<String, String> enrollmentDetails = mmEnrollmentDetails.forMobile("REGISTER");
        enrollmentDetails.putAll(forMobile());
        enrollmentDetails.put("enroll", "Y");
        return enrollmentDetails;
    }

    public Map<String, String> withoutMobileMidwifeEnrollmentThroughMobile() {
        Map<String, String> enrollmentDetails = forMobile();
        enrollmentDetails.put("enroll", "N");
        return enrollmentDetails;
    }

    @Override
    public Map<String, String> forClientRegistrationThroughMobile(TestPatient patient) {
        Map<String, String> enrollmentDetails = forMobile();
        updateFieldName(enrollmentDetails, "addCareHistory", "addMotherHistory");
        updateFieldName(enrollmentDetails, "serialNumber", "ancRegNumber");
        updateFieldName(enrollmentDetails, "estDeliveryDate", "expDeliveryDate");
        updateFieldByNameAndValue(enrollmentDetails, "registrationDate", "date", patient.getRegistrationDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd")));
        enrollmentDetails.put("staffId", patient.staffId());
        enrollmentDetails.put("facilityId", patient.facilityId());
        enrollmentDetails.put("motechId", patient.motechId());
        return enrollmentDetails;
    }

    public String booleanCodeForAddHistory(boolean value) {
        return value ? "1" : "0";
    }

    public String booleanCodeForDateConfirmed(boolean value) {
        return value ? "Y" : "N";
    }

    private String safe(LocalDate date) {
        return nullSafeToString(date, "yyyy-MM-dd");

    }

    public Boolean addHistory() {
        return addHistory;
    }

    public TestANCEnrollment withAddHistory(Boolean addHistory) {
        this.addHistory = addHistory;
        return this;
    }

    public List<ANCCareHistory> careHistory() {
        return addCareHistory;
    }

    public TestANCEnrollment withCareHistory(List<ANCCareHistory> careHistory) {
        this.addCareHistory = careHistory;
        return this;

    }

    public Boolean deliveryDateConfirmed() {
        return deliveryDateConfirmed;
    }

    public TestANCEnrollment withDeliveryDateConfirmed(Boolean deliveryDateConfirmed) {
        this.deliveryDateConfirmed = deliveryDateConfirmed;
        return this;

    }

    public LocalDate estimatedDateOfDelivery() {
        return estimatedDateOfDelivery;
    }

    public TestANCEnrollment withEstimatedDateOfDelivery(LocalDate estimatedDateOfDelivery) {
        this.estimatedDateOfDelivery = estimatedDateOfDelivery;
        return this;

    }

    public String facilityId() {
        return facilityId;
    }

    public TestANCEnrollment withFacilityId(String facilityId) {
        this.facilityId = facilityId;
        return this;

    }

    public String gravida() {
        return String.valueOf(gravida);
    }

    public TestANCEnrollment withGravida(String gravida) {
        this.gravida = gravida;
        return this;

    }

    public String height() {
        return height;
    }

    public TestANCEnrollment withHeight(String height) {
        this.height = height;
        return this;

    }

    public String lastTT() {
        return lastTT;
    }

    public TestANCEnrollment withLastTT(String last) {
        this.lastTT = last;
        return this;

    }

    public LocalDate lastTTDate() {
        return lastTTDate;
    }

    public TestANCEnrollment withLastTTDate(LocalDate lastDate) {
        this.lastTTDate = lastDate;
        return this;

    }

    public String lastIPT() {
        return lastIPT;
    }

    public TestANCEnrollment withLastIPT(String lastIPT) {
        this.lastIPT = lastIPT;
        return this;

    }

    public LocalDate lastIPTDate() {
        return lastIPTDate;
    }

    public TestANCEnrollment withLastIPTDate(LocalDate lastIPTDate) {
        this.lastIPTDate = lastIPTDate;
        return this;

    }

    public String motechPatientId() {
        return motechPatientId;
    }

    public TestANCEnrollment withMotechPatientId(String motechPatientId) {
        this.motechPatientId = motechPatientId;
        return this;

    }

    public String parity() {
        return String.valueOf(parity);
    }

    public TestANCEnrollment withParity(String parity) {
        this.parity = parity;
        return this;

    }

    public LocalDate registrationDate() {
        return registrationDate;
    }

    public TestANCEnrollment withRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
        return this;

    }

    public RegistrationToday registrationToday() {
        return registrationToday;
    }

    public TestANCEnrollment withRegistrationToday(RegistrationToday registrationToday) {
        this.registrationToday = registrationToday;
        return this;

    }

    public String serialNumber() {
        return serialNumber;
    }

    public TestANCEnrollment withSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public String staffId() {
        return staffId;
    }

    public TestANCEnrollment withStaffId(String staffId) {
        this.staffId = staffId;
        return this;
    }

    public String region() {
        return region;
    }

    public TestANCEnrollment withRegion(String region) {
        this.region = region;
        return this;
    }

    public String district() {
        return district;
    }

    public TestANCEnrollment withDistrict(String district) {
        this.district = district;
        return this;
    }

    public String subDistrict() {
        return subDistrict;
    }

    public TestANCEnrollment withSubDistrict(String subDistrict) {
        this.subDistrict = subDistrict;
        return this;
    }

    public String facility() {
        return facility;
    }

    public TestANCEnrollment withFacility(String facility) {
        this.facility = facility;
        return this;
    }

    public Boolean hasIPTHistory() {
        return addCareHistory != null && addCareHistory.contains(ANCCareHistory.IPT_SP);
    }

    public Boolean hasTTHistory() {
        return addCareHistory != null && addCareHistory.contains(ANCCareHistory.TT);
    }

    public String country() {
        return country;
    }
}

