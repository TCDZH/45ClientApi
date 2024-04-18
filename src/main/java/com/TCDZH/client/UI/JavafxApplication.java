package com.TCDZH.client.UI;

import com.TCDZH.client.ClientApplication;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class JavafxApplication extends Application {

    private ConfigurableApplicationContext context;

    @Override
    public void init() {

        //Registers these 3 things as beans so that they can be referenced/injected into different areas of the code
        ApplicationContextInitializer<GenericApplicationContext> initializer = genericApplicationContext -> {
            genericApplicationContext.registerBean(Application.class, () -> JavafxApplication.this);
            genericApplicationContext.registerBean(Parameters.class, () -> getParameters());//arguments
            genericApplicationContext.registerBean(HostServices.class, () -> getHostServices());//object that can be used to perform certain operations
        };

        this.context = new SpringApplicationBuilder().sources(ClientApplication.class)
                .initializers(initializer)
                .build().run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.context.publishEvent(new StageReadyEvent(primaryStage));//this broadcasts the stage so that any part of the code
                                                                    //that wants to use the stage needs only to listen for this event and it will get the stage
    }

    @Override
    public void stop() throws Exception {
        this.context.close();
        Platform.exit();
    }

    //Wrapper for the stage that allows it to be broadcast as an event
     class StageReadyEvent extends ApplicationEvent {

        public Stage getStage() {
            return Stage.class.cast(getSource());
        }

        public StageReadyEvent(Object source) {
            super(source);
        }
    }
}
