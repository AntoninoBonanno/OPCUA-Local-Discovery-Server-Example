let os = require("os");
let path = require("path");

const opcua = require("node-opcua");
//const opcua2 = require("node-opcua-certificate-manager")


const OPCUAServer = opcua.OPCUAServer;

const server = new OPCUAServer({
    port: 1435,
    registerServerMethod: opcua.RegisterServerMethod.LDS,
    discoveryServerEndpointUrl: "opc.tcp://MARIO-PC:4334",
    /*serverCertificateManager: new opcua2.OPCUACertificateManager({ 
        automaticallyAcceptUnknownCertificate: true,
        rootFolder: path.join(__dirname, "./certs")
    })*/
});



server.registerServerManager.timeout = 100;
server.once("serverRegistered", function () {
    console.log("server serverRegistered");
    //callback();
});
server.start(function () {
});
/*
server.initialize(() => {

    function new_address_space(server) {
        const addressSpace = server.engine.addressSpace;
        const namespace = addressSpace.getOwnNamespace();

        const device = namespace.addObject({
            organizedBy: addressSpace.rootFolder.objects,
            browseName: "MyDevice"
        });


        function available_memory() {
            // var value = process.memoryUsage().heapUsed / 1000000;
            const percentageMemUsed = os.freemem() / os.totalmem() * 100.0;
            return percentageMemUsed;
        }

        namespace.addVariable({
            componentOf: device,
            nodeId: "s=free_memory", // a string nodeID
            browseName: "FreeMemory",
            dataType: "Double",
            value: {
                get: function () {
                    return new opcua.Variant({
                        dataType: opcua.DataType.Double,
                        value: available_memory()
                    });
                }
            }
        });
    }

    new_address_space(server);
    console.log("Address space initialized.");

    server.start(() => {
        console.log(`Server is now listening on port ${server.endpoints[0].port}... ( press CTRL+C to stop)`);
        const endpointUrl = server.endpoints[0].endpointDescriptions()[0].endpointUrl;
        console.log("The primary server endpoint url is ", endpointUrl);
    });
});*/