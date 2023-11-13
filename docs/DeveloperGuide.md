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
<div style="page-break-after: always;"></div>

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

<div style="page-break-after: always;"></div>

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="600" />

<div style="page-break-after: always;"></div>

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

<div style="page-break-after: always;"></div>

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

<div style="page-break-after: always;"></div>

## Logic component <a id="logic-component"/>

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

<div style="page-break-after: always;"></div>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:**<br/>The lifeline for `DeleteCommandParser` and `DeleteCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

<div style="page-break-after: always;"></div>

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

<div style="page-break-after: always;"></div>

## Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="700" />



The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div style="page-break-after: always;"></div>

## Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="600" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

## Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

# **Implementation**

This section describes some noteworthy details on how certain features are implemented.

## Tag feature

This feature allows users to assign tags to / remove tags from customers in EzContact, making customers more recognizable to the users.


###### **Overview**

The activity diagram below shows the action sequence of updating the tags of a customer. Note that definition of not
valid indexes/tags/tag set will be defined later in following sections.

<puml src="diagrams/tag-feature/ExecuteActivityDiagram.puml"/>

<div style="page-break-after: always;"></div>

###### **Implementing `Tag`**

Before implementing the actual command execution of tag,
tags first needs to be stored in a `Person` object accordingly.
Hence, a `Person` will now also be associated to any number of `Tag`s.

<puml src="diagrams/tag-feature/PersonClassDiagram.puml"/>

###### **Integrating a command for handling tag features into the overall execution logic**

In order to integrate the command for handling tag features into the execution logic as described in [LogicComponent](#logic-component),
there are 3 main steps we need to implement:
1. Modify `AddressBookParser` to recognise the **tag** _command word_ and will create a `TagCommandParser` subsequently.
(Modification required is trivial and hence not described in detail)
2. Implement a `TagCommandParser` class that will parse the _command arguments_ and construct a `TagCommand` accordingly.
3. Implement a `TagCommand` class that will handle the main execution logic of the tag features and return a `CommandResult` accordingly.

<div style="page-break-after: always;"></div>

The sequence diagram below illustrates the interactions within the `Logic` component when executing a tag command,
taking `execute("tag 1 at/tall dt/short at/handsome")` API call to `LogicManager` as an example.

<puml src="diagrams/tag-feature/TagSequenceDiagram.puml" />
<box type="info" seamless>

**Note:**<br/>The lifeline for `TagCommandParser` and `TagCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</box>

###### **Implementing `TagCommandParser`**

`TagCommandParser` plays the role of parsing _command arguments_ into two information:<br/>
* **index** indicating the index of the targeted customer in the displayed customer list, and<br/>
* **descriptor** encapsulating tags to add to and/or delete from the targeted customer.<br/>

Both **index** and **descriptor** will then be used to construct a `TagCommand`.<br/>

Note that **duplicate tags will be ignored** (see [Design Considerations](#design-considerations) for more information).

The sequence diagram below illustrates the interactions of `TagCommandParser#parse(String arguments)`,
taking `parse(1 at/tall dt/short at/handsome)` call to the `TagCommandParser` as an example.

<puml src="diagrams/tag-feature/ParseSequenceDiagram.puml"/>


###### **Implementing `TagCommand`**

The following class diagram illustrates how a `TagCommand` holds information required for its execution.

<puml src= "diagrams/tag-feature/TagCommandClassDiagram.puml" />

`TagCommand` plays the role of executing the main logic of the tag feature, it will:
* Use information encapsulated in it to create the updated `Person` object accordingly.
* Update the `Model` accordingly to reflect the changes.

Note that a `TagCommand` is **non-executable** if there are **conflicting tags** (i.e. there are common tags to add and delete).

The sequence diagram below illustrates the interations of `TagCommand#execute(Model model)`,
taking `execute(m)` call to the `TagCommand` as an example. Note that the **reference frames have been omitted**
as the operations performed are trivial.

<puml src="diagrams/tag-feature/ExecuteSequenceDiagram.puml" />

###### **Wrapping up the tag feature**

As reaching this point, we have completed the implementation of the tag feature.
The following section will discuss certain design considerations when implementing this feature.

<br/>
<br/>

### Design considerations:

###### **Aspect: Data structure to store tags in a Person object:**

* **Alternative 1 (Current choice)** : Using `Set<Tag>`.
  * Pros: Easy to implement, enforces implicit uniqueness of each `Tag` in the `Set<Tag>`.
  * Cons: Tags are not ordered according to the time it is added.
* **Alternative 2** : Using `List<Tag>`.
  * Pros: Tags are ordered according to the time it is added.
  * Cons: Hard to implement, handling of duplicate `Tag` is more complicated.

**Reasoning :**<br/>
Alternative 1 was chosen over alternative 2 as the ordering of tags is considered not that important in the case of
storing tags.

###### **Aspect: Duplicate tags handling:**

* **Alternative 1 (Current choice)** : Allow users to add/delete duplicate tags as long as not conflicting(i.e. not adding and deleting the same tag).
  * Pros: Users will not be blocked from their action if they accidentally entered a duplicate tag.
  * Cons: Users might not be warned that they have entered a duplicate tag.
* **Alternative 2** : Block users from adding/deleting duplicate tags
  * Pros: Easy to implement
  * Cons: Users might be blocked from their action because they forgot that they already entered such a tag.

**Reasoning :**<br/>
Alternative 1 was chosen over alternative 2 based on the following reasons:
* Repeated action signals the users' strong intention of performing that action(e.g. wanting to add the same tag twice shows the importance of that tag).
* The target audience is forgetful and careless, it is common for the users to enter duplicate tags without realising it, blocking such actions brings no value to the product.

###### **Aspect: Deletion of non-existing tags:**
* **Alternative 1(current choice):** Simply ignore such deletions.
  * Pros: Users will not be blocked from their action(other tags will still be added/deleted) even though the command consists of such deletions.
  * Cons: Users will not be aware that the tag they are deleting from the customer does not exist, this may lead to certain misconceptions.
* **Alternative 2** : Block users from such deletions.
  * Pros: Easy to implement, users will be aware that the customer does not have the tag they are trying to delete.
  * Cons: User might be blocked from their action because they thought that the targeted customer does have the tag.

**Reasoning:**<br/>
Alternative 1 was selected over alternative 2 because the primary reason for users deleting a specific tag is that
they wish to prevent the tagged customer from having that tag. Therefore, whether the targeted customer
initially possesses the tag is of lesser importance in this context.

###### **Aspect: Addition of existing tags:**
This aspect is similar to the above aspect regarding **Deletion of non-existing tags**, the current choice is to simply
ignore such additions due to the same reason stated above.

<br/>
<br/>
<br/>


## Find feature
This find feature is designed to do partial search or prefix search on the customer list.

### Implementation

Sequence diagram below shows the interactions between `Logic` components when executing `execute("find n/Song r/vegetarian")`

<puml src="diagrams/find-feature/FindSequence.puml" />

<div style="page-break-after: always;"></div>

###### **Implementing `XYZContainsKeywordsPredicate`**
`XYZContainsKeywordsPredicate` = `AddressContainsKeywordsPredicate`, `NameContainsKeywordsPredicate` etc

<puml src="diagrams/find-feature/PredicateClassDiagram.puml"/>

This class inherits from Predicate interface and determine how the `find` feature searches for the right customers.
It tests each customer in the list with given `keywords`(search prompt given by users) in the following way:

1. The `attribute(name/address/...)` will be tested over each `keyword` in the search prompt (e.g. "james bond" is broken down into "james" & "bond")
1. The `attribute(name/address/...)` will also be tested **word by word** for every `keyword` in the prompt on these criteria:

   - If `attribute` **fully matches** all the `keywords` _(e.g. "james bond" = "james bond")_, it returns true
   - If `attribute` **contains all** the `keywords` _(e.g. searches "james" in "james bond")_, it returns true
   - If the `keyword` is **prefix** of the `attribute` _(e.g. searches "ja" in "james bond)_, it returns true
   - else returns false

###### **Implementing `PersonContainsKeywordsPredicate`**

This class serves as the primary predicate for testing multiple conditions. It houses various predicates such as
`NameContainsKeywordsPredicate` to check if specific criteria are met.

<div style="page-break-after: always;"></div>

###### **Implementing `FindCommandParser`**
`FindCommandParser` processes the input following the 'find' command, parsing it into distinct predicates based on the provided prefixes.
These predicates are then combined to create a `PersonContainsKeywordsPredicate` which is used by `FincCommand`

<puml src="diagrams/find-feature/ParseFindCommandSequenceDiagram.puml"/>

<div style="page-break-after: always;"></div>

###### **Implementing `FindCommand`**
`FindCommand` is executed on the `Model`, it will update the `Model` accordingly to
reflect the changes after the `FindCommand` completes its execution.

<puml src="diagrams/find-feature/ExecuteFindCommandSequenceDiagram.puml"/>

<div style="page-break-after: always;"></div>

### Design considerations:

###### **Aspect: Overall design of predicate:**

* **Alternative 1 (Current choice)** : Each attribute has their corresponding `Predicate`, `PersonContainsKeywordsPredicate` is responsible for testing them."
  * Pros: Code base be more modular.
  * Cons: Need to create quite amount of class.
* **Alternative 2** : Create a single predicate `PersonContainsKeywordsPredicate` which contains different methods to test different attributes against keywords.
  * Pros: Easier to implement, and the code is more straightforward to understand.
  * Cons: Harder to maintain the code when extending the search to include new attributes, as modifications to this class are required.

**Reasoning :**

Due to the Open-Closed Principle, we have opted for Alternative 1 to maintain modularity in our codebase.

###### **Aspect: Implementation of `XYZContainsKeywordsPredicate` regarding prefix:**

* **Alternative 1** (Current choice): Return customer when all keywords can be found as prefix in customer's name in **arbitrary order**.
    * Pros: Easy to implement, provides more flexibility to users for finding their customers.
    * Cons: Cannot differentiate `Lam Jiu` from `Jiu Lam`, a name can also match with multiple keywords (i.e. name `song` match with keywords `song song`).
* **Alternative 2** : Return customer when all keywords can be found as prefix in customer's name **in order**.
    * Pros: Can easily and accurately find a specified customer.
    * Cons: Harder to implement, less flexibility for the users to find the customer.
* **Alternative 3** : Returns customer when all keywords can be found as prefix in customer's name, where each prefix corresponds to a different word in name, i.e. name `song` cannot match with keywords `song song`.
    * Pros: More intuitive design.
    * Cons: Harder to implement, required a long algorithm to do so.

<div style="page-break-after: always;"></div>

**Reasoning :**

In this case, we chose Alternative 1 over Alternative 2 because the find feature becomes less versatile when we enforce the rule that
the name must match keywords in order. The consideration about our target users being forgetful affects our decision,
where users might forget and input the name in the wrong order.</br>

We also chose Alternative 1 over Alternative 3, although Alternative 3 provides a more accurate result, after doing some
research on many contact book-like applications, we found that most applications do not enforce that each word of the name can only match with one keyword.
In addition, Alternative 3 requires a more complicated algorithm.

###### **Aspect: Implementation of test method:**

* **Alternative 1 (Current choice)** : Convert keywords to `Stream` and use `allMatch`.
    * Pros: Easy to implement, code is clean.
    * Cons: Less flexible, e.g. cannot check the name matches the keywords in order.
* **Alternative 2** : Directly using the keywords as `List<String>` and using a for loop.
    * Pros: More flexible, can add more constraint on the test method.
    * Cons: Harder to implement, given many constraint, code becomes untidy.

**Reasoning :**

Alternative 1 is chosen over Alternative 2, because we want a slightly simpler design that does not need as much flexibility.


<div style="page-break-after: always;"></div>

## Insurance Feature
This feature allows users to assign / remove insurance package(s) to / from customers in EzContact to help users keep track of customers' insurances.

The activity diagram below shows the action sequence of updating insurances of a customer.

<puml src="diagrams/insurance-feature/ExecuteInsuranceActivityDiagram.puml"/>

### Implementation
The implementation of the Insurance feature consists of few parts, distributed across different components :

1. `Insurance` : stores the information about the insurance
1. `InsuranceCommand` : executes the action to assign/remove insurance
1. `InsuranceCommandParser` : parses the command to obtain required information

<div style="page-break-after: always;"></div>

**Implementing `Insurance`**

`Insurance` plays the role of storing information about an insurance and to be displayed on the product, as a single unit. It holds only one information, `insuranceName`.

<puml src="diagrams/insurance-feature/PersonInsuranceClassDiagram.puml"/>

**Implementing `InsuranceCommand`**

`InsuranceCommand` executes its command on the `Model`, it will update the `Model` accordingly to reflect the changes made by the command on the `Model`.

Sequence diagram below shows the execution of `InsuranceCommand`.

<puml src="diagrams/insurance-feature/ExecuteInsuranceSequenceDiagram.puml" />

<div style="page-break-after: always;"></div>

**Implementing `InsuranceCommandParser`**

`InsuranceCommandParser` interprets the remaining input after the `insurance` command, and parses it into relevant information needed by `InsuranceCommand`, which are
`Index` and `UpdatedPersonInsuranceDescriptor`.

* `Index` indicates the customer on the list to perform action on.
* `UpdatedPersonInsruanceDescriptor` holds the sets of insurances to add(`insurancesToAdd`) and delete(`insurancesToDelete`).

Sequence diagram below shows the execution of `InsuranceCommandParser#parse(String arguments)` with input `(1 ai/car insurance\n di/health insurance)`

<puml src="diagrams/insurance-feature/ParseInsuranceSequenceDiagram.puml" />

<div style="page-break-after: always;"></div>

**Integrating `InsuranceCommand` and `InsuranceCommandParser`**

In order to integrate them into current logic component, `AddressBookParser` has to be updated to recognise the command
`insurance` and call `parse(arguments)` from `InsuranceCommandParser`.

From here, `InsuranceCommandParser` will extract out the relevant information and create the corresponding `InsuranceCommand` and the command will be executed by other `Logic` components.

Sequence diagram below shows the interactions between `Logic` components when executing `execute("insurance 1 \nai/AIA di/Great Eastern)`

<puml src="diagrams/insurance-feature/InsuranceSequence.puml" />

<div style="page-break-after: always;"></div>

### Design Considerations:

###### **Aspect: Storing of `Insurance` in `Person`**


* **Alternative 1 (Current choice)** : use `Set<Insurance>` to hold all `Insurance` instances in `Person` object
  * Pros: Able to handle duplicates gracefully and maintain the uniqueness of each insurance with `Set<Insurance>`
  * Cons: Chronological order of `Insurance` inserted is not maintained
* **Alternative 2**: use `List<Insurance>` to hold all `Insurance` instances in `Person` object
  * Pros: Maintain the chronological order of `Insurance` inserted and sorting can be easily done on `Insurance` instances
  * Cons: Cannot ensure the uniqueness of each insurance and unable to handle duplicates in an efficient manner

**Reasoning:**

Alternative 1 is chosen over Alternative 2 for its ability to handle the duplicates more efficiently. We believe that this ability is more important
than the ability to sort the list in a more effective manner, as there are other workarounds that can be almost as efficient using `Set<Insurances>`.


###### **Aspect: Handling duplicate `Insurnace` entries**

* **Alternative 1 (Current choice)** : Allows the users to add / delete duplicate `Insurance` as long as no conflict exists (i.e. adding and deleting the same `Insurance`)
  * Pros: Ease of use for the users, as the users are not blocked for entering a duplicate
  * Cons: Users might not be aware of themselves entering duplicate `Insurance`
* **Alternative 2** : Blocks the users from performing the action and warn them about the duplicate `Insurance`
  * Pros: Easy to implement
  * Cons: Users will be clearly aware of their mistakes in entering duplicate `Insurance`

**Reasoning:**

Alternative 1 is chosen over Alternative 2 because we believe doing so will provide users a smoother experience with our product.
The reasoning comes from the users' intention of inserting the `InsuranceCommand`, that is wanting to assign an `Insurance` to a customer, so with
entering duplicate `Insurance`, the users' goal is still achieved, thus we think that there is no need to purposely block the users
for such action. With our handling of duplicate `Insurance`, no duplicate values will be added into the model with duplicate `Insurance` entries, and thus
it will not cause any error.

<div style="page-break-after: always;"></div>

###### **Aspect: Deleting non-existing `Insurance`**

* **Alternative 1 (Current choice)** : Allows the users to delete non-existing `Insurance` as long as no conflict exists (i.e. adding and deleting the same `Insurance`)
    * Pros: Ease of use for the users, as the users are not blocked for deleting non-existing `Insurance`
    * Cons: Users might not be aware of their mistakes
* **Alternative 2** : Blocks the users from performing the action and warn them about the mistake
    * Pros: Easy to implement
    * Cons: Users will be clearly aware of their mistakes

**Reasoning:**

Alternative 1 is chosen over Alternative 2 because we believe doing so will provide users a smoother experience with our product.
The reasoning comes from the users' intention of deleting an `Insurance`, that is wanting to remove that `Insurance` from the customer, so removing
a non-existing `Insurance` does not defeat the purpose, thus we think that there is no need to purposely block the users
for such action.

<div style="page-break-after: always;"></div>

##  Appointment feature

### Implementation

The appointment feature supports 4 different types of commands:
1. `addappt`
2. `deleteappt`
3. `markappt`
4. `unmarkappt`

All 4 features extends from the `abstract` `Command` class, managing the details of an apppointment
with a customer by editing the details of the `Appointment` and `AppointmentCount` class.

**Implementing `Appointment`**

This class is used to represent the appointment that each `Person` has, containing data:
* `date` of the appointment in `dd-MMM-yyyy` format as a `String`
* `time` of the appointment in `HHmm` format as a `String`
* `venue` of the appointment as a `String` lesser than or equals to 30 characters
By default, each `Person` has an empty default appointment with an empty `Date`.

<puml src="diagrams/appointment-feature/AppointmentClassDiagram.puml"/>

<div style="page-break-after: always;"></div>

**Implementing `AppointmentCommand`**
`AppointmentCommand` executes its command on the Model, updating the Model accordingly to reflect the changes made by the command on the Model. Note that an `AppointmentCommand` is **non-executable** if the index is not in range or the person has an existing appointment.

The sequence diagram below illustrates the interactions of `AppointmentCommand#execute(Model model)`, taking `execute(m)` call to the `AppointmentCommand` as an example. Note that the **reference frames have been omitted** as the operations performed are trivial.

<puml src="diagrams/appointment-feature/ExecutedAppointmentSequenceDiagram.puml"/>

<div style="page-break-after: always;"></div>

**Implementing `AppointmentCommandParser`**

`AppointmentCommandParser` plays the role of parsing *command arguments* into two or more fields:

* **index** indicating the index of the targeted customer in the displayed customer list
* **date** the date of the appointment
* **time** the time of the appointment(optional)
* **venue** the venue of the appointment(optional)

Both **index** and **date** minimally, will then be used to construct an AppointmentCommand.

The sequence diagram below illustrates the interactions of `AppointmentCommandParser#parse(String arguments)`, taking `parse(1 d/2025-12-12 t/12:55 v/Clementi Mall)` call to the `AppointmentCommandParser` as an example.

<puml src="diagrams/appointment-feature/AppointmentParserSequenceDiagram.puml" />

<div style="page-break-after: always;"></div>

**Integrating `AppointmentCommand` and `AppointmentCommandParser`**

`AddressBookParser` recognises the command `addappt` and calls `parse(arguements)` from `AppointmentCommandParser`.

`AppointmentCommandParser` will extract out the relevant information and create the corresponding `AppointmentCommand`
which will be executed by other `Logic` components.

The sequence diagram below shows the interactions between `Logic` components when the user inputs the command
`addappt 1 d/2025-12-12 t/12:55 v/Clementi Mall`.

<puml src="diagrams/appointment-feature/AddedAppointmentSequenceDiagram.puml" />

**Implementing `Addappt`**

`AppointmentCommandParser::parse()` uses `ParserUtil::parseDateString()`, `ParserUtil::parseTimeString()`
to check if `date`, `time`, `venue` follows the required formatting and the new `Appointment` object created by `AppointmentCommandParser:parse()`.

`AppointmentCommand::execute()` checks if the current `Appointment` is an `empty` appointment and if `true`, executes the `AppointmentCommand`.

<br/>

**Implementing `Deleteappt`**

Using a similar logic flow as `addappt`, it creates a new `Appointment` object with empty `date`, `time` and `venue` to replace the existing `Appointment` object. The new `Appointment` object is created in `DeleteAppointmentCommandParser::parse()`.

`Deleteappt` prevents the deletion of an appointment if there is no existing appointment by checking if the current `Appointment` is different from the `empty` appointment and if `true`, executes the `DeleteAppointmentCommand`.

<div style="page-break-after: always;"></div>

**Implementing `AppointmentCount`**

This class contains the number of marked appointments with a customer, stored as `count`, the
number of completed appointments as an `int`.

<br/>

**Implementing `Mark Appointment`**

Using a similar logic flow as `addappt`, it checks if the current `Appointment` is different from the `empty` appointment and if `true`, `MarkAppointmentCommand::execute()` will use `AppointmentCount::incrementAppointmentCount()` to increase the count by 1.
The existing `Appointment` object will be replaced by a new empty `Appointment` object, created in `MarkAppointmentCommandParser::parse()`.

<br/>

**Implementing `Unmarkappt`**

Using a similar logic flow as `addappt`, it prevents the user from unmarking an appointment if there is an existing
appointment by checking if the current `Appointment` is the same as the `empty` appointment and if `true`,
`UnmarkAppointmentCommand::execute()` will use `AppointmentCount::DecrementAppointmentCount()` to decrease the count by 1.

<br/> <br/>

### Design Considerations:

###### **Aspect: Implementation of Appointment feature**

* **Alternative 1 (Current choice)** : Have the appointment features split into 4 sub-features.
  * Pros: Isolation of a single sub-feature to a specific command is more intuitive
  * Pros: Reduces coupling
  * Cons: More checks and testcases are needed
  * Cons: More classes added, resulting in a larger codebase
* **Alternative 2**: Combine all sub-features into one appointment command.
  * Pros: Reduce the number of commands in the application making lesser to manage
  * Cons: There will be less abstraction, more coupling and more bug-prone: The same command
    class and parser class will handle all the four different features


<div style="page-break-after: always;"></div>

## Priority feature

This feature allows users to assign priority to a `Person`.
The default priority of each `Person` is `NONE`, unless a priority is explicitly assigned to the `Person`.

The activity diagram below shows the sequence of actions when users assign a priority to a `Person`.

<puml src="diagrams/priority-feature/UpdatePriorityActivityDiagram.puml"/>

### Implementation

**The `Priority` class**

The class is used to store the priority level of a `Person`.
The priority level can only be one of the values in the `Level` enumeration.

Each `Person` now has an additional attribute called priority.
The `Person` class now has a reference to the `Priority` class.

<puml src="diagrams/priority-feature/PriorityClassDiagram.puml"/>

<br>

<div style="page-break-after: always;"></div>

**Adding a new command word `priority`**

To allow users to assign priorities, we added a new command word `priority`.
The sequence diagram shows a series of actions in EzContact when a user inputs the command `priority 1 high`.

<puml src="diagrams/priority-feature/PriorityFeatureSequenceDiagram.puml"/>

<br>

**The `PriorityCommandParser` class**

The class is used to parse the string provided.
It will return a `PriorityCommand` if the index and priority are valid.

The sequence diagram below illustrates the interaction between `PriorityCommandParser` and `PriorityCommand` when `PriorityCommandParser#parse(String)` is invoked.

Taking `parse("1 high")`as an example.

<puml src="diagrams/priority-feature/PriorityCommandParserSequenceDiagram.puml"/>

<div style="page-break-after: always;"></div>

The sequence diagram below illustrates how the index and priority are parsed.

<puml src="diagrams/priority-feature/ParseIndexAndArgumentsSequenceDiagram.puml"/>

<br>

**The `PriorityCommand` class**

The class diagram below shows the main attributes and methods involved when assigning a priority to a `Person`.

<puml src="diagrams/priority-feature/PriorityCommandClassDiagram.puml"/>

<br>

<div style="page-break-after: always;"></div>

The sequence diagram illustrates the execution of the `PriorityCommand` and how the `Person` is updated.

<puml src="diagrams/priority-feature/PriorityCommandSequenceDiagram.puml"/>

<br>

### Design Consideration:

###### **Aspect: `Person` without an explicitly assigned `Priority`.**
* **Alternative 1 (Current Choice):** Give each `Person` a default priority `NONE`.
  * Pros:
    * Enhances code readability.
    * Do not need to handle null cases which happens when `Person` has `null` priority.
  * Cons:
    * More complexity during testing, have to make sure that the default value does not affect the outcome of test cases.
* **Alternative 2:** Keep the priority as `null`.
  * Pros:
    * Do not need to worry about the effect of default values on test cases.
  * Cons:
    * More `null` cases to handle.

<div style="page-break-after: always;"></div>

###### **Aspect: Choices of priority levels.**
* **Alternative 1 (Current Choice):** Fix the choices of priority level, namely `HIGH`, `MEDIUM`, `LOW` and `NONE`. (`NONE` is chosen when user removes or does not assign a priority).
  * Pros:
    * Simplicity, users do not have to create an extensive list of options for priority levels.
    * Ease of implementation.
  * Cons:
    * Reduced flexibility, users are now confined to limited choices of priority levels.
* **Alternative 2:** Allow users to define their own priority levels.
  * Pros:
    * Flexibility, users can customise the product according to their needs.
  * Cons:
    * Hard to implement, need to handle dynamic or custom priority levels.

<div style="page-break-after: always;"></div>

## Remark feature

### Implementation

The action of assigning a remark is mainly facilitated by three classes: `Remark`, `RemarkCommandParser` and `RemarkCommand`.

**The `Remark` class**


This class represents a person's attribute, including a remark string with a maximum length of 150 characters.
Every person created has this attribute, with the default value being an empty string, signifying no remark.


**The `RemarkCommandParser` class**

The class is used to parse the arguments into two information: `index` and `remark` and
returns a `RemarkCommand` if the arguments are valid.

The sequence diagram below illustrates the interaction between `RemarkCommandParser`, `RemarkCommand` and `Remark`.

Taking `parse("2 he likes pizza")`as an example.

<puml src="diagrams/remark-feature/RemarkCommandParserSequenceDiagram.puml"/>

**The `RemarkCommand` class**

The class is responsible in executing the task parsed by the `RemarkCommandParser`.
It will update the `Remark` of a `Person` and generate a `CommandResult` for the output.
Below is the class diagram of the `RemarkCommand` class.

<puml src="diagrams/remark-feature/RemarkClass.puml"/>

### Design Consideration:

###### **Aspect: Delete `Remark`**

* **Alternative 1 (Current choice)** : Using `remark <index>` without argument.
  * Pros: User-friendly, no need to remember an extra command.
  * Cons: User cannot store empty string as a remark
* **Alternative 2** : Implement a separate delete remark command.
  * Pros: Distinguishes between adding and deleting remarks.
  * Cons: Requires users to remember an additional command.

**Reasoning:**

In real-life scenarios, storing empty strings as remark is unlikely, hence
alternative 1 is preferred due to its user-friendliness.

<div style="page-break-after: always;"></div>

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
* needs to remember customer's information
* busy
* needs to maintain interactions with his/her customers over a long time span
* often forgets details about his/her customers
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**:

Manage customers' contact for existing/potential insurance contracts faster than GUI driven apps,
alongside helping users increase the chance of sealing deals with customers.

<div style="page-break-after: always;"></div>

## User stories

Priorities: High - `* * *`, Medium - `* *`, Low - `*`

| Priority | As a …​          | I want to …​                                                                           | So that I can…​                                                                            |
|----------|------------------|----------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------|
| `* * *`  | user             | be able to add new contacts to EzContact                                               | keep track of my contacts using EzContact                                                  |
| `* * *`  | user             | be able to update my contacts' information easily                                      | easily maintain up-to-date information of my contacts                                      |
| `* * *`  | user             | be able to search for specific contacts using their names                              | quickly lookup a contact and get their information                                         |
| `* * *`  | user             | be able to delete contacts                                                             |                                                                                            |
| `* * *`  | user             | be able to list out my contacts in EzContact                                           | see all my saved contacts in one view                                                      |
| `* * *`  | insurance agent  | be able to add customers' contacts to EzContact                                        | reach out to existing and potential customers easily                                       |
| `* * *`  | insurance agent  | be able to assign priorities to each customer                                          | prioritise customers that have a higher chance on sealing a deal                           |
| `* * *`  | insurance agent  | be able to view the type of insurance my customer currently holds                      | check customers' insurance profile easily                                                  |
| `* * *`  | insurance agent  | be able to easily know customers subscribed under a specific insurance plan            | quickly know who to find when there are changes to a specific insurance plan               |
| `* * *`  | insurance agent  | be able to apply descriptive tags to my customers                                      | easily identify and remember my customers using these tag                                  |
| `* * *`  | insurance agent  | be able to add details of appointments with customers                                  | keep track of appointments with customers                                                  |
| `* * *`  | insurance agent  | be able to delete cancelled appointments with customers                                | prevent confusion when arranging my schedule                                               |     
| `* * *`  | insurance agent  | be able to mark completed appointments with customers                                  | keep track of appointments completed with the customer to guage their potential interest   |
| `* *`    | user             | be able to search for a contact using its other particulars(not necessarily names)     | be more flexible when searching for contacts                                               |
| `* *`    | user             | be able to see my total numbers of contact entries in EzContact                        | know how many contacts I have in EzContact                                                 |
| `* *`    | forgetful person | be able to search for contacts using partial names                                     | find a contact without having to remember their full name                                  |
| `* *`    | forgetful person | have EzContact remind me of important task associated with certain contacts            | prevent myself from forgetting important tasks                                             |
| `* *`    | forgetful person | be able to add remarks to a certain contact                                            | be reminded of things to take note of when contacting a person                             |
| `* *`    | careless person  | be able to undo previous command                                                       | recover from unintentional commands                                                        |
| `* *`    | careless person  | be stopped from adding duplicate entries                                               | avoid myself from adding redundant data                                                    |
| `* *`    | careless person  | be suggested by EzContact for similar names when I'm searching for a person            | avoid myself from typographical errors                                                     |
| `* *`    | first time user  | be able to know commands in EzContact                                                  | play around with the features and get used to the application                              |
| `* *`    | fast typist      | have short commands                                                                    | execute commands faster                                                                    |
| `*`      | user             | be able to import my data from external sources into EzContact                         | avoid myself from having to copy my data manually                                          |
| `*`      | user             | be able to export my data                                                              | have a backup of data in case of data loss                                                 |
| `*`      | advanced user    | have multiple contact books                                                            | neatly organize my contacts based on contexts                                              |

<div style="page-break-after: always;"></div>

## Use cases

(For all use cases below, the **System** is the `EzContact` and the **Actor** is the `user`, unless specified otherwise)

#### Adding a customer

**Use Case: UC01 - add a customer**

**MSS:**<br/>
&emsp;1. User provides the details of a customer to be added.</br>
&emsp;2. System displays the details of the customer added by user.</br>
&emsp;Use case ends.

**Extensions:**</br>
&emsp;1a.  Details provided by user is incomplete or invalid.</br>
&emsp;&emsp;1a1. System displays an error message to alert User.</br>
&emsp;&emsp;Use case ends.

&emsp;1b.  Customer to be added is already in the System.<br/>
&emsp;&emsp;1b1. System displays an error message to alert User.<br/>
&emsp;&emsp;Use case ends.


#### Filtering customers

**Use case: UC02 - filter customers**

**MSS:**</br>
&emsp;1. User chooses to filter customers.</br>
&emsp;2. User selectively adds one/multiple category parameters to filter the customers for.</br>
&emsp;3. System displays the list of customers that meet the criteria.</br>
&emsp;Use case ends.

**Extensions:**</br>
&emsp;2a.  User doesn't select any categories to filter for.</br>
&emsp;&emsp;2a1. System displays the entire list of customers.</br>
&emsp;&emsp;Use case ends.

&emsp;3a.  None of the contacts meet the filter criteria.</br>
&emsp;&emsp;3a1. System shows an empty list with a warning message.</br>
&emsp;&emsp;Use case ends.

<div style="page-break-after: always;"></div>

#### Deleting a customer

**Use Case: UC03 - delete a customer**

**MSS:**</br>
&emsp;1. User requests a list of customers by <u>filtering customers(UC02)</u>.<br/>
&emsp;2. User provides the index of customer to be deleted.</br>
&emsp;3. System displays the details of the removed customer.</br>
&emsp;Use case ends.

**Extensions:**</br>
&emsp;2a. User provides invalid index.</br>
&emsp;&emsp;2a1. System shows an error message to alert User.</br>
&emsp;&emsp;Use case ends.

#### Editing a customer

**Use Case: UC04 - edit a customer's details**

**MSS:**</br>
&emsp;1. User requests a list of customers by <u>filtering customers(UC02)</u>.<br/>
&emsp;2. User provides information to the customer with its respective index.</br>
&emsp;3. System displays the details of the edited customer.</br>
&emsp;Use case ends.

**Extensions:**</br>
&emsp;2a. User provides invalid index or information.</br>
&emsp;&emsp;2a1. System shows an error message to alert User.</br>
&emsp;&emsp;Use case ends.

<div style="page-break-after: always;"></div>

#### Searching for a customer

**Use Case: UC05 - search for a customer**

**MSS:**</br>

&emsp;1.  User searches with a prompt.</br>
&emsp;2.  System shows a list of customers matching the prompt.</br>
&emsp;3.  User views the customers' information.</br>
&emsp;Use case ends.

**Extensions:**</br>
&emsp;1a. User searches with an invalid prompt format</br>
&emsp;&emsp;1a1. System shows an error message to User.</br>
&emsp;&emsp;Use case ends.

&emsp;2a. There is no customer that match the prompt.</br>
&emsp;&emsp;2a1. System shows an empty list.</br>
&emsp;&emsp;Use case ends.

#### Assigning priority to customer

**Use Case: UC06 - assign priority to a customer**

**MSS:**</br>

&emsp;1. User requests a list of customers by <u>filtering customers(UC02)</u>.<br/>
&emsp;2.  User provides priority to the customer with its respective index.</br>
&emsp;3.  System displays the new priority of customer.</br>
&emsp;Use case ends.

**Extensions:**</br>
&emsp;2a. User provides invalid index or priority.</br>
&emsp;&emsp;2a1. System shows an error message to alert User about the invalid command.</br>
&emsp;&emsp;Use case ends.

<div style="page-break-after: always;"></div>

#### Assigning insurance to customer

**Use Case: UC07 - assign insurance to a customer**

**MSS:**</br>

&emsp;1.  User requests the list of customers by <u>filtering customers(UCO2)</u>.</br>
&emsp;2.  User assigns insurance to the customer with its respective index.</br>
&emsp;3.  System displays the new insurance of customer.</br>
&emsp;Use case ends.

**Extensions:**</br>
&emsp;2a. User provides invalid index or information.</br>
&emsp;&emsp;2a1. System shows an error message to alert User about the invalid command.</br>
&emsp;&emsp;Use case ends.

#### Removing insurance from customer

**Use Case: UC08 - remove insurance from a customer**

**MSS:**</br>

&emsp;1.  User requests the list of customers by <u>filtering customers(UCO2)</u>.</br>
&emsp;2.  User removes insurance from the customer with its respective index.</br>
&emsp;3.  System displays the new insurance of customer.</br>
&emsp;Use case ends.

**Extensions:**</br>
&emsp;2a. User provides invalid index or information.</br>
&emsp;&emsp;2a1. System shows an error message to alert User about the invalid command.</br>
&emsp;&emsp;Use case ends.

<div style="page-break-after: always;"></div>

#### Updating tags of a customer

**Use Case: UC09 - update tags of a customer**

**Mss:**<br/>
&emsp;1. User requests a list of customers by <u>filtering customers(UC02)</u>.<br/>
&emsp;2. User provides index of the targeted customer in the displayed list.<br/>
&emsp;3. User provides information of tags to add to and/or delete from the targeted customer.<br/>
&emsp;4. System displays the details of the updated customer to the User.<br/>
&emsp;Use case ends.<br/>

**Extensions:**<br/>
&emsp;1a. Requested list is empty.<br/>
&emsp;&emsp;Use case ends.

&emsp;2a. User provided invalid index.<br/>
&emsp;&emsp;2a1. System displays an error message to alert the User.<br/>
&emsp;&emsp;Use case ends.

&emsp;3a. User provided invalid information of tags.<br/>
&emsp;&emsp;3a1. Systems displays an error message to alert the User.<br/>
&emsp;&emsp;Use case ends.

&emsp;3b. User provided information of tags that will not update the targeted customer.<br/>
&emsp;&emsp;3b1. Systems displays an error message to alert the User.<br/>
&emsp;&emsp;Use case ends.


#### Updating remark of a customer

**Use Case: UC10 - update remark of a customer**

**Mss:**<br/>
&emsp;1. User requests a list of customers by <u>filtering customers(UC02)</u>.<br/>
&emsp;2. User enters index and remark of the target customer.<br/>
&emsp;3. System updates the remark of specified customer accordingly.<br/>
&emsp;4. System displays the details of the updated customer.<br/>
&emsp;Use case ends.<br/>

**Extensions:**<br/>
&emsp;2a. User provided invalid index or information.<br/>
&emsp;&emsp;2a1. System displays an error message to alert the User.<br/>
&emsp;&emsp;Use case ends.

&emsp;2b. User provided remark that will not update the specified customer.<br/>
&emsp;&emsp;2b1. Systems displays an error message to alert the User.<br/>
&emsp;&emsp;Use case ends.<br/>

<div style="page-break-after: always;"></div>

#### Updating appointment of a customer

**Use Case: UC11 - add an appointment to a customer**

**Mss:**<br/>
&emsp;1. User requests a list of customers (UC02)</u>.<br/>
&emsp;2. User enters index and appointment details(date, time, venue) of the target customer.<br/>
&emsp;3. System adds the appointment to the specified customer accordingly.<br/>
&emsp;4. System displays the updated appointment details of the customer.<br/>
&emsp;Use case ends.

**Extensions:**<br/>
&emsp;2a. User provided invalid index.<br/>
&emsp;&emsp;2a1. System displays an error message to alert the User.<br/>
&emsp;&emsp;Use case ends.

&emsp;2b. User provided invalid appointment input parameters.<br/>
&emsp;&emsp;2b1. System shows an error message of the input constraints.<br/>
&emsp;&emsp;Use case ends.

&emsp;2c. An appointment has already been scheduled.<br/>
&emsp;&emsp;2c1. System shows an error message to alert the User.<br/>
&emsp;&emsp;Use case ends.

#### Updating appointment of a customer

**Use Case: UC12 - delete a customer's appointment**

**Mss:**<br/>
&emsp;1. User requests a list of customers (UC02)</u>.<br/>
&emsp;2. User enters index of the target customer.<br/>
&emsp;3. System deletes the appointment of the specified customer accordingly.<br/>
&emsp;4. System displays the updated empty appointment details of the customer.<br/>
&emsp;Use case ends.<br/>

**Extensions:**<br/>
&emsp;2a. User provided invalid index.<br/>
&emsp;&emsp;2a1. System displays an error message to alert the User.<br/>
&emsp;&emsp;Use case ends.

&emsp;2b. There is no existing appointment to delete.<br/>
&emsp;&emsp;2b1. System shows an error message to alert the User.<br/>
&emsp;&emsp;Use case ends.

<div style="page-break-after: always;"></div>

**Use Case: UC13 - mark a customer's appointment**

**Mss:**<br/>
&emsp;1. User requests a list of customers (UC02)</u>.<br/>
&emsp;2. User enters index of the target customer.<br/>
&emsp;3. System marks the appointment of the specified customer accordingly.<br/>
&emsp;4. System displays the updated appointment details of the customer.<br/>
&emsp;Use case ends.<br/>

**Extensions:**<br/>
&emsp;2a. User provided invalid index.<br/>
&emsp;&emsp;2a1. System displays an error message to alert the User.<br/>
&emsp;&emsp;Use case ends.

&emsp;2b. There is no existing appointment to mark.<br/>
&emsp;&emsp;2c1. System shows an error message to alert the User.<br/>
&emsp;&emsp;Use case ends.=

**Use Case: UC14 - unmark a customer's appointment**

**Mss:**<br/>
&emsp;1. User requests a list of customers (UC02)</u>.<br/>
&emsp;2. User enters index of the target customer.<br/>
&emsp;3. System unmarks the appointment of the specified customer accordingly.<br/>
&emsp;4. System displays the updated appointment details of the customer.<br/>
&emsp;Use case ends.<br/>

**Extensions:**<br/>
&emsp;2a. User provided invalid index.<br/>
&emsp;&emsp;2a1. System displays an error message to alert the User.<br/>
&emsp;&emsp;Use case ends.

&emsp;2b. There is an existing appointment.<br/>
&emsp;&emsp;2b1. System shows an error message to alert the User.<br/>
&emsp;&emsp;Use case ends.


<div style="page-break-after: always;"></div>

## Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2.  Should be able to hold up to 1000 customer without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  The user interface should be intuitive, easy to navigate and understand (i.e. concise and simple)
5.  The application should gracefully handle errors to prevent system crashes and data corruption.
6. The application should be offered as a free service to the public.
7. The application should be able to respond within one second.
8. The application should be able to handle and support manual edits to the data file, erroneous data files should not crash the application.

## Glossary

* **Mainstream OS**: Windows, Linux, Unix, OS-X
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **Command word:** The first word of a user command(e.g. `tag` is the command word of the command `tag 1 at/tall dt/short`)
* **Command arguments:** The remaining input of a user command(e.g. `1 at/tall dt/short` is the command arguments of the command `tag 1 at/tall dt/short`)

<div style="page-break-after: always;"></div>

--------------------------------------------------------------------------------------------------------------------
# **Appendix: Planned Enhancements**

This section covers the enhancements we plan to implement in the future.

#### Enhancement 1 : Deletion of all tags in a single command

**Feature flaw:** <br/>
As a customer might have many tags, and they could potentially want to remove all the
tags in one command, they would have to type out all the tags separately in order to achieve that.

**Proposed enhancement:**<br/>
We provide a convenient way for users to delete all the tags in one command by adding an optional parameter
to the command. The updated command format would be as follows: <br/>
`tag <index> [at/<tags to add>]... [dt/<tags to add>]... [dat/deleteall]`.

Justifications:
* As deleting all the tags is a destructive action, we require users to specify the `dat/` prefix to indicate
their interest in deleting all tags, and `deleteall` value to the prefix to serve as a confirmation of this
destructive command.

Updated behaviours (original behaviours of tag still hold):
* When a `dat/` prefix is supplied, there should not be any `at/` or `dt/` prefix supplied in the same command, if there
is, a format error message will be shown to the user.
* If the value provided to parameter `dat/` is not `deleteall`, show an error message to users, indicating that
they should supply the `deleteall` value to `dat/` in order to confirm the deletion.

**Examples:**<br/>
* `tag 1 dat/deleteall`<br/>
Expected: All the tags of customer at index 1 is deleted, a `successfully deleted all tags` message is shown to user.

* `tag 1 at/tall dat/deleteall`<br/>
Expected: Error, an error message showing the usage of tag command is shown to the user.

* `tag 1 dat/delete`<br/>
Expected: Error, an error message informing the user that they should input `deleteall` to confirm the deletion of all tags
is shown to the user.

<div style="page-break-after: always;"></div>

#### Enhancement 2: Edit appointment details

**Feature flaw:** <br/>
Users will not be able to easily update or modify appointment details if there are any changes or mistakes. If the appointment meeting location changes, or the scheduled time needs adjustment—without the ability to edit, users would have to delete and create a new appointment, potentially leading to confusion and decreased efficiency.

**Proposed enhancement:**<br/>
We provide a convenient way for users to edit the appointment details, date, time and venue, in a edit appointment command.The updated command format would be as follows: <br/>
`editappt <index> [d/<date>] [t/time] [v/venue]`

Justifications:
* As we need the details of the new appointment to be changed, at least one of the optional fields must be present.

Updated behaviours (original behaviours of appointment still hold):
* If the proposed appointment details entered in `editappt` are the same as the current appointment, there will be a error message to the user that there is no change.

**Examples:**<br/>
* `editappt 1 d/2026-12-16`<br/>
Expected: Edits the date of the first customer's appointment in the displayed list to be 16 Dec 2026, if it is different at first.

* `editappt 1`<br/>
Expected: Error, an error message informing the user to provide at least 1 appointment detail field to be changed.

<div style="page-break-after: always;"></div>

#### Enhancement 3: Unmark appointment recovers appointment details

**Feature flaw:** <br/>
After marking an appointment, the appointment details gets removed. However, after unmarking the appointment, the appointment details do not come back. This might cause the user to need to manually create the appointment meeting again, which can be a hassle, decreasing efficiency.


**Proposed enhancement:**<br/>
The unmark appointment `unmarkappt` will not only decrement the appointments completed counter by 1, but also restore the "marked" appointment details.

Updated behaviours (original behaviours of appointment still hold):
* Can be undone by marking the appointment again.

**Examples:**<br/>
* `unmarkappt 1`<br/>
Expected: Decrements the customer's appointment completed counter by 1, and restores the customer's appointment details to the previous marked appointment details.

#### Enhancement 4: Inserting `clear` pops out a confirmation window

**Feature flaw:** <br/>
After the user inputs the `clear` command, the customer list is cleared immediately. In some situations where the user just type `clear` in accident, the consequence is undesirable.

**Proposed enhancement:**<br/>
Pop a confirmation window for users to confirm once again if the user indeed wants to clear the customer list.

<div style="page-break-after: always;"></div>

#### Enhancement 5: find multiple tags and insurances
**Feature flaw:** <br/>
The current implementation employs a single prefix for multiple keywords in the find feature, such as `find i/Health Auto.`
This approach, however, lacks the ability to distinguish between distinct sets of keywords, leading to potential ambiguity.
For instance, it becomes challenging to differentiate whether the keywords correspond to a combination like `Health Auto` or separate entities like `Health Insurance` and `Auto Coverage`.

**Proposed enhancement:**<br/>
To address this limitation, it is recommended to enable the use of multiple identical prefixes for individual keywords. For instance, the enhanced syntax could be `find i/Health i/Auto`.
This modification allows the find feature to accommodate duplicate prefixes for both find and tag operations, thereby providing a more precise and unambiguous search capability.

**Justifications:**<br/>
* The problem we've spotted isn't just about insurance searches; it also affects tag searches.
* This problem only arises with tags and insurances since these are only two attribute allowed multiple instances.

**Examples:**<br/>
* `find i/abc i/apple`<br/>
  Expected: Identifies customers with two insurance entities whose names match the keywords `abc` and `apple` respectively.
  For instance, if there are customer with insurances named `abc insurance` and `apple insurance`, they would be included in the results.
* `find i/abc apple` <br/>
  Expected: Locates customers with an insurance entity whose name corresponds to the combined keyword `abc apple`, such as `abc apple insurance`.
  
The enhanced feature ensures accurate and targeted search results.

#### Enhancement 6: Increase flexibility of value input for phone number

**Feature flaw:** <br/>
In `add` and `edit` command, when entering `<phone number>` under `p/`, it takes in the 8 digits (Singaporean number) with no spaces or `-` (e.g. `12345678`).
However, it does not allow other common formats for Singaporean number that includes space and `-` (e.g. `1234-5678`, `1234 5678`), which can be a hassle to
users for not being allowed to so.

**Proposed enhancement:**<br/>
Allow `<phone number>` with format of `1234 5678` and `1234-5678`

**Updated behaviours** (original behaviour of `/p` still holds):
* `<phone number>` can now take the format of `1234-5678` and `1234 5678` and display the information in EzContact

**Examples:**<br/>
* `edit 1 p/1234-5678`
  Expected: Update the `<phone number>` of customer at index 1 to `1234-5678`
* `edit 1 p/1234 5678`
    Expected: Update the `<phone number>` of customer at index 1 to `1234 5678`

<div style="page-break-after: always;"></div>

#### Enhancement 7: Improve criteria for duplicate customer

**Feature flaw:** <br/>
Currently, duplicated customer is defined as customers that have either identical `<email>` or `<phone number>`, given that each customer
should have their respective contact details. However, this does not take into consideration that some customers might not have email (e.g. elderly)
and would have others to handle their incoming emails.

**Proposed enhancement:**<br/>
Modify the implementation on checking for duplicate customer such that it accepts identical `<email>` exist in EzContact, and update the
error message to `This phone number already exists in the contact book` instead of the original duplicate customer message to give users
more accurate feedback on what went wrong.

**Justification:**<br/>
The purpose of EzContact is to help our users (i.e. insurance agent) to manage contacts of customers that he needs to approach / interact with
thus the most important value our product has to offer, is to keep track of and contact customers effectively. Thus, we need to make sure that
each customer is contactable, leading to us enforcing the uniqueness of `<phone number>`. `<email>` is allowed to have duplicates because it is
possible and common for people to share `<email>`(especially elderly who have no email). `<name>` is also allowed because it is common to have
identical name. These restrictions give the users maximum flexibility and functionality while still ensuring that each customer is contactable.

**Updated behaviours**:<br/>
*  Adding customers with existing `<phone number>` are not allowed, error will be thrown indicating that the `<phone number>` already exists
*  Adding customers where values of their fields (except `p/`) already exist in the contact book are allowed.

**Examples:**<br/>
* `add n/joshua p/12345678 e/abc@gmail.com`, `add n/james p/78945612 /abc@gmail.com`
  Expected : Add `james` successfully into the contact book with no error
* `add n/joshua p/12345678 e/abc@gmail.com`, `add n/james p/12345678 /defg@gmail.com`
    Expected : Error message is thrown indicating that the `12345678` already exist in the contact book

<div style="page-break-after: always;"></div>

#### Enhancement 8: Delete all insurances with one command

**Feature flaw:** <br/>
With current implementation, when a customer has multiple insurances assigned to he/she, deleting all insurances requires the user to list out
all the insurances with `di/` prefix in our `insurance` command, which is inconvenient

**Proposed enhancement:**<br/>
Add a new optional prefix `dai/` and parameter `deleteall` for `insurance` command to indicate deleting all insurances,
the new command takes the format of <br/>
`insurance <index> [ai/<insurance to add>]... [di/<insurance to delete>]... [dai/deleteall]`

**Justification:**<br/>
Given that `dai/` will remove all the insurances of the customer at once, we require users to do a confirmation by specifying `deleteall`
for `dai/` to ensure that the user execute this command intentionally.

**Updated behaviours** (Original behaviour of `insurance` still holds) :
* When `dai/` is supplied, `ai/` and `di/` are not allowed. Format error will appear if either prefixes are used
* When `dai/` is supplied, if the value supplied to it is not `deleteall`, an error message will be thrown, indicating that `deleteall`
has to be supplied to confirm the deletion

**Examples:**<br/>
* `insurance 2 dai/deleteall`
  Expected : All insurances of customer at index 2 are removed
* `insurance 2 dai/deleteall ai/AIA di/cars`
  Expected : Error message is thrown, showing the usage of insurance
* `insurance 2 dai/dvsdv`
  Expected : Error message is thrown, telling users that `deleteall` has to be supplied to confirm deletion.


--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

# **Appendix: Effort**

This section gives an overview of the challenges, we as a team faced and the effort we have put in to make this project work.

####  Enhanced Logic Component  `* * *`

With the implementation of various new features in EzContact, the `logic` component who’s responsible for the parsing and
handling of our `commands` have to go through various modifications and enhancements to fulfill the needs of these features.
The new `logic` component needs to be more flexible regarding its constraint on the user input and also need to accommodate
the newly defined `commands` by us for the new features

**Effort:**

The enhancement can be broken down into a few parts:

* `ArgumentTokenizer`
* `CommandParser` & `AddressBookParser`
* `Command`


1. Implementation and logics of `ArgumentTokenizer` has to be understood thoroughly before putting our hands on the codebase.
Along the way, we have had multiple discussions on the choice of prefixes and restriction to put on the prefix input. Upon finalizing
the prefixes and constraints, work is split among members and each take up some part to work on.
2. For `CommandParser` and `AddressBookParser`, we each implement the parser associated with the features we are implementing. We also
integrate our parsers with `AddressBookParser` as it parses the command word and determines the `CommandParser` to use for remaining of
the command.
3. `Commands` are also implemented separately according to the features we are assigned to implement. We ensure that our implementation
does not break the Liskov Substitution Principle since all `commands` we implemented inherited the  `Commmand` class. We also ensure that
our implementation integrates well with each other and does not break others' functionality.
<br/>
All changes were done in small increments, in addition with testing using the newly written test cases. After finishing respective parts,
we also perform cross-checking on each other's implementation to ensure no bugs or flaws exist in our product. Some changes were made after
the rigorous testing as we found some feature flaws that can be further improved.

**Result :**

Our new `logic` component now accommodates multiple new features that drastically improve users experience and previous features are also
refined to provide users more flexibility and functionality.


####  Enhanced UI `* *`

The `UI` first has to be redesigned from the perspective of the purpose it serves. We have to ensure that all usages have to be accounted
in the new `UI` to ensure that users will always have a clear view of what's going on.

**Effort:**

We first redesign the structure of our `UI` to fit the new features in a sensible and user-friendly way. We tried out different layouts
and ways to present our data, and finalize on the current design, where the customer's information are displayed as `Customer Card`. After ensuring
the functionality is covered by `UI`, we move on to changing the colour scheme of our product. After many trial and error, and requesting feedbacks
from our friends, we have decided to use the current pastel green colour scheme.

**Result :**

A newly designed `UI` for EzContact and improved UI component with increased usability.


####  Enhanced Storage `* * *`

Given the newly introduced `attributes` in EzContact, the `storage` component is required to accommodate these new data when creating
the save file.

**Effort:**

Given that our saving system is implemented using `Jackson (JSON package)`, we first have to understand how the package works and how
it is integrated into our system. For each new `attribute`, we have created the corresponding `JSON-friendly data class` to handle these
data during saving. These classes handle the conversion between application-used and JSON-compatible data during loading and saving.
These classes are integrated to the existing `storage` component carefully to ensure that we do not break the existing system. The original
`storage` component is also refined and fixed of all discovered bugs.

**Result :**

A refined `storage` component that is able to handle all new `attributes` safely and correctly.


####  Enhanced Model `* *`

To accommodate the new features and `attributes`, the `model` component now has to handle these new `commands` and process these new `attributes`.

**Effort:**

New classes are created to represent and abstract these data to a higher level for easy manipulation. These classes are then integrated into
the `Person` class as it is the over-arching class represents the customer. New methods are also added to `model` component to perform the execution
specified by the different `commands`. We are implementing the respective classes corresponding to the features we are responsible for. The implementations
are done in small increment and are tested along the way.

**Result :**

A comprehensive model that holds and operates on our customer data according to the command given by logic component.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

# **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

## Feature to show

**Scenario**

Prerequisite : [condition needed to be fulfilled to perform the action if applicable]

1. Test case : `value` <br/>
   Expected : `result`
1. ...

_{ more test cases …​ }_

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

## Launch and shutdown

**Initial launch**

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file  <br/>
      Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

**Saving window preferences**

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.


## Deleting a person

**Deleting a person while all persons are being shown**

Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.


## Updating tags of a customer

**Updating the tags of a specific customer**

Prerequisite : -

1. Test case : `tag 1 at/tall at/fat dt/short dt/skinny` <br/>
   Expected : The tags assigned to the customer at index 1 will be updated accordingly(adds `tall` and `fat` tag, deletes `short` and `skinny` tag).

1. Test case : `tag 0 at/tall`<br/>
   Expected : Error, details shown in the status message(format error since the index is not a positive integer).

1. Test case : `tag 1`<br/>
   Expected : Error, details shown in the status message(format error since no tag to update is provided).

1. Test case: `tag 1 at/tall dt/tall`<br/>
   Expected : Error, details shown in the status message(conflicting tags).

1. Test case: `tag 1 dt/dsajdkl`, the tag to delete does not exist in cutomer 1<br/>
   Expected: Error, details shown in the status message(customer not updated).

<br/>

<div style="page-break-after: always;"></div>

## Update insurance of a customer

**Updating the insurances of a customer**

Prerequisite : -

1. Test case : `insurance 2 ai/AIA ai/cars di/health di/ABC` <br/>
   Expected : Customer is updated, `health` and `ABC` insurance are removed and `AIA` and `cars` insurance are added

1. Test case : `insurance 0 ai/AIA ai/cars di/health di/ABC` `` <br/>
   Expected : Customer is not updated. Error details shown in the status message (format error since the index is not a positive integer).

1. Test case : `insur 3 ai/EFG ai/JFK` <br/>
   Expected : Customer is not updated. Error details shown in the status message (Incorrect command word).

1. Test case : `insurance 4 ai/ABC di/ABC` <br/>
   Expected : Customer is not updated. Error details shown in the status message (conflicting changes).

1. Test case : `insurance 1 ` <br/>
   Expected : No customer is updated. Error details shown in the status message(format error since no insurances to update is provided).

<br/>

## Find customers

**Find customers**

Prerequisite : -

1. Test case : `find n/` <br/>
   Expected : Show all customers in the list, because every customer must has a name.

1. Test case : `find n/a`  <br/>
   Expected : Show all customers has a as a prefix in their name.

1. Test case : `find i/ABC t/male` <br/>
   Expected : Show all customers has ABC matches with their insurances and has male matches with their tags.

1. Test case : `find 123` <br/>
   Expected : Customer list not updated. Error details shown in the status message (format error, one prefix must be provided).

<br/>


## Update remark of a customer

**Updating the remark of a customer**

Prerequisite : -

1. Test case : `remark 2 he don't like pizza` <br/>
   Expected : Customer is updated. Customer's remark update to `he don't like pizza`.

1. Test case : `remark 2` `` <br/>
   Expected : Customer is updated. Customer's remark is deleted.

1. Test case : `remark` <br/>
   Expected : Customer is not updated. Error details shown in the status message (No index provided).
<br/>

## Updating priority of a customer

**Updating the priority of a specific customer**

Prerequisite : -

1. Test case : `priority 1 low` <br/>
   Expected : Priority of customer at index 1 is updated to `low`.

1. Test case : `priority 1 low`, old priority of customer at index 1 is also `low` <br/>
   Expected : Error, details shown in the status message(person is not changed).

1. Test case : `priority 0`<br/>
   Expected : Error, details shown in the status message(format error since the index is not a positive integer).

1. Test case : `priority 1`<br/>
   Expected : Error, details shown in the status message(format error since no priority is provided).

1. Test case: `priority 1 -` <br/>
   Expected : Priority of customer at index 1 is removed (and set to `NONE`), no priority label is shown in the Ui.

<br/>

## Updating appointment of a customer

**Updating the appointment of a specific customer**

Prerequisite : -

1. Test case : `addappt 1 d/2025-12-12` <br/>
   Expected : Appointment of customer at index 1 is updated to 12 Dec 2025 with empty time and venue.

1. Test case : `addappt 1 d/2025-12-12`, customer at index 1 has existing appointment <br/>
   Expected : Error, details shown in the status message(appointment is not added).

1. Test case : `deleteappt 1`, customer at index 1 has existing appointment <br/>
   Expected : Appointment of customer at index 1 is deleted, updated to become an empty appointment.

1. Test case : `deleteappt 1`, customer at index 1 does not have an existing appointment <br/>
   Expected : Error, details shown in the status message(no appointment).

1. Test case : `markappt 1`, customer at index 1 has an existing appointment <br/>
   Expected : Appointment of customer at index 1 is deleted, updated to become an empty appointment, and the appointments
completed counter is incremented by 1.

1. Test case : `markappt 1`, customer at index 1 does not have an existing appointment <br/>
   Expected : Error, details shown in the status message(No appointment exists to be marked).

1. Test case : `markappt 1`, customer at index 1 does not have existing appointment <br/>
   Expected : Appointments completed counter of customer at index 1 is deceremented by 1.

1. Test case : `umarkappt 1`, customer at index 1 has an existing appointment <br/>
   Expected : Error, details shown in the status message(cannot be undone if no completed appointment).


