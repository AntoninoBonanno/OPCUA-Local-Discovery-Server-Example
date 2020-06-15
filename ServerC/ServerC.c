/* This work is licensed under a Creative Commons CCZero 1.0 Universal License.
 * See http://creativecommons.org/publicdomain/zero/1.0/ for more information. */
/*
 * A simple server instance which registers with the discovery server (see server_lds.c).
 * Before shutdown it has to unregister itself.
 */

#include "open62541.h"

#include <signal.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>

#define DISCOVERY_SERVER_ENDPOINT "opc.tcp://localhost:"

UA_Boolean running = true;

static void stopHandler(int sign) {
    UA_LOG_INFO(UA_Log_Stdout, UA_LOGCATEGORY_SERVER, "received ctrl-c");
    running = false;
}


int main(int argc, char **argv) {
    signal(SIGINT, stopHandler); /* catches ctrl-c */
    signal(SIGTERM, stopHandler);

    UA_Server *server = UA_Server_new();
    UA_ServerConfig_setDefault(UA_Server_getConfig(server));
    UA_ServerConfig* config = UA_Server_getConfig(server);
    config->verifyRequestTimestamp = UA_RULEHANDLING_ACCEPT;
    config->applicationDescription.applicationName = UA_LOCALIZEDTEXT_ALLOC("en","ServerC");
    config->applicationDescription.productUri = UA_STRING_ALLOC("ServerC");
    config->applicationDescription.applicationUri = UA_STRING_ALLOC("ServerC");

    UA_CertificateVerification_AcceptAll(&config->certificateVerification);
    
    UA_Client *clientRegister = UA_Client_new();
    UA_ClientConfig_setDefault(UA_Client_getConfig(clientRegister));

    char * port = "4334";
    char * line = NULL;
    size_t len = 0;
    
    FILE *env = fopen("../.env","r");
    
    if(env != NULL) {
        while (getline(&line, &len, env) != -1) {
            if(strncmp(line, "LDS_PORT = ", 11) == 0) {
                port = &line[11];
                break;          
            }                      
        }
    }
    fclose(env);
    
    char endpointLDS[100];
    
    strcpy(endpointLDS, DISCOVERY_SERVER_ENDPOINT);
    strcat(endpointLDS, port);
    endpointLDS[strlen(endpointLDS)-1] = '\0';
    printf("\nLDS Endpoint = %s \n\n", endpointLDS);
    
    UA_StatusCode retval =
        UA_Server_addPeriodicServerRegisterCallback(server, clientRegister, endpointLDS,
                                                    10 * 60 * 1000, 500, NULL);
    
    if(retval != UA_STATUSCODE_GOOD) {
        UA_LOG_ERROR(UA_Log_Stdout, UA_LOGCATEGORY_SERVER,
                     "Could not create periodic job for server register. StatusCode %s",
                     UA_StatusCode_name(retval));
        UA_Client_disconnect(clientRegister);
        UA_Client_delete(clientRegister);
        UA_Server_delete(server);
        UA_Server_delete(server);
        return EXIT_FAILURE;
    }

    retval = UA_Server_run(server, &running);

    if(retval != UA_STATUSCODE_GOOD) {
        UA_LOG_ERROR(UA_Log_Stdout, UA_LOGCATEGORY_SERVER,
                     "Could not start the server. StatusCode %s",
                     UA_StatusCode_name(retval));
        UA_Client_disconnect(clientRegister);
        UA_Client_delete(clientRegister);
        UA_Server_delete(server);
        return EXIT_FAILURE;
    }

    retval = UA_Server_unregister_discovery(server, clientRegister);
    if(retval != UA_STATUSCODE_GOOD)
        UA_LOG_ERROR(UA_Log_Stdout, UA_LOGCATEGORY_SERVER,
                     "Could not unregister server from discovery server. StatusCode %s",
                     UA_StatusCode_name(retval));

    UA_Client_disconnect(clientRegister);
    UA_Client_delete(clientRegister);
    UA_Server_delete(server);
    return retval == UA_STATUSCODE_GOOD ? EXIT_SUCCESS : EXIT_FAILURE;;
}
