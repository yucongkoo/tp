---
layout: default.md
title: "User Guide"
pageNav: 3
---

# EzContact

EzContact is a

* Desktop app made for insurance agents to manage customer details, optimized for usage via Command Line Interface (CLI)
while still having the benefits of a Graphical User Interface (GUI).
* Contact management app that strives to get tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

2. Download the latest `ezcontact.jar` from [here](https://github.com/AY2324S1-CS2103T-W16-2/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for your EzContact.
4. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds.
Note how the app contains some sample data.<br>![Ui](images/Ui.png)
5. Type the command in the [Command Box](#ui-layout-description) and press Enter to execute it.
1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## UI Layout Description

Consider the following UI split into three parts:
![UiWithDescription](images/UiWithDescription.png)
As illustrated above, the UI will be split into three sections, we will be providing a name for each section and
will be using these names to refer to the section specified in the following User Guide.

**Command Box:** Box for users to input the command to be executed by EzContact.<br/>
**Result Display Box:** Box that displays the result of executing the entered command.<br/>
**Customer List Panel:** Panel that displays whole list of Customer Cards.<br/>
**Customer Card:** Card that displays information about each customer.<br/>

-----------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**Notes about the command format:**<br>

* Words in `<>` are the parameters to be supplied by the user.<br>
  e.g. in `add n/<name>`, `name` is a parameter which can be used as `add n/John Doe`.

* Items in `[]` are optional.<br>
  e.g `n/<name> [a/<address>]` can be used as `n/John Doe a/Kent Ridge` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/<tag>]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/<name> p/<phone number>`, `p/12341234 n/John` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command is `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>


### Adding a customer: `add`

**Format:**

`add n/<name> p/<phone number> e/<email> [a/<address>] [pr/<priority>] [t/<tag>]... [i/<insurance>]... [r/<remark>]`

**Description:**

* Adds a new customer with the respective details to EzContact.

<box type="warning" seamless>

**Caution:**
* `<name>` should be **alphanumeric**, **non-empty** and **not longer than 64 characters**.
* `<phone number>` should be an **8-digit number**(i.e. a Singapore number).
* `<email>` should be a **valid email address**(i.e. `local-part@domain`).
* `<address>` should **not be longer than 100 characters**.
* `<priority>` should **only be one of**: `high`, `medium`, `low`, `-`
* `<tag>` should be **alphanumeric**, **non-empty** and **not longer than 20 characters**.
* `<insurance>` should be **alphanumeric**, **non-empty** and **not longer than 32 characters**.
* `<remark>` should **not be longer than 150 characters**.
* A customer **must not have more than 10 tags** assigned to it.
* A customer **must not have more than 5 insurances** assigned to it.
* Adding a customer with a `<phone number>` or `<email>` that **already exists** in EzContact is **not allowed**.
</box>

**Examples:**

`add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 r/He is a coffee lover.`
* Adds the following [Customer Card](#ui-layout-description) to the [Customer List Panel](#ui-layout-description)
![AddEg1](images/add-command-examples/example1.png)

`add n/Ryan Ong p/64238876 e/ryanong@gmail.com t/tall t/skinny t/wears spectacles pr/medium i/car insurance`
* Adds the following [Customer Card](#ui-layout-description) to the [Customer List Panel](#ui-layout-description)
![AddEg2](images/add-command-examples/example2.png)

### Listing all customers : `list`

Shows a list of all existing customers in EZContact and the size of the list.

Format: `list`

<box type="warning" seamless>

**Caution:** A list command does not take in parameters e.g. `list 1`, `list first`, `list all` will be invalid.

Examples:
* `list` shows a list of all existing customers and the size of the list.

</box>

### Deleting a customer : `delete`

**Format:**

`delete <index>`

**Description:**
* Deletes the customer at the specified `<index>`.
* `<index>` refers to the index number shown in the displayed customer list.

<box type="warning" seamless>

**Caution:**
* `<index>` should only be one of the indices shown in the displayed list.
</box>

**Examples:**
* `list` followed by `delete 2` deletes the 2nd customer in the displayed list.
* `find Betsy` followed by `delete 1` deletes the 1st customer appeared in the list displayed by the `find` command.


### Editing a customer : `edit`

**Format:**

`edit <index> [n/<name>] [p/<phone number>] [e/<email>] [a/<address>]`

**Description:**
* Edits the customer at the specified `<index>`. 
* `<index>` refers to the index number shown in the displayed customer list.
* If values are provided for certain fields, existing values of their respective fields will be edited to the provided values.
* If no value is provided, the values of the fields remain unchanged.

<box type="warning" seamless>

**Caution:**
* **At least one** of the optional fields must be provided.
* `<index>` should **only be one of** the indices shown in the displayed list.
* `<name>` should be **alphanumeric**, **non-empty** and **not longer than 64 characters**.
* `<phone number>` should be an **8-digit number**(i.e. a Singapore number).
* `<email>` should be a **valid email address**(i.e. `local-part@domain`).
* `<address>` should **not be longer than 100 characters**.
* Tags are not editable.
* Priorities are not editable.
* Remarks are not editable.

</box>

**Examples:**
*  `edit 1 p/91234567 e/fong@example.com` edits the phone number and email address of the 1st customer to be `91234567` and `jiufong@example.com` respectively.
*  `edit 2 n/Betsy Crower` edits the name of the 2nd customer to be `Betsy Crower`.

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

**Format:**

`tag <index> [at/<tag to add>]... [dt/<tag to delete>]...`

**Description:**

* Updates the tags assigned to the customer at `<index>` in the displayed customer list.
* Duplicate tags to add/delete will be ignored by EzContact.
* Adding an existing tag or deleting a non-existing tag will be ignored by EzContact.

<box type="warning" seamless>

**Caution:**
* **At least one** `<tag to add>` or `<tag to delete>` should be provided.
* Adding and deleting the same tag is **not allowed**.
* `<index>` should be a **positive integer** and **not exceed** the index of the last person in the displayed customer list.
* `<tag to add>` and `<tag to delete>` should be **alphanumeric**, **non-empty** and **not longer than 20 characters**.
* The number of tags assigned to the customer after the update should **not exceed 10 tags**.
* The targeted customer's tags should **not remain unchanged** after the update command.

</box>

**Examples:**

`tag 3 at/ tall at/male dt/short dt/skinny`
* Adds `tall` and `male` tags, delete `short` and `skinny` tags from the third customer in the displayed customer list.

Before:
![TagEgBefore](images/tag-command-examples/before.png)

After:
![TagEgAfter](images/tag-command-examples/after.png)


### Updating priority of a customer: `priority`

**Format:**

`priority <index> <priority>`

**Description:**

* Updates the priority of the customer at the specified `<index>` in the displayed customer list to `<priority>`.
* If the customer has not been assigned any priority previously, `<priority>` is assigned directly to the customer.
* If `<priority>` is  `-` , the priority previously assigned the customer will be **removed**.
<box type="warning" seamless>

**Caution:**
* `<index>` should **only be one of** the indices shown in the displayed list.
* `<priority>` should **only be one of：** `high`, `medium`, `low`, `-`.

</box>

**Examples:**

`pr 1 high` updates the priority of the first customer to be `high`.

`pr 2 -` removes the priority assigned to the second customer.


### Adding a remark to a customer: `remark`

**You can add a remark to an existing customer, or update the current remark**.

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

### Clearing the customer list : `clear`

**Format:**

`clear`

**Description:**
* Clears the customer list.

<box type="warning" seamless>

**Caution:**
* No parameter is needed for this command, all parameter provided will be ignored.

</box>

**Examples:**

* `clear` clears the customer list in EzContact.
* `clear 123` will be interpreted as `clear`.

### Requesting for help : `help`

**Format:**

`help`

**Description:**
* Opens the help window.

<box type="warning" seamless>

**Caution:**
* No parameter is needed for this command, all parameter provided will be ignored.

</box>

**Examples:**

* `help` opens the help window.
* `help 123` will be interpreted as `help`.

### Exiting the program : `exit`

**Format:**

`exit`

**Description:**
* Exits the program.

<box type="warning" seamless>

**Caution:**
* No parameter is needed for this command, all parameter provided will be ignored.

</box>

**Examples:**

* `exit` exits EzContact.
* `exit 123` will be interpreted as `exit`.

----------------------------------------------------------------------------------------------------------------------
## Advanced Features

### Managing data

EzContact's data is stored as a json file at : `<JAR file directory>/data/addressbook.json`, where `<JAR file directory>`
is the directory you placed our `EzContact.jar` file.

**Saving data:**

EzContact's data is saved automaticaly whenever a command that changes the data is executed.

**Editing the data file:**

Advanced users are welcomed to update the data stored in EzContact directly by editing the `addressbook.json` file.

<box type="warning" seamless>

**Caution:**
If the format of the file becomes invalid after an edit, EzContact will launch with an empty data file, and will
overwrite the whole data file after a command that changes the data file is executed. Hence, users are **strongly
recommended to store a backup** of the data file before editing it.

</box>

**Storing/Restoring backup data files:**

Users can store a backup of the current data file by simply creating a copy of the `addressbook.json` file.
To restore a previously backed-up file, simply place the json file in the `<JAR file directory>/data` directory
and rename the file to `addressbook.json`.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action       | Format and Examples                                                                                                                                                                                                                              |
|--------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add**      | `add n/<name> p/<phone number> e/<email> [a/<address>] [pr/<priority>] [t/<tag>]... [i/<insurance>]... [r/<remark>]`          <hr>           `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/tall r/some remarks` |
| **Delete**   | `delete <index>`                                            <hr>       `delete 3`                                                                                                                                                                |
| **Edit**     | `edit <index> [n/<name>] [p/<phone number>] [e/<email>] [a/<address>] ` <hr> `edit 2 n/James Lee e/jameslee@example.com`                                                                                                                         |
| **List**     | `list`                                                                      <hr>                                                                                                                                                                 |
| **Find**     | `find KEYWORD [MORE_KEYWORD]...`                     <hr>      `find Any Cho`                                                                                                                                                                    |
| **Tag**      | `tag <index> [at/<tag to add>]... [dt/<tag to delete>]...`     <hr>         `tag 1 at/tall dt/short at/male`                                                                                                                                     |
| **Priority** | `pr <index> <priority>`  <hr>  `pr 1 medium`                                                                                                                                                                                                     |
| **Remark**   | `remark INDEX r/ [REMARK]` <hr>  `remark 2 r/some remarks`                                                                                                                                                                                       |
| **Clear**    | `clear`                                                                                                                                                                                                                                          |
| **Help**     | `help`                                                                                                                                                                                                                                           |
| **Exit**     | `exit`                                                                                                                                                                                                                                           |

-----------------------------------------------------------------------------------------------------------------------
## Glossary

|Term | Meaning                                                                                                                            |
| --------------|------------------------------------------------------------------------------------------------------------------------------------|
| Alphanumeric | Alphanumeric characters include uppercase letters from ‘A’ to ‘Z’, lowercase letters from ‘a’ to ‘z’, and numbers from ‘0` to ‘9’. |
| json file | Abbreviation for JavaScript Object Notation file, a file format that stores data in a human-readable form.                         |
