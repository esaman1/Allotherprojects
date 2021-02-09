from selenium import webdriver
import time

def order():
    #VARIABLES

    myAddress = '1 exeter rd'
    myPhone = '5512216983'
    myCreditCardNum = '4147202469065757'
    myCreditExpireMonth = '11'
    myCreditExpireYear = '25'
    myCVV = '605'

    #ADDS PS5 TO CART AND GOES TO CHECKOUT
    clickButton(addToCart)
    clickButton(checkOut)
    clickButton(continueWithoutAccount)

    #FILLS OUT SHIPPING INFO
    clickButton(firstContinue)
    enterData(firstName, myFirstName)
    enterData(lastName, myLastName)
    enterData(phone, myPhone)
    enterData(email, myEmail)
    enterData(address, myAddress)
    clickButton(confirmInfo)

    #FILLS OUT PAYMENT
    enterData(creditCardNum, myCreditCardNum)
    enterData(creditExpireMonth, myCreditExpireMonth)
    enterData(creditExpireYear, myCreditExpireYear)
    enterData(creditCVV, myCVV)


    #ORDER
    clickButton(reviewOrder)
    #clickButton(confirmOrder)

def clickButton(xpath):
    try:
        driver.find_element_by_xpath(xpath).click()
        pass
    except Exception:
        time.sleep(1)
        clickButton(xpath)

def enterData(field,data):
    try:
        driver.find_element_by_xpath(field).send_keys(data)
        pass
    except Exception:
        time.sleep(1)
        enterData(field,data)

if __name__ == "__main__":
    driver = webdriver.Chrome('C:/Users/ahame\Downloads\Coding\SeleniumChrome\chromedriver.exe')
    driver.get('https://www.walmart.com/ip/PlayStation-5-Console/363472942?irgwc=1&sourceid=imp_TmLS4i3ACxyLTfbwUx0Mo372UkEWREQFI0i61U0&veh=aff&wmlspartner=imp_159047&clickid=TmLS4i3ACxyLTfbwUx0Mo372UkEWREQFI0i61U0&sharedid=&affiliates_ad_id=565706&campaign_id=9383')
    time.sleep(3)
    order()
