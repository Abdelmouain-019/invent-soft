package com.project;

import java.io.File;

import com.project.ViewFactory;
import com.project.Database.Database;
import com.project.Events.NameChangedPublisher;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        Database.Init();
        NameChangedPublisher np = new NameChangedPublisher();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ViewFactory viewFactory = new ViewFactory(stage);
        viewFactory.showLoginView();
    }
}
