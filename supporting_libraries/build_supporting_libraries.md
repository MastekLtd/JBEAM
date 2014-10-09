
Build suppporting libraries:

AdvancedPre:
------------
* from command line go to .../JBEAM/supporting_libraries/AdvancedPRE
* execute below mentioned command
	mvn clean compile install
This will install the PRE dependency into your local .m2 repository.

jdbcpool:
---------
* from command line go to .../JBEAM/supporting_libraries/jdbcpool
* execute below mentioned command
	mvn clean compile install
This will install the jdbcpool dependency into your local .m2 repository.

stg-birt:
---------
* from command line go to .../JBEAM/supporting_libraries/stg-birt
* execute below mentioned command
	mvn clean compile install
This will install the stg-birt dependency into your local .m2 repository.


After completion of building supporting libraries, please proceed to build JBEAM core components by referring to [README.md] (https://github.com/MastekLtd/JBEAM/blob/master/README.md)
