# Simple Node-OPCUA Local Discovery Server

It's a simple project that implements OPCUA LDS in node.js.

We have created a Java client that requests registered servers from LDS.

We have created several servers, in different languages, that register with LDS.

* **Client**: Java Client in Java 
* **LDS**: Local Discovery Server in Node.js
* **ServerNode**: Server in Node.js
* **ServerCs**: Server in C#

![alt text](https://github.com/AntoninoBonanno/Simple-Node-OPCUA-Local-Discovery-Server/blob/master/LDS.png)

## Dependencies

* For LDS and ServerNode: [node-opcua](https://github.com/node-opcua/node-opcua/)
* For Client: [UA-Java-Legacy](https://github.com/OPCFoundation/UA-Java-Legacy)
* For ServerCs: [UA-.NETStandard](https://github.com/OPCFoundation/UA-.NETStandard), [Dotnet](https://dotnet.microsoft.com/download)

## Start project

* LDS

    `> cd LDS`
    
    `> npm install`
    
    `> npm run dev`
    
* ServerNode

    `> cd ServerNode`
    
    `> npm install`
    
    `> npm run dev`

* ServerCs (C#)

    `> cd ServerCs\bin`

    `> ServerCs.exe`
    
    Or 

    - Open ServerCs\ServerCs.sln on Visual Studio 
    - You can edit LDS default port inside Server\ServerCs.cs 

* Client

    `> cd ClientOPCUA_LDS`

    `> java -jar ClientForLDS.jar` (Run as administrator)

    Or

    - Import ClientOPCUA_LDS project on Eclipse (Eclipse -> Import -> Existing Projects into Workspace)
    - Import [UA-Java-Legacy](https://github.com/OPCFoundation/UA-Java-Legacy) stack into Workspace
    - Right click on the project "ClientOPCUA_LDS" and choose Properties.
    - Select Java Build Path -> Projects -> Add and select the stack project (opc-ua-stack)
    
**Copy the LDS endpoint url inside Client for start scan.** 


## Authors

[Bonanno Antonino](https://github.com/AntoninoBonanno), [Biuso Mario](https://github.com/Mariobiuso)
