package com.capgemini.starterkit.imageviewer.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import com.capgemini.starterkit.imageviewer.dataprovider.DataProvider;
import com.capgemini.starterkit.imageviewer.model.ImageSearch;
import com.capgemini.starterkit.imageviewer.dataprovider.data.ImageVO;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.control.ListView;

/**
 * Controller for the image viewer screen.
 * <p>
 * The JavaFX runtime will inject corresponding objects in the @FXML annotated
 * fields. The @FXML annotated methods will be called by JavaFX runtime at
 * specific points in time.
 * </p>
 *
 * @author Krzysztof
 */
public class ImageViewerController {

	/**
	 * <p>
	 * NOTE: The variable name must be {@code resources}.
	 * </p>
	 */
	@FXML
	private ResourceBundle resources;

	/**
	 * <p>
	 * NOTE: The variable name must be {@code location}.
	 * </p>
	 */
	@FXML
	private URL location;

	@FXML
	private Button searchButton;

	@FXML
	private ListView<ImageVO> resultList;

	@FXML
	private ScrollPane scrollPane;

	@FXML
	private ImageView imageView;

	private final DataProvider dataProvider = DataProvider.INSTANCE;

	private final ImageSearch model = new ImageSearch();


	/**
	 * The JavaFX runtime instantiates this controller.
	 * <p>
	 * The @FXML annotated fields are not yet initialized at this point.
	 * </p>
	 */
	public ImageViewerController() {
	}

	/**
	 * The JavaFX runtime calls this method after the FXML file loaded.
	 * <p>
	 * The @FXML annotated fields are initialized at this point.
	 * </p>
	 * <p>
	 * NOTE: The method name must be {@code initialize}.
	 * </p>
	 */
	@FXML
	private void initialize() {
		initializeResultList();

		/*
		 * Bind controls properties to model properties.
		 */
		resultList.itemsProperty().bind(model.resultProperty());
	}

	private void initializeResultList() {
		/*
		 * Show specific text for an empty table.
		 */
		resultList.setPlaceholder(new Label(resources.getString("table.emptyText")));

		/*
		 * When list element gets selected show selected picture.
		 */
		resultList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ImageVO>() {


			@Override
			public void changed(ObservableValue<? extends ImageVO> observable, ImageVO oldValue, ImageVO newValue) {
				if(newValue == null) {
					return;
				}

				Image image = new Image("file:///" + model.getDirectoryPath() + File.separator + newValue.getFilename());
				imageView.setImage(image);

			}
		});
	}

	/**
	 * The JavaFX runtime calls this method when the <b>Search</b> button is
	 * clicked.
	 *
	 * @param event
	 *            {@link ActionEvent} holding information about this event
	 */
	@FXML
	private void searchButtonAction(ActionEvent event) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File fileDir = directoryChooser.showDialog(null);

		if(fileDir == null) {
			return;
		}

		/*
		 * Use task to execute the potentially long running call in background
		 * (separate thread), so that the JavaFX Application Thread is not
		 * blocked.
		 */
		Task<Collection<ImageVO>> backgroundTask = new Task<Collection<ImageVO>>() {

			/**
			 * This method will be executed in a background thread.
			 */
			@Override
			protected Collection<ImageVO> call() throws Exception {
				/*
				 * Get the data.
				 */
				Collection<ImageVO> result = dataProvider.findImages(
						fileDir);

				return result;
			}

			/**
			 * This method will be executed in the JavaFX Application Thread
			 * when the task finishes.
			 */
			@Override
			protected void succeeded() {
				/*
				 * Copy the result to model.
				 */
				model.setResult(new ArrayList<ImageVO>(getValue()));

				/*
				 * Set the path to selected directory in model.
				 */
				model.setDirectoryPath(fileDir.getAbsolutePath());
			}
		};

		/*
		 * Start the background task.
		 */
		new Thread(backgroundTask).start();
	}

}
