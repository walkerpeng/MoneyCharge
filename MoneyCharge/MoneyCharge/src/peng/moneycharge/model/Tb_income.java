/**
 * 
 */
package peng.moneycharge.model;

/**
 * @author cwpcc
 * 
 */
public class Tb_income {
	// _id INTEGER NOT NULL PRIMARY KEY,no not null integer ,money decimal,time
	// varchar(10),
	// type integer ,handler varchar(100),mark varchar(200))
	private int _id;// 存储用户id
	private int no;// 存储收入编号
	private double money;// 存储收入金额
	private String time;// 存储收入时间
	private int type;// 存储收入类别id
	private String handler;// 存储收入地址
	private String mark;// 存储收入备注

	public Tb_income(int id, int no, double money, String time, int type,
			String handler, String mark) {
		// TODO Auto-generated constructor stub

		super();
		this._id = id;// 为用户id
		this.no = no;// 为收入编号赋值
		this.money = money;// 为收入金额赋值
		this.time = time;// 为收入时间赋值
		this.type = type;// 为收入类别赋值
		this.handler = handler;// 为收入付款方赋值
		this.mark = mark;// 为收入备注赋值
	}

	public Tb_income() {
		super();
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

}
