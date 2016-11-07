package com.cjon.book.service;

import com.cjon.book.dao.BookDAO;

public class BookService {

	// 리스트를 가져오는 일을 하는 method
	public String getList(String keyword) {
		// 일반적인 로직처리 나와요!!
		
		// 추가적으로 DB처리가 나올 수 있어요!
		// DB처리는 Service에서 처리는하는게 아니라..다른 객체를 이용해서
		// Database처리를 하게 되죠!!
		BookDAO dao = new BookDAO();
		String result = dao.select(keyword);	
		
		return result;
	}

	public String updateBook(String isbn, String price, String title, String author) {
		// TODO Auto-generated method stub
		BookDAO dao = new BookDAO();
		String result = dao.update(isbn,price,title,author);	
		return result;
	}

	public Boolean insertBook(String isbn, String imgbase64, String title, String author, String price ) {
		// TODO Auto-generated method stub
		
		BookDAO dao = new BookDAO();
		boolean result = dao.insert(isbn,imgbase64,title, author, price);
		
		return result;
	}

	public Boolean deleteBook(String isbn) {
		// TODO Auto-generated method stub
		BookDAO dao = new BookDAO();
		boolean result = dao.delete(isbn);
		
		return result;
	}

	public String getInfoList(String isbn) {
		BookDAO dao = new BookDAO();
		String result = dao.infoSelect(isbn);
		
		return result;
	}

	public Boolean insertMember(String id, String password, String email) {
		// TODO Auto-generated method stub
		
		BookDAO dao = new BookDAO();
		boolean result = dao.insertmember(id,password,email);
		
		return result;
		
	}

	public boolean loginMember(String id, String password) {
		BookDAO dao = new BookDAO();
		boolean result = dao.loginmember(id,password);
		
		return result;
	}

	public boolean shareBook(String isbn, String id) {
		BookDAO dao = new BookDAO();
		boolean result = dao.shareBook(isbn,id);
		
		return result;
	}

	public Boolean returnBook(String isbn, String id) {
		BookDAO dao = new BookDAO();
		boolean result = dao.returnBook(isbn,id);
		
		return result;
	}

	public Boolean insertComment(String isbn, String id, String comment) {
		BookDAO dao = new BookDAO();
		boolean result = dao.insertComment(isbn,id,comment);
		
		return result;
	}

	public String getcommentList(String isbn) {
		BookDAO dao = new BookDAO();
		String result = dao.commentSelect(isbn);
		
		return result;
	}

	
	public String mycommentList(String isbn,String id) {
		BookDAO dao = new BookDAO();
		String result = dao.mycommentList(isbn, id);
		
		return result;
	}

	public Boolean deleteComment(String num) {
		BookDAO dao = new BookDAO();
		boolean result = dao.deleteComment(num);
		
		return result;
	}

	public String KeywordList(String keyword) {
		BookDAO dao = new BookDAO();
		String result = dao.keywordList(keyword);	
		
		return result;
		
	}

}












