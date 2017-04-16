package org.rubicone.poc.vpush.uil.receiving;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.shared.ui.ui.Transport;

@CDIUI("receive-messages-httplongpolling")
@Push(transport = Transport.LONG_POLLING)
@Theme("vaadincdiuipushpoctheme")
public class HttpLongPollingUI extends ReceivingUI {

}
