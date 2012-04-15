package de.htwkleipzig.mmdb.gui;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class UploadDialog extends VerticalLayout {

	Window subwindow;

	public UploadDialog() {

		// Create the window...
		subwindow = new Window("A modal subwindow");
		// ...and make it modal
		subwindow.setModal(true);

		// Configure the windws layout; by default a VerticalLayout
		VerticalLayout layout = (VerticalLayout) subwindow.getContent();
		layout.setMargin(true);
		layout.setSpacing(true);
		MyUploader up = new MyUploader();
		// Add some content; a label and a close-button
		Label message = new Label("This is a modal subwindow.");
		subwindow.addComponent(message);

		Button close = new Button("Close", new Button.ClickListener() {
			// inline click-listener
			public void buttonClick(ClickEvent event) {
				// close the window by removing it from the parent window
				(subwindow.getParent()).removeWindow(subwindow);
			}
		});
		// The components added to the window are actually added to the window's
		// layout; you can use either. Alignments are set using the layout#
		layout.addComponent(up);
		layout.addComponent(close);

		layout.setComponentAlignment(close, Alignment.BOTTOM_LEFT);
		layout.setComponentAlignment(up, Alignment.TOP_CENTER);

	}

	public Window getSubwindow() {
		return this.subwindow;
	}
}
