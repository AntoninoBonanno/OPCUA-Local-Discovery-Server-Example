let path = require("path");
require('dotenv').config({ path: path.resolve(__dirname, '../.env') });

const opcua_cm = require("node-opcua-certificate-manager");
const OPCUADiscoveryServer = require("node-opcua-server-discovery").OPCUADiscoveryServer;

const discoveryServer = new OPCUADiscoveryServer({
    port: process.env.LDS_PORT,
    serverCertificateManager: new opcua_cm.OPCUACertificateManager({
        automaticallyAcceptUnknownCertificate: true,
        rootFolder: path.join(__dirname, "./certs")
    })
});

discoveryServer.start((err) => {
    if (err) {
        console.log("Errore: ", err);
        return;
    }

    var discoveryServerEndpoint = discoveryServer._get_endpoints()[0];
    console.log(`LDS is now listening on port ${discoveryServer.endpoints[0].port}... ( press CTRL+C to stop)`);
    console.log("The LDS endpoint url is: " + discoveryServerEndpoint.endpointUrl);
});


process.on('SIGINT', function () {
    discoveryServer.shutdown((err) => {
        if (err) console.log(err);
        else console.log("LDS stopped...");
    });

    process.exit(0);
});

