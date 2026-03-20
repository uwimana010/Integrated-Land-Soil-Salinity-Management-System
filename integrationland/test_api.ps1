$response = Invoke-WebRequest -Uri "http://localhost:8081/api/auth/register" -Method Post -Body '{"fullName":"John Doe","email":"john@doe.com","password":"password123","role":"USER"}' -ContentType "application/json"
Write-Host "Register Response:"
Write-Host $response.Content

$response2 = Invoke-WebRequest -Uri "http://localhost:8081/api/auth/login" -Method Post -Body '{"email":"john@doe.com","password":"password123"}' -ContentType "application/json"
Write-Host "Login Response:"
Write-Host $response2.Content
$token = (ConvertFrom-Json $response2.Content).token

Write-Host "Token extracted: $token"

$response3 = Invoke-WebRequest -Uri "http://localhost:8081/api/lands" -Method Get -Headers @{Authorization="Bearer $token"}
Write-Host "Lands Response:"
Write-Host $response3.Content

$response4 = Invoke-WebRequest -Uri "http://localhost:8081/api/crops" -Method Post -Body '{"cropName":"Wheat","salinityTolerance":"High","soilRequirement":"Clay"}' -ContentType "application/json" -Headers @{Authorization="Bearer $token"}
Write-Host "Crops Post Response:"
Write-Host $response4.Content
