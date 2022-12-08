import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;

class User{
	String id, pw;
	
	User(String id, String pw){
		this.id = id;
		this.pw = pw;
	}
	void print() {
		System.out.println(id +" "+pw);
	}
}

class UserList{
	ArrayList<User> ul = new ArrayList<>();
	
	void addUser(User u) {ul.add(u);}
	void print() {
		System.out.println("---print using class---");
		for(User u : ul)u.print();
	}
	boolean isValidID(String id) {
		for(User u: ul) if (u.id.equals(id)) return true;
		return false;
	}
	boolean isValidPass(String i, String p) {
		String pass = new String();
		for(User u : ul)
			if(u.id.equals(i)) {
				pass = u.pw;
				break;
			}
		if(pass.equals(p)) return true;
		return false;
	}
}
class LoginWindow extends JFrame{
	JTextField tf;
	JPasswordField pf;
	UserList ul;
	
	public LoginWindow() {
		setTitle("로그인");
		setLocation(200,200);
		setLayout(new GridLayout(3,1));
		
		JPanel p1 = new JPanel();
		add(p1);
		JPanel p2 = new JPanel();
		add(p2);
		JPanel p3 = new JPanel();
		add(p3);
		
		p1.add(new JLabel("아이디"));
		tf = new JTextField(20);
		p1.add(tf);
		p2.add(new JLabel("암    호"));
		pf = new JPasswordField(20);
		p2.add(pf);
		
		JButton login = new JButton("로그인");
		p3.add(login);
		login.addActionListener(e->{
			String id = tf.getText();
			String pw = new String(pf.getPassword());
			if(!ul.isValidID(id))
				JOptionPane.showMessageDialog(null, "유효하지 않은 아이디", "로그인", JOptionPane.ERROR_MESSAGE);
			else if(!ul.isValidPass(id, pw))
				JOptionPane.showMessageDialog(null, "부정확한 암호", "로그인", JOptionPane.ERROR_MESSAGE);
			else {
				JOptionPane.showMessageDialog(null, "로그인 완료", "로그인", JOptionPane.INFORMATION_MESSAGE);
				dispose();
			}
		});
		JButton reg = new JButton("회원가입");
		p3.add(reg);
		reg.addActionListener(e->{
			new RegWindow(ul);
			dispose();
		});
		JButton cancel = new JButton("취소");
		cancel.addActionListener(e -> {dispose();});
		p3.add(cancel);
		
		pack();
		setVisible(true);
	}
	
	public void setUserList(UserList ul) {
		this.ul = ul;
		this.ul.print();
	}
}

class RegWindow extends JFrame{
	JTextField tf;
	JPasswordField pf1, pf2;
	UserList ul;

	public RegWindow(UserList ul) {
		this.ul = ul;
		setTitle("회원가입");
		setLayout(new GridLayout(4,1));
		
		JPanel p1 = new JPanel();
		add(p1);
		JPanel p2 = new JPanel();
		add(p2);
		JPanel p3 = new JPanel();
		add(p3);
		JPanel p4 = new JPanel();
		add(p4);
		
		p1.add(new JLabel("ID"));
		tf = new JTextField(20);
		p1.add(tf);
		p2.add(new JLabel("PW"));
		pf1 = new JPasswordField(20);
		p2.add(pf1);
		p3.add(new JLabel("PW 확인"));
		pf2 = new JPasswordField(20);
		p3.add(pf2);
		JButton reg = new JButton("등록");
		p4.add(reg);
		reg.addActionListener(e ->{
			String id = tf.getText();
			String pw1 = new String(pf1.getPassword());
			String pw2 = new String(pf2.getPassword());
			if(ul.isValidID(id))
				JOptionPane.showMessageDialog(null, "이미 존재하는 아이디","회원가입", JOptionPane.ERROR_MESSAGE);
			else if (!pw1.equals(pw2))
				JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않음","회원가입", JOptionPane.ERROR_MESSAGE);
			else if(pw1.equals(""))
					JOptionPane.showMessageDialog(null, "비밀번호가 비어있음","회원가입", JOptionPane.ERROR_MESSAGE);
			else {
				File f = new File("data.txt");
				FileWriter fwr;
				try {
					fwr = new FileWriter(f, true);
					PrintWriter pwr = new PrintWriter(fwr);
					pwr.println(id);
					pwr.println(pw1);
					System.out.println("사용자 등록 완료");
					pwr.close();
					ul.addUser(new User(id,pw1));
					LoginWindow lw = new LoginWindow();
					lw.setUserList(ul);
					dispose();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		JButton cancel = new JButton("취소");
		p4.add(cancel);
		cancel.addActionListener(e->{
			dispose();
			LoginWindow lw = new LoginWindow();
			lw.setUserList(ul);
		});
		pack();
		setVisible(true);
	}
}

public class GUITest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		User u;
		UserList ul = new UserList();
		
		LoginWindow lw = new LoginWindow();
		
		Scanner sc = new Scanner(new File("data.txt"));
		while(sc.hasNext()) {
			String id = sc.next();
			String pw = sc.next();
			u = new User(id, pw);
			ul.addUser(u);
		}
		lw.setUserList(ul);
		sc.close();
	}

}