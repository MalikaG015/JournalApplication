package com.journalApp.demo.repository;

import com.journalApp.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


import java.util.List;

public class UserRepositoryImpl {
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    //I want list of all users whose email existe and they have opted for sentiment analysis-critera+query
    public List<User> getUsersForSA(){
        Query query=new Query();
//        query.addCriteria(Criteria.where("userName").is("Ram"));
//        query.addCriteria(Criteria.where("email").exists(true));
//        query.addCriteria(Criteria.where("SentimentAnalysis").is(true));
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("email").ne(null).ne(""),
                Criteria.where("email").exists(true),
                Criteria.where("sentimentAnalysis").is(true)
        );

        query.addCriteria(criteria);
        return mongoTemplate.find(query,User.class);
    }
}
