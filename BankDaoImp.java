package org.jsp.bank.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

import org.jsp.bank.model.Bank;

public class BankDaoImp implements BankDAO{
	String url="jdbc:mysql://localhost:3306/teca52?user=root&password=12345";
	Scanner scan=new Scanner(System.in);
	public void userRegistration(Bank bank) {
		
		String insert ="insert into bank( userFirstName, userLastName, mobileNumber, Password, Address, Amount, email_id, accountNumber) values(?,?,?,?,?,?,?,?);";
		
		try {
			Connection connection=DriverManager.getConnection(url);
			PreparedStatement preparedStatement=connection.prepareStatement(insert);
			preparedStatement.setString(1,bank.getUserFirstName());
			preparedStatement.setString(2,bank.getUserLastName());
			preparedStatement.setString(3,bank.getMobileNumber());
			preparedStatement.setString(4,bank.getPassword());
			preparedStatement.setString(5,bank.getAddress());
			preparedStatement.setDouble(6,bank.getAmount());
			Random random=new Random();
			int temp=random.nextInt(1000);
			String e=bank.getUserFirstName().toLowerCase();
			String bankemailid=(e+temp+"@teca52.com");
			preparedStatement.setString(7,bank.getEmail_id());
			Random r =new Random(100000000000l);
		    long ac=r.nextLong();
		    if(ac<10000000000l) {
		    	ac+=10000000000l;
		    }
			preparedStatement.setString(8,bank.getAccountNumber());
			int result=preparedStatement.executeUpdate();
			if(result!=0) {
				System.out.println("account created succsesfully");
				//System.out.println("your emailid is "+bankemailid);
				
			}
			else {
				System.out.println("inavild data");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public void credit() {
		// TODO Auto-generated method stub
		
	}

	public void changingThePassword() {
		// TODO Auto-generated method stub
		
	}

	public void mobileToMobileTransaction(String mobileNumber) {
		// TODO Auto-generated method stub
		try {
			Connection connection= DriverManager.getConnection(url);
			PreparedStatement ps=connection.prepareStatement("select * from bank where mobileNumber=?");
			ps.setString(1, mobileNumber);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				System.out.println("enter the recivers number");
				
				String recivermobileNumber=scan.next();
				        boolean numberstatus=true;
				        while(numberstatus) {
				        	
				        	if(recivermobileNumber.length()==10) {
				        		System.out.println("enter the amount");
				        		double amount=scan.nextDouble();
				        		numberstatus=false;
				        		double sendersamount=rs.getDouble("Amount");
				        		if(sendersamount>=amount) {
				        			double sub=sendersamount-amount;
				        			String upadte="update bank set Amount=? where mobileNumber=?";
				        			PreparedStatement psups=connection.prepareStatement(upadte);
				        			psups.setDouble(1, sub);
				        			psups.setString(2,mobileNumber);
				        			int result=psups.executeUpdate();
				        			if(result!=0) {
				        				System.out.println("amount debited successfully!!!");
				        				double redatabaseamount=rs.getDouble("Amount");
				        				double add=redatabaseamount+sendersamount;
				        				String updatere="update bank set Amount=? where mobileNumber=?";
				        				PreparedStatement psupr=connection.prepareStatement(updatere);
				        				psupr.setDouble(1, add);
				        				psupr.setString(2,recivermobileNumber);
				        				int resultre=psupr.executeUpdate();
				        				if(resultre!=0) {
				        					System.out.println("Amount credited ....!!");
				        				}
				        			}
				        			else {
				        				System.out.println("serever 404");
				        			}
				        		}
				        	}
				        	else {
				        		numberstatus=true;
				        	}
				        }
				
			}
			else {
				System.out.println("invalid number");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void checkBalance() {
		// TODO Auto-generated method stub
		
	}

	public void debit(String accountNumber, String password) {
		// TODO Auto-generated method stub
		try {
			Connection connection=DriverManager.getConnection(url);
			PreparedStatement preparedstatement=connection.prepareStatement("select * from bank where accountNumber=? and Password=?");
			preparedstatement.setString(1,accountNumber);
			preparedstatement.setString(2,password);
			ResultSet resultset=preparedstatement.executeQuery();
			if(resultset.next()) {
				System.out.println("enter your amount");
				boolean amountstatus=true;
				while(amountstatus) {
				   double useramount=scan.nextDouble();
				   if(useramount>=0) {
					   amountstatus=false;
					   double databaseamount=resultset.getDouble("Amount");
					   if(databaseamount>=useramount) {
						   double sub=databaseamount-useramount;
						   System.out.println("balance after debit is :"+sub);
						   String update="update bank set Amount=? where accountNumber=? and Password=?";
						   PreparedStatement ps=connection.prepareStatement(update);
						   ps.setDouble(1, sub);
						   ps.setString(2,accountNumber);
						   ps.setString(3,password);
						   int result=ps.executeUpdate();
						   if(result!=0) {
							   for(int i=0;i<5;i++) {
								   try {
									Thread.sleep(2000);
									System.out.print("."); 
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							   }
							   System.out.println("debit succsefully....");
						   }
					   }
				   }
				   else {
					   System.out.println("invaild amount");
					   System.out.println("enter the amount greater than 0");
				   }
				}
				
				
			}
			else {
				System.out.println("not ok");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
