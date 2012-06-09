package de.htwkleipzig.mmdb.mvc.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/*
 * 
 * Controller zum herunterladen von Dateien
 * ohne Caching
 * 
 * @author men0x
 * 
 */
public class FileDownloadController implements Controller {
	//

	private String filePath;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(FileDownloadController.class);

	//
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ServletContext servletContext = request.getSession()
				.getServletContext();

		String file = request.getParameter("file");
		String fileName = request.getParameter("fileName");

		// vielleicht nocht den rootpfad geben lassen bei unix gibt es ja keine
		// probleme aber windows?

		String tempDestPath = new File(new File("").getCanonicalPath())
				.toString()
				+ System.getProperty("file.separator")// < hier dann der
														// rootpfad
				+ this.filePath
				+ System.getProperty("file.separator")
				+ fileName;
		// sieht dann so aus / + filePath + / + fileName

		File downloadFile = new File(tempDestPath);

		// debuggin start

		System.out.println("filePath:" + tempDestPath);
		System.out.println("File da?:" + downloadFile.exists()
				+ " Leserechte?:" + downloadFile.isFile());

		// debugging ende
		try {
			downloadFile = new File(tempDestPath);
		} catch (Exception ex) {

			// TODO exception text

		}

		/*
		 * prüfe up file > 0
		 */

		int fileSize = (int) downloadFile.length();
		if (fileSize > 0) {

			BufferedInputStream in = new BufferedInputStream(
					new FileInputStream(downloadFile));
			String mimeType = servletContext.getMimeType(file);

			response.setBufferSize(fileSize);
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ fileName + "\"");
			response.setContentLength(fileSize);

			FileCopyUtils.copy(in, response.getOutputStream());
			in.close();
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} else {
			// einfach eine kleine html zurückgeben, falls die datei nicht da
			// ist
			response.setContentType("text/html");
			PrintWriter printwriter = response.getWriter();
			printwriter.println("<html>");
			printwriter.println("<br><h2>Datei:<br>" + file + "</h2>");
			printwriter
					.println("<br><h3><a href='javascript: history.go(-1)'>Zurück / Back </a></h3>");
			printwriter.println("<br>&copy; webAccess");
			printwriter.println("</html>");
			printwriter.flush();
			printwriter.close();
			// kann man ja noch austauchen mit ner jsp oder so was
		}

		return null;
	}

	// der path steht schon im controller fest eingecoded

	// return speicherort der datei
	public String getFilePath() {
		return filePath;
	}

	// speicherort der datei setzen
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
