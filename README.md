# OEM NMS

### CURL
Get access token:

* curl -X POST 'http://localhost:8090/api-admin/oauth/token' --header 'Authorization: Basic bm1zLWNsaWVudC0wMS1hZG1pbjpubXMtY2xpZW50LTAxLTEyMzQ1Ng==' --form 'grant_type=password' --form 'scope=nms-client-01' --form 'username=admin' --form 'password=123456'
