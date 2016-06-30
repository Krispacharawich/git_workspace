package com.krisp.springmvc.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.krisp.springmvc.model.User;

@Service("userService")
public class UserServiceImpl implements UserService{

	private static final AtomicLong counter = new AtomicLong();
	private static List<User> users;
	
	static{
		users = populateDummyUsers();
	}
	public User findById(long id) {
		// TODO Auto-generated method stub
		for(User user: users){
			if(user.getId() == id){
				return user;
			}
		}
		return null;
	}

	public User findByName(String name) {
		// TODO Auto-generated method stub
		for(User user: users){
			if(user.getUsername().equalsIgnoreCase(name)){
				return user;
			}
		}
		return null;
	}

	public void saveUser(User user) {
		// TODO Auto-generated method stub
		user.setId(counter.incrementAndGet());
		users.add(user);
	}

	public void updateUser(User user) {
		// TODO Auto-generated method stub
		int index = users.indexOf(user);
		users.set(index, user);
	}

	public void deleteUserById(long id) {
		// TODO Auto-generated method stub
		for(Iterator<User> iterator = users.iterator();iterator.hasNext();)
		{
			User user = iterator.next();
			if(user.getId() == id){
				iterator.remove();
			}
		}
	}

	public List<User> findAllUsers() {
		// TODO Auto-generated method stub
		return users;
	}

	public void deleteAllUsers() {
		// TODO Auto-generated method stub
		users.clear();
	}

	public boolean isUserExist(User user) {
		// TODO Auto-generated method stub
		return findByName(user.getUsername()) != null;
	}

	private static List<User> populateDummyUsers(){
		List<User> users = new ArrayList<User>();
        users.add(new User(counter.incrementAndGet(),"Sam", "NY", "sam@abc.com"));
        users.add(new User(counter.incrementAndGet(),"Tomy", "ALBAMA", "tomy@abc.com"));
        users.add(new User(counter.incrementAndGet(),"Kelly", "NEBRASKA", "kelly@abc.com"));
        return users;
	}
}
