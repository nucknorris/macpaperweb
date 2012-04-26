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

public class FileList extends VerticalLayout implements
		Property.ValueChangeListener {

	private static final long serialVersionUID = -7619413228955358314L;
	private static final Logger logger = LoggerFactory
			.getLogger(FileList.class);

	@Autowired
	private ElasticsearchService elasticService;

	Window subwindow;

	public FileList() {
		Injector.inject(this);
		subwindow = new Window("Indexing file");
		subwindow.setModal(true);
		subwindow.setHeight(230, UNITS_PIXELS);
		subwindow.setWidth(300, UNITS_PIXELS);

		VerticalLayout layout = (VerticalLayout) subwindow.getContent();
		layout.setMargin(true);
		layout.setSpacing(true);
		Label message = new Label("Choose the file you want to be indexed.");
		subwindow.addComponent(message);

		Button close = new Button("Close", new Button.ClickListener() {
			private static final long serialVersionUID = -6545625632671563939L;

			public void buttonClick(ClickEvent event) {
				(subwindow.getParent()).removeWindow(subwindow);
			}
		});

		setSpacing(true);

		ComboBox filesComboBox = new ComboBox("Please select a file");
		for (File file : Utilities.getAllFilesOfDirectory()) {
			filesComboBox.addItem(file.getName());
		}

		Button startIndexingFile = new Button("Index file");

		filesComboBox.setFilteringMode(Filtering.FILTERINGMODE_OFF);
		filesComboBox.setImmediate(true);
		// startIndexingFile.addListener(this);
		filesComboBox.addListener(this);

		layout.addComponent(filesComboBox);
		layout.addComponent(startIndexingFile);
		layout.addComponent(close);
		layout.setComponentAlignment(close, Alignment.BOTTOM_LEFT);
		layout.setComponentAlignment(filesComboBox, Alignment.TOP_CENTER);
	}

	public Window getSubwindow() {
		return subwindow;
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
					HomeController.INDEXTYPE, event.getProperty().toString(),
					data);
		} else {
			logger.info("Scheisse");
		}
		// getWindow().showNotification("Selected city: " +
		// event.getProperty());

	}
}
