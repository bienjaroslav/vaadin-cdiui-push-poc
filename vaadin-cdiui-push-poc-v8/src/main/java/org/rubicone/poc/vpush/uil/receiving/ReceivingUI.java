/*
 * Copyright 2017 Jaroslav Bien.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.rubicone.poc.vpush.uil.receiving;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.Date;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.rubicone.poc.vpush.bll.messaging.CDIBroadcaster;
import org.rubicone.poc.vpush.bll.messaging.CDIBroadcaster.MessageListener;

@Slf4j
public abstract class ReceivingUI extends UI implements MessageListener {

    @Inject
    private CDIBroadcaster broadcaster;
    
    private VerticalLayout receivedMessages;
    
    @Override
    protected void init(final VaadinRequest vaadinRequest) {
        this.initializeUI();
        
        this.registerForPushMessages();
    }
    
    private void initializeUI() {
        VerticalLayout mainLayout = new VerticalLayout();
        super.setContent(mainLayout);
               
        mainLayout.addComponent(this.createTitleLabel());
        
        mainLayout.addComponent(this.createInstructionsPanel());
        
        mainLayout.addComponent(this.createReceivedMessagesPanel());
    }
    
    private Label createTitleLabel() {
        Label titleLabel = new Label("receiving push messages (via " + this.getPushConfiguration().getTransport() + ")");
        
        titleLabel.addStyleName(ValoTheme.LABEL_HUGE);
        
        return titleLabel;
    }
    
    private Panel createInstructionsPanel() {
        Panel instructionsPanel = new Panel("instructions");
        VerticalLayout instructionsLayout = new VerticalLayout();
        instructionsPanel.setContent(instructionsLayout);
        
        Label instructionsLabel = new Label("the received messages should automatically appear in the below panel without requiring any user interaction/server round trip");
        instructionsLayout.addComponent(instructionsLabel);
        
        Label a = new Label("<hr/>", ContentMode.HTML);
        a.setWidth("100%");
        a.addStyleName(ValoTheme.LABEL_TINY);
        instructionsLayout.addComponent(a);
        
        Button refreshButton = new Button("refresh", VaadinIcons.REFRESH);
        refreshButton.addClickListener(event -> log.debug("manual refresh of received messages with server roundtrip"));
        instructionsLayout.addComponent(refreshButton);
        
        Label noteLabel = new Label("<i>" + VaadinIcons.INFO_CIRCLE_O.getHtml() + "note: dummy refresh button to perform server round trip to synchronize with server"
                + " side status of UI (to check for message -> in case visible after and not prior then the push does not work)</i>", ContentMode.HTML);
        noteLabel.addStyleName(ValoTheme.LABEL_TINY);
        instructionsLayout.addComponent(noteLabel);
        
        return instructionsPanel;
    }
    
    private Panel createReceivedMessagesPanel() {
        Panel receivedMessagesPanel = new Panel("received messages");
        
        this.receivedMessages = new VerticalLayout();
        
        receivedMessagesPanel.setContent(this.receivedMessages);
        
        return receivedMessagesPanel;
    }
    
    private void registerForPushMessages() {
        log.debug("UI '{}' registering for receiving push messages via '{}'", this.getUIId(), this.getPushConfiguration().getTransport());
        
        this.broadcaster.register(this);
    }

    @Override
    public void detach() {
        log.debug("unregistering UI '{}' for receiving push messages via '{}'", this.getUIId(), this.getPushConfiguration().getTransport());
        
        this.broadcaster.unregister(this);
        
        super.detach();
    }

    @Override
    public void notifyMessage(String message) {
        log.debug("UI '{}' notified with push message '{}', adding it into UI via '{}'", this.getUIId(), message, this.getPushConfiguration().getTransport());
        
        super.access(() -> this.receivedMessages.addComponent(new Label(new Date() + ": " + message)));
    }

}
