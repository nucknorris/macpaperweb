package de.htwkleipzig.mmdb.gui;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.AbstractSelect.Filtering;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.htwkleipzig.mmdb.mvc.controller.HomeController;
import de.htwkleipzig.mmdb.service.ElasticsearchService;
import de.htwkleipzig.mmdb.util.TikaParser;
import de.htwkleipzig.mmdb.util.Utilities;
import de.htwkleipzig.mmdb.vaadin.Injector;

public class ListDocuments extends VerticalLayout implements
		Property.ValueChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7619413228955358314L;
	private static final Logger logger = LoggerFactory
			.getLogger(ListDocuments.class);

	@Autowired
	private ElasticsearchService elasticService;

	Window subwindow;

	public ListDocuments() {
		Injector.inject(this);
		if(elasticService==null){
			logger.debug("komisch");
		}else{
			logger.debug("nciht so komisch");
		}
		// Create the window...
		subwindow = new Window("Choose the file, god damnit");
		// ...and make it modal
		subwindow.setModal(true);

		// Configure the windws layout; by default a VerticalLayout
		VerticalLayout layout = (VerticalLayout) subwindow.getContent();
		layout.setMargin(true);
		layout.setSpacing(true);
		// Add some content; a label and a close-button
		Label message = new Label("This is a modal subwindow.");
		subwindow.addComponent(message);

		Button close = new Button("Close", new Button.ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -6545625632671563939L;

			// inline click-listener
			public void buttonClick(ClickEvent event) {
				// close the window by removing it from the parent window
				(subwindow.getParent()).removeWindow(subwindow);
			}
		});

		setSpacing(true);

		ComboBox l = new ComboBox("Please select a city");
		for (File file : Utilities.allFiles()) {
			l.addItem(file.getName());
		}

		l.setFilteringMode(Filtering.FILTERINGMODE_OFF);
		l.setImmediate(true);
		l.addListener(this);

		layout.addComponent(l);

		// The components added to the window are actually added to the window's
		// layout; you can use either. Alignments are set using the layout#
		layout.addComponent(close);

		layout.setComponentAlignment(close, Alignment.BOTTOM_LEFT);
		layout.setComponentAlignment(l, Alignment.TOP_CENTER);
	}

	public Window getSubwindow() {
		return this.subwindow;
	}

	/*
	 * Shows a notification when a selection is made.
	 */
	public void valueChange(ValueChangeEvent event) {

		logger.debug("selected file {}", event.getProperty());
		TikaParser tika = new TikaParser();
		List<String> listOfKeywords = new ArrayList<String>();
		listOfKeywords.add("Summary");
		String abs = null;
		if (event.getProperty() != null) {
			try {
				abs = tika.parsePdfToXml(new URL("file:///tmp/uploads/"
						+ event.getProperty().toString()));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		if (abs != null) {
			// logger.info("Summary; {}", abs);
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("htmlPDF", abs);
		if (elasticService != null) {
			elasticService.save(HomeController.PAPERINDEX,
					HomeController.INDEXTYPE, event.getProperty().toString(), data);
		} else {
			logger.info("Scheisse");
		}
		// getWindow().showNotification("Selected city: " +
		// event.getProperty());

	}
}
