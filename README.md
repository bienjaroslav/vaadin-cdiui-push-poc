# Vaadin CDI UI server push PoC

The PoC testing Vaadin's [push](https://vaadin.com/docs/-/part/framework/advanced/advanced-push.html) on top of Vaadin's CDI UI.

The sample application can be used as well to demonstrate the usage of Vaadin's server push mechanism in automatic mode
to send messages from server to user/client browser without the need of server round trip from a browser.

The PoC was created as encountered issues with push functionality on top of Vaadin 7 and CDI UI; 
tests proved the push fully working correctly for Vaadin 8 (thus if you create application on top of Vaadin 7 and using CDI UI then
it is recommended to migrate to Vaadin 8 if you need push).

Tested with Payara Server 171.1 Full. Sample application should work on all Java EE 7
application servers.


## Source code description

Application consists of SendingUI where the message to be sent to other UIs could be entered
 (in real life projects the messages could be sent from background threads, but as well
 from such SendingUI in chatting application).
Within the SendingUI the user can open several ReceivingUIs at the same time, each with different push transport. 
The transport for push can be chosen when opening a ReceivingUI. Vaadin supports following push transports:
- WebSockets
- WebSockets + XHR
- HTTP long polling

SendingUI and ReceivingUIs are linked in back-end using singleton broadcaster
 which accepts message from SendingUI and broadcasts it to ReceivingUI(s)
 (the broadcaster pattern is described in
 [Vaadin's documentation](https://vaadin.com/docs7/-/part7/framework/advanced/advanced-push.html#advanced.push.pusharound.broadcaster)).


## Building and running

- Maven, JDK 8 and any JavaEE 7 compliant application server are required to build and run the application
- run `mvn package` to build application
- deploy application `vaadin-cdiui-push-poc-v8/target/vaadin-cdiui-push-poc-v8.war` to your application server to test Vaadin v8
- and/or deploy application `vaadin-cdiui-push-poc-v7/target/vaadin-cdiui-push-poc-v7.war` to your application server to test Vaadin v7


## Testing

1. Open SendingUI in browser
..- for Vaadin v8 *http://host:port/vaadin-cdiui-push-poc-v8/sending* (e.g. <http://localhost:8080/vaadin-cdiui-push-poc-v8/sending>)
..- for Vaadin v7 *http://host:port/vaadin-cdiui-push-poc-v7/sending* (e.g. <http://localhost:8080/vaadin-cdiui-push-poc-v7/sending>)
2. Wihtin SendingUI click on link to open 1 or more ReceivingUIs with appropriate push transport (ReceivingUIs are opened in separate browser windows)
3. Within SendingUI browser window type a message and press *Send*
4. Go to ReceivingUIs browser windows and check that the message is visible on the page

If the message is not visible within the ReceivingUI then there might be some issue with server push. You can click on *Refresh* button to perform
dummy server round trip which will ensure the browser state is synchronized with back-end and the message(s) should pop up. If it is the case then the
push does not work for given application server/browser/transport method/Vaadin version etc.

You can check as well console/log file within Java temporary directory with the log containing application debug messages to see what's happening.

**Note on testing Vaadin v7:**
As of now you will notice the push being working for v8 but not for v7 (with transports based on WebSockets)
 where the UI is refreshed only upon server round trip and moreover there is exception thrown
upon ReceivingUI registering for push messages receiving (extract from stack trace):

```
...
Warning:   java.lang.String cannot be cast to org.atmosphere.cpr.BroadcasterFuture
java.lang.ClassCastException: java.lang.String cannot be cast to org.atmosphere.cpr.BroadcasterFuture
...
Severe:   java.lang.ClassCastException: java.lang.String cannot be cast to javax.servlet.AsyncContext
...
```


## Links
- [Vaadin Framework - Advanced Topics - Vaadin CDI Add-on] (https://vaadin.com/docs/-/part/framework/advanced/advanced-cdi.html)
- [Vaadin Framework - Advanced Topics - Server Push](https://vaadin.com/docs/-/part/framework/advanced/advanced-push.html)

