package org.motechproject.ghana.national.functional.data;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.motechproject.util.DateUtil;

import java.util.HashMap;
import java.util.Map;

public class TestPatient {
    private String region;
    private String district;
    private String subDistrict;
    private String facility;
    private String facilityId;
    private String facilityIdWherePatientIsEdited;
    private String middleName;
    private String lastName;
    private String address;
    private LocalDate dateOfBirth;
    private String firstName;
    private PATIENT_REGN_MODE registrationMode;
    private PATIENT_TYPE patientType;
    private boolean estimatedDateOfBirth;
    private boolean insured;
    private String motechId;
    private boolean female;
    private String staffId;
    private LocalDate registrationDate;
    private String motherMotechId;
    private String serialNumber;

    public static TestPatient with(String firstName, String staffId) {
        TestPatient testPatient = new TestPatient();
        testPatient.staffId = staffId;
        testPatient.registrationMode = TestPatient.PATIENT_REGN_MODE.AUTO_GENERATE_ID;
        testPatient.region = "Central Region";
        testPatient.district = "Awutu Senya";
        testPatient.subDistrict = "Kasoa";
        testPatient.facility = "Papaase CHPS";

        testPatient.middleName = "Middle Name";
        testPatient.lastName = "Last Name";
        testPatient.dateOfBirth = DateUtil.newDate(1999, 11, 30);
        testPatient.address = "Address";
        testPatient.female = true;

        testPatient.firstName = firstName;
        testPatient.insured = false;
        testPatient.patientType = PATIENT_TYPE.OTHER;
        testPatient.estimatedDateOfBirth = false;
        testPatient.facilityId = "13212";
        testPatient.registrationDate = DateUtil.today();
        testPatient.serialNumber = "serialNumber";
        return testPatient;
    }

    public Map<String, String> editFromMobile() {
        Map<String, String> map = forMobile();
        map.put("updatePatientFacilityId", facilityIdWherePatientIsEdited);
        return map;
    }

    public Map<String, String> forMobile() {
        return new HashMap<String, String>() {{
            put("registrationMode", registrationMode.name());
            put("motechId", motechId);
            put("firstName", firstName);
            put("middleName", middleName);
            put("lastName", lastName);
            put("dateOfBirth", dateOfBirth.toString(DateTimeFormat.forPattern("yyyy-MM-dd")));
            put("sex", genderCode());
            put("insured", booleanCode(insured));
            put("region", region);
            put("district", district);
            put("subDistrict", subDistrict);
            put("facilityId", facilityId);
            put("address", address);
            put("registrantType", patientType.name());
            put("estimatedBirthDate", booleanCode(estimatedDateOfBirth));
            put("date", registrationDate.toString(DateTimeFormat.forPattern("yyyy-MM-dd")));
            put("staffId", staffId);
            put("motherMotechId", motherMotechId);
            put("serialNumber", serialNumber);
        }};
    }

    public String booleanCode(boolean value) {
        return value ? "Y" : "N";
    }

    public TestPatient registrationMode(PATIENT_REGN_MODE registrationMode) {
        this.registrationMode = registrationMode;
        return this;
    }

    public TestPatient patientType(PATIENT_TYPE patientType) {
        this.patientType = patientType;
        return this;
    }

    public String region() {
        return region;
    }

    public String district() {
        return district;
    }

    public String subDistrict() {
        return subDistrict;
    }

    public String facility() {
        return facility;
    }

    public String facilityId() {
        return facilityId;
    }

    public String middleName() {
        return middleName;
    }

    public String lastName() {
        return lastName;
    }

    public String address() {
        return address;
    }

    public LocalDate dateOfBirth() {
        return dateOfBirth;
    }

    public String firstName() {
        return firstName;
    }

    public PATIENT_REGN_MODE registrationMode() {
        return registrationMode;
    }

    public PATIENT_TYPE patientType() {
        return patientType;
    }

    public boolean estimatedDateOfBirth() {
        return estimatedDateOfBirth;
    }

    public TestPatient estimatedDateOfBirth(boolean estimatedDateOfBirth) {
        this.estimatedDateOfBirth = estimatedDateOfBirth;
        return this;
    }

    public boolean insured() {
        return insured;
    }

    public String genderCode() {
        return isFemale() ? "F" : "M";
    }

    public boolean isFemale() {
        return female;
    }

    public TestPatient motechId(String motechId) {
        this.motechId = motechId;
        return this;
    }

    public String motechId() {
        return motechId;
    }

    public boolean hasMotechId() {
        return StringUtils.isNotEmpty(motechId);
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public TestPatient registrationDate(LocalDate registrationDate){
        this.registrationDate = registrationDate;
        return this;
    }

    public TestPatient dateOfBirth(LocalDate dateOfBirth){
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public TestPatient female(boolean female) {
        this.female = female;
        return this;
    }

    public String staffId() {
        return staffId;
    }

    public TestPatient middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public TestPatient lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public TestPatient facilityIdWherePatientIsEdited(String facilityIdWherePatientIsEdited) {
        this.facilityIdWherePatientIsEdited = facilityIdWherePatientIsEdited;
        return this;
    }

    public TestPatient motherMotechId(String motherMotechId) {
        this.motherMotechId = motherMotechId;
        return this;
    }

    public TestPatient serialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }


    public static enum PATIENT_REGN_MODE {AUTO_GENERATE_ID, USE_PREPRINTED_ID}

    public static enum PATIENT_TYPE {PREGNANT_MOTHER, CHILD_UNDER_FIVE, OTHER}
}
