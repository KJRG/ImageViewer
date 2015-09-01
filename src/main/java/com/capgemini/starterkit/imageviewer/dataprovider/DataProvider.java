package com.capgemini.starterkit.imageviewer.dataprovider;

import java.io.File;
import java.util.Collection;

import com.capgemini.starterkit.imageviewer.dataprovider.data.ImageVO;
import com.capgemini.starterkit.imageviewer.dataprovider.impl.DataProviderImpl;

/**
 * Provides data.
 *
 * @author Krzysztof
 */
public interface DataProvider {

	/**
	 * Instance of this interface.
	 */
	DataProvider INSTANCE = new DataProviderImpl();

	Collection<ImageVO> findImages(File filepaths);
}
