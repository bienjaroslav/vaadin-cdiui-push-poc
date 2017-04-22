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

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.shared.ui.ui.Transport;

@CDIUI("receive-messages-httplongpolling")
@Push(transport = Transport.LONG_POLLING)
@Theme("vaadincdiuipushpoctheme")
public class HttpLongPollingUI extends ReceivingUI {

}
