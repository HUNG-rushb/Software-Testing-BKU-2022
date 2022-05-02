# jonit91278@wowcg.com
# H123456
# TYPE,"//*[@id=""username""]",andrew.viquang@gmail.com /html/body/div[2]/form/div[1]/input
import csv 
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
import time

options = webdriver.ChromeOptions()
options.add_experimental_option('excludeSwitches', ['enable-logging'])
options.add_argument("--window-size=1250,700")
driver = webdriver.Chrome(options=options)

def GotoURL(target,value,err):
    if err != "": 
        return err
    driver.get(target)
    return ""

def Click(target,value,err):
    if err != "": 
        return err
    try:
        element = driver.find_element(by = By.XPATH, value = target)
    except Exception as e:
        return "Không tìm thấy phần tử "+target
    element.click()
    return ""

def Type(target,value,err):
    if err != "": 
        return err
    try:
        element = driver.find_element(by = By.XPATH, value = target)
    except Exception as e:
        return "Không tìm thấy phần tử "+target
    element.clear()
    if value != "" :
        element.send_keys(value)
    return ""

def SwitchTo(target,value,err):
    if err != "": 
        return err
    try:
        element = driver.find_element(by = By.XPATH, value = target)
    except Exception as e:
        return "Không tìm thấy phần tử "+target
    driver.switch_to.frame(element)
    return ""

def SwitchBack(target,value,err):
    if err != "": 
        return err
    driver.switch_to_default_content()
    return ""

def Wait(target,value,err):
    if err != "": 
        return err
    time.sleep(int(target))
    return ""

def Enter(target,value,err):
    if err!="": 
        return err
    try:
        element=driver.find_element(by=By.XPATH, value=target)
    except Exception as e:
        return "Không tìm thấy phần tử "+target
    element.send_keys(Keys.ENTER)
    return ""

def Check(target,value,err):
    if err!="":
        return err
    try:
        element=driver.find_element(by=By.XPATH, value=target)
    except Exception as e:
        return "Không tìm thấy phần tử "+target
    if value!="" and element.text!=value:
        return "Không tìm thấy "+value
    return ""

def Start(target,value,err):
    print("Testcase ",target,":",end="")
    return ""

def End(target,value,err):
    if err!="":
        print(" Thất bại")
        print("\t\t",err)
    else:
        print(" Thành công")
    return ""

def Set(target,value,err):
    if err!="": return err
    
    try:
        driver.set_window_size(1920, 1080)
    except Exception as e:
        return "Lỗi set"
    return ""

def main(filename):
    csvfile = open(filename, mode='r',encoding='utf-8-sig')
    reader = csv.reader(csvfile)
    result = []
    # temp={
    #     testcase:0,
    #     success:True,
    #     error:""
    # }
    
    switcher = {
        "GOTOURL":GotoURL, 
        "CLICK":Click,
        "TYPE":Type,
        "CHECK":Check,
        "START": Start,
        "END":End,
        "SWITCHTO":SwitchTo,
        "SWITCHBACK":SwitchBack,
        "WAIT":Wait,
        "ENTER":Enter,
        "SET":Set
    }
    err = ""
    for row in reader:
        action=row[0]
        target=row[1] if len(row)>1 else ""
        value=row[2] if len(row)>2 else ""
        # print(action)
        func=switcher.get(action)

        if func is None: 
            print(" Sai lệnh, dừng test")
            break

        err = func(target,value,err)

print("Let's fucking test lmao")
# filename=input()
main("TEST.csv")
