

## Ceazione certificato per LDS Server

cd LDS

..\CertGenerator\Opc.Ua.CertificateGenerator.exe -cmd issue -sp . -an LDS_Server -au urn:BiuBon -dn LDSHost -pw LDSFilePassword -pem true

## Avvio 

cd LDS

npm run dev
