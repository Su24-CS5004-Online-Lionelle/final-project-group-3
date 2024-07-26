package jobplanner.model;

public enum JobCategory {
    ACCOUNTING_FINANCE("accounting-finance-jobs", "Accounting & Finance Jobs"),
    IT("it-jobs", "IT Jobs"),
    SALES("sales-jobs", "Sales Jobs"),
    CUSTOMER_SERVICES("customer-services-jobs", "Customer Services Jobs"),
    ENGINEERING("engineering-jobs", "Engineering Jobs"),
    HR("hr-jobs", "HR & Recruitment Jobs"),
    HEALTHCARE_NURSING("healthcare-nursing-jobs", "Healthcare & Nursing Jobs"),
    HOSPITALITY_CATERING("hospitality-catering-jobs", "Hospitality & Catering Jobs"),
    PR_ADVERTISING_MARKETING("pr-advertising-marketing-jobs", "PR, Advertising & Marketing Jobs"),
    LOGISTICS_WAREHOUSE("logistics-warehouse-jobs", "Logistics & Warehouse Jobs"),
    TEACHING("teaching-jobs", "Teaching Jobs"),
    TRADE_CONSTRUCTION("trade-construction-jobs", "Trade & Construction Jobs"),
    ADMIN("admin-jobs", "Admin Jobs"),
    LEGAL("legal-jobs", "Legal Jobs"),
    CREATIVE_DESIGN("creative-design-jobs", "Creative & Design Jobs"),
    GRADUATE("graduate-jobs", "Graduate Jobs"),
    RETAIL("retail-jobs", "Retail Jobs"),
    CONSULTANCY("consultancy-jobs", "Consultancy Jobs"),
    MANUFACTURING("manufacturing-jobs", "Manufacturing Jobs"),
    SCIENTIFIC_QA("scientific-qa-jobs", "Scientific & QA Jobs"),
    SOCIAL_WORK("social-work-jobs", "Social work Jobs"),
    TRAVEL("travel-jobs", "Travel Jobs"),
    ENERGY_OIL_GAS("energy-oil-gas-jobs", "Energy, Oil & Gas Jobs"),
    PROPERTY("property-jobs", "Property Jobs"),
    CHARITY_VOLUNTARY("charity-voluntary-jobs", "Charity & Voluntary Jobs"),
    DOMESTIC_HELP_CLEANING("domestic-help-cleaning-jobs", "Domestic help & Cleaning Jobs"),
    MAINTENANCE("maintenance-jobs", "Maintenance Jobs"),
    PART_TIME("part-time-jobs", "Part time Jobs"),
    OTHER_GENERAL("other-general-jobs", "Other/General Jobs"),
    UNKNOWN("unknown", "Unknown");

    private final String tag;
    private final String label;

    JobCategory(String tag, String label) {
        this.tag = tag;
        this.label = label;
    }

    public String getTag() {
        return tag;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }

    public static JobCategory fromTag(String tag) {
        for (JobCategory category : JobCategory.values()) {
            if (category.tag.equals(tag)) {
                return category;
            }
        }
        return UNKNOWN;
    }

    public static JobCategory fromLabel(String label) {
        for (JobCategory category : JobCategory.values()) {
            if (category.label.equals(label)) {
                return category;
            }
        }
        return UNKNOWN;
    }

    public static JobCategory fromString(String value) {
        for (JobCategory category : JobCategory.values()) {
            if (category.toString().equals(value)) {
                return category;
            }
        }
        return UNKNOWN;
    }
}
