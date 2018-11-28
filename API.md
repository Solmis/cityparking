**Start Parking**
----
Creates a parking ticket for a vehicle. Returns generated ticket ID, start time of the ticket and vehicle ID.

* **URL**

    `/startParking`
    
* **Method**

    `POST`
    
* **Data Params**

    `parkedVehicle: String`
    
* **Success response**

    * **Code:** 200
    * **Content:** 
    `{ ticketId: 42, startTimestamp: "2018-11-28T20:09:11.2902318+01:00", vehicleId: {"licensePlate": "ABC2360"} } `
    
* **Error response**

    * **Code:** 400
    * **Content:** `{ message: "Attribute parkedVehicle is required." }`
    
    
**End Parking**
----
Stops parking ticket with given ID. Returns ticket ID and stop time of the ticket.

* **URL**

    `/endParking`
    
* **Method**

    `POST`
    
* **Data Params**

    `ticketId: long`
    
* **Success response**

    * **Code:** 200
    * **Content:** 
    `{ ticketId: 42, endTimestamp: "2018-11-28T20:09:11.2902318+01:00" }`
    
* **Error response**

    * **Code:** 400
    * **Content:** `{ message: "Attribute ticketId is required." }`
    
**GetParkingReceipt**
----
Returns parking receipt data (i.e. parking time and cost).

* **URL**

    `/getParkingReceipt`
    
* **Method**

    `POST`
    
* **Data Params**

    `ticketId: long`
    
    `driverType: String  // values accepted: "regular" and "disabled"`
    
* **Success response**

    * **Code:** 200
    * **Content:** 
    `{
         ticketId: 42,
         driverType: "regular",
         currency: "PLN",
         defaultCurrency: "PLN",
         totalPriceInCents: 100,
         totalTimeInSeconds: 592,
         totalPriceInDefaultCurrency: 100
     }`
    
* **Error response**

    * **Code:** 400
    * **Content:** `{ message: "First end parking before trying to get a receipt." }`
    
    
**CheckVehicle**
----
Secret token required. Checks if given vehicle has a parking ticket.

* **URL**

    `/checkVehicle`
    
* **Method**

    `POST`
    
* **Data Params**

    `parkedVehicle: String`
    
    `secretToken: String`
    
* **Success response**

    * **Code:** 200
    * **Content:** 
    `{
         vehicleId: {
            "licensePlate": "WE12345"
         },
         hasTicket: "false"
     }`
    
* **Error response**

    * **Code:** 400
    * **Content:** `{ message: "Attribute parkedVehicle is required." }`
    
    OR
    
    * **Code:** 401
    * **Content:** `{ message: "Incorrect secretToken." }`
    
    
**GetDayIncome**
----
Secret token required. Returns total day income for a given day (in default currency).

* **URL**

    `/getDayIncome`
    
* **Method**

    `POST`
    
* **Data Params**

    `day: String // for example: "2018-01-31"`
    
    `secretToken: String`
    
* **Success response**

    * **Code:** 200
    * **Content:** 
    `{
         day: "2018-11-28",
         currency: "PLN",
         totalIncomeInCents: 100
     }`
    
* **Error response**

    * **Code:** 400
    * **Content:** `{ message: "Attribute day is required." }`
    
    OR
    
    * **Code:** 401
    * **Content:** `{ message: "Incorrect secretToken." }`