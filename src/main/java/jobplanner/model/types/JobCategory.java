package jobplanner.model.types;

/** A list of job categories. */
public enum JobCategory {
    // The categories for the job
    /** */
    ACCOUNTING_FINANCE("accounting-finance-jobs", "Accounting & Finance Jobs"),
    /** */
    IT("it-jobs", "IT Jobs"),
    /** */
    SALES("sales-jobs", "Sales Jobs"),
    /** */
    CUSTOMER_SERVICES("customer-services-jobs", "Customer Services Jobs"),
    /** */
    ENGINEERING("engineering-jobs", "Engineering Jobs"),
    /** */
    HR("hr-jobs", "HR & Recruitment Jobs"),
    /** */
    HEALTHCARE_NURSING("healthcare-nursing-jobs", "Healthcare & Nursing Jobs"),
    /** */
    HOSPITALITY_CATERING("hospitality-catering-jobs", "Hospitality & Catering Jobs"),
    /** */
    PR_ADVERTISING_MARKETING("pr-advertising-marketing-jobs", "PR, Advertising & Marketing Jobs"),
    /** */
    LOGISTICS_WAREHOUSE("logistics-warehouse-jobs", "Logistics & Warehouse Jobs"),
    /** */
    TEACHING("teaching-jobs", "Teaching Jobs"),
    /** */
    TRADE_CONSTRUCTION("trade-construction-jobs", "Trade & Construction Jobs"),
    /** */
    ADMIN("admin-jobs", "Admin Jobs"),
    /** */
    LEGAL("legal-jobs", "Legal Jobs"),
    /** */
    CREATIVE_DESIGN("creative-design-jobs", "Creative & Design Jobs"),
    /** */
    GRADUATE("graduate-jobs", "Graduate Jobs"),
    /** */
    RETAIL("retail-jobs", "Retail Jobs"),
    /** */
    CONSULTANCY("consultancy-jobs", "Consultancy Jobs"),
    /** */
    MANUFACTURING("manufacturing-jobs", "Manufacturing Jobs"),
    /** */
    SCIENTIFIC_QA("scientific-qa-jobs", "Scientific & QA Jobs"),
    /** */
    SOCIAL_WORK("social-work-jobs", "Social work Jobs"),
    /** */
    TRAVEL("travel-jobs", "Travel Jobs"),
    /** */
    ENERGY_OIL_GAS("energy-oil-gas-jobs", "Energy, Oil & Gas Jobs"),
    /** */
    PROPERTY("property-jobs", "Property Jobs"),
    /** */
    CHARITY_VOLUNTARY("charity-voluntary-jobs", "Charity & Voluntary Jobs"),
    /** */
    DOMESTIC_HELP_CLEANING("domestic-help-cleaning-jobs", "Domestic help & Cleaning Jobs"),
    /** */
    MAINTENANCE("maintenance-jobs", "Maintenance Jobs"),
    /** */
    PART_TIME("part-time-jobs", "Part time Jobs"),
    /** */
    OTHER_GENERAL("other-general-jobs", "Other/General Jobs"),
    /** */
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
