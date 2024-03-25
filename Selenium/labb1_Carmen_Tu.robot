*** Settings ***
Documentation       öppna car rental sidan, logga in, boka bil
Library         SeleniumLibrary
Suite Setup     Open car rental page


*** Variables ***
${url}               http://rental23.infotiv.net/
${browser}           headlesschrome
${mail}              carmen.tu@iths.se
${password}          123456
${wrongPass}         654321
${myPage}            //*[@id="mypage"]
${myPageBack}        //div[@id='backToStart']//button[@id='mypage']
${audiQ7}            //*[@id="bookQ7pass5"]
${pickBrand}         //*[@id="ms-list-1"]/button
${audiBox}           //*[@id="ms-opt-1"]
${pickPassengers}    //*[@id="ms-list-2"]/button
${passenger 5}       //*[@id="ms-opt-6"]
${carFromList}       //*[@id="bookQ7pass5"]
${listOfCars}        http://rental23.infotiv.net/webpage/html/gui/showCars.php
${cardNumb}          9876543219876543
${cardName}          C.T
${CVC}               222


*** Test Cases ***
test 1 flöde boka bil och verifiering
    Given Log in       ${mail}     ${password}
     When Pick dates for rental
     Then Book AUDI Q7
     And Type in card info
     And Go to my page
     And Cancel latest booking

test 2 negativ, filtrera bil
     Given Log in       ${mail}     ${password}
     When Pick dates for rental
     Then Select car brand and amount of passengers

test 3 avboka bil
     Given Log in       ${mail}     ${password}
     When Go to my page
     Then Cancel latest booking
     And Close browser

test 4 negativ, logga in fel lösenord
     Given Log in wrong password
     When Log in       ${mail}     ${password}
     Then Log out

test 5 logga ut
     Given Log in       ${mail}     ${password}
     Then Log out

test 6 visa/göm boknings historik
     Given Log in       ${mail}     ${password}
     When Go to my page
     Then Show order history
     And Hide order history

test 7 flödet för att gå till val av datum
     Given Log in       ${mail}     ${password}
     When Pick dates for rental
     Then Back to date selection


*** Keywords ***
Open car rental page
    [Documentation]     Öppna infotiv car rental
    [Tags]              VG_TEST_setup
    Open Browser        ${url}      ${browser}
    Maximize Browser Window

Log in
    [Documentation]     Loggar in på existerande konto
    [Tags]              VG_TEST_log in
    [Arguments]     ${mail}      ${password}
    Open car rental page
    Input text         //input[@id='email']    ${mail}
    Input Password         //input[@id='password']    ${password}
    Click Button        //button[@id='login']
    Wait Until Page Contains    You are signed in

Book AUDI Q7
    [Documentation]     Boka bil
    [Tags]              VG_TEST_book car
    Click Button       ${audiQ7}
    Wait Until Page Contains    Audi Q7

Pick dates for rental
    [Documentation]     Välj datum för bil hyrning och fortsätt
    [Tags]              VG_TEST_date for rental
    Input Text          //*[@id="start"]    0410
    Input Text          //*[@id="end"]    0410
    Click Button        //button[@id='continue']
    Wait Until Page Contains    2024-04-10


Select car brand and amount of passengers
    [Documentation]     Det här är ett negativt test som testar: Välj bil märke och antal passagerare
    [Tags]              VG_TEST_brand/passengers
    Click Button        ${pick brand}
    Click Button        ${audiBox}
    Click Button        ${pick passengers}
    Click Button        ${passenger 5}
    Wait Until Page Does Not Contain    Book
    Close Browser

Type in card info
    [Documentation]     Skriv in information
    [Tags]              VG_TEST_card info
    Input Text          //input[@id='cardNum']    ${cardNumb}
    Input Text          //input[@id='fullName']   ${cardName}
    Input Text          //input[@id='cvc']        ${CVC}
    Click Button        //button[@id='confirm']
    Wait Until Page Contains   You can view your booking on your page

Log in wrong password
    [Documentation]     Loggar in med fel lösenord
    [Tags]              VG_TEST_wrong password
    Open car rental page
    Input Text          //input[@id='email']    ${mail}
    Input Text          //input[@id='password']    ${wrongPass}
    Click Button        //button[@id='login']
    Wait Until Page Contains    Wrong e-mail or password

Go to my page
    [Documentation]     Gå till min sida
    [Tags]              VG_TEST_my page
    Click Button        ${myPage}
    Wait Until Page Contains    My bookings

Cancel latest booking
    [Documentation]     Cancel a booked car
    [Tags]              VG_TEST_cancel booking
    Click Button    //*[@id="unBook1"]
    Handle Alert
    Wait Until Page Contains    has been Returned
    Close Browser

Log out
    [Documentation]     Logga ut
    [Tags]              VG_TEST_log out
    Click Button        //button[@id='logout']
    Wait Until Page Contains Element    //input[@id='email']
    Close Browser

Show order history
    [Documentation]     Se bokningshistorik
    [Tags]              VG_TEST_history
    Click Button        //button[@id='show']
    Wait Until Page Contains    My order history

Hide order history
    [Documentation]     Göm bokningshistorik
    [Tags]              VG_TEST_hide history
    Click Button        //button[@id='hide']
    Wait Until Page Does Not Contain    My order history
    Close Browser

Reset date
    [Documentation]     Nollställ valt datum
    [Tags]              VG_TEST_reset date
    Click Button        //button[@id='reset']

Back to date selection
    [Documentation]     Gå tillbaka till att välja datum
    [Tags]              VG_TEST_back date selection
    Click Button        //button[@id='backToDateButton']
    Wait Until Page Contains    When do you want to make your trip?
    Close Browser




