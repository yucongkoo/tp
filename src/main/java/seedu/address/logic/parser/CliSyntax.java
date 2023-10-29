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
}
