---
layout: default.md
title: "User Guide"
pageNav: 3
---

# EzContact

EzContact is a

* desktop app made for insurance agents to manage customer details, optimized for usage via Command Line Interface (CLI)
* while still having the benefits of a Graphical User Interface (GUI).
* If you can type fast, EzContact can get your contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

2. Download the latest `ezcontact.jar` from [here](https://github.com/AY2324S1-CS2103T-W16-2/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for your EzContact.
4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar ezcontact.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)
5. Type the command in the command box and press Enter to execute it.
1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>


### Adding a customer: `add`

**Adds a new customer to EZContact**.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL [a/ADDRESS] [pr/PRIORITY] [t/TAG]...`

* Adds a new customer with the respective details to EzContact.

<box type="warning" seamless>

**Caution:**
* `NAME` should **not be longer than 64 characters**.
* `PHONE_NUMBER` should be an **8-digit number**(i.e. a Singapore number).
* `EMAIL` should be a **valid email address**(i.e. `local-part@domain`).
* `ADDRESS` should be **non-empty**.
* `PRIORITY` should be one of: `high`, `medium`, `low`, `-`
* `TAG` should be **alphanumeric** and **not longer than 20 characters**.
* A customer **must not have more than 10 tags** assigned to it.
* Adding a customer with a `NAME` that **already exists** in EzContact is **not allowed**.
</box>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe e/betsycrowe@example.com p/12345678`
* `add n/Ryan Ong p/64238876 e/ryanong@gmail.com t/tall t/skinny t/wears spectacles pr/medium`

### Listing all customers : `list`

Shows a list of all existing customers in EZContact and the size of the list.

Format: `list`

<box type="warning" seamless>

**Caution:** A list command does not take in parameters e.g. `list 1`, `list first`, `list all` will be invalid.

Examples:
* `list` shows a list of all existing customers and the size of the list.

</box>

### Deleting a customer : `delete`

Deletes the specified customer from EZContact.

Format: `delete INDEX`
* Deletes the customer at the specified `INDEX`.
* The index refers to the index number shown in the displayed customers list.
* The `INDEX` is 1-indexed

<box type="warning" seamless>

**Caution:** The index **must be a positive integer** 1, 2, 3, ...
</box>


Examples:
* `list` followed by `delete 2` deletes the 2nd customer in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st customer in the results of the `find` command.


### Editing a customer : `edit`

Edits an existing customer in EZContact.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS]`

* Edits the customer at the specified `INDEX`. The index refers to the index number shown in the displayed customer list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

<box type="warning" seamless>

**Caution:**
* `INDEX` should be a **positive integer** and **not exceed** the index of the last person in the displayed customer list.
* `NAME` should **not be longer than 64 characters**.
* `PHONE_NUMBER` should be an **8-digit number**(i.e. a Singapore number).
* `EMAIL` should be a **valid email address**(i.e. `local-part@domain`).
* `ADDRESS` should be **non-empty**.
* Tags are not editable.
* Priorities are not editable.

</box>

Examples:
*  `edit 1 p/91234567 e/jiufong@example.com` Edits the phone number and email address of the 1st customer to be `91234567` and `jiufong@example.com` respectively.
*  `edit 2 n/Betsy Crower` Edits the name of the 2nd customer to be `Betsy Crower`.

### Finding customers : `find`

Finds customers whose names contain all the given keywords as prefix.

Format: `find KEYWORD [MORE_KEYWORD]...`

* The search is case-insensitive. e.g. `find Adam` returns `adam`
* The order of the keywords does not matter. e.g.`find Adam Leong` returns `Leong Adam` or `Adam Chen Leong`
* Only the name information is searched.
* The search support partial search, only required keywords match name as a prefix. e.g. `find A` returns `Adam Leong` or `Andy Chong`
* Only the customers matching all the keywords will be returned e.g. </br>
`find Adam Leong` return `Adam Leong Keng Fat` but not `Adam Huat`</br>
`find A L` return `Adam Leong` but not `Adam Tan`

Examples:
* `find Adam H` Finds all the customers whose name contains `Adam` and  `H` as prefix
* `find Song` Finds all the customers whose name contains `Song` as prefix

### Tagging a customer: `tag`

**Updates the tags assigned to an existing customer**.

Format: `tag INDEX [at/TAG_TO_ADD]... [dt/TAG_TO_DELETE]...`

* Updates the tags assigned to the customer at `INDEX` in the displayed customer list.
* Duplicate tags to add/delete will be ignored by EzContact.
* Adding an existing tag or deleting a non-existing tag will be ignored by EzContact.

<box type="warning" seamless>

**Caution:**
* **At least one** `TAG_TO_ADD` or `TAG_TO_DELETE` should be provided.
* Adding and deleting the same tag is **not allowed**.
* `INDEX` should be a **positive integer** and **not exceed** the index of the last person in the displayed customer list.
* `TAG_TO_ADD` and `TAG_TO_DELETE` should be **alphanumeric** and **not longer than 20 characters**.
* The number of tags assigned to the customer after an update should **not exceed 10 tags**.

</box>

Examples:

`tag 1 at/tall dt/short`
* Adds `tall` tag to the first person.
* Deletes `short` tag from the first person(ignored if the first person does not have `short` tag originally).

`tag 2 at/tall at/wears spectacles dt/short at/tall`
* Adds `tall` and `wears spectacles` tag to the second person (duplicate `tall` is ignored).
* Deletes `short` tag from the second person.

### Updating priority of a customer: `pr`

**Updates the priority assigned to an existing customer**.

Format: `pr INDEX NEW_PRIORITY`

* Updates the priority of the customer at `INDEX` in the displayed customer list to `NEW_PRIORITY`.
* If the customer is not previously assigned any priority, `NEW_PRIORITY` is assigned to the customer.
* Letting `NEW_PRIORITY` to be  `-`  means removing the priority previously assigned the customer.
<box type="warning" seamless>

**Caution:**
* `INDEX` should be a **positive integer** and **not exceed** the index of the last person in the displayed customer list.
* `NEW_PRIORITY` should be **ONE** of `high`, `medium`, `low`, `-`.

</box>

Examples:

`pr 1 high` Updates the priority of the first customer to be `high`.

`pr 2 -` Removes the priority assigned to the second customer.


### Remark a customer: `remark`

**Remarks a custom with some texts**.

Format: `remark INDEX r/ [REMARK]`

* Updates the remark of the customer at `INDEX` in the displayed customer list.
* If you wish to delete the remark, update the remark without text after `r/`, e.g. `remark INDEX r/`.

<box type="warning" seamless>

**Caution:**
* `INDEX` should be a **positive integer** and **not exceed** the index of the last person in the displayed customer list.
* `Remark` cannot be longer than 150 characters.

</box>

Examples:

`remark 1 r/ he likes pizza` Updates the remark of the first customer in the displayed list to `he likes pizza`.

`remark 2 r/` Removes the remark from the second customer in the displayed list.

### Exiting the program : `exit`

Exits the program.

Format: `exit`



_More coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action       | Format and Examples                                                                                                                                                                               |
|--------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add**      | `add n/NAME p/PHONE_NUMBER e/EMAIL [a/ADDRESS] [t/TAG]... [r/REMAEK]`          <hr>           `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/tall r/some remarks` |
| **Delete**   | `delete INDEX`                                            <hr>       `delete 3`                                                                                                                   |
| **Edit**     | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] ` <hr> `edit 2 n/James Lee e/jameslee@example.com`                                                                                    |
| **List**     | `list`                                                                      <hr>                                                                                                                  |
| **Find**     | `find KEYWORD [MORE_KEYWORD]...`                     <hr>      `find Any Cho`                                                                                                                     |
| **Tag**      | `tag INDEX [at/TAG_TO_ADD]... [dt/TAG_TO_DELETE]...`     <hr>         `tag 1 at/tall dt/short at/male`                                                                                            |
| **Priority** | `pr INDEX NEW_PRIORITY`  <hr>  `pr 1 medium`                                                                                                                                                      |
| **Remark**   | `remark INDEX r/ [REMARK]` <hr>  `remark 2 r/some remarks`                                                                                                                                        |

