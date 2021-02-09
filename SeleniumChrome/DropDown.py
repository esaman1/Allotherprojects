from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.action_chains import ActionChains
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.select import Select

path = 'C:/Users/ahame/Downloads/Coding/SeleniumChrome/chromedriver.exe'


# instantiates browser using chrome
browser = webdriver.Chrome(path)
browser.implicitly_wait(60)
browser.maximize_window()


# navigates to url
browser.get("https://amazon.com")
menu = Select(browser.find_element_by_id('searchDropdownBox'))
for option in menu.options:
    print(option.text)


# menu.select_by_index(6)
# menu.select_by_visible_text('Computers')
menu.select_by_value('search-alias=amazon-pharmacy')