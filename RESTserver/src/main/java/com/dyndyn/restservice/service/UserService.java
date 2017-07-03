package com.dyndyn.restservice.service;

import com.dyndyn.model.User;
import com.dyndyn.restservice.repository.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.util.List;

import static org.apache.commons.codec.binary.StringUtils.getBytesUtf8;

/**
 * The UserService class is used to hold business
 * logic for working with the UserRepository.
 *
 * @author Roman Dyndyn
 */
@Service
public class UserService {

    @Value("${security.messageDigestAlgorithm}")
    private String messageDigestAlgorithm;

    private UserRepository userRepository;
    private HexBinaryAdapter hexBinaryAdapter;

    @Autowired
    public UserService(UserRepository userRepository, HexBinaryAdapter hexBinaryAdapter) {
        this.userRepository = userRepository;
        this.hexBinaryAdapter = hexBinaryAdapter;
    }

    public void add(User user) {
        user.setPassword(encodePassword(user.getPassword()));
        userRepository.add(user);
    }

    public User getById(int id) {
        return userRepository.getById(id);
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }

    public void update(User user) {
        userRepository.update(user);
    }

    public void remove(int userId) {
        userRepository.remove(userId);
    }

    public User getByEmail(String email){
        try {
            return userRepository.getByEmail(email);
        } catch (NoResultException e){
            return null;
        }
    }

    public String encodePassword(String password) {
        return hexBinaryAdapter.marshal(DigestUtils.getDigest(messageDigestAlgorithm).digest(getBytesUtf8(password)));
    }
}
