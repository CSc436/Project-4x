package com.shared;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.shared.model.commands.Command;

public interface MyFactory extends AutoBeanFactory {

	AutoBean<Command> command();

}
