**API**

API is described in file `API.md`.

**ASSUMPTIONS**

* System time is set properly (including time zone).
* Prices in other currencies will be like `factor * priceInDefaultCurrency`.
* Income of parking operator on a given day is a sum of costs of tickets "stopped" on this day.
* No payment transactions are implemented, so `/getParkingReceipt` request adds fake payment for given ticket ID.
Fake payments are needed to test `/getDayIncome` request.
* Ticket IDs are very simple here and unsafe (anyone can stop a ticket with specific number). Change from `long` number
to a `String` long hash probably could do the job better.
* Secret token is hardcoded in `application.properties` file. It's not secure.