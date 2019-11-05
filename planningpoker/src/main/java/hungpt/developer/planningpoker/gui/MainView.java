package hungpt.developer.planningpoker.gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

import com.bayesserver.inference.InconsistentEvidenceException;

import  hungpt.developer.planningpoker.entity.Resource;
import  hungpt.developer.planningpoker.entity.UserStory;
import  hungpt.developer.planningpoker.gui.schedule.FrameAgile;
import  hungpt.developer.planningpoker.model.AlgorithmScheduling;
import  hungpt.developer.planningpoker.model.BayesNet;
import  hungpt.developer.planningpoker.util.FileManage;

public class MainView {

	private JFrame frame;
	private JTextField txtNameProject;
	private File teamSelectedFile = null;
	private File taskSelectedFile = null;
	private JLabel labelTeamFile;
	private JLabel labelTaskFile;
	// private String projectName;
	// private String iterID;
	private JTextField txtLength;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView window = new MainView();
					// window.initialize();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		// frame.setBounds(100, 100, 450, 300);
		frame.setSize(600, 300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblProjectName = new JLabel("Project Name ");
		lblProjectName.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblProjectName.setBounds(49, 35, 95, 39);
		frame.getContentPane().add(lblProjectName);

		JLabel lblTeamData = new JLabel("Team Data");
		lblTeamData.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTeamData.setBounds(49, 116, 89, 39);
		frame.getContentPane().add(lblTeamData);
		labelTeamFile = new JLabel("");
		labelTeamFile.setBounds(280, 129, 200, 14);
		frame.getContentPane().add(labelTeamFile);
		JButton btnChooseFileTeam = new JButton("Choose File");
		btnChooseFileTeam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				int returnValue = jfc.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					teamSelectedFile = jfc.getSelectedFile();
					labelTeamFile.setText(teamSelectedFile.getAbsolutePath());
					System.out.println(teamSelectedFile.getAbsolutePath());
				}
			}
		});
		btnChooseFileTeam.setBounds(152, 125, 120, 23);
		frame.getContentPane().add(btnChooseFileTeam);

		JLabel lblTaskData = new JLabel("Task Data");
		lblTaskData.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTaskData.setBounds(49, 166, 75, 29);
		frame.getContentPane().add(lblTaskData);
		labelTaskFile = new JLabel("");
		labelTaskFile.setBounds(280, 174, 200, 14);
		frame.getContentPane().add(labelTaskFile);
		JButton btnChooseFileTask = new JButton("Choose File");
		btnChooseFileTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				int returnValue = jfc.showOpenDialog(null);
				// int returnValue = jfc.showSaveDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					taskSelectedFile = jfc.getSelectedFile();
					labelTaskFile.setText(taskSelectedFile.getAbsolutePath());
					System.out.println(taskSelectedFile.getAbsolutePath());
				}
			}
		});
		btnChooseFileTask.setBounds(152, 170, 120, 23);
		frame.getContentPane().add(btnChooseFileTask);

		txtNameProject = new JTextField("Name");
		txtNameProject.setBounds(152, 45, 180, 20);
		frame.getContentPane().add(txtNameProject);
		txtNameProject.setColumns(10);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

//				int iterationLength = 85;
				int iterationLength = Integer.parseInt(txtLength.getText());
//				if (teamSelectedFile == null) {
//					JOptionPane.showMessageDialog(null, "Hãy chọn file lưu thông tin các resource");
//				} else if (taskSelectedFile == null) {
//					JOptionPane.showMessageDialog(null, "Hãy chọn file lưu thông tin các task");
//				} else
				{

					try {

						// đọc dữ liệu resource và task
					    List<Resource> resources = FileManage.readTeams(teamSelectedFile.getAbsolutePath());
//						List<Resource> resources = FileManage.readTeams("resources.csv");
						
					    List<UserStory> userStories = FileManage.readStory(taskSelectedFile.getAbsolutePath());
//						List<UserStory> userStories = FileManage.readStory("data1.csv");
						
						// thực hiện lập lịch
						AlgorithmScheduling algorithm = new AlgorithmScheduling(userStories, resources, iterationLength);
						algorithm.runAlgorithm();

						// nếu lập được lịch thì hiển thị kết quả
						if (algorithm.isExistSchedule()) {
						    
						    int numTask = 0;
						    for(UserStory u: userStories){
						        numTask += u.getListTask().size();
						    }
						            
						    
							BayesNet net = new BayesNet(resources, numTask);
							frame.dispose();

							new FrameAgile(net, algorithm.getScheduleWithTimeTask(), algorithm.getScheduledWithNameTask());
						} else {
							JOptionPane.showMessageDialog(null,
									"Không thể lập lịch với bộ dữ liệu đã chọn. Hãy chọn bộ dữ liệu khác.");
						}
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					} catch (InconsistentEvidenceException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}

				}
			}
		});
		btnSubmit.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSubmit.setBounds(250, 227, 89, 23);
		frame.getContentPane().add(btnSubmit);

		JLabel lblLength = new JLabel("Length Sprint");
		lblLength.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblLength.setBounds(49, 76, 95, 29);
		frame.getContentPane().add(lblLength);

		txtLength = new JTextField("85");
		txtLength.setBounds(152, 81, 86, 20);
		frame.getContentPane().add(txtLength);
		txtLength.setColumns(10);

	}
}
