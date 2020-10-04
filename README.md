# MockingBird
For ease and scalable mocking solution


This project has few major feature as listed below:

1- If mock is not available it can get the response by hitting the actual downstream url saved in proxyDataInformationDao, hence preventing test case failure.

2- Because of the above feature this can be used in QA and preprod environment properties making QAs life easier while testing and automating microservices. They don't have to change the downstream urls again and again. 

3- If there are microservice that has encryption and decryption involve, you can write that decryption logic here, so you don't have to switch encryption from dev code's properties file and mocking is possible easily.

4- It is scalable and faster as mongo provides replication and sharding features.

5- This does not required restarting the server if you want to add new mock in the database,
Its now "easy peasy lemon squeezy".


For Running this application you have to add mongo and sql server details,
also you have you create the respective mongo documents.
 Rest all logic is preity clear and simple 