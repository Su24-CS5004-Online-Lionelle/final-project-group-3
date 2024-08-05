package jobplanner.model.types;

/**
 * A list of job query parameters.
 */
public enum JobQueryParameter {    
    /** Represents the job title or keyword to search for. */
    WHAT("what"),

    /** Represents the location keyword to search for jobs in a specific place. */
    WHERE("where"),

    /** Represents the country level in the location hierarchy. */
    COUNTRY("location0"),

    /** Represents the state level in the location hierarchy. */
    STATE("location1"),

    /** Represents the city level in the location hierarchy. */
    CITY("location2"),

    /** Represents the job category or industry. */
    CATEGORY("category"),

    /** Represents the date the job was posted, to filter by recency. */
    DATE_POSTED("max_days_old"),

    /** Represents the minimum salary filter for job listings. */
    SALARY_MIN("salary_min"),

    /** Represents the maximum salary filter for job listings. */
    SALARY_MAX("salary_max"),

    /** Represents the filter to search for full-time jobs. */
    FULL_TIME("full_time"),

    /** Represents the filter to search for part-time jobs. */
    PART_TIME("part_time"),

    /** Represents the filter to search for contract jobs. */
    CONTRACT("contract");

    /** The tag for the parameter. */
    private final String tag;

    /**
     * Constructor for the job query parameter.
     * 
     * @param tag the tag for the parameter
     */
    JobQueryParameter(String tag) {
        this.tag = tag;
    }

    /**
     * Gets the tag for the parameter.
     * 
     * @return the tag for the parameter
     */
    public String getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return tag;
    }

    /**
     * Gets the parameter from the tag.
     * 
     * @param tag the tag to get the parameter from
     * @return the parameter
     */
    public static JobQueryParameter fromTag(String tag) {
        for (JobQueryParameter parameter : JobQueryParameter.values()) {
            if (parameter.toString().equals(tag)) {
                return parameter;
            }
        }
        return null;
    }

    /**
     * Gets the parameter from the string.
     * 
     * @param value the string to get the parameter from
     * @return the parameter
     */
    public static JobQueryParameter fromString(String value) {
        for (JobQueryParameter parameter : JobQueryParameter.values()) {
            if (parameter.toString().equals(value)) {
                return parameter;
            }
        }
        return null;
    }
}
