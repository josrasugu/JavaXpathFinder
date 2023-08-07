#Endpoints
Add path to database
POST http://localhost:8080/save-path
Sample post parameters
{
"url":"http://localhost/bpo_ticketing/dashboard.php?content=report&report=all",
"path":"/html/body/div[1]/div/div[2]/div[2]/div[2]/div/ul/li[3]/a"
}

Generate new true path
http://localhost:8080/generate
Sample post parameters
{
"url":"http://localhost/bpo_ticketing/dashboard.php?content=report&report=all",
"path":"/html/body/div[1]/div/div[2]/div[2]/div[2]/div/ul/li[3]/a"
}
