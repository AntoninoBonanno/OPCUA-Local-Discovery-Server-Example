let os = require("os");

const opcua = require("node-opcua");

const server = new opcua.OPCUAServer({
    port: 4334,
    resourcePath: "/UA/BiusoBonannoLDS",
    buildInfo: {
        productName: "LDS_Server",
        buildNumber: "1",
        buildDate: new Date()
    }
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
});