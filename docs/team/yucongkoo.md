---
  layout: default.md
  title: "Yucongkoo's Project Portfolio Page"
---

## Project: EzContact

EzContact is a desktop app made for insurance agents to manage customer details,
optimized for usage via Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI).
If you can type fast, EzContact can get your contact management tasks done faster than traditional GUI apps.

Given below are my contributions to the project.
<br/>

* **New Feature**: Added a `tag` command that allows users to update tags of a specific customer in EzContact.
    * **What it does:** Users can update tags associated to a specific customer, by providing tags to add and tags to delete
  through the tag command.
    * **Justification:** This feature significantly improved the product to make it a better fit to our target audience.
  Through associating descriptive tags to a customer, our users will be more likely to remember their customers and will also be
  helpful in identifying their customers.
    * **Highlights:** This enhancement required an in-depth analysis of design alternatives, from choice of data structure to hold the
  tags, to handling of certain user behaviours such as handling of duplicate tags provided, conflicting tags provided and
  addition(deletion) of existing(non-existing). The decisions made were mainly driven by ensuring the best user experience.
    * **Credits:** The association between the `Tag` class and `Person` class was originally implemented in AB-3,
  there was not much modification done to this association
<br/>
<br/>
* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=yucongkoo&breakdown=false&sort=groupTitle%20dsc&sortWithin=title&since=2023-09-22&timeframe=commit&mergegroup=&groupSelect=groupByRepos)
<br/>
<br/>

* **Project management**:
    * Managed releases `v1.2` - `v1.4` (5 releases) on GitHub
    * Managed the issue tracker and milestones, ensured team members' timely delivery of deliverables and team tasks
    * Distributed team tasks on a weekly basis. Hosted the features brainstorming session (Issues [#101](https://github.com/AY2324S1-CS2103T-W16-2/tp/issues/101), [141](https://github.com/AY2324S1-CS2103T-W16-2/tp/issues/141))
    * Managed bugs reported in PE-D, identified and filtered out duplicate bugs, while assigning each bug to the responsible developer and provided a summary (Issue [#289](https://github.com/AY2324S1-CS2103T-W16-2/tp/issues/289)).

<div style="page-break-after: always;"></div>

* **Enhancements to existing features**:
  * Made the address field of a customer optional (PRs [#68](https://github.com/AY2324S1-CS2103T-W16-2/tp/pull/68), [#83](https://github.com/AY2324S1-CS2103T-W16-2/tp/pull/83),[#102](https://github.com/AY2324S1-CS2103T-W16-2/tp/pull/102), [#197](https://github.com/AY2324S1-CS2103T-W16-2/tp/pull/197))
  * Refactored the `JsonAdaptedPerson` structure, introduced JsonAdaptedAttributes(`JsonAdaptedName`, `JsonAdaptedPhone` etc.) to facilitate
  conversion between json format and model type(PR [#103](https://github.com/AY2324S1-CS2103T-W16-2/tp/pull/103)). Throughout the process of refactoring,discovered and solved a bug in original AB-3 conversion[PR [#204](https://github.com/AY2324S1-CS2103T-W16-2/tp/pull/204)].
  * Created UI componenet `FlowPaneLabel` used to display priority, tags and insurances associated to a customer (PRs [#162](https://github.com/AY2324S1-CS2103T-W16-2/tp/pull/162), [#208](https://github.com/AY2324S1-CS2103T-W16-2/tp/pull/208)).
  * Updated the GUI color scheme and overall design (PR [#189](https://github.com/AY2324S1-CS2103T-W16-2/tp/pull/189))
  * Added the functionality of secondary prefix, that is every prefix now has a shorthand representation and a full name representation,
  while also making the prefixes not case-sensitive (PR [#238](https://github.com/AY2324S1-CS2103T-W16-2/tp/pull/238))
  <br/>
  <br/>
* **Documentation**:
  * User Guide:
    * Updated the `Quick Start` section and created the `UI Layout Description` section.
    * Drafted the overall structure and formatting for features section to be used across team members.
    * Added documentation for the features `add`, `tag` and also the `Advanced Features` section.
    * Added the `Prefix to full-name prefix translation table` section.
    * Checked the degree of standardization between different sections of the UG.
    * Tweaked the UG to make it pdf-version friendly.
  * Developer Guide:
    * Added the implementation details of the `tag` feature (including all the UML diagrams used in the explanation).
    * Added use case for adding a customer(UC01) and updating tags of a customer(UC09).
    * Added the manual testing instructions for the `tag` feature.
    * Added the Planned Enhancement 1: Deletion of all tags(and insurances) in one command.
    * Identified and fixed a bug of UML diagram in the `Design - Model Component` section.
  <br/>
  <br/>
* **Community**:
  * PRs reviewed(with non-trivial review comments): [#77](https://github.com/AY2324S1-CS2103T-W16-2/tp/pull/77/files/c7bff1718ed1ad97f10a426a26ddbd38c7f9d88f),
  [#110](https://github.com/AY2324S1-CS2103T-W16-2/tp/pull/110/files/21f6f6890e91e85acba38fe4b4c6924dc4dda5e8),
  [#120-1](https://github.com/AY2324S1-CS2103T-W16-2/tp/pull/120/files/14f6a08e3141168016ea041cf8a1c440f69b3d2d), [#120-2](https://github.com/AY2324S1-CS2103T-W16-2/tp/pull/120/files/14f6a08e3141168016ea041cf8a1c440f69b3d2d),
  [#119](https://github.com/AY2324S1-CS2103T-W16-2/tp/pull/119/files/737161d463230bae517c56e3e339fc9594c1565e),
  [#148](https://github.com/AY2324S1-CS2103T-W16-2/tp/pull/148/files/140747789d7932a9f5f9382bd39d56577f6c1bd7)
  * Contributed to forum discussions (examples: [1](https://github.com/nus-cs2103-AY2324S1/forum/issues/368#issuecomment-1801798119), [2](https://github.com/nus-cs2103-AY2324S1/forum/issues/317), [3](https://github.com/nus-cs2103-AY2324S1/forum/issues/393))
  * Reported bugs and suggestions for other teams in the class (examples can be found [here](https://github.com/yucongkoo/ped/issues))
