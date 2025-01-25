# Setup

## Kafka

Download kafka_2.13-3.3.1 or any other latest version

1. `sudo sysctl -w net.ipv6.conf.all.disable_ipv6=1`
2. `sudo sysctl -w net.ipv6.conf.default.disable_ipv6=1`
3. `kafka-server-start.sh ~/kafka*/config/kraft/server.properties`

Run commands 1 and 2 if running in wsl (windows subsystem)
Run command 3 to begin kafka server in kraft mode (without zookeeper)

## Vault

1. To start vault server run below command  
   `./vault server -config=tmp/vault-server.hcl`

2. Below command to unseal vault server  
   `./vault operator unseal`

3. Below 2 commands to put and get vault secrets  
   `./vault kv put secret/weather-app-dev app.open.weather.api.key="key" spring.data.redis.password="password"`  
   `./vault kv get secret/weather-app-dev`

## Redis

## Prometheus

Update `prometheus.yml` file and add below job in scrape config

```yaml
scrape_configs:
  # weather app backend job
  - job_name: 'weather-app-backend'
    metrics_path: '/actuator/prometheus'
    scrape_interval: 60s
    static_configs:
      - targets: [ 'localhost:8080' ]
```

Run prometheus like below  
`.\prometheus.exe`

## Grafana

Run Grafana like below  
`.\grafana-server.exe`

## Loki

Run loki like below  
`.\loki-windows-amd64.exe --config.file=loki-local-config.yml`

## Jaeger

Start jaeger by running the command below    
`.\jaeger-all-in-one.exe`

## Jmeter

Run Jmeter test file like below  
`.\apache-jmeter-5.6.3\apache-jmeter-5.6.3\bin\jmeter -n -t weather-app-load-test.jmx -l weather-app-load-test-result-1.csv -Djmeter.save.saveservice.output_format=csv`

## Run Configuration

### Env Variables

`VAULT_TOKEN=token;TRUST_STORE_PASSWORD=password`

### VM Options

`-javaagent:C:\Users\shahw\Desktop\Repos\opentelemetry-javaagent-jar\opentelemetry-javaagent.jar -Dotel.service.name=weather-app-backend -Dotel.logs.exporter=none -Dotel.metrics.exporter=none -Dotel.traces.eporter=otlp`

## Dynamically Create Rules for Generating Advices

The DynamicAdviceFetcherService fetches list of rules from redis at fixed intervals of time.  
The fixed interval can be provided by the property `app.advice.fetch.interval.ms=60000`. It has default value of 600000.

The rules can be added to redis using the command below

```
SADD RULES '{"@class":"com.weather.backend.domain.Advice","condition":"description.contains(\"cloud\")","value":"Take an umbrella"}'
SADD RULES '{"@class":"com.weather.backend.domain.Advice","condition":"tempMin > 25","value":"Do not Wear a jacket"}'
SADD RULES '{"@class":"com.weather.backend.domain.Advice","condition":"tempMin < 15","value":"Wear a jacket"}'
```

Note that each weather forecast has below fields which can be used to define the conditions for each advice.  
The condition for each advice should be a valid spring expression language.

```
String dtTxt;
String summary;
String description;
Double tempMin;
Double tempMax;
Double windSpeed;
```

The rules can be removed like below

```
SREM RULES "{\"@class\":\"com.weather.backend.domain.Advice\",\"condition\":\"description.contains(\\\"cloud\\\")\",\"value\":\"Take an umbrella\"}"
```

The rules can be viewed using below command

```
SMEMBERS RULES
```

# NFRs

1. All Solid Principles Implemented
   1. Single Responsibility Principle followed in all classes
   2. Open Closed Principle Interfaces used to ensure that new code can be written without changing old code and new code can be easily injected
   3. Liskov Substitution Principle - All objects of implementation/subclass can be replaced by respective interface/superclass without breaking the application
   4. Interface Segregation Principle - Interfaces broken down to the smallest possible logical units 
   5. Dependency Inversion Principle - Service classes depend on interfaces not implementation of interfaces
2. 12 Factor App Methodology Implemented
   1. Single Codebase (Git Vcs)
   2. Dependencies defined in pom
   3. Env variables used to provide Vault connection secrets
   4. Backing Service treated as resource (3rd party api can be easily switched by changing property and no code change needed)
   5. Used Jenkins to perform build release and run independent of each other
   6. App is stateless process (Cache stored in Redis so even if app/process shutdown and restart state/data will not be lost)
   7. Port Binding (App utilizes spring boot and is a standalone server hence no need to deploy in another web server to expose http endpoint)
   8. Concurrency - Docker used to create image which helps in running multiple instances of the process
   9. Disposability - Any instance of process can be started and stopped without affecting the state of the application
   10. Dev/Prod difference is minimal - All backing services technology are same in dev and prod
   11. Log Aggregation done via Loki and visualized via Grafana
   12. Admin (One off) processes (setup) is part of codebase in readme to ensure same configuration
3. HATEOAS Principle implemented by sending weather icon complete url in response json. This allows client to dynamically fetch the icon image without having prior knowledge of the icon base url.  
4. 1. Performance Optimization achieved by 
      1. Checking Cache before sending request to 3rd party resource
      2. Using Circuit Breaker to avoid overloading 3rd party resource 
   2. Security best practices have been followed like
      1. Not logging any sensitive data
      2. Utilizing Hashicorp Vault to fetch application secrets
      3. Configuring Hashicorp Vault token in environment variables
5. Code is production ready. Below are points taken care of -
   1. Code Quality checked via Sonar Lint Plugin
   2. Test Cases written
   3. Exception Handling done
   4. Log aggregation via Loki and visualization via Grafana
   5. Application secrets provided securely
   6. Docker image created for scalability
   7. Docker image created with different tags for rollback
   8. Documentation done via Swagger and Readme
   9. Monitoring done by Prometheus and Grafana
6. Application Secrets provided securely via Vault and vault credentials provided securely via environment variable
7. Test Driven Development done by creating test cases
8. Behaviour Driven Development done by creating
