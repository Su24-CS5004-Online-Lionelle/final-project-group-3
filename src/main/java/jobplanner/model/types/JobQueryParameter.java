package jobplanner.model.types;

/**
 * A list of job query parameters.
 */
public enum JobQueryParameter {
    // The parameters for the job query
    WHAT("what"),
    WHERE("where"),
    COUNTRY("location0"),
    STATE("location1"),
    CITY("location2"),
    CATEGORY("category"),
    DATE_POSTED("max_days_old"),
    SALARY_MIN("min_salary"),
    SALARY_MAX("max_salary"),
    FULL_TIME("full_time"),
    PART_TIME("part_time"),
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
