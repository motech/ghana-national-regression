package org.motechproject.ghana.national.functional.data;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.motechproject.ghana.national.domain.CwcCareHistory;
import org.motechproject.ghana.national.domain.RegistrationToday;
import org.motechproject.util.DateUtil;

import java.util.*;

import static org.motechproject.ghana.national.functional.util.MobileFormUtils.updateFieldByNameAndValue;
import static org.motechproject.ghana.national.functional.util.MobileFormUtils.updateFieldName;
import static org.motechproject.ghana.national.tools.Utility.nullSafeToString;

public class TestCWCEnrollment implements CareEnrollment {

    private String staffId;
    private String facilityId;
    private String motechPatientId;
    private RegistrationToday registrationToday;
    private LocalDate registrationDate;
    private String serialNumber;
    private Boolean addHistory;
    private List<CwcCareHistory> addCareHistory;
    private LocalDate lastBcgDate;
    private LocalDate lastOPVDate;
    private LocalDate lastVitaminADate;
    private LocalDate lastIPTiDate;
    private LocalDate lastYellowFeverDate;
    private LocalDate lastPentaDate;
    private LocalDate lastRotavirusDate;
    private LocalDate lastMeaslesDate;
    private String lastOPV;
    private String lastIPTi;
    private String lastPenta;
    private String lastRotavirus;
    private String country;
    private String region;
    private String district;
    private String subDistrict;
    private String facility;
    private LocalDate lastPneumococcalDate;
    private String lastPneumococcal;
    private String lastVitaminA;
    private String lastMeasles;

    public static TestCWCEnrollment create() {
        return createWithoutHistory().addHistoryDetails();
    }

    public TestCWCEnrollment addHistoryDetails() {
        this.addHistory = true;
        this.addCareHistory = Arrays.asList(CwcCareHistory.values());
        this.lastBcgDate = DateUtil.newDate(2000, 11, 1);
        this.lastYellowFeverDate = DateUtil.newDate(2000, 11, 5);
        this.lastMeaslesDate = DateUtil.newDate(2000, 11, 7);
        this.lastPentaDate = DateUtil.newDate(2000, 11, 6);
        this.lastRotavirusDate = DateUtil.newDate(2000, 11, 7);
        this.lastPneumococcalDate = DateUtil.newDate(2000, 11, 2);
        this.lastOPVDate = DateUtil.newDate(2000, 11, 2);
        this.lastVitaminADate = DateUtil.newDate(2000, 11, 3);
        this.lastIPTiDate = DateUtil.newDate(2000, 11, 4);
        this.lastOPV = "1";
        this.lastIPTi = "2";
        this.lastPenta = "3";
        this.lastVitaminA = "blue";
        this.lastMeasles = "1";
        this.lastRotavirus = "1";
        this.lastPneumococcal = "2";
        return this;
    }

    public static TestCWCEnrollment createWithoutHistory() {
        final TestCWCEnrollment enrollment = new TestCWCEnrollment();
        enrollment.addCareHistory = new ArrayList<CwcCareHistory>();
        enrollment.registrationToday = RegistrationToday.IN_PAST;
        enrollment.registrationDate = DateUtil.newDate(2011, 2, 2);
        enrollment.serialNumber = "serialNumber";
        enrollment.country = "Ghana";
        enrollment.region = "Central Region";
        enrollment.district = "Awutu Senya";
        enrollment.subDistrict = "Kasoa";
        enrollment.facility = "Papaase CHPS";
        enrollment.facilityId = "13212";
        enrollment.addHistory = false;
        return enrollment;
    }

    @Override
    public Map<String, String> forClientRegistrationThroughMobile(TestPatient patient) {
        Map<String, String> enrollmentDetails = forMobile();
        updateFieldName(enrollmentDetails, "addCareHistory", "addChildHistory");
        updateFieldName(enrollmentDetails, "serialNumber", "cwcRegNumber");
        updateFieldByNameAndValue(enrollmentDetails, "registrationDate", "date", patient.getRegistrationDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd")));
        enrollmentDetails.put("staffId", patient.staffId());
        enrollmentDetails.put("facilityId", patient.facilityId());
        enrollmentDetails.put("motechId", patient.motechId());
        return enrollmentDetails;
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

    public Map<String, String> forMobile() {
        return new HashMap<java.lang.String, java.lang.String>() {{
            put("staffId", staffId);
            put("facilityId", facilityId);
            put("motechId", motechPatientId);
            put("registrationToday", registrationToday.name());
            put("registrationDate", safe(registrationDate));
            put("serialNumber", serialNumber);
            put("addHistory", booleanCodeForAddHistory(addHistory));
            List<String> concatenatedCareHistories = new ArrayList<String>();
            for (CwcCareHistory history : addCareHistory) {
                if (recorded(history))
                    concatenatedCareHistories.add(history.name());
            }
            put("addCareHistory", StringUtils.join(concatenatedCareHistories, ","));
            put("bcgDate", safe(lastBcgDate));
            put("lastOPVDate", safe(lastOPVDate));
            put("lastVitaminADate", safe(lastVitaminADate));
            put("lastIPTiDate", safe(lastIPTiDate));
            put("yellowFeverDate", safe(lastYellowFeverDate));
            put("lastPentaDate", safe(lastPentaDate));
            put("lastRotavirusDate", safe(lastRotavirusDate));
            put("lastPneumococcalDate", safe(lastPneumococcalDate));
            put("measlesDate", safe(lastMeaslesDate));
            put("lastMeasles", lastMeasles);
            put("lastVitaminA", lastVitaminA);
            put("lastPenta", lastPenta);
            put("lastRotavirus", lastRotavirus);
            put("lastPneumococcal", lastPneumococcal);
            put("lastOPV", lastOPV);
            put("lastIPTi", lastIPTi);

        }};

    }

    private boolean recorded(CwcCareHistory history) {
        if (CwcCareHistory.BCG.equals(history))
            return lastBcgDate != null;
        if (CwcCareHistory.IPTI.equals(history))
            return lastIPTiDate != null;
        if (CwcCareHistory.MEASLES.equals(history))
            return lastMeaslesDate != null;
        if (CwcCareHistory.OPV.equals(history))
            return lastOPVDate != null;
        if (CwcCareHistory.PENTA.equals(history))
            return lastPentaDate != null;
        if (CwcCareHistory.PNEUMOCOCCAL.equals(history))
            return lastPneumococcalDate != null;
        if (CwcCareHistory.ROTAVIRUS.equals(history))
            return lastRotavirusDate != null;
        if (CwcCareHistory.VITA_A.equals(history))
            return lastVitaminADate != null;
        if (CwcCareHistory.YF.equals(history))
            return lastYellowFeverDate != null;
        return false;
    }

    private String safe(LocalDate date) {
        return nullSafeToString(date, "yyyy-MM-dd");
    }

    public String booleanCodeForAddHistory(boolean value) {
        return value ? "1" : "0";
    }

    public String getStaffId() {
        return staffId;
    }

    public TestCWCEnrollment withStaffId(String staffId) {
        this.staffId = staffId;
        return this;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public TestCWCEnrollment withFacilityId(String facilityId) {
        this.facilityId = facilityId;
        return this;
    }

    public String getMotechPatientId() {
        return motechPatientId;
    }

    public TestCWCEnrollment withMotechPatientId(String motechPatientId) {
        this.motechPatientId = motechPatientId;
        return this;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public TestCWCEnrollment withRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
        return this;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public TestCWCEnrollment withSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public Boolean getAddHistory() {
        return addHistory;
    }

    public TestCWCEnrollment withAddHistory(Boolean addHistory) {
        this.addHistory = addHistory;
        return this;
    }

    public List<CwcCareHistory> getAddCareHistory() {
        return addCareHistory;
    }

    public TestCWCEnrollment withAddCareHistory(List<CwcCareHistory> addCareHistory) {
        this.addCareHistory = addCareHistory;
        lastBcgDate = null;
        lastIPTiDate = null;
        lastIPTi = null;
        lastOPV = null;
        lastOPVDate = null;
        lastPenta = null;
        lastPentaDate = null;
        lastVitaminADate = null;
        lastMeaslesDate = null;
        lastYellowFeverDate = null;
        lastRotavirus = null;
        lastRotavirusDate = null;
        lastPneumococcal = null;
        lastPneumococcalDate = null;
        return this;
    }

    public LocalDate getLastBcgDate() {
        return lastBcgDate;
    }

    public TestCWCEnrollment withLastBcgDate(LocalDate bcgDate) {
        this.lastBcgDate = bcgDate;
        return this;
    }

    public LocalDate getLastOPVDate() {
        return lastOPVDate;
    }

    public TestCWCEnrollment withLastOPVDate(LocalDate lastOPVDate) {
        this.lastOPVDate = lastOPVDate;
        return this;
    }

    public LocalDate getLastVitaminADate() {
        return lastVitaminADate;
    }

    public TestCWCEnrollment withLastVitaminADate(LocalDate lastVitaminADate) {
        this.lastVitaminADate = lastVitaminADate;
        return this;
    }

    public LocalDate getLastIPTiDate() {
        return lastIPTiDate;
    }

    public TestCWCEnrollment withLastIPTiDate(LocalDate lastIPTiDate) {
        this.lastIPTiDate = lastIPTiDate;
        return this;
    }

    public LocalDate getLastYellowFeverDate() {
        return lastYellowFeverDate;
    }

    public TestCWCEnrollment withLastYellowFeverDate(LocalDate yellowFeverDate) {
        this.lastYellowFeverDate = yellowFeverDate;
        return this;
    }

    public LocalDate getLastPentaDate() {
        return lastPentaDate;
    }

    public TestCWCEnrollment withLastPentaDate(LocalDate lastPentaDate) {
        this.lastPentaDate = lastPentaDate;
        return this;
    }

    public LocalDate getLastPneumococcalDate() {
        return lastPneumococcalDate;
    }

    public TestCWCEnrollment withLastPneumococcalDate(LocalDate lastPneumococcalDate) {
        this.lastPneumococcalDate = lastPneumococcalDate;
        return this;
    }

    public String getLastPneumococcal() {
        return lastPneumococcal;
    }

    public TestCWCEnrollment withLastPneumococcal(String lastPneumococcal) {
        this.lastPneumococcal = lastPneumococcal;
        return this;
    }

    public TestCWCEnrollment withLastRotavirus(String lastRotavirus) {
        this.lastRotavirus = lastRotavirus;
        return this;
    }

    public TestCWCEnrollment withLastRotavirusDate(LocalDate lastRotavirusDate) {
        this.lastRotavirusDate = lastRotavirusDate;
        return this;
    }

    public LocalDate getLastRotavirusDate() {
        return lastRotavirusDate;
    }

    public String getLastRotavirus() {
        return lastRotavirus;
    }

    public LocalDate getLastMeaslesDate() {
        return lastMeaslesDate;
    }

    public TestCWCEnrollment withLastMeaslesDate(LocalDate measlesDate) {
        this.lastMeaslesDate = measlesDate;
        return this;
    }

    public String getLastOPV() {
        return lastOPV;
    }

    public TestCWCEnrollment withLastOPV(String lastOPV) {
        this.lastOPV = lastOPV;
        return this;
    }

    public String getLastIPTi() {
        return lastIPTi;
    }

    public TestCWCEnrollment withLastIPTi(String lastIPTi) {
        this.lastIPTi = lastIPTi;
        return this;
    }

    public String getLastPenta() {
        return lastPenta;
    }

    public String getLastMeasles() {
        return lastMeasles;
    }

    public String getLastVitaminA() {
        return lastVitaminA;
    }

    public TestCWCEnrollment withLastPenta(String lastPenta) {
        this.lastPenta = lastPenta;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSubDistrict() {
        return subDistrict;
    }

    public void setSubDistrict(String subDistrict) {
        this.subDistrict = subDistrict;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public RegistrationToday getRegistrationToday() {
        return registrationToday;
    }

    public Boolean hasBcgHistory() {
        return addCareHistory.contains(CwcCareHistory.BCG);
    }

    public Boolean hasIPTiHistory() {
        return addCareHistory.contains(CwcCareHistory.IPTI);
    }

    public Boolean hasOPVHistory() {
        return addCareHistory.contains(CwcCareHistory.OPV);
    }

    public Boolean hasPentaHistory() {
        return addCareHistory.contains(CwcCareHistory.PENTA);
    }

    public Boolean hasVitaminAHistory() {
        return addCareHistory.contains(CwcCareHistory.VITA_A);
    }

    public Boolean hasMeaslesHistory() {
        return addCareHistory.contains(CwcCareHistory.MEASLES);
    }

    public Boolean hasYellowFeverHistory() {
        return addCareHistory.contains(CwcCareHistory.YF);
    }

    public TestCWCEnrollment withHistoryDays3WeeksBeforeRegistrationDate(int weeks) {
        this.withLastPneumococcalDate(registrationDate.minusWeeks(weeks))
                .withLastMeaslesDate(registrationDate.minusWeeks(weeks))
                .withLastPentaDate(registrationDate.minusWeeks(weeks))
                .withLastYellowFeverDate(registrationDate.minusWeeks(weeks))
                .withLastIPTiDate(registrationDate.minusWeeks(weeks))
                .withLastOPVDate(registrationDate.minusWeeks(weeks))
                .withLastRotavirusDate(registrationDate.minusWeeks(weeks))
                .withLastVitaminADate(registrationDate.minusWeeks(weeks))
                .withLastBcgDate(registrationDate.minusWeeks(weeks));
        return this;
    }


}



