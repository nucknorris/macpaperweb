package de.htwkleipzig.mmdb.gui;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class UploadDialog extends VerticalLayout {

    /**
     * @uml.property  name="subwindow"
     * @uml.associationEnd  multiplicity="(1 1)"
     */
    Window subwindow;

    public UploadDialog() {

        subwindow = new Window("File uploader");
        subwindow.setModal(true);
        subwindow.setHeight(280, UNITS_PIXELS);
        subwindow.setWidth(500, UNITS_PIXELS);
        VerticalLayout layout = (VerticalLayout) subwindow.getContent();
        layout.setMargin(true);
        layout.setSpacing(true);
        MyUploader uploader = new MyUploader();
        Label message = new Label("To upload a new file choose file and click on upload now.");
        subwindow.addComponent(message);

        Button close = new Button("Close", new Button.ClickListener() {

            public void buttonClick(ClickEvent event) {
                (subwindow.getParent()).removeWindow(subwindow);
            }
        });
        layout.addComponent(uploader);
        layout.addComponent(close);

        layout.setComponentAlignment(close, Alignment.BOTTOM_LEFT);
        layout.setComponentAlignment(uploader, Alignment.TOP_CENTER);

    }

    /**
     * @return
     * @uml.property  name="subwindow"
     */
    public Window getSubwindow() {
        return this.subwindow;
    }
}
