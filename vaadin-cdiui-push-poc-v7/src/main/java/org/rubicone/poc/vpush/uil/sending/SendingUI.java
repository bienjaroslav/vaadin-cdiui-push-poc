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
package org.rubicone.poc.vpush.uil.sending;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractTextField.TextChangeEventMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.Date;
import javax.inject.Inject;
import org.rubicone.poc.vpush.bll.messaging.CDIBroadcaster;

@CDIUI("")
@Theme("vaadincdiuipushpoctheme")
public class SendingUI extends UI {
    
    @Inject
    private CDIBroadcaster broadcaster;

    private VerticalLayout sentMessages;
    
    @Override
    protected void init(VaadinRequest request) {
        this.initializeUI();
    }
    
    private void initializeUI() {
        VerticalLayout mainLayout = new VerticalLayout();
        super.setContent(mainLayout);
        
        mainLayout.addComponent(this.createTitleLabel());
        
        mainLayout.addComponent(this.createInstructionsPanel());
        
        mainLayout.addComponent(this.createPushMessageSendingForm());
        
        mainLayout.addComponent(this.createSentMessagesPanel());
    }
    
    private Label createTitleLabel() {
        Label titleLabel = new Label("sending push messages");
        
        titleLabel.addStyleName(ValoTheme.LABEL_HUGE);
        
        return titleLabel;
    }
    
    private Panel createInstructionsPanel() {
        Panel instructionsPanel = new Panel("instructions");
        
        VerticalLayout instructionsLayout = new VerticalLayout();
        instructionsPanel.setContent(instructionsLayout);
        
        Label instructionsLabel = new Label("open another UI receiving notifications from server using following transport:");
        instructionsLayout.addComponent(instructionsLabel);
        
        Link websocketsUILink = new Link("websockets", new ExternalResource("./receive-messages-websockets"));
        websocketsUILink.setIcon(FontAwesome.CHEVRON_RIGHT);
        websocketsUILink.setTargetName("_blank");
        instructionsLayout.addComponent(websocketsUILink);
        
        Link websocketsXhrUILink = new Link("websockets + XHR", new ExternalResource("./receive-messages-websocketsxhr"));
        websocketsXhrUILink.setIcon(FontAwesome.CHEVRON_RIGHT);
        websocketsXhrUILink.setTargetName("_blank");
        instructionsLayout.addComponent(websocketsXhrUILink);
        
        Link httpLongPollingUILink = new Link("HTTP long polling", new ExternalResource("./receive-messages-httplongpolling"));
        httpLongPollingUILink.setIcon(FontAwesome.CHEVRON_RIGHT);
        httpLongPollingUILink.setTargetName("_blank");
        instructionsLayout.addComponent(httpLongPollingUILink);
        
        Label noteLabel = new Label("<i>" + FontAwesome.INFO_CIRCLE.getHtml() + " note: you may open several UIs at the same time to receive push notifications</i>",
                ContentMode.HTML);
        noteLabel.addStyleName(ValoTheme.LABEL_TINY);
        instructionsLayout.addComponents(noteLabel);
        
        return instructionsPanel;
    }
    
    private FormLayout createPushMessageSendingForm() {
        FormLayout pushMessageSendingForm = new FormLayout();
        
        Panel pushMessageSendingPanel = new Panel("push message sending form");
        pushMessageSendingPanel.setContent(pushMessageSendingForm);
        
        TextField inputField = new TextField("text to send");
        inputField.setTextChangeEventMode(TextChangeEventMode.EAGER);
        pushMessageSendingForm.addComponent(inputField);
        
        Button sendButton = new Button("send", FontAwesome.LOCATION_ARROW);
        sendButton.setDisableOnClick(true);
        sendButton.setEnabled(false);
        sendButton.addClickListener(event -> {
            this.broadcaster.broadcast(inputField.getValue());
            this.sentMessages.addComponent(new Label(new Date() + ": " + inputField.getValue()));
            inputField.setValue("");
        });
        
        inputField.addTextChangeListener(event -> {
            sendButton.setEnabled(!event.getText().isEmpty());
        });
        
        pushMessageSendingForm.addComponent(sendButton);
        
        return pushMessageSendingForm;
    }
    
    private Panel createSentMessagesPanel() {
        Panel sentMessagesPanel = new Panel("sent messages");
        
        this.sentMessages = new VerticalLayout();
        
        sentMessagesPanel.setContent(this.sentMessages);
        
        return sentMessagesPanel;
    }
    
}
