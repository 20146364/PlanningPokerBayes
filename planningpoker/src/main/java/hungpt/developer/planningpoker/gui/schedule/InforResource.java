
package hungpt.developer.planningpoker.gui.schedule;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import  hungpt.developer.planningpoker.entity.Resource;
import hungpt.developer.planningpoker.model.BayesNet;

/**
 * @author tuanl
 *
 */
public class InforResource extends JFrame {

	private static final long serialVersionUID = 1L;
	private Resource resource = null;
	private static Resource resourceTemp = new Resource();
	private List<Resource> listResource = null;
	private JComboBox<String> cmbTimPressure = null;
	private JComboBox<String> cmbProductivity = null;
	private JComboBox<String> cmbHumanSkill = null;
	private JComboBox<String> cmbManagerExp = null;
	private JComboBox<String> cmbResource = null;
	private boolean isDisplay = true;

	public InforResource(BayesNet net) {

		this.listResource = net.getResources();
		this.resource = listResource.get(0);
		this.setSize(600, 350);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.initComponent(listResource.size());

		this.setVisible(true);
	}

	public void initComponent(int numResource) {
		JLabel label = new JLabel("Thông tin nguồn lực");
		label.setFont(new Font("Tahoma", Font.BOLD, 20));
		label.setBounds(220, 20, 250, 25);
		this.getContentPane().add(label);

		JLabel lblResource = new JLabel("Resource");
		lblResource.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblResource.setBounds(120, 60, 200, 20);
		this.getContentPane().add(lblResource);

		cmbResource = new JComboBox<String>();
		for (int i = 1; i <= numResource; i++) {
			cmbResource.addItem("Resource " + i);
		}
		cmbResource.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cmbResource.setBounds(350, 60, 95, 20);
		this.getContentPane().add(cmbResource);

		cmbResource.addActionListener(arg0 -> {
			int indexSelect = cmbResource.getSelectedIndex();
			resource = listResource.get(indexSelect);
			cmbManagerExp.setSelectedItem(resource.getManagementExperience());
			cmbHumanSkill.setSelectedIndex(resource.getHumanSkill() - 1);
			cmbProductivity.setSelectedIndex(resource.getProductivity() - 1);
			cmbTimPressure.setSelectedItem(resource.getTimPressure());
		});

		JLabel lblAgileExp = new JLabel("Kinh nghiệm quản lý");
		lblAgileExp.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblAgileExp.setBounds(120, 90, 200, 20);
		this.getContentPane().add(lblAgileExp);

		cmbManagerExp = new JComboBox<String>();
		cmbManagerExp.addItem("yes");
		cmbManagerExp.addItem("no");
		cmbManagerExp.setSelectedItem(resource.getManagementExperience());
		cmbManagerExp.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cmbManagerExp.setBounds(350, 90, 95, 20);
		this.getContentPane().add(cmbManagerExp);

		JLabel lblAgileSkill = new JLabel("Năng lực của nhân viên");
		lblAgileSkill.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblAgileSkill.setBounds(120, 120, 95, 20);
		this.getContentPane().add(lblAgileSkill);

		cmbHumanSkill = new JComboBox<String>();
		for (int i = 1; i <= 5; i++) {
			cmbHumanSkill.addItem("Level " + i);
		}
		cmbHumanSkill.setSelectedItem("Level " + resource.getHumanSkill());
		cmbHumanSkill.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cmbHumanSkill.setBounds(350, 120, 95, 20);
		this.getContentPane().add(cmbHumanSkill);

		JLabel lblWorkSkill = new JLabel("Năng suất công việc");
		lblWorkSkill.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblWorkSkill.setBounds(120, 150, 200, 20);
		this.getContentPane().add(lblWorkSkill);

		cmbProductivity = new JComboBox<String>();
		for (int i = 1; i <= 5; i++) {
			cmbProductivity.addItem("Level " + i);
		}
		cmbProductivity.setSelectedItem("Level " + resource.getProductivity());
		cmbProductivity.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cmbProductivity.setBounds(350, 150, 95, 20);
		this.getContentPane().add(cmbProductivity);

		JLabel lblDaily = new JLabel("Áp lực thời gian");
		lblDaily.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDaily.setBounds(120, 180, 200, 20);
		this.getContentPane().add(lblDaily);

		cmbTimPressure = new JComboBox<String>();
		cmbTimPressure.addItem("yes");
		cmbTimPressure.addItem("no");
		cmbTimPressure.setSelectedItem(resource.getTimPressure());
		cmbTimPressure.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cmbTimPressure.setBounds(350, 180, 95, 20);
		this.getContentPane().add(cmbTimPressure);

		JButton btnChange = new JButton("Cập nhật");
		btnChange.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnChange.setBounds(160, 230, 100, 30);
		this.getContentPane().add(btnChange);

		btnChange.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				resourceTemp.setManagementExperience(resource.getManagementExperience());
				resourceTemp.setHumanSkill(resource.getHumanSkill());
				resourceTemp.setProductivity(resource.getProductivity());
				resourceTemp.setTimPressure(resource.getTimPressure());
				resourceTemp.setID(resource.getID());
				String agileExp = (String) cmbManagerExp.getSelectedItem();
				String agileSkill = (String) cmbHumanSkill.getSelectedItem();
				String workSkill = (String) cmbProductivity.getSelectedItem();
				String daily = (String) cmbTimPressure.getSelectedItem();
				resource.setManagementExperience(agileExp);
				resource.setHumanSkill(Integer.parseInt(agileSkill.substring(6)));
				resource.setProductivity(Integer.parseInt(workSkill.substring(6)));
				resource.setTimPressure(daily);
				JOptionPane.showMessageDialog(null,
						"Bạn vừa thay đổi thông tin về nguồn lực " + (cmbResource.getSelectedIndex() + 1)
								+ ". Hãy quay lại màn hình schedule để theo dõi kết quả thực hiện các "
								+ "công việc của nguồn lực này.");
				isDisplay = false;
				display();

			}
		});

		JButton btnReset = new JButton("Reset");
		btnReset.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnReset.setBounds(300, 230, 100, 30);
		this.getContentPane().add(btnReset);

		btnReset.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				resource.setManagementExperience(resourceTemp.getManagementExperience());
				resource.setHumanSkill(resourceTemp.getHumanSkill());
				resource.setProductivity(resourceTemp.getProductivity());
				resource.setTimPressure(resourceTemp.getTimPressure());
				if (cmbResource.getSelectedIndex() == resourceTemp.getID() - 1) {
					cmbTimPressure.setSelectedItem(resourceTemp.getTimPressure());
					cmbProductivity.setSelectedItem("Level " + resource.getProductivity());
					cmbHumanSkill.setSelectedItem("Level " + resource.getHumanSkill());
					cmbManagerExp.setSelectedItem(resource.getManagementExperience());
				}
				FrameAgile.reLoad();

			}
		});

	}

	private void display() {
		if (!this.isDisplay) {
			this.dispose();
		}
	}
}
