from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.common.action_chains import ActionChains


path = 'C:/Users/ahame\Downloads\Coding\SeleniumChrome\chromedriver.exe'
# instantiates browser using chrome
browser = webdriver.Chrome(path)
browser.maximize_window()
browser.get("https://www.walmart.com/ip/PlayStation-5-Console/363472942?irgwc=1&sourceid=imp_TmLS4i3ACxyLTfbwUx0Mo372UkEWREQFI0i61U0&veh=aff&wmlspartner=imp_159047&clickid=TmLS4i3ACxyLTfbwUx0Mo372UkEWREQFI0i61U0&sharedid=&affiliates_ad_id=565706&campaign_id=9383")
browser.implicitly_wait(2)
add_to_cart = browser.find_element_by_css_selector("#add-on-atc-container > div:nth-child(1) > section > div.valign-middle.display-inline-block.prod-product-primary-cta.primaryProductCTA-marker > div.prod-product-cta-add-to-cart.display-inline-block > button")
add_to_cart.click()
checkout = browser.find_element_by_css_selector("#cart-root-container-content-skip > div.mobile-pac-sticky-footer.hide-content-m > div > div.new-ny-styling.cart-pos-proceed-to-checkout > div > button.button.ios-primary-btn-touch-fix.hide-content-m.button--primary")
# checkout = browser.find_element_by_id("CartCheckOutBtnBottom")
checkout.click()
guest = browser.find_element_by_css_selector("body > div.js-content > div > div.checkout-wrapper > div > div.accordion-inner-wrapper > div.checkout-accordion > div > div > div > div.CXO_module_container > div > div > div > div > div.CXO_welcome-mat.ny-lite-wm-variation.borderless > div > div:nth-child(4) > div > section > section > div > button")
guest.click()
continue1 = browser.find_element_by_css_selector("body > div.js-content > div > div.checkout-wrapper > div > div.accordion-inner-wrapper > div.checkout-accordion > div > div > div > div:nth-child(2) > div > div.CXO_module_body.ResponsiveContainer > div > div > div > div.text-left.Grid > div > div > div.CXO_fulfillment_continue > button")
continue1.click()