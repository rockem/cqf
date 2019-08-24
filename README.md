# Q-Fetcher


### How to run server
```bash
$ OCR_API_KEY=<key> ./gradlew bootRun
```

### How to fetch with demo files
```bash
curl -X POST http://localhost:8080/api/v1/fetch -H 'Content-Type: application/json' -d '{"manifest": "https://raw.githubusercontent.com/rockem/cqf/master/demo/manifest.dat"}'
```

### Todo
* Handle more edge cases 
* Parallel fetching for different sources
* Make caching configurable 
