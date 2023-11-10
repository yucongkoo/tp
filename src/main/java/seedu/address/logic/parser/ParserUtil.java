package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.priority.Priority.isValidPriority;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Email;
import seedu.address.model.person.Insurance;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Tag;
import seedu.address.model.person.predicate.AddressContainsKeywordsPredicate;
import seedu.address.model.person.predicate.EmailContainsKeywordsPredicate;
import seedu.address.model.person.predicate.InsuranceContainsKeywordsPredicate;
import seedu.address.model.person.predicate.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicate.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.predicate.PriorityContainsKeywordsPredicate;
import seedu.address.model.person.predicate.RemarkContainsKeywordsPredicate;
import seedu.address.model.person.predicate.TagContainsKeywordsPredicate;
import seedu.address.model.priority.Priority;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return Address.createAddress(address.trim());
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parse a {@code String insurance} into a {@code Insurance}
     * Leading, trailing and contiguous whitespaces between words will be trimmed.
     *
     */
    private static Insurance parseInsurance(String insurance) throws ParseException {
        requireNonNull(insurance);

        String trimmed = insurance.trim();
        trimmed = trimmed.replaceAll("\\s+", " ");

        if (!Insurance.isValidInsuranceName(trimmed)) {
            throw new ParseException(Insurance.MESSAGE_CONSTRAINTS);
        }

        return new Insurance(trimmed);
    }

    /**
     * Parses {@code Collection<String> insurances} into a {@code Set<Insurance>}.
     */
    public static Set<Insurance> parseInsurances(Collection<String> insurances) throws ParseException {
        requireNonNull(insurances);
        Set<Insurance> insuranceSet = new HashSet<>();
        for (String i : insurances) {
            insuranceSet.add(parseInsurance(i));
        }

        return insuranceSet;
    }

    /**
     * Parses a {@code String priority} into a {@code Priority}.
     * Leading and trailing whitespaces will be trimmed.
     * Input will be formatted to lower case to disable case-sensitivity.
     *
     * @throws ParseException if the given {@code priority} is invalid.
     */
    public static Priority parsePriority(String priority) throws ParseException {
        requireNonNull(priority);
        String trimmedPriority = priority.trim();
        String formattedPriority = trimmedPriority.toLowerCase(); // disable case-sensitivity
        if (!isValidPriority(formattedPriority)) {
            throw new ParseException((Priority.MESSAGE_CONSTRAINTS));
        }
        return new Priority(formattedPriority);
    }

    /**
     * Parses a {@code String remark} into a {@code remark}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code remark} is invalid.
     */
    public static Remark parseRemark(String remark) throws ParseException {
        requireNonNull(remark);
        String trimmedRemark = remark.trim();
        if (!Remark.isValidRemark(trimmedRemark)) {
            throw new ParseException((Remark.MESSAGE_CONSTRAINTS));
        }
        return new Remark(trimmedRemark);
    }

    /**
     * Parses a {@code String keyword} into corresponding {@code Predicate<Person>}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Predicate<Person> parseNameKeywords(String keyword) {
        requireNonNull(keyword);
        String trimmedKeywords = keyword.trim();
        String[] keywords = trimmedKeywords.split("\\s+");
        return new NameContainsKeywordsPredicate(Arrays.asList(keywords));
    }

    /**
     * Parses a {@code String keyword} into corresponding {@code Predicate<Person>}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Predicate<Person> parseAddressKeywords(String keyword) {
        requireNonNull(keyword);
        String trimmedKeywords = keyword.trim();
        String[] keywords = trimmedKeywords.split("\\s+");
        return new AddressContainsKeywordsPredicate(Arrays.asList(keywords));
    }

    /**
     * Parses a {@code String keyword} into corresponding {@code Predicate<Person>}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Predicate<Person> parseEmailKeywords(String keyword) {
        requireNonNull(keyword);
        String trimmedKeywords = keyword.trim();
        String[] keywords = trimmedKeywords.split("\\s+");
        return new EmailContainsKeywordsPredicate(Arrays.asList(keywords));
    }

    /**
     * Parses a {@code String keyword} into corresponding {@code Predicate<Person>}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Predicate<Person> parseInsuranceKeywords(String keyword) {
        requireNonNull(keyword);
        String trimmedKeywords = keyword.trim();
        String[] keywords = trimmedKeywords.split("\\s+");
        return new InsuranceContainsKeywordsPredicate(Arrays.asList(keywords));
    }

    /**
     * Parses a {@code String keyword} into corresponding {@code Predicate<Person>}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Predicate<Person> parsePhoneKeywords(String keyword) {
        requireNonNull(keyword);
        String trimmedKeywords = keyword.trim();
        String[] keywords = trimmedKeywords.split("\\s+");
        return new PhoneContainsKeywordsPredicate(Arrays.asList(keywords));
    }

    /**
     * Parses a {@code String keyword} into corresponding {@code Predicate<Person>}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Predicate<Person> parseTagKeywords(String keyword) {
        requireNonNull(keyword);
        String trimmedKeywords = keyword.trim();
        String[] keywords = trimmedKeywords.split("\\s+");
        return new TagContainsKeywordsPredicate(Arrays.asList(keywords));
    }

    /**
     * Parses a {@code String keyword} into corresponding {@code Predicate<Person>}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Predicate<Person> parseRemarkKeywords(String keyword) {
        requireNonNull(keyword);
        String trimmedKeywords = keyword.trim();
        String[] keywords = trimmedKeywords.split("\\s+");
        return new RemarkContainsKeywordsPredicate(Arrays.asList(keywords));
    }

    /**
     * Parses a {@code String keyword} into corresponding {@code Predicate<Person>}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Predicate<Person> parsePriorityKeywords(String keyword) {
        requireNonNull(keyword);
        String trimmedKeywords = keyword.trim();
        String[] keywords = trimmedKeywords.split("\\s+");
        return new PriorityContainsKeywordsPredicate(Arrays.asList(keywords));
    }

    /**
     * Checks if the retrieved date from user input is valid.
     *
     * A valid date input is of the format yyyy-mm-dd.
     * `mm` is a 2-digit number in the range 01-12, which represents a calendar month.
     * `dd` is a 2-digit number in the range of 01-31, depending on the number of days in the calendar month.
     *
     * If the retrieved date is valid, returns the date in `dd MMM yyyy` format.
     * Otherwise, it means that the user did not enter the correct input. A ParseException will be thrown.
     *
     * @param date Date String retrieved from user input
     * @return A String representing the date in the specified format if it is valid (for add/update)
     * @throws ParseException Thrown when the date retrieved is invalid
     */
    public static String parseDateString(String date) throws ParseException {
        try {
            checkDate(date);
            // converts the date to the specified format
            date = LocalDate.parse(date).format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
        } catch (DateTimeParseException dtpe) {
            throw new ParseException(Appointment.MESSAGE_CONSTRAINTS);
        }

        return date.trim();
    }

    /**
     * Checks if the date input has already passed.
     * If date input is in the past, date is invalid, throws an error.
     *
     * @param date Input date string.
     * @throws ParseException Date input has passed.
     */
    private static void checkDate(String date) throws ParseException {
        LocalDate localDate = LocalDate.parse(date);
        LocalDate now = LocalDate.now();

        if (localDate.isBefore(now)) {
            throw new ParseException(Appointment.PREVIOUS_DATE_INPUT);
        }
    }

    /**
     * Checks if the retrieved time from user input is valid.
     *
     * A valid time input is of the format hh:mm (in 24-hour format).
     * `hh` is a 2-digit number in the range 00-23, which represents the hour in the 24-hour format.
     * `mm` is a 2-digit number in the range of 00-59, which represents the minute in the 24-hour format.
     *
     * If the retrieved time is valid, returns the time in `HHmm` format.
     * Otherwise, it means that the user did not enter the correct input. A ParseException will be thrown.
     *
     * @param time Time String retrieved from user input
     * @return A String representing the time in the specified format if it is valid.
     * @throws ParseException Thrown when the date retrieved is invalid
     */
    public static String parseTimeString(String time) throws ParseException {
        String validationPattern = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$";

        if (!time.equals(Appointment.NO_TIME)) {
            // checks that time only contains HH:mm and nothing else
            if (!time.matches(validationPattern)) {
                throw new ParseException(Appointment.MESSAGE_CONSTRAINTS);
            }

            // converts the time to the specified format
            time = LocalTime.parse(time).format(DateTimeFormatter.ofPattern("HHmm"));
        }

        return time.trim();
    }
}
