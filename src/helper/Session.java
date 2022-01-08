//package helper;
//
//import model.User;
//
//public class Session {
//
//	private static User loginUser;
//	private static Session session;
//	
//	private Session() {
//		// TODO Auto-generated constructor stub
//	}
//	
//	public void login(User user) {
//		loginUser = user;
//	}
//	
//	public User getUser() {
//		return loginUser;
//	}
//	
//	public void logout() {
//		loginUser = null;
//	}
//
//	public static Session getSession() {
//		if(session == null) session = new Session();
//		return session;
//	}
//}
