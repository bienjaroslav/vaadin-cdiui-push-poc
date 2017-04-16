package org.rubicone.poc.vpush.uil.receiving;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;

@CDIUI("receive-messages-websockets")
@Push
@Theme("vaadincdiuipushpoctheme")
public class WebsocketsUI extends ReceivingUI {
    
}
