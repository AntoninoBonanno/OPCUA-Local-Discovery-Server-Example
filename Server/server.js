let os = require("os");
let path = require("path");
require('dotenv').config({ path: path.resolve(__dirname, '../.env') });

const opcua = require("node-opcua");
const opcua_cm = require("node-opcua-certificate-manager");
const OPCUAServer = opcua.OPCUAServer;

const server = new OPCUAServer({
    resourcePath: "/UA/ServerBB", // this path will be added to the endpoint resource name
    port: parseInt(process.env.SERVERBB_PORT),
    serverInfo: {
        applicationUri: "ServerBB",
        productUri: "NodeOPCUA-ServerBB",
        applicationName: { text: "ServerBB", locale: "en" },
        productUri: "ServerBB",
    },
    registerServerMethod: opcua.RegisterServerMethod.LDS,
    discoveryServerEndpointUrl: `opc.tcp://${os.hostname()}:${process.env.LDS_PORT}`,

    serverCertificateManager: new opcua_cm.OPCUACertificateManager({
        automaticallyAcceptUnknownCertificate: true,
        rootFolder: path.join(__dirname, "./certs")
    }),
});

server.registerServerManager.timeout = 100;
server.once("serverRegistered", function () {
    console.log("server serverRegistered");
});

server.initialize(() => {

    function new_address_space(server) {
        const addressSpace = server.engine.addressSpace;
        const namespace = addressSpace.getOwnNamespace();

        const device = namespace.addObject({
            organizedBy: addressSpace.rootFolder.objects,
            browseName: "MyDevice"
        });

        function available_memory() {
            const percentageMemUsed = os.freemem() / os.totalmem() * 100.0;
            return percentageMemUsed;
        }

        namespace.addVariable({
            componentOf: device,
            nodeId: "i=1", // a string nodeID
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
        console.log("The primary server endpoint url is: " + endpointUrl);
    });
});