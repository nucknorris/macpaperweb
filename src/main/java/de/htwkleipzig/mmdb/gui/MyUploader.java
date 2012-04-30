package de.htwkleipzig.mmdb.gui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.terminal.FileResource;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Upload;

import de.htwkleipzig.mmdb.util.Utilities;

public class MyUploader extends CustomComponent implements Upload.SucceededListener, Upload.FailedListener,
        Upload.Receiver {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    Panel root; // Root element for contained components.
    // Panel imagePanel; // Panel that contains the uploaded image.
    File file; // File to write to.

    private static final Logger LOGGER = LoggerFactory.getLogger(MyUploader.class);

    public MyUploader() {
        // Injector.injectC(this);

        root = new Panel("My Upload Component");
        setCompositionRoot(root);

        // Create the Upload component.
        final Upload upload = new Upload("Upload the file here", this);

        // Use a custom button caption instead of plain "Upload".
        upload.setButtonCaption("Upload Now");

        // Listen for events regarding the success of upload.
        upload.addListener((Upload.SucceededListener) this);
        upload.addListener((Upload.FailedListener) this);

        root.addComponent(upload);
        root.addComponent(new Label("Click 'Browse' to " + "select a file and then click 'Upload'."));

        // // Create a panel for displaying the uploaded image.
        // imagePanel = new Panel("Uploaded image");
        // imagePanel.addComponent(new Label("No image uploaded yet"));
        // root.addComponent(imagePanel);
    }

    // Callback method to begin receiving the upload.
    public OutputStream receiveUpload(String filename, String MIMEType) {
        FileOutputStream fos = null; // Output stream to write to

        file = new File(Utilities.getProperty("paper.directory"));
        if (!file.exists()) {
            LOGGER.debug("dir {} doesn't exists", Utilities.getProperty("paper.directory"));
            file.mkdir();
            LOGGER.debug("was dir cretaed? {}", file.exists());
        }
        file = new File(Utilities.getProperty("paper.directory") + filename);
        LOGGER.debug("absolut Path {}", file.getAbsolutePath());
        try {
            // Open the file for writing.
            fos = new FileOutputStream(file);
        } catch (final java.io.FileNotFoundException e) {
            // Error while opening the file. Not reported here.
            e.printStackTrace();
            return null;
        }

        return fos; // Return the output stream to write to
    }

    // This is called if the upload is finished.
    public void uploadSucceeded(Upload.SucceededEvent event) {
        // Log the upload on screen.
        root.addComponent(new Label("File " + event.getFilename() + " of type '" + event.getMIMEType() + "' uploaded."));
        // TODO - Event damit sich die Liste an Files aktualisiert. Oder: Die Datei wird direkt geparsed und die
        // Bearbeitungsuebersicht wird geladen

        // Display the uploaded file in the image panel.
        final FileResource imageResource = new FileResource(file, getApplication());

        // imagePanel.removeAllComponents();
        // imagePanel.addComponent(new Embedded("", imageResource));
    }

    // This is called if the upload fails.
    public void uploadFailed(Upload.FailedEvent event) {
        // Log the failure on screen.
        root.addComponent(new Label("Uploading " + event.getFilename() + " of type '" + event.getMIMEType()
                + "' failed."));
    }
}
