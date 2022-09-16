# How to run app on Local environment:
1. Clone source code
2. Run "mvn clean install"
3. Run "java -jar DropWizardJokes-0.0.1-SNAPSHOT.jar server"
4. Go to browser url: http://localhost:8080/jokes?query={SEARCH_KEY_WORD} ex: http://localhost:8080/jokes?query=zoo

# Rate limiter
- 5 requests per minute. If exceed will return the error "RateLimiter 'chucknorris' does not permit further calls".
- How to test: Refresh the search API more than 5 times in a minute, you will get the error.
