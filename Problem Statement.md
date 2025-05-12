**Weather Prediction**

**Problem Statement (Full Stack – UI, Service \+ CI/CD)**

 Develop, test and deploy a micro service to show the output of a city's (to be taken as an input parameter) next 3 days high and low temperatures. If rain is predicted in next 3 days or temperature goes above 40 degree Celsius then mention 'Carry umbrella' or 'Use sunscreen lotion' respectively in the output, for that day;  
 Demonstrate adding additional conditions, with the least code changes & deployment:

 1\. In case of high winds (i.e.,) Wind \> 10mph, mention “It’s too windy, watch out\!”  
 2\. In case of Thunderstorms, mention “Don’t step out\! A Storm is brewing\!”  
 3\. End user should be able to view results by changing the input parameters

 • The service should be ready to be released to production or live environment  
 • The service should be accessible via web browser or postman (using any one of JavaScript frameworks, HTML or JSON)  
 • The solution should support offline mode with toggles  
 • The service should return relevant results as expected, even while the underlying dependencies (Ex: Public API) are not available\!

 (Use your own code/logic/data structures and without 3rd party libraries or DB)

 **API Data Sources**  
 **APIs**  
 [https://api.openweathermap.org/data/2.5/forecast?q=london\&appid=d2929e9483efc82c82c32ee7e02d563e\&cnt=10](https://nam02.safelinks.protection.outlook.com/?url=https%3A%2F%2Fapi.openweathermap.org%2Fdata%2F2.5%2Fforecast%3Fq%3Dlondon%26appid%3Dd2929e9483efc82c82c32ee7e02d563e%26cnt%3D10&data=05%7C01%7Cbibhanshu.swain%40publicissapient.com%7Cd69495161f42448f3b3208db19360bd5%7Cd52c9ea17c2147b182a333a74b1f74b8%7C0%7C0%7C638131494237433500%7CUnknown%7CTWFpbGZsb3d8eyJWIjoiMC4wLjAwMDAiLCJQIjoiV2luMzIiLCJBTiI6Ik1haWwiLCJXVCI6Mn0%3D%7C3000%7C%7C%7C&sdata=xmqP2s1sBKhpmt7Jm0lSzscuRhogp%2FnORSYIxwTDxI0%3D&reserved=0)  
 **Key**: d2929e9483efc82c82c32ee7e02d563e  
 **Documentation**: [https://openweathermap.org/api](https://nam02.safelinks.protection.outlook.com/?url=https%3A%2F%2Fopenweathermap.org%2Fapi&data=05%7C01%7Cbibhanshu.swain%40publicissapient.com%7Cd69495161f42448f3b3208db19360bd5%7Cd52c9ea17c2147b182a333a74b1f74b8%7C0%7C0%7C638131494237433500%7CUnknown%7CTWFpbGZsb3d8eyJWIjoiMC4wLjAwMDAiLCJQIjoiV2luMzIiLCJBTiI6Ik1haWwiLCJXVCI6Mn0%3D%7C3000%7C%7C%7C&sdata=b8%2FZOj3VTwPOxEiCGkfLK1eQe1nkzvNEZrHn7gWu%2F9k%3D&reserved=0)

Note: Please use the above API end-point only; The API Key might not work for other APIs in the documentation

 **Expected output**  
 (via an UI mechanism of your choice – Ex: React page)  
 1\. List of temperatures along with date  
 2\. Prediction along with the time window

 **NFRs**  
 • Demonstrate SOLID, 12 Factor and HATEOAS principles, Design Patterns in the design and implementation  
 • Demonstrate Performance, Optimization & Security aspects  
 • Demonstrate Production readiness of the code  
 • Demonstrate TDD & BDD & Quality aspects  
 • Demonstrate sensitive information used in the Micro Services such as API keys are protected / encrypted

 **Documentation**  
 • Include the open-API spec./Swagger to be part of the code. Should be able to view API via swagger (Documentation to explain the purpose of the API along with Error codes that the service consumers & client must be aware of\!)
 • In the README, add a sequence diagram or flowchart created using [draw.io](https://nam02.safelinks.protection.outlook.com/?url=http%3A%2F%2Fdraw.io%2F&data=05%7C01%7Cbibhanshu.swain%40publicissapient.com%7Cd69495161f42448f3b3208db19360bd5%7Cd52c9ea17c2147b182a333a74b1f74b8%7C0%7C0%7C638131494237433500%7CUnknown%7CTWFpbGZsb3d8eyJWIjoiMC4wLjAwMDAiLCJQIjoiV2luMzIiLCJBTiI6Ik1haWwiLCJXVCI6Mn0%3D%7C3000%7C%7C%7C&sdata=DwvFt7XUa7N1VWHS7LQLzee6HZj7aHob0%2FJ1UF9kM%2BI%3D&reserved=0) – [https://www.draw.io](https://nam02.safelinks.protection.outlook.com/?url=https%3A%2F%2Fwww.draw.io%2F&data=05%7C01%7Cbibhanshu.swain%40publicissapient.com%7Cd69495161f42448f3b3208db19360bd5%7Cd52c9ea17c2147b182a333a74b1f74b8%7C0%7C0%7C638131494237433500%7CUnknown%7CTWFpbGZsb3d8eyJWIjoiMC4wLjAwMDAiLCJQIjoiV2luMzIiLCJBTiI6Ik1haWwiLCJXVCI6Mn0%3D%7C3000%7C%7C%7C&sdata=hVb6fz9z7JRSrWQGrzDLoi0b2%2BtJg4jJjDmkg5UCJpQ%3D&reserved=0)

 **Build & Deploy**  
 CI  
 • Build CI/CD pipeline for your project(s); Pipeline scripts need to be part of the codebase;  
 • Ensure the Jenkins job config., scripts are a part of the project sources  
 CD  
 • Demonstrate the service deployment using a Docker container (Create a docker image and publish service locally)  
 • Ensure the docker files are a part of the project sources 

