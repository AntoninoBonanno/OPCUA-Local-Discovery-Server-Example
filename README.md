# Simple Node-OPCUA Local Discovery Server

It is a simple project that implements OPCUA LDS in node-js.

We have created a java client that asks LDS for registered servers. The client is designed specifically for the project servers.

The server is built in node-js, registers to the LDS and exposes a variable for reading the free memory.

![alt text](https://github.com/AntoninoBonanno/ProgettoII/blob/master/LDS.png)

## Dependencies

* [node-opcua](https://github.com/node-opcua/node-opcua/)
* [UA-Java-Legacy](https://github.com/OPCFoundation/UA-Java-Legacy)

## Start project

* LDS
    `cd LDS`
    `npm install`
    `npm run dev`
* Server
    `cd Server`
    `npm install`
    `npm run dev`
* Client
    `cd Client`
    `java -jar ClientLDS.jar`
    
    Or  
    - Import project on eclipse
    - Eclipse -> Import -> Existing Projects into Workspace
    - Import [UA-Java-Legacy](https://github.com/OPCFoundation/UA-Java-Legacy)lo stack into Workspace
    - Right click on the project "ClientOPCUA_LDS" and choose Properties.
    - Select Java Build Path -> Projects -> Add and select the stack project (opc-ua-stack)


## How to generate a certificate for LDS Server

`cd LDS`
`..\CertGenerator\Opc.Ua.CertificateGenerator.exe -cmd issue -sp . -an LDS_Server -au urn:BiuBon -dn LDSHost -pw LDSFilePassword -pem true`

## Authors

[Bonanno Antonino](https://github.com/AntoninoBonanno), [Biuso Mario](https://github.com/Mariobiuso)