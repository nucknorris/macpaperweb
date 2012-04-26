package de.htwkleipzig.mmdb.gui;

import java.util.Collection;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.htwkleipzig.mmdb.mvc.controller.HomeController;
import de.htwkleipzig.mmdb.service.ElasticsearchService;
import de.htwkleipzig.mmdb.vaadin.Injector;

public class FullTextSearch extends VerticalLayout {

	Window subwindow;

	private static final Logger logger = LoggerFactory
			.getLogger(FullTextSearch.class);
	private final TextField editor = new TextField("insert text:");
	Label plainText = null;
	VerticalLayout layout;

	@Autowired
	private ElasticsearchService elasticService;

	public FullTextSearch() {
		Injector.inject(this);

		// Create the window...
		subwindow = new Window("Search a fulltextSearch");
		// ...and make it modal
		subwindow.setModal(true);
		subwindow.setHeight(80, UNITS_PERCENTAGE);
		subwindow.setWidth(80, UNITS_PERCENTAGE);

		layout = (VerticalLayout) subwindow.getContent();
		// Configure the windws layout; by default a VerticalLayout

		layout.setMargin(true);
		layout.setSpacing(true);
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
		Button search = new Button("start", new Button.ClickListener() {

			// inline click-listener
			public void buttonClick(ClickEvent event) {
				if (plainText != null) {
					//
					layout.removeComponent(plainText);
					plainText = null;
				}
				// close the window by removing it from the parent window
				String value = (String) editor.getValue();
				logger.info("search for a query");

				QueryStringQueryBuilder qb = QueryBuilders.queryString(value)
						.allowLeadingWildcard(false).useDisMax(true);
				SearchResponse search = elasticService.searchsearch(
						HomeController.PAPERINDEX, qb);
				logger.info("total hits {}", search.getHits().getTotalHits());
				logger.info("MaxScore {}", search.getHits().getMaxScore());
				StringBuilder sb = new StringBuilder();
				for (SearchHit hit : search.getHits().getHits()) {
					if (hit.isSourceEmpty()) {
						logger.info("source is empty");
					}
					logger.info("id of the document {}", hit.getId());
					logger.info("score of the hit {}", hit.getScore());

					Map<String, Object> source = hit.sourceAsMap();
					for (String key : source.keySet()) {
						logger.info("key of the source {}", key);
						// logger.info(source.get(key).toString());
						sb.append(source.get(key).toString());
					}
				}
				logger.info("MaxScore {}", search.getHits().getHits());
				logger.debug("Wert des editors {}", value);

				plainText = new Label(sb.toString());
				plainText.setContentMode(Label.CONTENT_XHTML);

				layout.addComponent(plainText);
				layout.setComponentAlignment(plainText, Alignment.BOTTOM_LEFT);

			}
		});

		setSpacing(true);

		editor.setImmediate(true);

		// The components added to the window are actually added to the window's
		// layout; you can use either. Alignments are set using the layout#
		layout.addComponent(editor);
		layout.addComponent(close);
		layout.addComponent(search);

		layout.setComponentAlignment(search, Alignment.BOTTOM_LEFT);
		layout.setComponentAlignment(close, Alignment.BOTTOM_LEFT);
		layout.setComponentAlignment(editor, Alignment.TOP_CENTER);

	}

	public Window getSubwindow() {
		return this.subwindow;
	}
}
