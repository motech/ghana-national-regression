package org.motechproject.ghana.national.functional.mobileforms;

public class MobileForm {
    private String studyName;
    private String xmlTemplateName;

    public MobileForm(String studyName, String xmlTemplateName) {
        this.studyName = studyName;
        this.xmlTemplateName = xmlTemplateName;
    }

    public String getStudyName() {
        return studyName;
    }

    public String getXmlTemplateName() {
        return xmlTemplateName;
    }

    public static MobileForm registerClientForm() {
        return new MobileForm("NurseDataEntry", "register-client-template.xml");
    }

    public static MobileForm careHistoryForm() {
        return new MobileForm("NurseDataEntry", "care-history-template.xml");
    }

    public static MobileForm editClientForm() {
        return new MobileForm("NurseDataEntry", "edit-client-template.xml");
    }

    public static MobileForm registerANCForm() {
        return new MobileForm("NurseDataEntry", "register-anc-template.xml");
    }

    public static MobileForm pncMotherForm() {
        return new MobileForm("NurseDataEntry", "pnc-mother-template.xml");
    }

    public static MobileForm pncChildForm() {
        return new MobileForm("NurseDataEntry", "pnc-child-template.xml");
    }

    public static MobileForm registerCWCForm() {
        return new MobileForm("NurseDataEntry", "register-cwc-template.xml");
    }

    public static MobileForm deliveryNotificationForm() {
        return new MobileForm("NurseDataEntry", "delivery-notification-template.xml");
    }

    public static MobileForm registerMobileMidwifeForm() {
        return new MobileForm("NurseDataEntry", "mobile-midwife-template.xml");
    }

    public static MobileForm ancVisitForm() {
        return new MobileForm("NurseDataEntry", "anc-visit-template.xml");
    }
    public static MobileForm deliveryForm() {
        return new MobileForm("NurseDataEntry", "delivery-template.xml");
    }
    public static MobileForm ttVisitForm() {
        return new MobileForm("NurseDataEntry", "tt-visit-template.xml");
    }

    public static MobileForm queryClientForm() {
        return new MobileForm("NurseQuery", "client-query-template.xml");
    }

    public static MobileForm cwcVisitForm() {
        return new MobileForm("NurseDataEntry", "cwc-visit-template.xml");
    }

    public static MobileForm generalQueryForm() {
        return new MobileForm("NurseQuery","general-query-template.xml");
    }


}
