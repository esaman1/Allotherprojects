from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.common.action_chains import ActionChains
import time


path = 'C:/Users/ahame/Downloads/Coding/SeleniumChrome/chromedriver.exe'


browser = webdriver.Chrome(path)

browser.maximize_window()

browser.get("https://www.kickstarter.com")


tab1 = browser.window_handles[0]
tab1_title = browser.title

print(tab1_title)


browser.execute_script('window.open("https://www.stackoverflow.com", "new tab")')

tab2 = browser.window_handles[1]

browser.switch_to_window(tab2)

tab2_title = browser.title

print(tab2_title)

time.sleep((2))

browser.switch_to_window(tab1)