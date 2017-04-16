package org.rubicone.poc.vpush.uil.receiving;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.shared.ui.ui.Transport;

@CDIUI("receive-messages-websocketsxhr")
@Push(transport = Transport.WEBSOCKET_XHR)
@Theme("vaadincdiuipushpoctheme")
public class WebsocketsXhrUI extends ReceivingUI {
    
}
