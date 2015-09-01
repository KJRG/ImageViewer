package com.capgemini.starterkit.imageviewer.dataprovider.impl;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collection;

import com.capgemini.starterkit.imageviewer.dataprovider.DataProvider;
import com.capgemini.starterkit.imageviewer.dataprovider.data.ImageVO;

/**
 * Provides data.
 *
 * @author Krzysztof
 */
public class DataProviderImpl implements DataProvider {

	private Collection<ImageVO> images = new ArrayList<>();

	public DataProviderImpl() {
	}

	@Override
	public Collection<ImageVO> findImages(File filepaths) {

		/*
		 * Select all the files being images.
		 */
		File[] pictures = filepaths.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".gif");
			}
		});

		/*
		 * Generate new list of images.
		 */
		images.clear();
		for(File picture : pictures) {
			images.add(new ImageVO(picture.getName()));
		}

		return images;
	}
}
