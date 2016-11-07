package com.cjon.book.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.cjon.book.common.DBTemplate;

public class BookDAO {

	public String select(String keyword) {
		// Database처리가 나와요!
		// 일반적으로 Database처리를 쉽게 하기 위해서
		// Tomcat같은 경우는 DBCP라는걸 제공해 줘요!
		// 추가적으로 간단한 라이브러리를 이용해서 DB처리를 해 볼꺼예요!!
		// 1. Driver Loading ( 설정에 있네.. )
		// 2. Connection 생성
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		try {
			String sql = "select bisbn, bimgbase64, btitle, bauthor, bprice from book where btitle like ?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			rs = pstmt.executeQuery();
			JSONArray arr = new JSONArray();
			while(rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("isbn", rs.getString("bisbn"));
				obj.put("imgbase64", rs.getString("bimgbase64"));
				obj.put("title", rs.getString("btitle"));
				obj.put("author", rs.getString("bauthor"));
				obj.put("price", rs.getString("bprice"));
				arr.add(obj);
			}
			result = arr.toJSONString();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(rs);
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
	}

	public String update(String isbn, String price, String title, String author) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		
		String result = null;
		try {
			System.out.println(isbn);
			System.out.println(price);
			String sql = "update book set bprice=?,btitle=?,bauthor=? where bisbn=?";
			pstmt= con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(price));
			pstmt.setString(2, title);
			pstmt.setString(3, author);
			pstmt.setString(4, isbn);
			
			int count = pstmt.executeUpdate();
			// 결과값은 영향을 받은 레코드의 수
			if( count == 1 ) {
				
				// 정상처리이기 때문에 commit
				DBTemplate.commit(con);
				
				String sql2 = "select bisbn, bimgurl, btitle, bauthor, bprice " + "from book where bisbn = ?";

				PreparedStatement pstmt2 = null;
				ResultSet rs = null;

				pstmt2 = con.prepareStatement(sql2);
				pstmt2.setString(1, isbn);
				rs = pstmt2.executeQuery();

				rs.next();
				JSONObject obj = new JSONObject();
				obj.put("isbn", rs.getString("bisbn"));
				obj.put("img", rs.getString("bimgurl"));
				obj.put("title", rs.getString("btitle"));
				obj.put("author", rs.getString("bauthor"));
				obj.put("price", rs.getString("bprice"));

				result = obj.toJSONString();
				
				DBTemplate.close(rs);
				DBTemplate.close(pstmt2);

				
			} else {
				DBTemplate.rollback(con);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
	}

	public boolean insert(String isbn, String imgbase64, String title, String author, String price) {
		// TODO Auto-generated method stub
		
		
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		
		boolean result = false;
		try {
			
			System.out.println(price);
			
			String sql = "insert into book (bimgbase64,btitle,bauthor,bprice,bisbn,buser) value (?,?,?,?,?,?)";
			pstmt= con.prepareStatement(sql);
			
			pstmt.setString(1, imgbase64);
			pstmt.setString(2, title);
			pstmt.setString(3, author);
			pstmt.setInt(4, Integer.parseInt(price));
			pstmt.setString(5, isbn);
		
	
			int count = pstmt.executeUpdate();
			// 결과값은 영향을 받은 레코드의 수
			if( count == 1 ) {
				result = true;
				// 정상처리이기 때문에 commit
				DBTemplate.commit(con);
			} else {
				DBTemplate.rollback(con);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		
		return result;
	}

	public boolean delete(String isbn) {
		// TODO Auto-generated method stub
		
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		
		boolean result = false;
		try {
			
			System.out.println(isbn);
			
			String sql = "delete from book where bisbn=?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, isbn);
			
			int count = pstmt.executeUpdate();
			// 결과값은 영향을 받은 레코드의 수
			if( count == 1 ) {
				result = true;
				// 정상처리이기 때문에 commit
				DBTemplate.commit(con);
			} else {
				DBTemplate.rollback(con);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		
		return result;
	}

	public String infoSelect(String isbn) {
		// TODO Auto-generated method stub

		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		try {
			String sql = "select bdate, bpage, bsupplement, bpublisher from book where bisbn=?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, isbn);
			rs = pstmt.executeQuery();
			JSONObject obj = null;
			while(rs.next()) {
				obj = new JSONObject();
				obj.put("date", rs.getString("bdate"));
				obj.put("page", rs.getString("bpage"));
				obj.put("supplement", rs.getString("bsupplement"));
				obj.put("publisher", rs.getString("bpublisher"));
				
				
			}
			System.out.println("여긴 DAO");
			result = obj.toJSONString();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(rs);
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
		
	}

	public boolean insertmember(String id, String password, String email) {
		
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		
		boolean result = false;
		try {
			
			System.out.println(id);
			
			String sql = "insert into member (mid,memail,mpassword) value (?,?,?)";
			pstmt= con.prepareStatement(sql);
			
			pstmt.setString(1, id);
			pstmt.setString(2, email);
			pstmt.setString(3, password);
			
			int count = pstmt.executeUpdate();
			// 결과값은 영향을 받은 레코드의 수
			if( count == 1 ) {
				result = true;
				// 정상처리이기 때문에 commit
				DBTemplate.commit(con);
			} else {
				DBTemplate.rollback(con);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		
		return result;
		
		
	}

	public boolean loginmember(String id, String password) {
		
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;
		
		try {
			String sql = "select mpassword,memail from member where mid=?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			rs.next();
			String passcheck = rs.getString("mpassword");
			System.out.println(passcheck);
			JSONObject obj = null;
			
			if(password.equals(passcheck)==true){
				
				System.out.println("check true");
				 result= true;
			}else{
				System.out.println("check false");
				result= false;
			}
		
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(rs);
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
	}

	public boolean shareBook(String isbn, String id) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;
		
		try {
			
			String sql = "select bshare from book where bisbn=?"; // 선택한 도서 대출가능여부 확인
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1,isbn);
			rs = pstmt.executeQuery();
			rs.next();
			String ableshare = rs.getString("bshare");
			System.out.println(ableshare);
			JSONObject obj = null;
			
			if(ableshare.equals("0")==true){
				
				System.out.println("0 : 대여가능");
				//rs=null;
				pstmt=null;
				
				String sql2 = "update book set bshare=?,buser=? where bisbn=?"; // 선택한 도서 대출가능여부 확인
				pstmt= con.prepareStatement(sql2);
				pstmt.setString(1,"1");
				pstmt.setString(2,id);
				pstmt.setString(3,isbn);
				int count = pstmt.executeUpdate();
				System.out.println(count);
				
				DBTemplate.commit(con);
				result= true;
				 
			}else{
				
				System.out.println("대여불가");
				DBTemplate.rollback(con);
				result= false;
			}
		
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(rs);
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;

	}

	public boolean returnBook(String isbn, String id) {
		
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;
		
		try {
				
				String sql = "update book set bshare=?,buser=? where bisbn=?"; // 선택한 도서 대출가능여부 확인
				pstmt= con.prepareStatement(sql);
				pstmt.setString(1,"0");
				pstmt.setString(2,"");
				pstmt.setString(3,isbn);
				int count = pstmt.executeUpdate();
				System.out.println(count);
				DBTemplate.commit(con);
				result= true;
				 
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(rs);
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
	}

	public boolean insertComment(String isbn, String id, String comment) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		
		boolean result = false;
		try {
		
			System.out.println("insert isbn dAO : " + isbn);
			
			String sql = "insert into comment (bisbn,bid,bcomment) value (?,?,?)";
			pstmt= con.prepareStatement(sql);
			
			pstmt.setString(1, isbn);
			pstmt.setString(2, id);
			pstmt.setString(3, comment);
			
			int count = pstmt.executeUpdate();
			// 결과값은 영향을 받은 레코드의 수
			if( count == 1 ) {
				
				result = true;
				// 정상처리이기 때문에 commit
				DBTemplate.commit(con);
			} else {
				DBTemplate.rollback(con);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		
		return result;
	}

	public String commentSelect(String isbn) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		try {
			String sql = "select bid, bcomment from comment where bisbn=?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, isbn);
			rs = pstmt.executeQuery();
			JSONArray arr = new JSONArray();
			result = arr.toJSONString();
			while(rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("id", rs.getString("bid"));
				obj.put("comment", rs.getString("bcomment"));
				arr.add(obj);
			}
			System.out.println("여긴 comment DAO");
			result = arr.toJSONString();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(rs);
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
		
	}

	public String mycommentList(String isbn, String id) {
		
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		try {
			String sql = "select c.seq,c.bisbn, c.bcomment,b.btitle,b.bauthor from comment c inner join book b where (c.bisbn=b.bisbn) and bid=?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			JSONArray arr = new JSONArray();
			result = arr.toJSONString();
			while(rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("title", rs.getString("b.btitle"));
				obj.put("comment", rs.getString("c.bcomment"));
				obj.put("author", rs.getString("b.bauthor"));
				obj.put("isbn", rs.getString("c.bisbn"));
				obj.put("num", rs.getString("c.seq"));
				
				arr.add(obj);
			}
			System.out.println("여긴 My comment view DAO");
			result = arr.toJSONString();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(rs);
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
	}

	public boolean deleteComment(String num) {

		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		
		boolean result = false;
		try {
			
			System.out.println(num);
			
			String sql = "delete from comment where seq=?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, num);
		
			int count = pstmt.executeUpdate();
			// 결과값은 영향을 받은 레코드의 수
			if( count == 1 ) {
				result = true;
				// 정상처리이기 때문에 commit
				DBTemplate.commit(con);
			} else {
				DBTemplate.rollback(con);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		
		return result;
	}

	public String keywordList(String keyword) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		try{
			String sql = "select b.btitle,c.bid,c.bcomment from comment c inner join book b where (c.bisbn=b.bisbn) and bcomment like ?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			rs = pstmt.executeQuery();
			JSONArray arr = new JSONArray();
			while(rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("title", rs.getString("b.btitle"));
				obj.put("id", rs.getString("c.bid"));
				obj.put("comment", rs.getString("c.bcomment"));
				arr.add(obj);
			}
			result = arr.toJSONString();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(rs);
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
	}

}


