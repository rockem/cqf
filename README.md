# Q-Fetcher


### How to run server
```bash
$ OCR_API_KEY=<key> ./gradlew bootRun
```

### How to fetch with demo files
```bash
curl -X POST http://localhost:8080/api/v1/fetch -H 'Content-Type: application/json' -d '{"manifest": "https://raw.githubusercontent.com/rockem/cqf/master/demo/manifest.dat"}'
```

### Notes
Of course as this is an exercise there are some unhandled edge cases 
Also we can make things much more asynchronous to perform better with large manifests 
