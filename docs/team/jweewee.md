---
layout: default.md
title: "Jweewee's Project Portfolio Page"
---

### Project EzContact

EzContact is a desktop app made for insurance agents to manage customer details,
optimized for usage via Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI).
If you can type fast, EzContact can get your contact management tasks done faster than traditional GUI apps.
AddressBook Level 3


Given below are my contributions to the project.

###### **New Feature**: Appointment

* What it does: Allows the user to assign appointment details: Date, Time, Venue, to a customer, and keep track of the number of
appointments completed with a customers.
* Justification: This feature significantly enhances the product's suitability for the target user.
Users can keep track of appointments with customers to gauge the stage in the selling process that a customer is in to maximise
efficiency and increase their conversion rate of closing customer deals.
* Highlights: 
  * Analysed design alternatives, from the implementation of `Appointment` and `AppointmentCount`
  classes, to handling addition/deletion of `Appointment`, marking/unmarking of `AppointmentCount`
  * Designed the details of what an appointment should contain and its restrictions(Date, Time, Venue) using the
  `LocalDateTime` class vs `String` representations
  * Also detailed the UI appearance output of `Appointment`.
  * The decisions made were mainly driven by ensuring the best user experience
  of an insurance salesman needing the essential tools to keep track of appointments.

###### **Code contributed**: [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=jweewee&breakdown=false&sort=groupTitle%20dsc&sortWithin=title&since=2023-09-22&timeframe=commit&mergegroup=&groupSelect=groupByRepos)

###### **Project management**:
* Have weekly meetings and brainstorm sessions to decide direction of the project.
* Have some commits and merged some pull requests in every milestone.
* Have some reviews and comments on teammates' pull requests.
* Discussed project ideation, use cases, target audience revamp



###### **Documentation**:
* User Guide:
  * Added a comprehensive user guide section for the appointment feature which includes:
    * Appointment details
    * Addappt feature
    * Deleteappt feature
    * Markappt feature
    * Unmarkappt feature
    * List feature
* Developer Guide:
  * Offers in-depth insights into the implementation details of the Appointment feature,
    providing developers with a clear understanding of its structure and functionality
  * Includes a section on design considerations, highlighting the principles and best practices that guided the development of these features.
    This section promotes code extensibility, maintainability, and adherence to SOLID principles, enhancing the overall quality of the codebase.
  * Adds use cases for Appointment feature and list feature.
  * Added user stories for Appointment feature and list feature.
  * Added the manual testing instructions for the Appointment-related features.


