import bs4
import requests

res = requests.get('https://www.walmart.com/ip/Microsoft-Xbox-One-S-1TB-All-Digital-Edition-Console/936036983')
soup = bs4.BeautifulSoup(res.text, 'html.parser')
price = soup.prettify()
print(price)
