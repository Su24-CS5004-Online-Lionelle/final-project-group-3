package jobplanner.model.types;

/** A list of job categories. */
public enum JobCategory {
    /** Accounting and finance jobs. */
    ACCOUNTING_FINANCE("accounting-finance-jobs", "Accounting & Finance Jobs"),
        
    /** Information Technology jobs. */
    IT("it-jobs", "IT Jobs"),

    /** Sales jobs. */
    SALES("sales-jobs", "Sales Jobs"),

    /** Customer services jobs. */
    CUSTOMER_SERVICES("customer-services-jobs", "Customer Services Jobs"),

    /** Engineering jobs. */
    ENGINEERING("engineering-jobs", "Engineering Jobs"),

    /** Human Resources and Recruitment jobs. */
    HR("hr-jobs", "HR & Recruitment Jobs"),

    /** Healthcare and nursing jobs. */
    HEALTHCARE_NURSING("healthcare-nursing-jobs", "Healthcare & Nursing Jobs"),

    /** Hospitality and catering jobs. */
    HOSPITALITY_CATERING("hospitality-catering-jobs", "Hospitality & Catering Jobs"),

    /** Public relations, advertising, and marketing jobs. */
    PR_ADVERTISING_MARKETING("pr-advertising-marketing-jobs", "PR, Advertising & Marketing Jobs"),

    /** Logistics and warehouse jobs. */
    LOGISTICS_WAREHOUSE("logistics-warehouse-jobs", "Logistics & Warehouse Jobs"),

    /** Teaching jobs. */
    TEACHING("teaching-jobs", "Teaching Jobs"),

    /** Trade and construction jobs. */
    TRADE_CONSTRUCTION("trade-construction-jobs", "Trade & Construction Jobs"),

    /** Administrative jobs. */
    ADMIN("admin-jobs", "Admin Jobs"),

    /** Legal jobs. */
    LEGAL("legal-jobs", "Legal Jobs"),

    /** Creative and design jobs. */
    CREATIVE_DESIGN("creative-design-jobs", "Creative & Design Jobs"),

    /** Graduate jobs. */
    GRADUATE("graduate-jobs", "Graduate Jobs"),

    /** Retail jobs. */
    RETAIL("retail-jobs", "Retail Jobs"),

    /** Consultancy jobs. */
    CONSULTANCY("consultancy-jobs", "Consultancy Jobs"),

    /** Manufacturing jobs. */
    MANUFACTURING("manufacturing-jobs", "Manufacturing Jobs"),

    /** Scientific and quality assurance jobs. */
    SCIENTIFIC_QA("scientific-qa-jobs", "Scientific & QA Jobs"),

    /** Social work jobs. */
    SOCIAL_WORK("social-work-jobs", "Social work Jobs"),

    /** Travel jobs. */
    TRAVEL("travel-jobs", "Travel Jobs"),

    /** Energy, oil, and gas jobs. */
    ENERGY_OIL_GAS("energy-oil-gas-jobs", "Energy, Oil & Gas Jobs"),

    /** Property jobs. */
    PROPERTY("property-jobs", "Property Jobs"),

    /** Charity and voluntary jobs. */
    CHARITY_VOLUNTARY("charity-voluntary-jobs", "Charity & Voluntary Jobs"),

    /** Domestic help and cleaning jobs. */
    DOMESTIC_HELP_CLEANING("domestic-help-cleaning-jobs", "Domestic help & Cleaning Jobs"),

    /** Maintenance jobs. */
    MAINTENANCE("maintenance-jobs", "Maintenance Jobs"),

    /** Part-time jobs. */
    PART_TIME("part-time-jobs", "Part time Jobs"),

    /** Other and general jobs not categorized elsewhere. */
    OTHER_GENERAL("other-general-jobs", "Other/General Jobs"),

    /** Unknown job category. */
    UNKNOWN("unknown", "Unknown");

    /** The tag for the category. */
    private final String tag;
    /** The label for the category. */
    private final String label;

    /**
     * Constructor for the job category.
     * 
     * @param tag   the tag for the category
     * @param label the label for the category
     */
    JobCategory(String tag, String label) {
        this.tag = tag;
        this.label = label;
    }

    /**
     * Gets the tag for the category.
     * 
     * @return the tag for the category
     */
    public String getTag() {
        return tag;
    }

    /**
     * Gets the label for the category.
     * 
     * @return the label for the category
     */
    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }

    /**
     * Gets the category from the tag.
     * 
     * @param tag the tag to get the category from
     * @return the category
     */
    public static JobCategory fromTag(String tag) {
        for (JobCategory category : JobCategory.values()) {
            if (category.tag.equals(tag)) {
                return category;
            }
        }
        return UNKNOWN;
    }

    /**
     * Gets the category from the label.
     * 
     * @param label the label to get the category from
     * @return the category
     */
    public static JobCategory fromLabel(String label) {
        for (JobCategory category : JobCategory.values()) {
            if (category.label.equals(label)) {
                return category;
            }
        }
        return UNKNOWN;
    }

    /**
     * Gets the category from the string value.
     * 
     * @param value the value to get the category from
     * @return the category
     */
    public static JobCategory fromString(String value) {
        for (JobCategory category : JobCategory.values()) {
            if (category.toString().equals(value)) {
                return category;
            }
        }
        return UNKNOWN;
    }
}
