package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_EMPTY = new Prefix("");
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_REMARK = new Prefix("r/");
    public static final Prefix PREFIX_ADD_TAG = new Prefix("at/");
    public static final Prefix PREFIX_DELETE_TAG = new Prefix("dt/");
    public static final Prefix PREFIX_INSURANCE = new Prefix("i/");
    public static final Prefix PREFIX_ADD_INSURANCE = new Prefix("ai/");
    public static final Prefix PREFIX_DELETE_INSURANCE = new Prefix("di/");
    public static final Prefix PREFIX_PRIORITY = new Prefix("pr/");
    public static final Prefix PREFIX_APPOINTMENT = new Prefix("d/");
    public static final Prefix PREFIX_APPOINTMENT_TIME = new Prefix("t/");
    public static final Prefix PREFIX_APPOINTMENT_VENUE = new Prefix("v/");

    // Lower case prefixes
    public static final Prefix PREFIX_NAME_UPPER = new Prefix("N/");
    public static final Prefix PREFIX_PHONE_UPPER = new Prefix("P/");
    public static final Prefix PREFIX_EMAIL_UPPER = new Prefix("E/");
    public static final Prefix PREFIX_ADDRESS_UPPER = new Prefix("A/");
    public static final Prefix PREFIX_TAG_UPPER = new Prefix("T/");
    public static final Prefix PREFIX_REMARK_UPPER = new Prefix("R/");
    public static final Prefix PREFIX_ADD_TAG_UPPER = new Prefix("AT/");
    public static final Prefix PREFIX_DELETE_TAG_UPPER = new Prefix("DT/");
    public static final Prefix PREFIX_INSURANCE_UPPER = new Prefix("I/");
    public static final Prefix PREFIX_ADD_INSURANCE_UPPER = new Prefix("AI/");
    public static final Prefix PREFIX_DELETE_INSURANCE_UPPER = new Prefix("DI/");
    public static final Prefix PREFIX_PRIORITY_UPPER = new Prefix("PR/");
    public static final Prefix PREFIX_APPOINTMENT_UPPER = new Prefix("D/");
    public static final Prefix PREFIX_APPOINTMENT_TIME_UPPER = new Prefix("T/");
    public static final Prefix PREFIX_APPOINTMENT_VENUE_UPPER = new Prefix("V/");
}
