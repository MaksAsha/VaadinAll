package com.askhaev.VaadinAll;

import com.vaadin.data.Binder;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Component
@Scope("prototype")
public class UserPage extends HorizontalLayout {

    @Autowired
    UserRepository userRepository;

    Grid<User> grid = new Grid<>();
    FormLayout formLayout = new FormLayout();

    TextField textFieldName = new TextField("name");
    Button button = new Button(VaadinIcons.SAFE);
    private final Binder<User> binder = new Binder<>();

    public UserPage() {
        setSpacing(true);
        setSizeFull();

        grid.addColumn(User::getId).setCaption("id");
        grid.addColumn(User::getName).setCaption("name");

        grid.addItemClickListener(itemClick -> {
            User item = itemClick.getItem();
            if (item != null)
                binder.readBean(item);
        });

        binder.forField(textFieldName).bind(User::getName, User::setName);

        formLayout.addComponents(textFieldName, button);
        button.addClickListener(e -> {
            User user = new User();
            user.setName(textFieldName.getValue());
            userRepository.save(user);

//            userRepository.save(binder.getBean());
            grid.setItems(userRepository.findAllByOrderByNameDesc());
        });

        addComponents(grid, formLayout);
    }

    @PostConstruct
    public void init() {
        grid.setItems(userRepository.findAllByOrderByNameDesc());
    }
}
