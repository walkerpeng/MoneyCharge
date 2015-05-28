package peng.moneycharge.model;

public class Tb_pay {
	// _id INTEGER NOT NULL PRIMARY KEY,no not null integer AUTOINCREMENT ,money
	// decimal,time varchar(10),"
	// + "type integer ,address varchar(100),mark varchar(200))

	private int _id;
	private int no;// 存储支出编号
	private double money;// 存储支出金额
	private String time;// 存储支出时间
	private int type;// 存储支出类别
	private String address;// 存储支出地点
	private String mark;// 存储支出备注

	public Tb_pay() {
		// TODO Auto-generated constructor stub
		super();
	}

	public Tb_pay(int id, int no, double money, String time, int type,
			String address, String mark) {
		super();
		this._id = id;
		this.no = no;// 为支出编号赋值
		this.money = money;// 为支出金额赋值
		this.time = time;// 为支出时间赋值
		this.type = type;// 为支出类别赋值
		this.address = address;// 为支出地点赋值
		this.mark = mark;// 为支出备注赋值

	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getNo() {
		return no;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

}
