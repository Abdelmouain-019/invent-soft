package com.project.Events;

import java.util.ArrayList;
import java.util.List;

import com.project.Events.interfaces.NameChanged;

public class NameChangedPublisher {
    private List<NameChanged> listeners = new ArrayList<>();

    public static NameChangedPublisher instance;

    public NameChangedPublisher() {
        instance = this;
    }

    public void addListener(NameChanged l) {
        listeners.add(l);
    }

    public void removeListener(NameChanged l) {
        listeners.remove(l);
    }

    public void fireEvent() {
        for (NameChanged c : listeners) {
            c.onEvent();
        }
    }
}
