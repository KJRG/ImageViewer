package com.capgemini.starterkit.imageviewer.model;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.starterkit.imageviewer.dataprovider.data.ImageVO;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

public class ImageSearch {

	private final StringProperty directoryPath = new SimpleStringProperty();
	private final ListProperty<ImageVO> result = new SimpleListProperty<>(
			FXCollections.observableList(new ArrayList<>()));

	public final String getDirectoryPath() {
		return directoryPath.get();
	}

	public final void setDirectoryPath(String value) {
		directoryPath.set(value);
	}

	public StringProperty directoryPathProperty() {
		return directoryPath;
	}

	public final List<ImageVO> getResult() {
		return result.get();
	}

	public final void setResult(List<ImageVO> value) {
		result.setAll(value);
	}

	public ListProperty<ImageVO> resultProperty() {
		return result;
	}

	@Override
	public String toString() {
		return "Image [directoryPath=" + directoryPath + ", result=" + result + "]";
	}
}
