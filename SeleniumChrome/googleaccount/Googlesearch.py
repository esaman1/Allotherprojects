# Generated by Selenium IDE
import time
import json
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.action_chains import ActionChains
from selenium.webdriver.support import expected_conditions
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.desired_capabilities import DesiredCapabilities

driver = webdriver.Chrome(executable_path='C:/Users/ahame/Downloads/Coding/SeleniumChrome/chromedriver')
  
driver.get("https://www.google.com/")
driver.set_window_size(1280, 678)
driver.find_element(By.NAME, "q").send_keys("IBM is the best")
driver.find_element(By.NAME, "q").send_keys(Keys.ENTER)
driver.save_screenshot("Test.png")
  