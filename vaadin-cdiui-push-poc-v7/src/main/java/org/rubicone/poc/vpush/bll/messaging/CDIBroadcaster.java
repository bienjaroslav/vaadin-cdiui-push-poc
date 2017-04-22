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
package org.rubicone.poc.vpush.bll.messaging;

import com.vaadin.ui.UI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.ejb.Singleton;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class CDIBroadcaster {

    private final ExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadExecutor();
    
    private final List<MessageListener> LISTENERS = new CopyOnWriteArrayList<>();

    public static interface MessageListener {
        
        void notifyMessage(String message);
        
    }
    
    public void register(MessageListener listener) {
        log.debug("registering listener/UI '{}'", ((UI) listener).getUIId());
        
        this.LISTENERS.add(listener);
    }

    public void unregister(MessageListener listener) {
        log.debug("unregistering listener/UI '{}'", ((UI) listener).getUIId());
        
        this.LISTENERS.remove(listener);
    }

    public void broadcast(String message) {
        log.debug("broadcasting message '{}'", message);

        new ArrayList<>(this.LISTENERS)
                .stream()
                .forEach(l -> {
                    this.EXECUTOR_SERVICE.execute(() -> l.notifyMessage(message));
                });
    }

}
