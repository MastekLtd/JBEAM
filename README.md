JBEAM
=====

JBEAM  - Java based Batch scheduling, processing and monitoring framework.

At a high level, JBEAM can be leveraged to 
* Define a batch calendar
* Schedule and execute end of the day processing of business objects
* Schedule execution of inbound and outbound interface jobs
* Schedule any end of business day or monthly reports
* Monitoring of the Jobs status and execution


Quickstart
===========

JBEAM uses [Apache Maven] (http://maven.apache.org/) as its build tool.
This section describes some of the basics to developers and contributors new to Apache Maven to know to get productive quickly.

How to build
------------

To build JBEAM core components:

` git clone git://github.com/MastekLtd/JBEAM.git`

First build the [supporting libraries] (https://github.com/MastekLtd/JBEAM/blob/master/supporting_libraries/) required for JBEAM by following the [instructions] (https://github.com/MastekLtd/JBEAM/blob/master/supporting_libraries/BuildingLibraries.txt)

After building supporting libraries use following commands to build JBEAM core components

 ` cd jbeam\jbeam-core-components`
 
 ` mvn clean compile install`

To build JBEAM UI components, refer to [Developer guide] ()


Working with JBEAM Source
-------------------------

Refer to the [Developer guide] () for detail help on 
   * setting up workspace
   * sample code on writing new batch jobs and batch execution handlers.
   * important classes of the framework and it's significance
   * build instructions for core components and UI components
   * debugging tips
   * database design and table structure of core and monitor
   
