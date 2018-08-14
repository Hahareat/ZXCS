package gui.lxh;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import bean.Admin;
import bean.Depot;
import bean.Employee;
import bean.PayWay;
import bean.Supplier;
import bean.orders.InOrder;
import bean.orders.InOrder_tui;

import dao.DepotsDao;
import dao.EmployeesDao;
import dao.OrderDao;
import dao.PayWaysDao;

import service.AdminService;
import service.InService;
import util.CastUtil;
import util.MyDateChooser;

//�˿��˻�
public class BuyReturnGoodsModelWindow extends JDialog{
	OrderDao order_dao=null;													//����dao
	String dh=null;																//��������
	DepotsDao  depots_dao=null;													//�ֿ�dao
	EmployeesDao employees_dao=null;											//�����ˣ�Ա��dao��	
	PayWaysDao pay_ways_dao=null;												//֧����ʽ��dao
		
	//����
	MyDateChooser dc1,dc2,dc3;													//��������С����
	
	JTabbedPane tabbed;
	JPanel jp_creturn;															//�˿��˻����
	JPanel jp_creturn_top,jp_creturn_center,jp_creturn_bottom;
	JPanel jp_creturn_top_top,jp_creturn_top_center;							//�����е����
	JPanel jp_creturn_center_top,jp_creturn_center_center;						//�в��е����
	JPanel jp_creturn_bottom1,jp_creturn_bottom2;								//�ײ��е����
	JLabel lbl_ordernumber,lbl_returngoods;
	/**
	 * ��������װ�ֿ��������
	 */
	JComboBox cbox_depot;	
	DefaultComboBoxModel cbox_depot_model;
	/**
	 * ������������
	 */
	JComboBox cbox_employees;
	DefaultComboBoxModel combox_employees_model;
																				//������//
	
	JTextField tf_name,tf_selldate;												//�����ı�
	JTextField tf_wantmoney,tf_paymoney,tf_bz;									//�ײ��ı�
	JButton btn_seek,btn_addgoods,btn_returnall,btn_ok,btn_exit;
	Vector columnNames;
	Vector data;
	DefaultTableModel tablemodel;
	JTable table;
	
	JPanel jp_returngdcheck;					//�˻���ѯ���
	JPanel jp_rgc_top,jp_rgc_center;			//��Ϊ�������в�
	JPanel jp_tab_p1,jp_tab_p2,jp_tab_p3;
	JPanel jp_tab_p1_1,jp_tab_p1_2,jp_tab_p2_1,jp_tab_p2_2;	//�в������������
	JTabbedPane tabbed_center;
	JButton btn_look,btn_check,btn_out,btn_tui;
	JTextField tf_rgc_name,tf_rgc_check,tf_rgc_order;
	JButton btn_soc_check1,btn_soc_check2;
	Vector<Vector> date1,date2,date3,date4,date5;
	Vector columnNames1,columnNames2,columnNames3,columnNames4,columnNames5;
	DefaultTableModel table1model,table2model,table3model,table4model,table5model;
	JTable table1,table2,table3,table4,table5;
	String[] arr1={"���ݺ�","��������","��Ӧ������","�ֿ�����","Ӧ�����","ʵ�����","Ƿ����","��������","������","����Ա","��ע"};		
	String[] arr2={"��Ʒ���","��Ʒ����","��λ","����","����","�ܽ��","����ͺ�","��ɫ"};
	String[] arr3={"��Ʒ���","��Ʒ����","��λ","����","�ܽ��","����ͺ�","��ɫ","��������","��ע"};
	String[] arr4={"����������","���ݺ�","��������","��Ʒ���","��Ʒ����","��λ","����","����","�ܽ��","����","����ͺ�","��ɫ","������","�ֿ�"};
	String[] arr5={"��������","���ݺ�","��Ʒ���","��Ʒ����","����","����","�ܽ��","��λ","����ͺ�","��ɫ","�ֿ�","������","����������" };
	/**
	 * Vector v ����һ��ά���й�����
	 */
	/**
	 * ֧����ʽ��������
	 */
																				//֧����ʽ
		JComboBox cbox_pay;
		DefaultComboBoxModel combox_model;
		Vector v=null;
	
	public BuyReturnGoodsModelWindow(){
		order_dao=new OrderDao();												//��ʼ������dao
		employees_dao=new EmployeesDao();										//��ʼ��Ա��dao
		depots_dao=new DepotsDao();												//��ʼ���ֿ�dao
		pay_ways_dao=new PayWaysDao();											//֧����ʽ��dao
		dc1=MyDateChooser.getInstance("yyyy-MM-dd");
		dc2=MyDateChooser.getInstance("yyyy-MM-dd");
		dc3=MyDateChooser.getInstance("yyyy-MM-dd");							//С������ʼ��
		
		
		tabbed=new JTabbedPane();												//ѡ�
		jp_creturn=new JPanel();												//�˿��˻����
		jp_returngdcheck=new JPanel();											//�˻���ѯ���
		
		tabbed.add("�ɹ��˻�",jp_creturn);
		tabbed.add("�˻���ѯ",jp_returngdcheck);
		jp_returngdcheck.setLayout(new BorderLayout());							//���ñ߽粼��
		jp_creturn.setLayout(new BorderLayout());
		/**
		 * ��ʼ���ֿ�������
		 */
		cbox_depot_model=new DefaultComboBoxModel(depots_dao.getDepots());							//����combobox���ڴ�ȡ�ֿ�����
		cbox_depot=new JComboBox(cbox_depot_model);										//�ֿ�������
		
/*-----------------------------------------------�˿��˻����------------------------------------------------------*/		
		//�������
		jp_creturn_top=new JPanel();
		jp_creturn_top_top=new JPanel();
		lbl_returngoods=new JLabel("�ɹ��˻�");
		dh=order_dao.getInOrderTuiId();													//�����Ǹ���ɫ��ʾ�ĵ�����ȫ��dhװ��
		lbl_ordernumber=new JLabel("���ţ�"+dh);
		lbl_ordernumber.setForeground(Color.red);
		jp_creturn_top_center=new JPanel();
		tf_name=new JTextField(15);
		btn_seek=new JButton("����");
		tf_selldate=new JTextField(15);
		jp_creturn.add(jp_creturn_top,BorderLayout.NORTH);			//���ö�������λ��
		jp_creturn_top.setLayout(new BorderLayout());				//���ö������Ĳ���
		lbl_returngoods.setFont(new Font("΢���ź�",Font.BOLD,17));	//���õ�����
		jp_creturn_top_top.setLayout(new GridLayout(1,5));
		jp_creturn_top_top.add(new JLabel());
		jp_creturn_top_top.add(new JLabel());
		jp_creturn_top_top.add(lbl_returngoods);
		lbl_ordernumber.setForeground(Color.red);
		jp_creturn_top_top.add(lbl_ordernumber);
		jp_creturn_top_top.add(new JLabel()); 	
		jp_creturn_top_center.setBorder(BorderFactory.createTitledBorder(""));
		jp_creturn_top_center.add(new JLabel("�����̣�"));
		jp_creturn_top_center.add(tf_name);
		tf_name.setText("���ұ�ѡ�񹩻���--��");
		tf_name.setEditable(false);
		jp_creturn_top_center.add(btn_seek);
		jp_creturn_top_center.add(new JLabel("�����ֿ⣺"));
		jp_creturn_top_center.add(cbox_depot);
		tf_selldate.setText(dc1.getStrDate());
		dc1.register(tf_selldate);													//����һ������
		jp_creturn_top_center.add(new JLabel("�˻����ڣ�"));
		jp_creturn_top_center.add(tf_selldate);
		jp_creturn_top.add(jp_creturn_top_top,BorderLayout.NORTH);
		jp_creturn_top.add(jp_creturn_top_center,BorderLayout.CENTER);
		
		//�в����------------------------------------------------------
		jp_creturn_center=new JPanel();
		btn_addgoods=new JButton("�����˻���Ʒ");
		btn_returnall=new JButton("�����˻�");
		jp_creturn_center_top=new JPanel();
		jp_creturn_center_center=new JPanel();
		
		jp_creturn_center.setLayout(new BorderLayout());
		jp_creturn_center_top.setLayout(new GridLayout(1,6));
		jp_creturn_center_top.setBorder(BorderFactory.createTitledBorder(""));
		jp_creturn_center_top.add(btn_addgoods);
		jp_creturn_center_top.add(btn_returnall);
		jp_creturn_center_top.add(new JLabel());
		jp_creturn_center_top.add(new JLabel());
		jp_creturn_center_top.add(new JLabel());
		jp_creturn_center_top.add(new JLabel());
		jp_creturn_center.add(jp_creturn_center_top,BorderLayout.NORTH);
		jp_creturn_center_center.setLayout(new GridLayout(1,1));
		columnNames=new Vector();
		columnNames.add("��Ʒ���");
		columnNames.add("��Ʒ����");
		columnNames.add("��λ");
		columnNames.add("����ͺ�");
		columnNames.add("����");
		columnNames.add("����");
		columnNames.add("�ܽ��");
		data=new Vector();
		
		
		/**
		 * ��ʼ��������������
		 */
		combox_employees_model=new DefaultComboBoxModel(employees_dao.getEmployees());
		cbox_employees=new JComboBox(combox_employees_model);
		
		/**
		 * ��ʼ��֧����ʽ������
		 */
																					//����combobox���ڴ�ȡ֧����ʽ
		combox_model=new DefaultComboBoxModel(pay_ways_dao.getPayWaysInfo());
		cbox_pay=new JComboBox(combox_model);
		/**
		 * ������ı�
		 */
		tablemodel=new DefaultTableModel(data, columnNames);
		table=new JTable(tablemodel);
		jp_creturn_center_center.add(new JScrollPane(table));
		jp_creturn_center.add(jp_creturn_center_center);
		jp_creturn.add(jp_creturn_center,BorderLayout.CENTER);
		
		//�ײ����----------------------------------------------------
		jp_creturn_bottom=new JPanel();
		jp_creturn.add(jp_creturn_bottom,BorderLayout.SOUTH);	
		jp_creturn_bottom.setLayout(new GridLayout(2,1));
		jp_creturn_bottom1=new JPanel();
		jp_creturn_bottom.add(jp_creturn_bottom1);
		jp_creturn_bottom1.add(new JLabel("Ӧ�ս�"));
		tf_wantmoney=new JTextField(15);
		jp_creturn_bottom1.add(tf_wantmoney);
		jp_creturn_bottom1.add(new JLabel("ʵ�ս�"));
		tf_paymoney=new JTextField(15);
		jp_creturn_bottom1.add(tf_paymoney);
		
		jp_creturn_bottom1.add(new JLabel("֧����ʽ��"));
		jp_creturn_bottom1.add(cbox_pay);
		jp_creturn_bottom1.add(new JLabel("�����ˣ�"));
		jp_creturn_bottom1.add(cbox_employees);
		jp_creturn_bottom.add(jp_creturn_bottom1);
		
		jp_creturn_bottom2=new JPanel();
		jp_creturn_bottom2.add(new JLabel("��ע"));
		tf_bz=new JTextField(53);
		jp_creturn_bottom2.add(tf_bz);
		btn_ok=new JButton("ȷ��");
		jp_creturn_bottom2.add(btn_ok);
		btn_exit=new JButton("�˳�");
		jp_creturn_bottom2.add(btn_exit);
		jp_creturn_bottom.add(jp_creturn_bottom2	);
		
/*----------------------------------------------------�˻���ѯ���------------------------------------------------------*/
		
		//�������------------------------------------------------------
		jp_rgc_top=new JPanel();
		btn_look=new JButton("�߼���ѯ");
		btn_check=new JButton("�鿴����");
		btn_out=new JButton("����");
		btn_tui=new JButton("�˳�");
		tf_rgc_name=new JTextField(8);
		tf_rgc_check=new JTextField(8);
		tf_rgc_order=new JTextField(8);
		btn_soc_check1=new JButton("��ѯ");
		jp_rgc_top.setBorder(BorderFactory.createTitledBorder(""));
		jp_rgc_top.add(btn_look);
		jp_rgc_top.add(btn_check);
		jp_rgc_top.add(btn_out);
		jp_rgc_top.add(btn_tui);
		jp_rgc_top.add(new JLabel("��������"));
		tf_rgc_name.setText(dc2.getStrDate());
		dc2.register(tf_rgc_name);											//����һ������
		jp_rgc_top.add(tf_rgc_name);
		jp_rgc_top.add(new JLabel("��"));
		tf_rgc_check.setText(dc3.getStrDate());	
		dc3.register(tf_rgc_check);											//����һ������
		jp_rgc_top.add(tf_rgc_check);
		jp_rgc_top.add(new JLabel("�ͻ�/���ݺ�"));
		jp_rgc_top.add(tf_rgc_order);
		jp_rgc_top.add(btn_soc_check1);
		jp_returngdcheck.add(jp_rgc_top,BorderLayout.NORTH);
		//�в����-----------------------------------------------------
		jp_rgc_center=new JPanel();
		tabbed_center=new JTabbedPane();
		jp_tab_p1=new JPanel();
		jp_tab_p2=new JPanel();
		jp_tab_p3=new JPanel();
		jp_tab_p1_1=new JPanel();
		jp_tab_p1_2=new JPanel();
		jp_tab_p2_1=new JPanel();
		jp_tab_p2_2=new JPanel();
		columnNames1=new Vector();
		columnNames2=new Vector();
		columnNames3=new Vector();
		columnNames4=new Vector();
		columnNames5=new Vector();
		for (String str:arr1) {
			columnNames1.add(str);
		}
		for (String str:arr2) {
			columnNames2.add(str);
		}
		for (String str:arr3) {
			columnNames3.add(str);
		}
		for (String str:arr4) {
			columnNames4.add(str);
		}
		for (String str:arr5) {
			columnNames5.add(str);
		}
		table1model=new DefaultTableModel(date1,columnNames1);
		table1=new JTable(table1model);
		table2model=new DefaultTableModel(date2,columnNames2);
		table2=new JTable(table2model);
		table3model=new DefaultTableModel(date3,columnNames3);
		table3=new JTable(table3model);
		table4model=new DefaultTableModel(date4,columnNames4);
		table4=new JTable(table4model);
		table5model=new DefaultTableModel(date5,columnNames5);
		table5=new JTable(table5model);
		jp_rgc_center.setLayout(new GridLayout(1,1));
		//����ѡ�
		tabbed_center.add("���ݱ�",jp_tab_p1);
		jp_tab_p1.setLayout(new GridLayout(2,1));
		jp_tab_p1.add(jp_tab_p1_1);
		jp_tab_p1_1.setLayout(new GridLayout(1,1));
		jp_tab_p1_1.add(new JScrollPane(table1));
		jp_tab_p1.add(jp_tab_p1_2);
		jp_tab_p1_2.setLayout(new GridLayout(1,1));
		jp_tab_p1_2.add(new JScrollPane(table2));
		//����ѡ�
		tabbed_center.add("���ܱ�",jp_tab_p2);
		jp_tab_p2.setLayout(new GridLayout(2,1));
		jp_tab_p2.add(jp_tab_p2_1);
		jp_tab_p2_1.setLayout(new GridLayout(1,1));
		jp_tab_p2_1.add(new JScrollPane(table3));
		jp_tab_p2.add(jp_tab_p2_2);
		jp_tab_p2_2.setLayout(new GridLayout(1,1));
		jp_tab_p2_2.add(new JScrollPane(table4));
		//��ϸѡ�
		tabbed_center.add("��ϸ��",jp_tab_p3);
		jp_tab_p3.setLayout(new GridLayout(1,1));
		jp_tab_p3.add(new JScrollPane(table5));
		jp_rgc_center.add(tabbed_center);
		jp_returngdcheck.add(jp_rgc_center,BorderLayout.CENTER);
		
		
		
		 	
	/**
	 * ��������
	 * @author lxh
	 * 		 
	 */
		/**
		 * ����seek ������
		 */
		btn_seek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SupplierInfoModelWindow sp=new SupplierInfoModelWindow();
				 v=sp.retData;
				try {
					tf_name.setText(v.get(1).toString());
				} catch (Exception e2) {
					
				}
			}
		});
		/**
		 * �����˻���Ʒ
		 */
		btn_addgoods.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddReturnGoodsModelWindow(BuyReturnGoodsModelWindow.this);
			}
		});
		/**
		 * �����˻�
		 */
		btn_returnall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new BuyDocumentsCheckModelWindow();
			}
		});
		/*
		 * ȷ���¼�
		 * ���ɶ���
		 * 
		 */
		btn_ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String date=tf_selldate.getText().trim();
					Depot depot=(Depot) cbox_depot.getSelectedItem();
					Double wantMoney=Double.parseDouble(tf_wantmoney.getText().trim());
					Double payMoney=Double.parseDouble(tf_paymoney.getText().trim());
					Employee agent=(Employee) cbox_employees.getSelectedItem();
					Admin operator=AdminService.admin;
					String bz=tf_bz.getText().trim();
					PayWay payWay=(PayWay) cbox_pay.getSelectedItem();
					Supplier supplier=new CastUtil().VectorToSupplier(v);
					
					InOrder_tui in_order_tui=new InOrder_tui(dh, date, depot, wantMoney, payMoney, agent, operator, bz, payWay, supplier);
					new InService().addOrder(in_order_tui,new CastUtil().VerctorToHashSet(data));
					JOptionPane.showMessageDialog(null, "�ύ�ɹ�!");
					
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "�ύʧ��!");
				}
				
				
				
			}
		});
		/**
		 * btn_exit�˳�
		 */
		btn_exit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
			BuyReturnGoodsModelWindow.this.setVisible(false);
				
			}
		});
		this.setTitle("�ɹ��˻�");
		this.add(tabbed);
		this.setBounds(300, 100, 900, 550);
		this.setModal(true);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		new AdminService().Login("admin", "123");
		new BuyReturnGoodsModelWindow();
	}
}