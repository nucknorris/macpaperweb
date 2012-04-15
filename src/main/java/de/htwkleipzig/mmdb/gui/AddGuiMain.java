package de.htwkleipzig.mmdb.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.Application;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.htwkleipzig.mmdb.vaadin.Injector;

public class AddGuiMain extends Application {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5505881212388372414L;
	private Window window;

	private static final Logger logger = LoggerFactory
			.getLogger(AddGuiMain.class);
	private static final String CAPTION = "Upload";
	private static final String TOOLTIP = "Upload stuff";

	public void init() {
		window = new Window("MacPaper - MAin Page");
		setMainWindow(window);
		logger.info("Supercooles windowgeladen");
		final UploadDialog up = new UploadDialog();
		// Button w/ text and tooltip
		// Add a button for opening the subwindow
		Button open = new Button("Open modal window",
				new Button.ClickListener() {
					/**
					 * 
					 */
					private static final long serialVersionUID = -4267332513514597320L;

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

		Button listDocuments = new Button("Open file chooser",
				new Button.ClickListener() {
					/**
					 * 
					 */
					private static final long serialVersionUID = -7453985490712171418L;

					// inline click-listener
					public void buttonClick(ClickEvent event) {
						if (up.getSubwindow().getParent() != null) {
							// window is already showing
							window.showNotification("Window is already open");
						} else {
							// Open the subwindow by adding it to the parent
							// window
							window.addWindow(new ListDocuments().getSubwindow());
						}
					}
				});

		Button getDocument = new Button("Open file chooser",
				new Button.ClickListener() {
					/**
					 * 
					 */
					private static final long serialVersionUID = -7453985490712171418L;

					// inline click-listener
					public void buttonClick(ClickEvent event) {
						if (up.getSubwindow().getParent() != null) {
							// window is already showing
							window.showNotification("Window is already open");
						} else {
							// Open the subwindow by adding it to the parent
							// window
							window.addWindow(new ListDocuments().getSubwindow());
						}
					}
				});

		Button searchDocument = new Button("fulltext search",
				new Button.ClickListener() {
					/**
					 * 
					 */
					private static final long serialVersionUID = -7453985490712171418L;

					// inline click-listener
					public void buttonClick(ClickEvent event) {
						if (up.getSubwindow().getParent() != null) {
							// window is already showing
							window.showNotification("Window is already open");
						} else {
							// Open the subwindow by adding it to the parent
							// window
							window.addWindow(new SearchDocument()
									.getSubwindow());
						}
					}
				});

		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);

		window.setContent(layout);
		layout.addComponent(open);
		layout.addComponent(searchDocument);

		// The components added to the window are actually added to the window's
		// layout; you can use either. Alignments are set using the layout#
		layout.addComponent(listDocuments);

		layout.setComponentAlignment(open, Alignment.BOTTOM_LEFT);
		layout.setComponentAlignment(searchDocument, Alignment.BOTTOM_LEFT);
		layout.setComponentAlignment(listDocuments, Alignment.TOP_CENTER);
	}

}
