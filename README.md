# README.md

## API Exposure

To Configure User Authentication and authorization in Cognito and API Gateway
1.  Create a user pool
2.  Create an API Gateway authorizer with the chosen user pool. 
3.  Cnable the authorizer on selected API methods


To Call API method with user pool enabled, API Clients:

1.  Sign a user in to the chosen user pool, and obtain an identity token or access token. 
2.  Call the deployed API Gateway API and supply the appropriate token in the Authorization header. 

## Create the stack using AWS Cloudformation   
1.  Create S3 Bucket
2.  Deploy the packge using cloudformation in JAMBOLAN-DATAPI-STAGE or JAMBOLAN-DATA-PROD

$ aws --region eu-west-1 cloudformation deploy 
		--template-file /<path>/jambolan-dataapi.template \
		--stack-name <YOUR STACK NAME> --capabilities CAPABILITY_IAM


## Update the stack using AWS Cloudformation

1. Get template
$ aws --region us-west-1 cloudformation get-template --stack-name JAMBOLAN-STAGE > orig.tamplate

2. Modify template and update stack (this is direct update)
$ aws --region us-west-1 cloudformation update-stack --template-url <S3 URL for template>  --stack-name <STACK_NAMR> --capabilities CAPABILITY_IAM