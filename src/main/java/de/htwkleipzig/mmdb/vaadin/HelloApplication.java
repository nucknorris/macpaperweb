/*
 * Copyright 2009 IT Mill Ltd.
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
package de.htwkleipzig.mmdb.vaadin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.Application;
import com.vaadin.ui.Window;

import de.htwkleipzig.mmdb.gui.MyUploader;
import de.htwkleipzig.mmdb.service.ElasticsearchService;

/**
 * The Application's "main" class
 */
public class HelloApplication extends Application {
    /**
	 * 
	 */
    private static final long serialVersionUID = -786044568801260648L;
    /**
     * @uml.property  name="window"
     * @uml.associationEnd  
     */
    private Window window;
    /**
     * @uml.property  name="elasticService"
     * @uml.associationEnd  readOnly="true"
     */
    @Autowired
    private ElasticsearchService elasticService;
    private static final Logger logger = LoggerFactory.getLogger(HelloApplication.class);

    @Override
    public void init() {
        window = new Window("My Vaadin Application");
        setMainWindow(window);
        // window.addComponent(new UploadGui());

        window.addComponent(new MyUploader());
    }

}
