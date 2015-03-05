/*
Purpose:  A class to handle table creation and initialization.
*/

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import oracle.jdbc.driver.*;

/*
A class that creates and initializes MutualFuture db tables to its default (i.e. starting) state.
*/
public class createTable
{
	public static void main(String[] args)
	{
		Connection con = null;

		try{

			DriverManager.registerDriver(new OracleDriver());
			String connectString = "jdbc:oracle:thin:@(description=(address=(host=db0.cs.pitt.edu)(protocol=tcp)(port=1521))(connect_data=(sid=dbclass)))";
			String userid = "ceb32";
			String pwd = "CyprusDB09";
			con = DriverManager.getConnection(connectString,userid,pwd);
		}
		catch (Exception e)
		{
			System.out.println(e);
			System.exit(1);
		}

		System.out.println("Connected");

		Statement stmt = null;
		try
		{
			stmt = con.createStatement();

			System.out.println("dropping trigger date_change");
			stmt.executeUpdate("drop trigger date_change");
			System.out.println("dropping trigger Invest");
			stmt.executeUpdate("drop trigger invest");
			System.out.println("dropping trigger Buy");
			stmt.executeUpdate("drop trigger buy");
			System.out.println("dropping trigger Sell");
			stmt.executeUpdate("drop trigger sell");
			System.out.println("dropping PREFERES");
			stmt.executeUpdate("drop table PREFERS");
			System.out.println("dropping ALLOCATION");
		    stmt.executeUpdate("drop table ALLOCATION");
		    System.out.println("dropping OWNS");
			stmt.executeUpdate("drop table OWNS");
			System.out.println("dropping TRANSACTION");
			stmt.executeUpdate("drop table TRANSACTION");
			System.out.println("dropping TEMP TRANSACTION");
			stmt.executeUpdate("drop table TEMP_TRANSACTION");
		    System.out.println("dropping CLOSINGPRICE");
			stmt.executeUpdate("drop table CLOSINGPRICE");
			System.out.println("dropping MUTUALFUND");
		    stmt.executeUpdate("drop table MUTUALFUND");
			System.out.println("dropping CUSTOMER");
			stmt.executeUpdate("drop table CUSTOMER");
			System.out.println("dropping ADMINISTRATOR");
			stmt.executeUpdate("drop table ADMINISTRATOR");
			System.out.println("dropping MUTUALDATE");
			stmt.executeUpdate("drop table MUTUALDATE");
			System.out.println("dropping ALLOC_SEQ");
			stmt.executeUpdate("drop sequence ALLOC_SEQ");
			System.out.println("dropping TRANSAC_SEQ");
		    stmt.executeUpdate("drop sequence TRANSAC_SEQ");


			System.out.println("about to create temp_transaction");

			stmt.executeUpdate("create table TEMP_TRANSACTION (trans_id INT NOT NULL, customer_login VARCHAR(10), symbol VARCHAR(20), t_date DATE, action VARCHAR(10), num_shares INT, price FLOAT, amount FLOAT,PRIMARY KEY(trans_id))");

			System.out.println("about to create ALLOC_SEC sequence");

			stmt.executeUpdate("create sequence ALLOC_SEQ start with 0 minvalue 0");

			System.out.println("about to create TRANSAC_SEQ sequence");

			stmt.executeUpdate("create sequence TRANSAC_SEQ start with 0 minvalue 0");

			System.out.println("about to create MUTUALFUND table");

			stmt.executeUpdate("create table MUTUALFUND(symbol VARCHAR(20), name VARCHAR(30), description VARCHAR(100), category VARCHAR(10), c_date DATE, PRIMARY KEY(symbol) )");

			System.out.println("about to create CLOSINGPRICE table");

			stmt.executeUpdate("create table CLOSINGPRICE(symbol VARCHAR(20), price FLOAT, p_date DATE, PRIMARY KEY(symbol,p_date))");

			System.out.println("about to create CUSTOMER table");

			stmt.executeUpdate("create table CUSTOMER(customer_login VARCHAR(10), name varchar(20), email varchar(20), address varchar(20), password varchar(10), balance float, primary key(customer_login) )");

			System.out.println("about to create ADMINISTRATOR table");

			stmt.executeUpdate("create table ADMINISTRATOR(admin_login VARCHAR(10), name varchar(20), email varchar(20), address varchar(20), password varchar(10), primary key(admin_login) )");

			System.out.println("about to create ALLOCATION table");

			stmt.executeUpdate("create table ALLOCATION (allocation_no INT NOT NULL, customer_login VARCHAR(10), p_date DATE, PRIMARY KEY(allocation_no), FOREIGN KEY(customer_login) REFERENCES CUSTOMER(customer_login))");

			System.out.println("about to create PREFERS table");

			stmt.executeUpdate("create table PREFERS (allocation_no INT NOT NULL, symbol varchar(20), percentage FLOAT, PRIMARY KEY(allocation_no, symbol), FOREIGN KEY(allocation_no) REFERENCES ALLOCATION(allocation_no), FOREIGN KEY(symbol) REFERENCES MUTUALFUND(symbol))");

			System.out.println("about to create TRANSACTION table");

            stmt.executeUpdate("create table TRANSACTION (trans_id INT NOT NULL, customer_login VARCHAR(10), symbol VARCHAR(20), t_date DATE, action VARCHAR(10), num_shares INT, price FLOAT, amount FLOAT,PRIMARY KEY(trans_id), FOREIGN KEY(customer_login) REFERENCES CUSTOMER(customer_login), FOREIGN KEY(symbol) REFERENCES MUTUALFUND(symbol))");

            System.out.println("about to create OWNS table");

			stmt.executeUpdate("create table OWNS (customer_login VARCHAR(10) NOT NULL, symbol VARCHAR(20), shares INT, PRIMARY KEY(customer_login, symbol), FOREIGN KEY(customer_login) REFERENCES CUSTOMER(customer_login), FOREIGN KEY(symbol) REFERENCES MUTUALFUND(symbol))");

            System.out.println("about to create MUTUALDATE table");

			stmt.executeUpdate("create table MUTUALDATE (c_date DATE NOT NULL, PRIMARY KEY(c_date))");

            System.out.println("inserting values into MUTUALFUND");

			stmt.executeUpdate("insert into MUTUALFUND values ('MM',       'money-market',                     'money market, conservative',                 'fixed',      '28-JAN-08')");
			stmt.executeUpdate("insert into MUTUALFUND values ('RE',       'real-estate',                      'real estate',                                'fixed',      '28-JAN-08')");
			stmt.executeUpdate("insert into MUTUALFUND values ('STB',       'short-term-bonds',                 'short term bonds',                           'bonds',      '28-JAN-08')");
			stmt.executeUpdate("insert into MUTUALFUND values ('LTB',       'long-term-bonds',                  'long term bonds',                            'bonds',      '28-JAN-08')");
			stmt.executeUpdate("insert into MUTUALFUND values ('BBS',      'balance-bonds-stocks',             'balance bonds and stocks',                   'mixed',      '28-JAN-08')");
			stmt.executeUpdate("insert into MUTUALFUND values ('SRBS',      'social-respons-bonds-stocks',      'social responsibility bonds and stocks',     'mixed',      '28-JAN-08')");
			stmt.executeUpdate("insert into MUTUALFUND values ('GS',        'general-stocks',                   'general stocks',                             'stocks',     '28-JAN-08')");
			stmt.executeUpdate("insert into MUTUALFUND values ('AS',        'aggressive-stocks',                'aggressive stocks',                          'stocks',     '28-JAN-08')");
			stmt.executeUpdate("insert into MUTUALFUND values ('IMS',       'international-markets-stock',      'international markets stock, risky',         'stocks',     '28-JAN-08')");

			System.out.println("inserting values into CLOSINGPRICE");

			stmt.executeUpdate("insert into CLOSINGPRICE values ('MM',   '10', '28-JAN-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('MM',   '11', '29-JAN-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('MM',   '12', '30-JAN-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('MM',   '15', '31-JAN-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('MM',   '14', '01-FEB-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('MM',   '15', '02-FEB-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('MM',   '16', '03-FEB-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('RE',   '10', '28-JAN-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('RE',   '12', '29-JAN-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('RE',   '15', '30-JAN-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('RE',   '14', '31-JAN-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('RE',   '16', '01-FEB-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('RE',   '17', '02-FEB-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('RE',   '15', '03-FEB-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('STB',  '10', '28-JAN-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('STB',  '9',  '29-JAN-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('STB',  '10', '30-JAN-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('STB',  '12', '31-JAN-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('STB',  '14', '01-FEB-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('STB',  '10', '02-FEB-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('STB',  '12', '03-FEB-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('LTB',  '10', '28-JAN-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('LTB',  '12', '29-JAN-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('LTB',  '13', '30-JAN-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('LTB',  '15', '31-JAN-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('LTB',  '12', '01-FEB-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('LTB',  '9',  '02-FEB-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('LTB',  '10', '03-FEB-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('BBS',  '10', '28-JAN-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('BBS',  '11', '29-JAN-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('BBS',  '14', '30-JAN-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('BBS',  '18', '31-JAN-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('BBS',  '13', '01-FEB-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('BBS',  '15', '02-FEB-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('BBS',  '16', '03-FEB-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('SRBS', '10', '28-JAN-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('SRBS', '12', '29-JAN-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('SRBS', '12', '30-JAN-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('SRBS', '14', '31-JAN-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('SRBS', '17', '01-FEB-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('SRBS', '20', '02-FEB-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('SRBS', '20', '03-FEB-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('GS',   '10', '28-JAN-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('GS',   '12', '29-JAN-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('GS',   '13', '30-JAN-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('GS',   '15', '31-JAN-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('GS',   '14', '01-FEB-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('GS',   '15', '02-FEB-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('GS',   '12', '03-FEB-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('AS',   '10', '28-JAN-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('AS',   '15', '29-JAN-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('AS',   '14', '30-JAN-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('AS',   '16', '31-JAN-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('AS',   '14', '01-FEB-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('AS',   '17', '02-FEB-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('AS',   '18', '03-FEB-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('IMS',  '10', '28-JAN-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('IMS',  '12', '29-JAN-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('IMS',  '12', '30-JAN-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('IMS',  '14', '31-JAN-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('IMS',  '13', '01-FEB-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('IMS',  '12', '02-FEB-08')");
			stmt.executeUpdate("insert into CLOSINGPRICE values ('IMS',  '11', '03-FEB-08')");

			System.out.println("inserting values into CUSTOMER");

			stmt.executeUpdate("insert into CUSTOMER values ('mike',   'Mike', 'mike@mutualfund.com','1st street','pwd',750)");
			stmt.executeUpdate("insert into CUSTOMER values ('mary',   'Mary', 'mary@mutualfund.com','2nd street','pwd',0)");

			System.out.println("inserting values into ADMINISTRATOR");

			stmt.executeUpdate("insert into ADMINISTRATOR values ('admin',   'Adminstrator', 'admin@mutualfund.com','5th Ave, Pitt','root')");

			System.out.println("inserting values into ALLOCATION");

			stmt.executeUpdate("insert into ALLOCATION (allocation_no, customer_login, p_date) values (ALLOC_SEQ.nextval, 'mike', TO_DATE('29-JAN-08', 'DD-MON-YY'))");
			stmt.executeUpdate("insert into ALLOCATION (allocation_no, customer_login, p_date) values (ALLOC_SEQ.nextval, 'mary', TO_DATE('29-JAN-08', 'DD-MON-YY'))");
			stmt.executeUpdate("insert into ALLOCATION (allocation_no, customer_login, p_date) values (ALLOC_SEQ.nextval, 'mike', TO_DATE('03-FEB-08', 'DD-MON-YY'))");

			System.out.println("inserting values into PREFERS");

			stmt.executeUpdate("insert into PREFERS values (0, 'MM', 0.5)");
			stmt.executeUpdate("insert into PREFERS values (0, 'RE', 0.5)");
			stmt.executeUpdate("insert into PREFERS values (1, 'STB', 0.2)");
			stmt.executeUpdate("insert into PREFERS values (1, 'LTB', 0.4)");
			stmt.executeUpdate("insert into PREFERS values (1, 'BBS', 0.4)");
			stmt.executeUpdate("insert into PREFERS values (2, 'GS', 0.3)");
			stmt.executeUpdate("insert into PREFERS values (2, 'AS', 0.3)");
			stmt.executeUpdate("insert into PREFERS values (2, 'IMS', 0.4)");

			System.out.println("inserting values into TRANSACTION");

			stmt.executeUpdate("insert into TRANSACTION (trans_id, customer_login, t_date, action, amount) values (TRANSAC_SEQ.nextval, 'mike', TO_DATE('29-JAN-08', 'DD-MON-YY'), 'deposit', 1000)");
			stmt.executeUpdate("insert into TRANSACTION values (TRANSAC_SEQ.nextval, 'mike', 'MM', TO_DATE('29-JAN-08', 'DD-MON-YY'), 'buy', 50, 15, 500)");
			stmt.executeUpdate("insert into TRANSACTION values (TRANSAC_SEQ.nextval, 'mike', 'RE', TO_DATE('29-JAN-08', 'DD-MON-YY'), 'buy', 50, 10, 500)");
			stmt.executeUpdate("insert into TRANSACTION values (TRANSAC_SEQ.nextval, 'mike', 'MM', TO_DATE('01-FEB-08', 'DD-MON-YY'), 'sell', 50, 15, 750)");

			System.out.println("inserting values into OWNS");

			stmt.executeUpdate("insert into OWNS values ('mike', 'RE', 50)");

			System.out.println("inserting values into MUTUALDATE");

			stmt.executeUpdate("insert into MUTUALDATE values (TO_DATE('04-FEB-08', 'DD-MON-YY'))");

			System.out.println("creating SELL trigger");

			stmt.executeUpdate("create trigger Sell "+
			    "after insert on TRANSACTION "+
			    "referencing new as newRow "+
			    "for each row "+
			    "declare "+
			        "old_share_amount number; "+
			        "old_balance number; "+
			        "old_date date; "+
			        "stock_price float; "+
			    "begin "+
			        "if :newRow.action='sell' then "+
			            "select c_date into old_date from mutualdate; "+
			            "select balance into old_balance from customer "+
			            "where customer_login = :newRow.customer_login; "+
			            "select price into stock_price from closingprice "+
			            "where symbol = :newRow.symbol and p_date = (old_date - 1); "+
			            "select shares into old_share_amount from "+
			            "owns where customer_login = :newRow.customer_login and symbol = :newRow.symbol; "+

			            "if old_share_amount > :newRow.num_shares then "+
			                "update OWNS "+
			                "set shares = (old_share_amount - :newRow.num_shares) "+
			                "where customer_login = :newRow.customer_login and symbol = :newRow.symbol; "+
			            "else "+
			                "delete from OWNS "+
			                "where customer_login = :newRow.customer_login and "+
			                "symbol = :newRow.symbol; "+
			            "end if; "+
			            "update customer set balance = (old_balance + (:newRow.num_shares * stock_price)) "+
			            "where customer_login = :newRow.customer_login; "+
			        "end if; "+
			    "end;");

			    System.out.println("creating BUY trigger");

			    stmt.executeUpdate("create trigger Buy "+
					"after insert on TRANSACTION "+
					"referencing new as newRow "+
					"for each row "+
					"declare "+
					    "old_balance number; "+
						"old_date date; "+
			            "stock_price float; "+
						"old_share_amount number; "+
						"c number; "+
					"begin "+
						"if :newRow.action='buy' then "+
						    "select c_date into old_date from mutualdate; "+
							"select balance into old_balance from customer "+
							"where customer_login = :newRow.customer_login; "+
							"select price into stock_price from closingprice "+
			                "where symbol = :newRow.symbol and p_date = (old_date-1); "+
							"select count(*) into c from owns where symbol = :newRow.symbol and customer_login=:newRow.customer_login; "+
							"if c = 0 then "+
								"insert into owns values (:newRow.customer_login, :newRow.symbol, :newRow.num_shares); "+
							"else "+
								"select shares into old_share_amount from "+
								"owns where customer_login = :newRow.customer_login and symbol = :newRow.symbol; "+

								"update OWNS set shares = (old_share_amount + :newRow.num_shares) "+
								"where customer_login = :newRow.customer_login and symbol = :newRow.symbol; "+
							"end if; "+
							"update customer set balance = (old_balance - (:newRow.num_shares * stock_price)) "+
			            	"where customer_login = :newRow.customer_login; "+
						"end if; "+
					"end;");

				System.out.println("creating Invest trigger");

				stmt.executeUpdate("create trigger Invest "+
						"after insert on TRANSACTION "+
						"referencing new as newRow "+
						"for each row "+
						"declare "+
							"alloc_num number; "+
							"old_date date; "+
							"stock_price float; "+
							"old_share_amount number; "+
							"currAmount float; "+
							"availShares number; "+
							"final_balance float; "+
							"investAmt float; "+
							"leftover float; "+
						"begin "+
							"if :newRow.action='deposit' then "+
								"final_balance := :newRow.amount; "+
								"leftover := 0; "+
								"update customer set balance=(balance+final_balance) where customer_login =:newRow.customer_login; "+
								"select allocation_no into alloc_num from(select allocation_no from allocation where customer_login = :newRow.customer_login order by allocation_no desc) where rownum=1; "+
								"select c_date into old_date from mutualdate; "+
								"for prefers_rec in (select symbol, percentage from prefers where allocation_no = alloc_num order by percentage DESC) "+
								"loop "+
									"currAmount := (prefers_rec.percentage * :newRow.amount) + leftover; "+
									"if currAmount = (2*leftover) then "+
									    "currAmount := currAmount - leftover; "+
									"end if; "+
									"select price into stock_price from closingprice "+
										"where symbol = prefers_rec.symbol and p_date = (old_date-1); "+
										"availShares := trunc((currAmount / stock_price)); "+
										"final_balance := final_balance - (availShares * stock_price); "+
										"leftover := final_balance; "+
										"investAmt := availShares * stock_price; "+
										"if availShares > 0 then "+
										    "insert into temp_transaction values(TRANSAC_SEQ.nextval, :newRow.customer_login, prefers_rec.symbol, old_date, 'buy', availShares, stock_price, investAmt); "+
										"end if; "+
								"end loop; "+
								"end if; "+

						"end;");

				System.out.println("creating date_change trigger");

				stmt.executeUpdate("create trigger date_change "+
							"after update of c_date on mutualdate "+
							"for each row "+
							"declare "+
							    "curr_date date; "+
							    "buy_sum float; "+
							    "sell_sum float; "+
							    "deposit_sum float; "+
							    "real_balance float; "+
							    "curr_owns number; "+
							    "trans_action varchar(10); "+
							    "r_count number; "+
							"begin "+
							    "buy_sum := 0; "+
							    "sell_sum := 0; "+
							    "deposit_sum := 0; "+
							    "curr_owns := 0; "+
							    "curr_date := :new.c_date; "+
							    "delete from owns; "+
							    "for cust_rec in (select customer_login, balance from customer) "+
							    "loop "+
							        "for trans_rec in (select trans_id, customer_login,symbol,t_date,action,num_shares, price, amount from transaction where customer_login = cust_rec.customer_login and MONTHS_BETWEEN(curr_date,t_date) >= 0) "+
							        "loop "+
							            "trans_action := trans_rec.action; "+
							            "if trans_action = 'buy' then "+
							                "buy_sum := buy_sum + trans_rec.amount; "+
							                "select count(*) into r_count from owns where customer_login = trans_rec.customer_login and symbol=trans_rec.symbol; "+
							                "if r_count > 0 then "+
							                    "select shares into curr_owns from owns where customer_login = trans_rec.customer_login and symbol = trans_rec.symbol; "+
							                    "curr_owns := curr_owns + trans_rec.num_shares; "+
							                    "update owns set shares = curr_owns where customer_login = trans_rec.customer_login and symbol=trans_rec.symbol; "+
							                 "else "+
							                     "insert into owns values (trans_rec.customer_login, trans_rec.symbol, trans_rec.num_shares); "+
							                 "end if; "+
							            "end if; "+
							            "if trans_action = 'sell' then "+
											"sell_sum := sell_sum + trans_rec.amount; "+
											"select count(*) into r_count from owns where customer_login = trans_rec.customer_login and symbol=trans_rec.symbol; "+
											"if r_count > 0 then "+
												"select shares into curr_owns from owns where customer_login = trans_rec.customer_login and symbol = trans_rec.symbol; "+
												"curr_owns := curr_owns - trans_rec.num_shares; "+
												"if curr_owns <= 0 then "+
												    "delete from owns where customer_login = trans_rec.customer_login and symbol = trans_rec.symbol; "+
												"else "+
												    "update owns set shares = curr_owns where customer_login = trans_rec.customer_login and symbol=trans_rec.symbol; "+
												"end if; "+
											 "else "+
											     //there would be an error here - no sell should proceed if we don't own the stock!
												 "curr_owns := 0; "+
							                 "end if; "+
							            "end if; "+
							            "if trans_action = 'deposit' then "+
										"deposit_sum := deposit_sum + trans_rec.amount; "+
							            "end if; "+
							            "real_balance := (deposit_sum + sell_sum) - buy_sum; "+
							            "update customer set balance = real_balance where customer_login = cust_rec.customer_login; "+
							        "end loop; "+
							    "end loop; "+
							"end;");
		}
		catch (Exception e)
		{
			System.out.println(e);
		}

		try{
			con.close();
		}
		catch (Exception e)
		{
			System.out.println(e);
			System.exit(1);
		}

		System.out.println("Connection Closed");
	}
}
