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

import com.intellij.execution.RunManager;
import com.intellij.execution.RunnerAndConfigurationSettings;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JPanel;
import java.util.ArrayList;

public class RunConfigSelectionDialog extends DialogWrapper {
	private JPanel contentPane;
	private RunConfigList configList;
	private DefaultListModel listModel;

	public RunConfigSelectionDialog (Project project) {
		super(project, true);
		setModal(true);
		init();
		setSize(320, 250);
		setTitle("Select Run Configuration");

		listModel = new DefaultListModel();
		for (RunnerAndConfigurationSettings config : RunManager.getInstance(project).getAllSettings()) {
			listModel.addElement(new RunConfigModel(config));
		}
		configList.setModel(listModel);
		configList.getEmptyText().setText("There are no run configurations in project.");
	}

	public ArrayList<RunConfigModel> getSelectedConfigs () {
		int[] selected = configList.getSelectedIndices();
		ArrayList<RunConfigModel> models = new ArrayList<RunConfigModel>(selected.length);
		for (int i : selected) {
			models.add((RunConfigModel) listModel.get(i));
		}
		return models;
	}

	@Nullable
	@Override
	protected String getDimensionServiceKey () {
		return "com.kotcrab.quicklaunch.dimentions.RunConfigSelectionDialog";
	}

	@Nullable
	@Override
	protected JComponent createCenterPanel () {
		return contentPane;
	}
}
