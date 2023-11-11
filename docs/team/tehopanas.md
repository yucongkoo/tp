---
layout: default.md
title: "TehOPanas's Project Portfolio Page"
---

## Project EzContact

EzContact is a desktop app made for insurance agents to manage customer details,
optimized for usage via Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI).
If you can type fast, EzContact can get your contact management tasks done faster than traditional GUI apps.

Given below are my contributions to the project.
<br/>

###### **New Feature**: 
* Implemented `Insurance` feature that includes a new `insurance` command and a new attribute to the customers in our EzContact.
  * What it does: User can assign or remove insurances associated with a customer on EzContact, by providing the name of the insurance.
  * Justification: Given that our users are insurance agents, our users have to constantly keep track of what insurances each customer 
currently holds, has interest in or plans to cancel. With this feature, our users can efficiently keep track of these information and make the 
correct decisions and moves when interacting with their customers, subsequently increasing their efficiency and chances of securing deals.
  * Highlights: Implementation of this feature requires in-depth analysis of the model component, different data structures to handle the information
and handling of user behaviours (e.g. duplicate, conflicting insurances). These analysis determine our design choices from all the possible alternatives.


###### **Code contributed**: [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=tehopanas&breakdown=false&sort=groupTitle%20dsc&sortWithin=title&since=2023-09-22&timeframe=commit&mergegroup=&groupSelect=groupByRepos)

###### **Enhancements to existing features**:
  * Improve validation of input value for customer's information (e.g. name, phone, email...)
  * Change the definition of duplicate entries, allowing same name, but phone and email must be unique
  * Make command words and prefixes case-insensitive, giving users more flexibility in using the product

###### **Documentation**:
* User Guide: 
  * Added documentation for `insurance` feature
  * Standardise formatting and layout of the entire User Guide
  * Added introduction to the User Guide
  * Added examples for `find` and `remark` feature
  * Improve description for `find` feature's documentation
  * Update the glossary
  * Ensure that all parts of the documentation are consistent and correct
* Developer Guide: 
  * Added documentation for `Insurance` feature (i.e. Implementation, design choices, UML diagrams...)
  * Standardise the formatting and layout for Developer Guide
  * Standardise the format for Appendix: Effort, Appendix: Planned Enhancement,Appendix: Instruction for Manual Testing and Acknowledgement
  * Added uses cases for `Insurance` feature
  * Updated product scope to be focused on insurance agents
  * Update and improve user stories to become more targeted towards insurance agents

###### **Community**:
* `to be added soon`


