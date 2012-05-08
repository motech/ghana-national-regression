package org.motechproject.ghana.national.functional.framework;

import org.motechproject.ghana.national.functional.TestEnvironmentConfiguration;

public class ApplicationURLs {
    public static final String LOGIN_PATH = "/ghana-national-web/login.jsp";
    public static final String HOME_PAGE_PATH = "/ghana-national-web/admin";
    public static final String OPENMRS_PATH = "/openmrs";
    private static final String PATIENT_PAGE_PATH = "/patientDashboard.form?patientId=";
    private static final String ENCOUNTER_PAGE_PATH = "/admin/encounters/encounter.form?encounterId=";
    private TestEnvironmentConfiguration configuration;

    public ApplicationURLs(TestEnvironmentConfiguration configuration) {
        this.configuration = configuration;
    }

    public String forLoginPage() {
        return forPath(LOGIN_PATH);
    }

    public String forHomePage() {
        return forPath(HOME_PAGE_PATH);
    }

    public String forPath(String path) {
        return String.format("http://%s:%s%s", configuration.host(), configuration.port(), path);
    }

    public String forOpenMRSHomePage() {
        return String.format("http://%s:%s%s", configuration.host(), configuration.port(), OPENMRS_PATH);
    }

    public String forOpenMRSPatientPage(String openMrsId) {
        return String.format("http://%s:%s%s", configuration.host(), configuration.port(), OPENMRS_PATH + PATIENT_PAGE_PATH + openMrsId);
    }

    public String forOpenMRSEncounterPage(String encounterId) {
        return String.format("http://%s:%s%s", configuration.host(), configuration.port(), OPENMRS_PATH + ENCOUNTER_PAGE_PATH + encounterId);
    }
}
