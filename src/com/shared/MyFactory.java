package com.shared;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;

import control.commands.Command;

public interface MyFactory extends AutoBeanFactory {

	AutoBean<Command> command();

}
