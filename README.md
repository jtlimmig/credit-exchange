# Credit Exchange
Migme credit exchange services

## Sub Modules/Folders
- common : Submodule to put all the sharable credit-exchange specific codes between each submodule
- dataservice : Submodule for direct CRUD operations on metadata
- replicationservice : Submodule for replication services such as purchasing, gifting and etc...
- web : Submodule to expose all dataservice and replicationservice endpoints over http (REST)
- deployment : Subfolder to keep all the scripts required for deployment



## Design
![Image](./deployment/cxb_design_diagram_v2.png)

## Prerequisites

- Java 8
- Scala 2.11.5 (or newer)

## Setup

- Nexus: Sbt settings (~/.sbt/0.13/nexus.sbt)

	```
	resolvers += Resolver.mavenLocal

	resolvers += "Mig33" at "https://tools.projectgoth.com/nexus/content/groups/public/"

	credentials += Credentials("Sonatype Nexus Repository Manager",
        	                   "tools.projectgoth.com",
                	           "YOUR_USERNAME",
                        	   "YOUR_PASSWORD")
	```

### Build

`sbt clean compile assembly`
