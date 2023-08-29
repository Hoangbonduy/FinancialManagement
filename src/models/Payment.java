package models;

public class Payment {
	String namepayment, datepayment, typepayment, notepayment;
	Long moneypayment,idpayment;

	public Payment(Long idpayment,String namepayment, Long moneypayment, String datepayment, String typepayment,String notepayment) {
		super();
		this.idpayment = idpayment;
		this.namepayment = namepayment;
		this.moneypayment = moneypayment;
		this.datepayment = datepayment;
		this.typepayment = typepayment;
		this.notepayment = notepayment;
	}

	public Long getIdpayment() {
		return idpayment;
	}

	public void setIdpayment(Long idpayment) {
		this.idpayment = idpayment;
	}

	public String getNamepayment() {
		return namepayment;
	}

	public void setNamepayment(String namepayment) {
		this.namepayment = namepayment;
	}

	public Long getMoneypayment() {
		return moneypayment;
	}

	public void setMoneypayment(Long moneypayment) {
		this.moneypayment = moneypayment;
	}

	public String getDatepayment() {
		return datepayment;
	}

	public void setDatepayment(String datepayment) {
		this.datepayment = datepayment;
	}

	public String getTypepayment() {
		return typepayment;
	}

	public void setTypepayment(String typepayment) {
		this.typepayment = typepayment;
	}

	public String getNotepayment() {
		return notepayment;
	}

	public void setNotepayment(String notepayment) {
		this.notepayment = notepayment;
	}
    
	
}
