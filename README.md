# Setup

## Jenkins

`sudo wget -O /etc/yum.repos.d/jenkins.repo https://pkg.jenkins.io/redhat/jenkins.repo`
`sudo rpm --import https://pkg.jenkins.io/redhat/jenkins.io-2023.key`
`sudo dnf upgrade`
` sudo yum install java-17-amazon-corretto-devel`
`sudo dnf install jenkins`
`sudo systemctl enable jenkins`
`sudo systemctl start jenkins`
`sudo systemctl status jenkins`
`sudo systemctl stop jenkins`
`sudo cat /var/lib/jenkins/secrets/initialAdminPassword`
`sudo yum install git`
`sudo yum update -y`
`sudo yum -y install docker`
`sudo systemctl enable docker`
`sudo systemctl start docker`
`sudo usermod -a -G docker ec2-user`
`sudo chmod 666 /var/run/docker.sock`

## Kafka

Download kafka_2.13-3.3.1 or any other latest version
`sudo yum install java-11-amazon-corretto-devel`
`wget https://archive.apache.org/dist/kafka/3.9.0/kafka_2.13-3.9.0.tgz`
`tar xzf kafka_2.13-3.9.0.tgz`
`kafka_2.13-3.9.0/bin/kafka-storage.sh random-uuid`
`kafka_2.13-3.9.0/bin/kafka-storage.sh format -t <uuid> -c kafka_2.13-3.9.0/config/kraft/server.properties`
`kafka_2.13-3.9.0/bin/kafka-server-start.sh kafka_2.13-3.9.0/config/kraft/server.properties &`
`kafka_2.13-3.9.0/bin/kafka-topics.sh --bootstrap-server localhost:9092 --topic weather.app.message --create --partitions 3 --replication-factor 1`
`vi kafka_2.13-3.9.0/config/kraft/server.properties`
`listeners=PLAINTEXT://0.0.0.0:9092`
`advertised.listeners=PLAINTEXT://44.201.193.112:9092`

1. `sudo sysctl -w net.ipv6.conf.all.disable_ipv6=1`
2. `sudo sysctl -w net.ipv6.conf.default.disable_ipv6=1`
3. `kafka-server-start.sh ~/kafka*/config/kraft/server.properties`

Run commands 1 and 2 if running in wsl (windows subsystem)
Run command 3 to begin kafka server in kraft mode (without zookeeper)

## Vault

One time setup steps
On basis of ec2 instance architecture download the correct binary
`curl -o vault_1.18.4_linux_amd64.zip https://releases.hashicorp.com/vault/1.18.4/vault_1.18.4_linux_amd64.zip`
`unzip vault_1.18.4_linux_amd64.zip vault_1.18.4_linux_amd64`
`cd vault_1.18.4_linux_amd64`
`mkdir tmp`
`mkdir tmp/vault-data`
`openssl req -x509 -newkey rsa:4096 -sha256 -days 365 -nodes -keyout tmp/vault-key.pem -out tmp/vault-cert.pem -subj "/CN=localhost" -addext "subjectAltName=DNS:localhost,IP:127.0.0.1"`

```
cat > tmp/vault-server.hcl << EOF
api_addr                = "https://127.0.0.1:8200"
cluster_addr            = "https://127.0.0.1:8201"
cluster_name            = "weather-app-vault-cluster"
disable_mlock           = true
ui                      = true

listener "tcp" {
address       = "127.0.0.1:8200"
tls_cert_file = "/home/ec2-user/vault_1.18.4_linux_amd64/tmp/vault-cert.pem"
tls_key_file  = "/home/ec2-user/vault_1.18.4_linux_amd64/tmp/vault-key.pem"
}

backend "raft" {
path    = "/home/ec2-user/vault_1.18.4_linux_amd64/tmp/vault-data"
node_id = "learn-vault-server"
}
EOF
```
`./vault server -config=tmp/vault-server.hcl`
`export VAULT_ADDR=https://127.0.0.1:8200`
`export VAULT_SKIP_VERIFY=true`
`./vault operator init -key-shares=1 -key-threshold=1`
Store the Unseal Key and Initial Root Token securely
`./vault operator unseal` and enter the unseal key
`./vault status`
`./vault login`
`./vault secrets enable -path=secret kv`
`openssl req -x509 -newkey rsa:4096 -sha256 -days 365 -nodes -keyout tmp/vault-key.pem -out tmp/vault-cert.pem -subj "/CN=localhost" -addext "subjectAltName=DNS:localhost,IP:127.0.0.1,IP:44.201.193.112,IP:172.31.82.154"`
`openssl pkcs12 -export -in vault-cert.pem -inkey vault-key.pem -out keystore.p12 -name mykey`
`scp -i ./Infra.pem ec2-user@44.201.193.112:/home/ec2-user/vault_1.18.4_linux_amd64/tmp/vault-key.p12 .`
`keytool -importkeystore -srckeystore vault-key.p12 -srcstoretype PKCS12 -destkeystore vault-keystore.jks -deststoretype JKS`
`openssl x509 -outform der -in tmp/vault-cert.pem -out tmp/vault-cert.der`
`keytool -importcert -file vault-cert.der -alias vault-cert -keystore C:\Users\mohshahw\.jdks\corretto-17.0.5\lib\security\cacerts`
`keytool -list -keystore C:\Users\mohshahw\.jdks\corretto-17.0.5\lib\security\cacerts`
`ps aux | grep vault`

1. To start vault server run below command  
   `./vault server -config=tmp/vault-server.hcl`

2. Below command to unseal vault server
   `export VAULT_ADDR=https://127.0.0.1:8200`
   `export VAULT_SKIP_VERIFY=true`
   `./vault operator unseal`

3. Below 2 commands to put and get vault secrets  
   `./vault kv put secret/weather-app-prod app.open.weather.api.keys="key1,key2,key3" spring.data.redis.password="pass"`  
   `./vault kv get secret/weather-app-prod`

## Redis

`sudo yum install gcc`
`wget http://download.redis.io/redis-stable.tar.gz`
`tar xvzf redis-stable.tar.gz`
`cd redis-stable`
`make`
`vi redis-custom.conf`
`requirepass "password"`
`mkdir log`
`logfile "/home/ec2-user/redis-stable/log/redis-server.log"`
`sudo ./redis-stable/src/redis-server redis-stable/redis-custom.conf &`
`redis-stable/src/redis-cli -a pass1234`

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

`wget https://dl.grafana.com/oss/release/grafana-11.5.0.linux-amd64.tar.gz`
``
Run Grafana like below  
`.\grafana-server.exe`

`ssh -i .\Infra.pem -NL 3000:localhost:3000 ec2-user@44.201.193.112`

## Loki

`wget https://github.com/grafana/loki/releases/download/v3.3.2/loki-linux-amd64.zip`
`unzip loki-linux-amd64.zip -d loki-linux-amd64`
`wget https://raw.githubusercontent.com/grafana/loki/main/cmd/loki/loki-local-config.yaml`


Run loki like below  
`.\loki-windows-amd64.exe --config.file=loki-local-config.yml`
`./loki-linux-amd64 -config.file=loki-local-config.yaml`

## Jaeger

`wget https://github.com/jaegertracing/jaeger/releases/download/v1.65.0/jaeger-1.65.0-linux-amd64.tar.gz`
` tar -xvzf jaeger-1.65.0-linux-amd64.tar.gz`
``

Start jaeger by running the command below    
`.\jaeger-all-in-one.exe`

## Jmeter

Run Jmeter test file like below  
`.\apache-jmeter-5.6.3\apache-jmeter-5.6.3\bin\jmeter -n -t weather-app-load-test.jmx -l weather-app-load-test-result-1.csv -Djmeter.save.saveservice.output_format=csv`

## Run Configuration

### Env Variables

`VAULT_TOKEN=token;TRUST_STORE_PASSWORD=password`

### VM Options
`-javaagent:C:\Users\mohshahw\Desktop\Repos\opentelemetry-javaagent-2.12.0.jar -Dotel.service.name=weather-app-backend -Dotel.logs.exporter=none -Dotel.metrics.exporter=none -Dotel.traces.eporter=otlp -Dotel.exporter.otlp.endpoint=http://44.201.193.112:4318 -Djavax.net.ssl.trustStore="C:\Users\mohshahw\.jdks\corretto-17.0.5\lib\security\cacerts" -Djavax.net.ssl.trustStorePassword="changeit"`
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
3. HATEOAS Principle implemented
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
