const opcua = require("node-opcua");
const path = require("path");

const OPCUAClient = opcua.OPCUAClient;

const port = 2000;
const discoveryServerEndpointUrl = "opc.tcp://MARIO-PC:4334";

const client = OPCUAClient.create({
   
});


client.connect(discoveryServerEndpointUrl, function (err) {
    console.log(err);


    client.findServers(function (err, servers) {
        if (!err) {
            //servers.length.should.eql(1);
            console.log("Ci sono ", servers.length, "server");
            console.log(servers)
            client.disconnect(function (err) {
                console.log(err);
            });

            client.connect(servers[1].discoveryUrls[0], function (err) {
                console.log(err);
                console.log("bravo");
            });
        }
        else console.log(err);
    });

});







