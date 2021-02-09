from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.common.action_chains import ActionChains
import uuid



# Path to my chrome driver
path = 'C:/Users/ahame\Downloads\Coding\SeleniumChrome\chromedriver.exe'
# instantiates browser using chrome
browser = webdriver.Chrome(path)

# navigates to url
browser.implicitly_wait(30)
browser.get("https://google.com")
browser.maximize_window()

# Presses sign in
sign_in = browser.find_element_by_link_text("Sign in")
sign_in.click()

# 
# locates username box and completes form then presses enter
email_box = browser.find_element(By.NAME, "identifier")
email_box.send_keys("ahmedstester1")
email_box.send_keys(Keys.ENTER)

# locates password box and completes form then presses enter
browser.implicitly_wait(10)
password_box = browser.find_element(By.NAME,"password")
password_box.send_keys("Yinyang730*")
password_box.send_keys(Keys.ENTER)

# Navigates to gmail
gmail = browser.find_element_by_link_text("Gmail")
gmail.click()



# Begins Compose
quick_compose_action = ActionChains(browser)
quick_compose_action.send_keys('c').perform()


# Writes recipients email and hits enter to confirm after locating recipient box
browser.implicitly_wait(30)
recipient_box = browser.find_element_by_id(':8z')
browser.implicitly_wait(30)
select_recipient_action = ActionChains(browser)
select_recipient_action.move_to_element(recipient_box).send_keys("ahamed.bus@gmail.com").send_keys(Keys.ENTER).perform()


# Writes subject text
browser.implicitly_wait(30)
subject_box = browser.find_element_by_id(":8h")
subject_action = ActionChains(browser).click(subject_box).send_keys("How are you").send_keys(Keys.ENTER).perform()



#Writes email message 
browser.implicitly_wait(30)
message_box = browser.find_element_by_id(":9m")
message_action = ActionChains(browser).click(message_box).send_keys("Just checking").perform()


#Clicks on Send
send_button = browser.find_element_by_id(":87")
send_button.click()

# Gets proof from sent
browser.implicitly_wait(30)
browser.get('https://mail.google.com/mail/u/0/#sent')
firstmessage = browser.find_element(By.ID, ":bx").click()

#Creates file name to save not unique image
a = str(uuid.uuid4())
browser.save_screenshot(f"{a}.png")






