let os = require("os");
let path = require("path");

//const opcua = require("node-opcua");
const opcua_cm = require("node-opcua-certificate-manager");

const OPCUADiscoveryServer = require("node-opcua-server-discovery").OPCUADiscoveryServer;
let discoveryServerEndpointUrl = "opc.tcp://localhost:4334";



const discoveryServer = new OPCUADiscoveryServer({
    port: 4334,
    resourcePath: "/UA/BiusoBonannoLDS",
    buildInfo: {
        productName: "LDS_Server",
        buildNumber: "1",
        buildDate: new Date()
    },
    serverCertificateManager: new opcua_cm.OPCUACertificateManager({ 
        automaticallyAcceptUnknownCertificate: true,
        rootFolder: path.join(__dirname, "./certs")
    })
});

discoveryServer.start((err) => {
    discoveryServerEndpointUrl = discoveryServer._get_endpoints()[0].endpointUrl;
    console.log("discoveryServerEndpointUrl : ", discoveryServerEndpointUrl);
    console.log("discovery server started");
    
    
});


process.on('SIGINT', function () {
    discoveryServer.shutdown();
    process.exit(0);
});

