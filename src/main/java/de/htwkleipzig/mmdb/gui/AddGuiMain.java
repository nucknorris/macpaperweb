package de.htwkleipzig.mmdb.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window;

import de.htwkleipzig.mmdb.service.ElasticsearchService;

public class AddGuiMain extends Application {

	private Window window;
	@Autowired
	private ElasticsearchService elasticService;

	private static final Logger logger = LoggerFactory
			.getLogger(AddGuiMain.class);
	private static final String CAPTION = "Upload";
	private static final String TOOLTIP = "Upload stuff";

	public void init() {
		window = new Window("MacPaper - MAin Page");
		setMainWindow(window);
		final UploadDialog up = new UploadDialog();
		// Button w/ text and tooltip
		// Add a button for opening the subwindow
		Button open = new Button("Open modal window",
				new Button.ClickListener() {
					// inline click-listener
					public void buttonClick(ClickEvent event) {
						if (up.getSubwindow().getParent() != null) {
							// window is already showing
							window.showNotification("Window is already open");
						} else {
							// Open the subwindow by adding it to the parent
							// window
							window.addWindow(up.getSubwindow());
						}
					}
				});

		window.addComponent(open);
	}

}
