import requests

res = requests.get("https://automatetheboringstuff.com/files/rj.txt")
RJ = open('C:/Users/ahame/Downloads\Coding\AutomateTheBoringThings\WebBrowser\RomeoAndJuliet.txt', 'wb')
for chunk in res.iter_content(100000):
    RJ.write(chunk)
RJ.close()
# res.raise_for_status()

# print(res.status_code)
# print(res.text)