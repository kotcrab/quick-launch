/*
 * Copyright 2016 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kotcrab.quicklaunch;

import com.intellij.execution.ProgramRunnerUtil;
import com.intellij.execution.RunManager;
import com.intellij.execution.RunnerAndConfigurationSettings;
import com.intellij.execution.executors.DefaultDebugExecutor;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionToolbarPosition;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.ui.AnActionButton;
import com.intellij.ui.AnActionButtonRunnable;
import com.intellij.ui.ToggleActionButton;
import com.intellij.ui.ToolbarDecorator;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/** @author Kotcrab */
public class QuickLaunchToolWindow {
	private JPanel mainContent;
	private RunConfigList configList;
	private DefaultListModel listModel;

	private Settings settings;

	public QuickLaunchToolWindow (final Project project) {
		settings = new Settings(project);

		ToolbarDecorator decorator = ToolbarDecorator.createDecorator(configList);
		decorator.setToolbarPosition(ActionToolbarPosition.TOP);
		decorator.addExtraAction(new ToggleDebugActionButton());
		decorator.setAddAction(new AnActionButtonRunnable() {
			@Override
			public void run (AnActionButton anActionButton) {
				RunConfigSelectionDialog selectionDialog = new RunConfigSelectionDialog(project);
				if (selectionDialog.showAndGet()) {
					for (RunConfigModel newModel : selectionDialog.getSelectedConfigs()) {
						if (checkIfModelAlreadyAdded(newModel) == false) {
							listModel.addElement(newModel);
						}
					}
				}
			}

			private boolean checkIfModelAlreadyAdded (RunConfigModel newModel) {
				for (Object o : listModel.toArray()) {
					if (((RunConfigModel) o).name.equals(newModel.name)) {
						return true;
					}
				}
				return false;
			}
		});
		mainContent.add(decorator.createPanel(), BorderLayout.CENTER);

		listModel = new DefaultListModel();
		for (String name : settings.getFavoritesConfigs()) {
			for (RunnerAndConfigurationSettings config : RunManager.getInstance(project).getAllSettings()) {
				if (config.getName().equals(name)) {
					listModel.addElement(new RunConfigModel(config));
				}
			}
		}

		configList.getEmptyText().setText("There are no favorite run configurations.");
		configList.setModel(listModel);
		configList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked (MouseEvent evt) {
				if (evt.getClickCount() == 2) {

					int index = configList.locationToIndex(evt.getPoint());
					RunConfigModel model = (RunConfigModel) listModel.getElementAt(index);

					for (RunnerAndConfigurationSettings config : RunManager.getInstance(project).getAllSettings()) {
						if (config.getName().equals(model.name)) {
							RunManager.getInstance(project).setSelectedConfiguration(config);
							ProgramRunnerUtil.executeConfiguration(project, config,
									settings.isDebugMode() ? DefaultDebugExecutor.getDebugExecutorInstance() : DefaultRunExecutor.getRunExecutorInstance());
							return;
						}
					}

					listModel.remove(index);
					JOptionPane.showMessageDialog(null, "Configuration '" + model.name + "' does not exist and has been removed from favorites list.");
				}
			}
		});

		listModel.addListDataListener(new ListDataListener() {
			@Override
			public void intervalAdded (ListDataEvent e) {
				saveFavorites();
			}

			@Override
			public void intervalRemoved (ListDataEvent e) {
				saveFavorites();
			}

			@Override
			public void contentsChanged (ListDataEvent e) {
				saveFavorites();
			}
		});
	}

	public JPanel getMainContent () {
		return mainContent;
	}

	private void saveFavorites () {
		ArrayList<String> savedModels = new ArrayList<String>();
		for (Object obj : listModel.toArray()) {
			RunConfigModel model = (RunConfigModel) obj;
			savedModels.add(model.name);
		}
		settings.setFavoritesConfigs(savedModels);
	}

	class ToggleDebugActionButton extends ToggleActionButton {
		public ToggleDebugActionButton () {
			super("Toggle Debug Mode", AllIcons.Actions.StartDebugger);
		}

		@Override
		public boolean isSelected (AnActionEvent e) {
			return settings.isDebugMode();
		}

		@Override
		public void setSelected (AnActionEvent e, boolean state) {
			settings.setDebugMode(state);
		}
	}
}
