from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.common.action_chains import ActionChains
import time


path = 'C:/Users/ahame/Downloads/Coding/SeleniumChrome/chromedriver.exe'


browser = webdriver.Chrome(path)


# browser.get("https://")

# alert_element = browser.switch_to.alert
# alert_message = alert_element.text

time.sleep(3)
# alert_element.accept()
