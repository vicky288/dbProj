package com.functionalities.fines;

public class FineResult {
	int loan_id;
	float fine_amount;
	public int getLoan_id() {
		return loan_id;
	}
	public void setLoan_id(int loan_id) {
		this.loan_id = loan_id;
	}
	public float getFine_amount() {
		return fine_amount;
	}
	public void setFine_amount(float fine_amount) {
		this.fine_amount = fine_amount;
	}
}
