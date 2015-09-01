package com.capgemini.starterkit.imageviewer.dataprovider.data;

public class ImageVO {

	private String filename;

	public ImageVO(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Override
	public String toString() {
		return getFilename();
	}
}
