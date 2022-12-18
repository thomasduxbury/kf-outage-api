
# Outage API

A tool that enables the discovery of site device outages and posts them to an API.

# Info
When running the app, a CommandLineRunner will automatically begin execution by checking the site "norwich-pear-tree".

An end point /monitor-site/{siteId} is also exposed which allows a site of your choosing to be checked by issuing a POST request. 

Additionally, a request param 'validOutageStart' can be passed to this end point to customise filtering of the outage start date (e.g. "2022-01-01T00:00:00.000Z").

The tool will launch on port 8080.

## Getting Started

### Dependencies

* Java 17

### Executing program

* Run the program with:
```
./gradlew bootRun
```
* Run the tests with:
```
./gradlew clean test --info
```
## Notes

* Project structured by layer rather than by feature, but I think structuring by feature would also work for a project of this nature
* API Key could be encrypted if it were to remain in the .properties file, alternatively, it could be an environment variable/Kubernetes secret
* The filtering algorithm could be optimised as there's no need to iterate over device outages that have already been matched against a device id 
* Initially started with one Repository class per domain (SiteInfo, Outage, EnhancedSiteInfo), but it felt better to put them all into a single HTTP service class. This could be refactored if more functionality was to be added
* Unit tests need to cover more scenarios (HTTP errors, etc), but I hope I've demonstrated various different testing techniques
* The error handling is a little verbose; I also wasn't too sure what should be implemented in terms of resiliency against 500 responses. I've added functionality to catch 500 responses which would allow dealing with them as required (eg. retrying).
* I've realised I prefer the functional style of error handling, in terms of always returning a value from a method (e.g. Either type in Scala) and pattern matching against it. I think the error handling I've added could be tidied up a little.
