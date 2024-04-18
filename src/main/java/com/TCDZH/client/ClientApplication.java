package com.TCDZH.client;

import com.TCDZH.client.UI.JavafxApplication;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientApplication {

	public static void main(String[] args) {
		Application.launch(JavafxApplication.class, args);
	}

}
