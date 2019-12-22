package com.chicman.utility;

import com.chicman.ChicManUI;
import com.vaadin.ui.Image;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class ImageUploader implements Receiver, SucceededListener {

	private final ChicManUI eventHandler;
	private final Image imagePlaceHolder;
	private File file;
	
	public ImageUploader(ChicManUI eventHandler, Image imagePlaceHolder) {
		this.eventHandler = eventHandler;
		this.imagePlaceHolder = imagePlaceHolder;
	}

	@Override
	public void uploadSucceeded(SucceededEvent event) {
		eventHandler.setImageSourceToPlaceholder(file, imagePlaceHolder);
	}

	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		FileOutputStream output = null;
		file = new File(filename);
		
		try {
			output = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return output;
	}

}
