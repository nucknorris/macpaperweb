package de.htwkleipzig.mmdb.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.Application;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SuppressWarnings({ "serial" })
public class MainGui extends Application {
    private Window window;

    private static final Logger LOGGER = LoggerFactory.getLogger(MainGui.class);

    public void init() {
        LOGGER.debug("setting up main window");
        setTheme("reindeer");
        window = new Window("MacPaper - Main Page");
        setMainWindow(window);
        LOGGER.info("setting up upload dialog");
        final UploadDialog uploadDialog = new UploadDialog();
        LOGGER.info("setting up file list");
        final FileList fileList = new FileList();
        LOGGER.info("setting up fulltext search");
        final FullTextSearch fullTextSearch = new FullTextSearch();

        /*
         * Button to open the dialog to upload a new file.
         */
        Button upload = new Button("Upload a new file", new Button.ClickListener() {

            public void buttonClick(ClickEvent event) {
                if (uploadDialog.getSubwindow().getParent() != null) {
                    window.showNotification("Window is already open");
                } else {
                    window.addWindow(uploadDialog.getSubwindow());
                }
            }
        });

        /*
         * Button to open the dialog that lists all documents in the directory /tmp/uploads
         */
        Button listDocuments = new Button("List documents", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                if (fileList.getSubwindow().getParent() != null) {
                    window.showNotification("Window is already open");
                } else {
                    window.addWindow(fileList.getSubwindow());
                }
            }
        });

        /*
         * Button to open the fulltext-search window
         */
        Button search = new Button("fulltext search", new Button.ClickListener() {
            private static final long serialVersionUID = -7453985490712171418L;

            public void buttonClick(ClickEvent event) {
                if (fullTextSearch.getSubwindow().getParent() != null) {
                    window.showNotification("Window is already open");
                } else {
                    window.addWindow(fullTextSearch.getSubwindow());
                }
            }
        });

        Button login = new Button("login");

        /*
         * The Layout
         */

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setSpacing(true);
        mainLayout.setMargin(true);

        HorizontalLayout header = new HorizontalLayout();

        header.setMargin(true);
        header.setSpacing(true);
        header.addComponent(upload);
        header.addComponent(listDocuments);
        header.addComponent(login);

        Panel searchPanel = new Panel();
        VerticalLayout vLayout = (VerticalLayout) searchPanel.getContent();
        vLayout.addComponent(search);
        vLayout.setComponentAlignment(search, Alignment.MIDDLE_CENTER);

        mainLayout.addComponent(header);
        mainLayout.addComponent(searchPanel);
        window.setContent(mainLayout);
    }

}
