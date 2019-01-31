# README.md

## Deployment

1. Build package  

    `$ mvn clean package`

2. Create S3 Bucket and copy `jambolan-cloudformation.yaml` and t`arget/jambolanapi-LATEST.jar` into an S3 Bucket.

3. Create a user pool and take note of the ARN  

4.  Create the Stack using cloudformation (jambolan-cloudformation.yaml) 

    `$ aws cloudformation create-stack --stack-name <STACK-NAME> --template-url <S3-URL> --parameters \ `
    `ParameterKey=JarS3BucketParam,ParameterValue=<BUCKET-NAME-FOR-JARFILE> \ `
    `ParameterKey=JarFilenameParam,ParameterValue=<JAR-FILENAME> \ `
    `ParameterKey=ApiStageNameParam,ParameterValue=<API-STAGE-NAME> \ `
    `ParameterKey=UserPoolArnParam,ParameterValue=<USERPOOL-ARN>`

# Invoking the API

1.  Sign a user in to the user pool, and obtain the access token. 
2.  Call the deployed API and supply the appropriate token in the Authorization header.