---
layout: default.md
title: "Developer Guide"
pageNav: 3
---

# EzContact Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

# **Acknowledgements**

* Libraries used: [JavaFX](https://openjfx.io/), [Jackson](https://github.com/FasterXML/jackson), [JUnit5](https://github.com/junit-team/junit5)

--------------------------------------------------------------------------------------------------------------------

# **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

# **Design**


### Architecture


<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

## UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

## Logic component <a id="logic-component"/>

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<div markdown="block" class="alert alert-info">

**Note:**<br/>The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

## Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


## Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

## Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

--------------------------------------------------------------------------------------------------------------------

# **Implementation**

This section describes some noteworthy details on how certain features are implemented.

## Tag feature

### Implementation

**Implementing `Tag`**

Before implementing the actual command execution of tag,
tags first needs to be stored in a `Person` object accordingly.
A `Person` is associated to any number of `Tag`s.

<puml src="diagrams/tag-feature/PersonClassDiagram.puml"/>

**Integrating a command for handling tag features into the execution logic**

In order to integrate the command for handling tag features into the execution logic as described in [LogicComponent](#logic-component),
we first update the `AddressBookParser` to recognise the `tag` [command word](#glossary) and will create a `TagCommandParser` subsequently.
The `TagCommandParser` will then parse the [command arguments](#glossary) to create a `TagCommand` that can be executed. 

The sequence diagram below illustrates the interactions within the `Logic` component when executing a tag command,
taking `execute("tag 1 at/tall dt/short at/handsome")` API call to `LogicManager` as an example.

<puml src="diagrams/tag-feature/TagSequenceDiagram.puml" />


**Implementing `TagCommandParser`**

`TagCommandParser` plays the role of parsing [command arguments](#glossary) into two information:<br/>
* `index` indicating the index of the targeted customer in the displayed customer list, and<br/>
* `descriptor` encapsulating tags to add to/delete from the targeted customer.<br/>

Both `index` and `updatePersonTagsDescriptor` will be used to create the `TagCommand` to be executed.<br/>
The parsing steps is as follows:
1. Parse the command arguments into `index`, `tagsToAdd` and `tagsToDelete`.
1. Create the `UpdatePersonTagsDescriptor` using `tagsToAdd` and `tagsToDelete`.
1. Verify that there is at least one tag to add/delete.
1. Construct and return the `TagCommand`.

Note that **duplicate tags will be ignored** (see [Design Considerations](#design-considerations) for more information).

The sequence diagram below illustrates the interactions of `TagCommandParser#parse(String arguments)`,
taking `parse(1 at/tall dt/short at/handsome)` call to the `TagCommandParser` as an example. Note that the 
**reference frames have been omitted** as the operations performed are trivial.

<puml src="diagrams/tag-feature/ParseSequenceDiagram.puml"/>


**Implementing `TagCommand`**

`TagCommand` plays the role of executing the tag command on a `Model`, it will update the `Model` accordingly to
reflect the changes after the tag command completes its execution.<br/>
The execution step is as follows:<br/>
1. Retrieve `personToUpdate` from the model using `index`.
1. Retrieve `tagsToAdd` and `tagsToDelete` from `updatePersonTagsDescriptor`.
1. Create the `updatedPerson` from the above information.
1. Verify that the `updatedPerson` has a different tag set from `personToUpdate` (i.e. the command execution will change the person).
1. Verify that the `updatedPerson` has a valid number of tags (i.e. not exceeding the maximum allowed tag count).
1. Set the `personToUpdate` to `updatedPerson` in the `Model`.
1. Returns the `CommandResult` of the execution.

The sequence diagram below illustrates the interations of `TagCommand#execute(Model model)`,
taking `execute(m)` call to the `TagCommand` as an example. Note that the **reference frames have been omitted**
as the operations performed are trivial.

<puml src="diagrams/tag-feature/ExecuteSequenceDiagram.puml" />

### Design considerations:

###### **Aspect: Data structure to store tags in a Person object:**

* **Alternative 1(current choice):** Using `Set<Tag>`.
  * Pros: Easy to implement, enforces implicit uniqueness of each `Tag` in the `Set<Tag>`.
  * Cons: Tags are not ordered according to the time it is added.
* **Alternative 2:** Using `List<Tag>`.
  * Pros: Tags are ordered according to the time it is added.
  * Cons: Hard to implement, handling of duplicate `Tag` is more complicated.

**Reasoning:**<br/>
Alternative 1 was chosen over alternative 2 as the ordering of tags is considered not that important in the case of
storing tags.

###### **Aspect: Duplicate tags handling:**

* **Alternative 1(current choice):** Allow users to add/delete duplicate tags as long as not conflicting(i.e. not adding and deleting the same tag).
  * Pros: Users will not be blocked from their action if they accidentally entered a duplicate tag.
  * Cons: Users might not be warned that they have entered a duplicate tag.
* **Alternative 2:** Block users from adding/deleting duplicate tags
  * Pros: Easy to implement
  * Cons: Users might be blocked from their action because they forgot that they already entered such a tag.

**Reasoning:**<br/>
Alternative 1 was chosen over alternative 2 based on the following reasons:
* Repeated action signals the user's strong intention of performing that action(e.g. wanting to add the same tag twice shows the importance of that tag).
* The target audience is forgetful and careless, it is common for the users to enter duplicate tags without realising it, blocking such actions brings no value to the product.


###### **Aspect: Deletion of non-existing tags:**
* **Alternative 1(current choice):** Simply ignore such deletions.
  * Pros: Users will not be blocked from their action(other tags will still be added/deleted) even though the command consists of such deletions.
  * Cons: Users will not be aware that the tag they are deleting from the customer does not exist, this may lead to certain misconceptions.
* **Alternative 2:** Block users from such deletions.
  * Pros: Easy to implement, users will be aware that the customer does not have the tag they are trying to delete.
  * Cons: User might be blocked from their action because they thought that the targeted customer does have the tag.

**Reasoning:**<br/>
Alternative 1 was selected over alternative 2 because the primary reason for users deleting a specific tag is that
they wish to prevent the tagged customer from having that tag. Therefore, whether the targeted customer
initially possesses the tag is of lesser importance in this context.

## Find feature
This find feature is designed to be a partial search or prefix search.

### Implementation

**Implementing `NameContainsKeywordsPredicate`**
This class determine how the find feature find the customers. It contains a test method to test such, the details as below.
* This find feature is a partial search, we said keyword match with the name when name contains keyword as a prefix.
* This test method return true when all the keywords match with the name.
* Using a stream to check if all the keywords match with the name.

**Implementing `FindCommand`**
This class execute the find command on `Model`, it will update the `Model` accordingly to
reflect the changes after the find command completes its execution.

### Design considerations:

**Aspect: Design of test method:**

* **Alternative 1(current choice):** Returns customers when their names match with all keywords as a prefix.
    * Pros: Easy to implement, more flexible for user to find customers.
    * Cons: Cannot differentiate `Lam Jiu` and `Jiu Lam`, a name can match with multiple keywords, i.e. name `song` match with keywords `song song`.
* **Alternative 2:** Returns customers when their names match with all keywords as a prefix in order.
    * Pros: Can easily and accurately find a specified customer.
    * Cons: Harder to implement, less flexible for user to find for customer.
* **Alternative 3:** Returns customers when their names match with all keywords as a prefix, where each name can only match with one keyword, i.e. name `song` cannot match with keywords `song song`.
    * Pros: More intuitive design.
    * Cons: Hard to implement, required a long algorithm to do so.

In this case, we use choose Alternative 1 over Alternative 2 because the find feature become less useful when we restrict
the name must match keywords in order. The consideration about our target user is a forgetful insurance agent affect our decision,
since our target audience might forget and input the name in wrong order sometimes.</br>

Alternative 1 over Alternative 3, although Alternative 3 seems like a more accurate version, but we do some research on most of the contact book like application.
We found that most of the find feature design do not restrict that each name can only match with one keyword.
In addition, Alternative 3 requires a more complicated algorithm.
Hence, we choose Alternative 1 over 3.

**Aspect: Implementation of test method:**

* **Alternative 1(current choice):** Convert keywords to `Stream` and using `allMatch`.
    * Pros: Easy to implement, code is clean.
    * Cons: Less flexible, ex: cannot check the name match the keywords in order.
* **Alternative 2:** Directly using the keywords as `List<String>` and using a for loop.
    * Pros: More flexible, can add more constraint on the test method.
    * Cons: Hard to implement, if we want to add many constraint, code is untidy.
Alternative 1 over Alternative 2, because we choose a slightly simpler design and do not need much flexibility on implementation.
Hence, we decided to choose an alternative which can keep our code clean and easy to implement.



## Insurance Feature
This feature allows user to assign / remove insurance package(s) to / from customers in EZContact.

### Implementation
The implementation of the Insurance feature consists of few parts, distributed across different components :


1. `Insurance` : stores the information about the insurance
1. `InsuranceCommand` : executes the action to assign/remove insurance
1. `InsuranceCommandParser` : parses the command to obtain required information

**Implementing `Insurance`**

`Insurance` plays the role of storing information about an insurance and to be displayed on the product, as a single unit. It holds one information, `insuranceName`.

**[Class diagram of `Insurance` and `Person`]**


**Implementing `InsuranceCommand`**

`InsuranceCommand` executes its command on the `Model`, it will update the model accordingly to reflect the changes made by the command on the `Model`

**[Sequence diagram of executing `InsuranceCommand`]**

**Implementing `InsuranceCommandParser`**

`InsuranceCommandParser` receives the remaining input after the command `ins`, and turns it into valid information needed by `InsuranceCommand`, which are
`Index` and `UpdatedPersonInsuranceDescriptor`.

`UpdatedPersonInsruanceDescriptor` holds the sets of insurances to add and delete.


**Integrating `InsuranceCommand` and `InsuranceCommandParser`**

In order to integrate them into current logic component, `AddressBookParser` has to be updated to recognise the command
`ins` and call `parse(String args)` from `InsuranceCommandParser`.

From here, `InsuranceCommandParser` will extract out the relevant information and create the corresponding `InsuranceCommand`.

**[Sequence Diagram from `AddressBookParser` -> `Model`]**


### Design Considerations:

**Aspect: Storing of `Insurance` in `Person`**

* **Alternative 1** (Current solution) : use `Set<Insurance>` to hold all `Insurance` instances in `Person` object
  * Pros: Able to handle duplicates gracefully with `Set<Insurance>`
  * Cons: Chronological order of `Insurance` inserted is not maintained
* **Alternative 2**: use `List<Insurance>` to hold all `Insurance` instances in `Person` object
  * Pros: Maintain the chronological order of `Insurance` inserted and sorting can be easily done on `Insurance` instances
  * Cons: Handling of duplicates is more complicated

Reasoning:

The handling of duplicates is a more important aspect to handle as compared to the keeping track of the order of `Insurance` instances inserted where lexicographical
order has more significance in our product. There are also easy workaround to perform sorting with `Set<Insurance>`.


## \[Proposed\] Appointment feature

### Implementation

The appointment feature supports 5 different type of command:

1. `add appointment`
2. `edit appointment`
3. `delete appointment`
4. `mark appointment`
5. `unmark appointment`

## Priority feature

### Implementation

The action of assigning a priority is mainly facilitated by three classes: `Priority`, `PriorityCommandParser` and `PriorityCommand`.

**The `Priority` class**

The class is used to represent different priority levels for each `Person`.
By default, each `Person` has a priority `Level` of `-` unless the user explicitly assign the `Priority` of another `Level`.

<puml src="diagrams/priority-feature/PriorityClassDiagram.puml"/>

**The `PriorityCommandParser` class**

The class is used to parse the arguments into two information: `index` and `priority`.
It will then return a `PriorityCommand` should the arguments are valid.

The sequence diagram below illustrates the interaction between `PriorityCommandParser` and `PriorityCommand` when `PriorityCommandParser#parse(String)` is invoked.

Taking `parse(1 high)`as an example.

<puml src="diagrams/priority-feature/PriorityCommandParserSequenceDiagram.puml"/>

**The `PriorityCommand` class**

The class is responsible in executing the task parsed by the `PriorityCommandParser`.
It will update the `Priority` of a `Person`.

### Design Consideration:

The `Level` enum class is chosen because our system only allows four priority level: `HIGH`, `MEDIUM`, `LOW` and `-`.
The reason of choosing `-` as the default priority level is to ease the process of distinguishing having priority and not having priority.



## \[Proposed\] Undo/redo feature

### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how the undo operation works:

<puml src="diagrams/UndoSequenceDiagram.puml" alt="UndoSequenceDiagram" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

## \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

# **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

# **Appendix: Requirements**

## Product scope

**Target user profile**:

Target user : Insurance agent

* needs to provide services / insurance plans to customer
* has a need to manage a significant number of customers
* needs to maintain interactions with his/her customers over a long time span
* often forgets details about his/her customers
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**:  manage customers' contact for existing/potential insurance contracts faster than GUI driven apps


## User stories

Priorities: High - `* * *`, Medium - `* *`, Low - `*`

| Priority | As a …​          | I want to …​                                                                      | So that I can…​                                                  |
|----------|------------------|-----------------------------------------------------------------------------------|------------------------------------------------------------------|
| `* * *`  | insurance agent  | to be able to add customers' contacts to EZContact                                | reach out to existing and potential customers easily             |
| `* * *`  | user             | to add new contacts to EZContact                                                  |                                                                  |
| `* * *`  | user             | update my contacts/information easily                                             |                                                                  |
| `* * *`  | insurance agent  | be able to assign priorities to each customer                                     | prioritise customers that have a higher chance on sealing a deal |
| `* * *`  | insurance agent  | view the type of insurance my customer currently holds                            | to check customers' profile                                      |
| `* * *`  | user             | be able to search for specific contacts                                           | quickly lookup a customer and get their contact                  |
| `* * *`  | user             | be able to delete contacts                                                        |                                                                  |
| `* * *`  | user             | to list out all my contacts                                                       | to see all the person in my list                                 |
| `* * *`  | user             | be able to see my total numbers of entries in EZContact                           | know how many contacts are in EZContact now                      |
| `* *`    | first time user  | be able to know commands in EZContact                                             | play around with the features and get used to the application    |
| `* *`    | fast typist      | have short commands                                                               | execute command faster                                           |
| `* *`    | forgetful person | apply tags to my contacts                                                         | identify the person quickly                                      |
| `* *`    | forgetful person | search for contacts using partial keyword                                         | find the contact without remembering their full name             |
| `* *`    | careless person  | be able to undo previous command                                                  | recover from unintentional commands                              |
| `* *`    | careless person  | be stopped from adding duplicate entries                                          | avoid adding redundant data                                      |
| `* *`    | careless person  | want the address book to suggest similar names when im searching for a person     | avoid typographical errors                                       |
| `* *`    | forgetful person | have the application remind me of important task associated with certain contacts |                                                                  |
| `* *`    | user             | search for a contact by its other particulars(not necessarily names)              | be more flexible when searching for contacts                     |
| `*`      | user             | import my data from external sources into EZContact                               | avoid copying my data manually                                   |
| `*`      | advanced user    | have multiple contact books                                                       | neatly organize my contacts based on contexts                    |
| `*`      | user             | be able to export my data                                                         | have a backup when data loss happens                             |
| `*`      | forgetful person | be able to add remarks to a certain contact                                       | be reminded of things to take note of when contacting a person   |




## Use cases

(For all use cases below, the **System** is the `EZContact` and the **Actor** is the `user`, unless specified otherwise)

#### Adding a customer

**Use Case: UC01 - add a customer**

**MSS:**
1. User enters the details of customer to be added.
2. System adds the customer.
3. System displays the details of customer added by user.</br>
   Use case ends.

**Extensions:**</br>
2a.  Details provided by user is incomplete or invalid.</br>
&emsp;2a1. System shows an error message to alert User.</br>
&emsp;&emsp;&emsp;Use case ends.

#### Filtering customers

**Use case: UC02 - filter customers**

**MSS:**
1. User chooses to filter customers.
2. User enters filter command and selectively adds one/multiple category parameters to filter the customers for.
3. System filters the customers list.
4. System displays the list of customers that meet the criteria.
   Use case ends.

**Extensions:**</br>
2b.  User doesn't select any categories to filter for.<br>
&emsp;2b1. System shows an error message to alert User about the invalid command.
&emsp;&emsp;&emsp;Use case ends.

3a.  None of the contacts meet the filter criteria.
&emsp;3a1. System shows an empty list with a warning message.
&emsp;&emsp;&emsp;Use case ends.### Searching for a person

#### Deleting a customer

**Use Case: UC03 - delete a customer**

**MSS:**
1. User lists out the customers.
2. System shows the list of customers to user.
3. User deletes the customer with the index number shown in the displayed list.
4. System displays the details of the removed customer.</br>
   Use case ends.

**Extensions:**</br>
4a. Invalid delete command or invalid index.</br>
&emsp;4a1. System shows an error message to alert User about the invalid command.</br>
&emsp;&emsp;&emsp;Use case ends.

#### Editing a customer

**Use Case: UC04 - edit a customer's details**

**MSS:**
1.  User requests to list out the customers.
2.  System shows the list of customers.
3.  User edits the details of customer with its respective index.
4.  System displays the details of the edited customer.</br>
    Use case ends.

**Extensions:**</br>
3a. User provides invalid index or information.</br>
&emsp;3a1. System shows an error message to alert User about the invalid command.</br>
&emsp;&emsp;&emsp;Use case ends.

#### Searching for a customer

**Use Case: UC05 - search for a customer**

**MSS:**

1.  User searches with a prompt.
2.  System shows a list of customers matching the prompt.
3.  User views the customers' information.</br>
    Use case ends.

**Extensions:**</br>
1a. User searches with an invalid prompt format</br>
&emsp;1a1. System shows an error message to User.</br>
&emsp;&emsp;&emsp;&nbsp;Use case ends.

2a. There is no customer that match the prompt.</br>
&emsp;2a1. System shows an empty list.</br>
&emsp;&emsp;&emsp;&nbsp;Use case ends.

#### Assigning priority to customer

**Use Case: UC06 - assign priority to a customer**

**MSS:**

1.  User requests to list out the customers.
2.  System lists out the customers.
3.  User assigns priority to the customer with its respective index.
4.  System displays the new priority of customer.</br>
    Use case ends.

**Extensions:**</br>
3a. User provides invalid index or information.</br>
&emsp;3a1. System shows an error message to alert User about the invalid command.</br>
&emsp;&emsp;&emsp;Use case ends.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2.  Should be able to hold up to 1000 customer without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  The user interface should be intuitive, easy to navigate and understand (i.e. concise and simple)
5.  The application should gracefully handle errors to prevent system crashes and data corruption.

*{More to be added}*

## Glossary

* **Mainstream OS**: Windows, Linux, Unix, OS-X
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **Command word:** The first word of a user command(e.g. `tag` is the command word of the command `tag 1 at/tall dt/short`)
* **Command arguments:** The remaining input of a user command(e.g. `1 at/tall dt/short` is the command arguments of the command `tag 1 at/tall dt/short`)

--------------------------------------------------------------------------------------------------------------------

# **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

## Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

## Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

## Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
