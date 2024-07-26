package jobplanner.model;

public enum JobQueryParameter {
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

    private final String tag;

    JobQueryParameter(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return tag;
    }

    public static JobQueryParameter fromTag(String tag) {
        for (JobQueryParameter parameter : JobQueryParameter.values()) {
            if (parameter.toString().equals(tag)) {
                return parameter;
            }
        }
        return null;
    }

    public static JobQueryParameter fromString(String value) {
        for (JobQueryParameter parameter : JobQueryParameter.values()) {
            if (parameter.toString().equals(value)) {
                return parameter;
            }
        }
        return null;
    }
}
